package cs3500.animator.view;

import cs3500.animator.controller.AnimationControllerFeatures;
import cs3500.animator.model.AnimatorModelState;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * Represents a graphical view of an animation (part of the MVC pattern). Utilizes the Java Swing
 * library for creating a window and drawing shapes in it. Has a read-only link to the model to
 * query about the animation state.
 */
public class AnimatorGraphicsView extends JFrame implements SimpleAnimatorView {

  //private final AnimatorModelState model; //todo uncomment when needed
  private final AnimatorPanel panel;

  /**
   * Constructor for an AnimatorGraphicsView. Builds upon the JFrame implementation.
   * @param model the model to show a graphical representation of.
   * @throws IllegalArgumentException if the model is null.
   */
  public AnimatorGraphicsView(AnimatorModelState model) {
    super();
    if (model == null) {
      throw new IllegalArgumentException("model can't be null");
    }

    //this.model = model; //todo uncomment when needed

    this.setTitle("Animation");
    this.setSize(model.getWidthOfAnimation(), model.getHeightOfAnimation());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    //use a borderlayout with drawing panel in center and button panel in south
    this.setLayout(new BorderLayout());
    panel = new AnimatorPanel(model);
    panel.setPreferredSize(
        new Dimension(model.getWidthOfAnimation() + 100, model.getHeightOfAnimation() + 100));
    JScrollPane scrollPanel = new JScrollPane(panel);
    //scrollPanel.setAutoscrolls(true);
    //todo fix the above
    this.add(scrollPanel, BorderLayout.CENTER);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void setCallbacks(AnimationControllerFeatures controller) {
    //this view doesn't support callbacks, so this function does nothing
  }

  @Override
  public String getNewCommand() {
    //this view doesn't support new commands, so this function does nothing
    return null;
  }

  @Override
  public int getTempo() {
    //this view doesn't support slow-mo, so this function does nothing
    return -1;
  }

  @Override
  public void setNewCommand() {
    //this view doesn't support new commands, so this function does nothing
  }

  @Override
  public void refresh(int tick) {
    //tell the panel it needs to query the model for the shapes again
    this.panel.setRegenShapes();
    //this.repaint(tick, 0, 0, model.getWidthOfAnimation(), model.getHeightOfAnimation());
    this.panel.setTick(tick);
    this.panel.repaint();
  }

}
