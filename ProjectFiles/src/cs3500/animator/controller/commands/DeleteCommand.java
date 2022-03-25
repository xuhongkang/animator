package cs3500.animator.controller.commands;

import cs3500.animator.model.AnimatorModel;

/**
 * Command Handler for Delete Command.
 */
public class DeleteCommand implements AnimatorCommand {
  private String tag;
  private AnimatorModel model;

  /**
   * Constructor for delete command.
   * @param params is the string input for parameters
   * @param model is the model
   */
  public DeleteCommand(String params, AnimatorModel model) {
    this.tag = params;
    this.model = model;
  }

  public void build() {
    this.model.deleteShape(tag);
  }
}
