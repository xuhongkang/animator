package cs3500.animator.view;

import java.io.IOException;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.Shape;
import cs3500.animator.model.ShapeState;

/**
 *
 */
public class AnimatorViewImpl implements AnimatorView {
  private AnimatorModel<Shape, ShapeState> model;
  private Appendable output;

  /**
   * Alternative constructor for Animator View Implementation.
   * @param model
   */
  public AnimatorViewImpl(AnimatorModel<Shape, ShapeState> model) {
    this(model, System.out);
  }

  /**
   * Constructor for Animator View Implementation.
   * @param model
   * @param output
   */
  public AnimatorViewImpl(AnimatorModel<Shape, ShapeState> model, Appendable output) {
    if (model == null) {
      throw new IllegalArgumentException("Invalid Model, Model is Null.");
    }
    if (output == null) {
      throw new IllegalArgumentException("Invalid Output, Output is Null.");
    }
    this.model = model;
    this.output = output;
  }

  private String legend() {
    return "# t == tick\n" +
            "# (x,y) == position\n" +
            "# (w,h) == dimensions\n" +
            "# (r,g,b) == color (with values between 0 and 255)\n" +
            "#                    start                               end\n" +
            "#        -----------------------------     -----------------------------\n" +
            "#        t   x   y   w   h   r   g   b     t   x   y   w   h   r   g   b";
  }

  @Override
  public void viewMessage(String message) throws IOException {
    this.output.append(message);
  }

  @Override
  public void viewState() {

  }
}
