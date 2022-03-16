package cs3500.animator.view;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.Shape;

/**
 *
 */
public class AnimatorViewImpl implements AnimatorView {
  private AnimatorModel<Shape, Motion> model;
  private Appendable output;

  /**
   * Alternative constructor for Animator View Implementation.
   * @param model
   */
  public AnimatorViewImpl(AnimatorModel<Shape, Motion> model) {
    this(model, System.out);
  }

  /**
   * Constructor for Animator View Implementation.
   * @param model
   * @param output
   */
  public AnimatorViewImpl(AnimatorModel<Shape, Motion> model, Appendable output) {
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
  public void viewMessage(String message) {

  }

  @Override
  public void viewState() {

  }
}
