package cs3500.animator.controller;

import java.io.IOException;
import java.util.Scanner;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.ShapeState;
import cs3500.animator.model.Motion;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.AnimatorViewImpl;

/**
 * Simple Implementation of the Controller for animator.
 */
public class AnimatorControllerImpl implements AnimatorController {
  private Readable input;
  private Appendable output;
  private AnimatorView view;
  private AnimatorModel<String, ShapeState> model;

  /**
   * Constructor for animator controller implementation.
   * @param input     Represent the input from the user.
   * @param output     Represent the Appendable to output with.
   * @param model     Represent the model to call actions on.
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

    this.view = new AnimatorViewImpl(this.model, this.output);
  }

  @Override
  public void readInput() {
    try {
      Scanner s = new Scanner(this.input);
      String currentTag = null;
      try {
        while (s.hasNext()) {
          String commandLine = s.nextLine();
          String commands[] = commandLine.split(" ");
          Motion.MotionBuilder mb = new Motion.MotionBuilder();
          for (String command : commands) {
            String[] identifier = command.split("/");
            if (identifier.length != 2) {
              throw new IllegalArgumentException("Multiple or No Slashes Grouped up, Can only be one.");
            }
            String prefix = identifier[0];
            String params = identifier[1];
            if (params.split(",").length > 2) {
              throw new IllegalArgumentException("Invalid amount of Parameters.");
            }
            switch (prefix) {
              case "create":
                CreateCommand cmd = new CreateCommand(params, this.model);
                cmd.build();
                currentTag = cmd.getTag();
                break;
              case "tag":
                new TagCommand(params, currentTag, mb);
                break;
              case "time":
                new TimeCommand(params, mb).build();
                break;
              case "sLoc":
                break;
              case "eLoc":
                break;
              case "sDim":
                break;
              case "eDim":
                break;
              case "color":
                break;
            }
          }
        }
      } catch (IllegalArgumentException iae) {
        this.view.viewMessage(iae.getMessage());
        return;
      }
      this.view.viewState();
    } catch (IOException ioe) {
      throw new IllegalStateException("IO Error");
    }
  }
}
