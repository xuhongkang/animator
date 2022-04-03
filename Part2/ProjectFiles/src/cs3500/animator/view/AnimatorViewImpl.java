package cs3500.animator.view;

import java.io.IOException;
import java.util.Set;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.ShapeProperty;

/**
 * Basic View Implementation for animator.
 */
public class AnimatorViewImpl implements AnimatorView {
  private AnimatorModel model;
  private Appendable output;

  /**
   * Alternative constructor for Animator View Implementation.
   * @param model is the animator model
   */
  public AnimatorViewImpl(AnimatorModel model) {
    this(model.build(), System.out);
  }

  /**
   * Constructor for Animator View Implementation.
   * @param model is the animator model
   * @param output is the appendable output
   */
  public AnimatorViewImpl(AnimatorModel model, Appendable output) {
    if (model == null) {
      throw new IllegalArgumentException("Invalid Model, Model is Null.");
    }
    if (output == null) {
      throw new IllegalArgumentException("Invalid Output, Output is Null.");
    }
    this.model = model;
    this.output = output;
  }

  @Override
  public void viewMessage(String message) throws IOException {
    this.output.append(message + "\n");
  }

  @Override
  public void viewState() throws IOException {
    String legend = "# [LEGEND]\n" +
                    "# t == tick\n" +
                    "# (x,y) == position\n" +
                    "# (w,h) == dimensions\n" +
                    "# (r,g,b) == color (with values between 0 and 255)\n";
    Set<String> tags = this.model.getTags();
    if (tags.isEmpty()) {
      viewMessage("No Shapes or Motions Initialized.");
    } else {
      this.viewMessage(legend);
      for (String t: tags) {
        ShapeProperty sp = this.model.getShapeProperty(t);
        this.viewMessage(String.format("shape %s %s", sp.getShape().toString(), t));
        String motionMessage = String.format("motion %s   ", t);
        String motionMessageIndent = " ".repeat(motionMessage.length());
        this.viewMessage(sp.toString(motionMessageIndent));
      }
    }
  }
}
