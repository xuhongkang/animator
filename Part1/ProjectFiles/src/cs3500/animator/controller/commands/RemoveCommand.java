package cs3500.animator.controller.commands;

import cs3500.animator.model.AnimatorModel;

/**
 * Command Handler for Remove Command.
 */
public class RemoveCommand implements AnimatorCommand {
  private String tag;
  private AnimatorModel model;

  /**
   * Constructor for remove command.
   * @param params is the string input for parameters
   * @param model is the model
   */
  public RemoveCommand(String params, AnimatorModel model) {
    this.tag = params;
    this.model = model;
  }

  public void build() {
    this.model.removeLastMotion(this.tag);
  }
}
