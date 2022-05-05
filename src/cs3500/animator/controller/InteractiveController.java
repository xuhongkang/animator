package cs3500.animator.controller;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.SimpleAnimatorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Timer;

/**
 * A more complex controller that has features that can be given to a view to allow interactivity.
 * Supports basic animation playing, along with controls for speed, start/stop, and looping.
 */
public class InteractiveController implements AnimatorController, AnimationControllerFeatures {

  //private final AnimatorModel model; //todo undo this when needed
  private final SimpleAnimatorView view;
  private AnimatorModel model;
  private int tps;
  private final int[] currTick = {0}; //the current tick we're at
  //private final ActionListener animationPlayer; //todo undo this when needed
  private Timer apTimer; //animation player timer.
  private boolean isAnimationLooping;
  private ActionListener animationPlayer;
  private boolean inSlowMo;

  /**
   * Constructor for interactive controller that takes in model, view, and speed (ticks per second).
   *
   * @param model the model to control
   * @param view  the view to control
   * @param tps   the initial speed of the animation (in ticks per second)
   */
  public InteractiveController(AnimatorModel model, SimpleAnimatorView view, int tps) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("model and view cannot be null");
    } else if (tps < 1) {
      throw new IllegalArgumentException("tps cannot be less than 1");
    }
    this.model = model;
    this.view = view;
    this.tps = tps;
    this.isAnimationLooping = false;
    this.animationPlayer = new CActionListener();
    //the timer that calls the animation
    this.apTimer = new Timer(1000 / tps, animationPlayer);
    this.inSlowMo = false;
  }

  @Override
  public void startSlowMo(int tempo) {
    this.inSlowMo = true;
    this.apTimer.stop();
    if (tempo > 0) {
      this.apTimer = new Timer(1000 / tempo, animationPlayer);
    }
    this.apTimer.start();
  }

  @Override
  public void resumeNormalSpeed() {
    this.inSlowMo = false;
    this.apTimer.stop();
    this.apTimer = new Timer(1000 / this.tps, animationPlayer);
    this.apTimer.start();
  }

  @Override
  public void increaseSpeed() {
    this.setAnimationSpeed(tps + 10);
  }

  @Override
  public void decreaseSpeed() {
    this.setAnimationSpeed(tps - 10);
  }

  @Override
  public void setAnimationSpeed(int tps) {
    //quick exit for invalid inputs
    if (tps < 1) {
      //if tps less than possible, set to lowest possible value
      tps = 1;
    }
    //set the timer to the new delay
    this.tps = tps;
    apTimer.setDelay(1000 / this.tps);
    //System.out.println(tps);
  }

  @Override
  public void resumeAnimation() {
    if (!apTimer.isRunning()) {
      apTimer.start();
    }
  }

  @Override
  public void pauseAnimation() {
    if (apTimer.isRunning()) {
      apTimer.stop();
    }
  }

  @Override
  public void goToBeginning() {
    currTick[0] = 0;
    view.refresh(currTick[0]);
    this.resumeAnimation();
  }

  @Override
  public void toggleLooping() {
    this.isAnimationLooping = !isAnimationLooping;
  }

  @Override
  public void addNewCommand() {
    try {
      String command = this.view.getNewCommand();
      FileWriter fw = new FileWriter("TemporaryCommands.txt");
      fw.write(command);
      fw.close();
      this.view.setNewCommand();
    } catch (IOException ioe) {
      throw new IllegalArgumentException("Temporary File IO Error");
    }
  }

  //the action listener that plays the animation and refreshes the view
  class CActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      int tempo = view.getTempo();
      if (tempo != -1 && !inSlowMo) {
        startSlowMo(tempo);
      } else if (inSlowMo) {
        resumeNormalSpeed();
      }
      System.out.println("Tempo: " + tempo);
      view.refresh(currTick[0]);
      currTick[0] += 1;
      if (currTick[0] > model.getAnimationDuration() && isAnimationLooping) {
        currTick[0] = 0;
      } else if (currTick[0] > model.getAnimationDuration()) {
        apTimer.stop();
      }
    }
  }

  @Override
  public void playAnimation() {
    //need to pass callback stuff to view
    view.setCallbacks(this);
    //view makeVisible
    view.makeVisible();
    //start the timer
    apTimer.setInitialDelay(0);
    apTimer.start();
    //System.out.println("timer started");

    //do we need an infinite loop here?
  }
}
