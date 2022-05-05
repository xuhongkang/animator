package cs3500.animator.view;

import cs3500.animator.controller.AnimationControllerFeatures;
import cs3500.animator.model.AnimatorModelState;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;

/**
 * Represents a view of the model with interactive features. Features provided are the ability to
 * play, pause, speed-up, slow-down, and loop the animation.
 */
public class InteractiveAnimatorView extends JFrame implements SimpleAnimatorView, ActionListener {

  private final Map<String, Runnable> buttonClickedActions;
  private final AnimatorPanel aniPanel;
  private final JTextField inputTextBox;
  private final TreeSet<Integer> discreteTime;
  private boolean isDiscrete;

  /**
   * Constructor for an interactiveAnimatorView.
   * @param model the read-only model to build a view from.
   * @throws IllegalArgumentException if the model is null
   */
  public InteractiveAnimatorView(AnimatorModelState model) {
    if (model == null) {
      throw new IllegalArgumentException("model can't be null");
    }
    buttonClickedActions = new HashMap<>();
    this.isDiscrete = false;

    this.setTitle("Interactive Animation!");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.setSize(model.getWidthOfAnimation(), model.getHeightOfAnimation());

    //the panel for the animation
    this.aniPanel = new AnimatorPanel(model);
    aniPanel.setPreferredSize(
        new Dimension(model.getWidthOfAnimation(), model.getHeightOfAnimation()));
    this.discreteTime = this.aniPanel.getDiscreteTime();
    JScrollPane jsp = new JScrollPane(aniPanel);
    jsp.setPreferredSize( new Dimension(model.getWidthOfAnimation(), model.getHeightOfAnimation()));
    this.add(jsp, BorderLayout.CENTER);

    //the panel at the top for buttons
    JPanel topButtonPanel = new JPanel();
    topButtonPanel.setLayout(new FlowLayout());

    //buttons
    JButton pauseButton = new JButton("Pause");
    pauseButton.addActionListener(this);
    pauseButton.setActionCommand("pause button");
    topButtonPanel.add(pauseButton);

    JButton playButton = new JButton("Play");
    playButton.addActionListener(this);
    playButton.setActionCommand("play button");
    topButtonPanel.add(playButton);

    /*
    loopButton = new JButton("Toggle Loop");
    loopButton.addActionListener(this);
    loopButton.setActionCommand("loop button");
    topButtonPanel.add(loopButton);
    */
    JCheckBox restartBox = new JCheckBox("Loop");
    restartBox.addActionListener(this);
    restartBox.setActionCommand("loop box");
    topButtonPanel.add(restartBox);

    JCheckBox outlineBox = new JCheckBox("Outline");
    outlineBox.addActionListener(this);
    outlineBox.setActionCommand("outline box");
    topButtonPanel.add(outlineBox);

    JCheckBox discreteBox = new JCheckBox("Discrete");
    discreteBox.addActionListener(this);
    discreteBox.setActionCommand("discrete box");
    topButtonPanel.add(discreteBox);

    //private final JButton loopButton;
    JButton restartButton = new JButton("Restart");
    restartButton.addActionListener(this);
    restartButton.setActionCommand("restart button");
    topButtonPanel.add(restartButton);

    JButton incSpeedButton = new JButton("Increase speed");
    incSpeedButton.addActionListener(this);
    incSpeedButton.setActionCommand("speed up");
    topButtonPanel.add(incSpeedButton);

    JButton decSpeedButton = new JButton("Decrease speed");
    decSpeedButton.addActionListener(this);
    decSpeedButton.setActionCommand("slow down");
    topButtonPanel.add(decSpeedButton);

    //input text box
    JPanel fpsControl = new JPanel();

    fpsControl.add(new JLabel("Add Shape/Motion: "));
    this.inputTextBox = new JTextField();
    inputTextBox.setColumns(20);
    inputTextBox.setText("");
    inputTextBox.setActionCommand("add new");
    inputTextBox.addActionListener(this);
    fpsControl.add(inputTextBox);
    topButtonPanel.add(fpsControl);

    JButton addCommandButton = new JButton("Add");
    addCommandButton.addActionListener(this);
    addCommandButton.setActionCommand("add command");
    topButtonPanel.add(addCommandButton);

    this.add(topButtonPanel, BorderLayout.NORTH);
  }

  public String getNewCommand() {
    return this.inputTextBox.getText();
  }

  @Override
  public int getTempo() {
    return this.aniPanel.getTempoAt();
  }

  @Override
  public void setNewCommand() {
    this.aniPanel.loadNewCommand();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void refresh(int tick) {
    System.out.println("Tick: " + tick);
    this.aniPanel.setRegenShapes();
    if (isDiscrete) {
      if (!this.discreteTime.contains(tick)) {
        this.aniPanel.setTick(this.discreteTime.ceiling(tick));
      } else {
        if (this.discreteTime.ceiling(tick + 1) != null) {
          this.aniPanel.setTick(this.discreteTime.ceiling(tick + 1));
        }
      }
    } else {
      this.aniPanel.setTick(tick);
    }
    this.repaint();
  }

  @Override
  public void setCallbacks(AnimationControllerFeatures controller) {
    buttonClickedActions.put("pause button", controller::pauseAnimation);
    buttonClickedActions.put("play button", controller::resumeAnimation);
    buttonClickedActions.put("loop box", controller::toggleLooping);
    buttonClickedActions.put("restart button", controller::goToBeginning);
    buttonClickedActions.put("speed up", controller::increaseSpeed);
    buttonClickedActions.put("slow down", controller::decreaseSpeed);
    buttonClickedActions.put("add command", controller::addNewCommand);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    //System.out.println(e.getActionCommand());
    if (buttonClickedActions.containsKey(e.getActionCommand())) {
      //System.out.println(e.getActionCommand());
      buttonClickedActions.get(e.getActionCommand()).run();
    } else if (e.getActionCommand().equals("outline box")) {
      this.aniPanel.toggleFillMode();
    } else if (e.getActionCommand().equals("discrete box")) {
      this.isDiscrete = !isDiscrete;
    }
  }
}
