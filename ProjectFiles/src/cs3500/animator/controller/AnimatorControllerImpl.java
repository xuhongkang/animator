package cs3500.animator.controller;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.Shape;
import cs3500.animator.model.ShapeState;
import cs3500.animator.view.AnimatorView;

/**
 * Simple Implementation of the Controller for animator.
 */
public class AnimatorControllerImpl implements AnimatorController {
  private Readable input;
  private Appendable output;
  private AnimatorView view;
  private AnimatorModel<Shape, ShapeState> model;

  /**
   * Constructor for animator controller implementation.
   * @param input
   * @param output
   * @param model
   */
  public AnimatorControllerImpl(Readable input, Appendable output, AnimatorModel model) {
    if (input == null) {
      throw new IllegalArgumentException("Invalid Input, Input is Null");
    }
    if (output == null) {
      throw new IllegalArgumentException("Invalid Output, Output is Null");
    }
    if (model == null) {
      throw new IllegalArgumentException("Invalid Model, Model is null");
    }
    this.input = input;
    this.output = output;
    this.model = model;
  }

  @Override
  public void readInput() {

  }
}
