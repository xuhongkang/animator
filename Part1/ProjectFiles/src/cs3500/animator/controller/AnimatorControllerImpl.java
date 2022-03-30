package cs3500.animator.controller;

import java.io.IOException;
import java.util.Scanner;

import cs3500.animator.controller.commands.ColorCommand;
import cs3500.animator.controller.commands.CreateCommand;
import cs3500.animator.controller.commands.DeleteCommand;
import cs3500.animator.controller.commands.EdimCommand;
import cs3500.animator.controller.commands.ElocCommand;
import cs3500.animator.controller.commands.RemoveCommand;
import cs3500.animator.controller.commands.SdimCommand;
import cs3500.animator.controller.commands.SlocCommand;
import cs3500.animator.controller.commands.TagCommand;
import cs3500.animator.controller.commands.TimeCommand;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.Motion;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.AnimatorViewImpl;

// Don't need to worry about controller for this assignment.

/**
 * Simple Implementation of the Controller for animator.
 */
public class AnimatorControllerImpl implements AnimatorController {
  private Readable input;
  private AnimatorView view;
  private AnimatorModel model;

  /**
   * Constructor for animator controller implementation.
   * @param input     Represents the input received from the user.
   * @param output     Represents the Appendable to send outputs to.
   * @param model     REpresents the model to call actions on.
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
    this.model = model;

    this.view = new AnimatorViewImpl(this.model, output);
  }

  @Override
  public void readInput() {
    try {
      Scanner s = new Scanner(this.input);
      String currentTag = null;
      try {
        while (s.hasNext()) {
          String commandLine = s.nextLine();
          String[] commands = commandLine.split(" ");
          Motion.MotionBuilder mb = new Motion.MotionBuilder();
          boolean hasCreate = false;
          boolean hasDelete = false;
          for (String command : commands) {
            String[] identifier = command.split("/");
            if (identifier.length != 2) {
              throw new IllegalArgumentException("Multiple or No Slashes Grouped up, Can only be " +
                      "one.");
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
                hasCreate = true;
                break;
              case "del":
                new DeleteCommand(params, this.model).build();
                hasDelete = true;
                break;
              case "rmv":
                new RemoveCommand(params, this.model).build();
                hasDelete = true;
                break;
              case "tag":
                TagCommand tmd = new TagCommand(params, currentTag, mb);
                tmd.build();
                currentTag = tmd.getTag();
                break;
              case "time":
                new TimeCommand(params, mb).build();
                break;
              case "sloc":
                new SlocCommand(params, mb).build();
                break;
              case "eloc":
                new ElocCommand(params, mb).build();
                break;
              case "sdim":
                new SdimCommand(params, mb).build();
                break;
              case "edim":
                new EdimCommand(params, mb).build();
                break;
              case "color":
                new ColorCommand(params, mb).build();
                break;
              default:
                throw new IllegalArgumentException("Commands Cannot Be Recognized.");
            }
          }
          if (!hasCreate && !hasDelete) {
            Motion m = mb.build();
            if (m.getTag() == null) {
              m.setTag(currentTag);
            }
            this.model.addMotion(m);
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


