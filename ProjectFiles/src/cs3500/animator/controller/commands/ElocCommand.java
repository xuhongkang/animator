package cs3500.animator.controller.commands;

import cs3500.animator.model.Motion;

/**
 * Command Handler for Eloc Command.
 */
public class ElocCommand implements AnimatorCommand {
  private Integer endX;
  private Integer endY;
  private Motion.MotionBuilder builder;

  /**
   * Constructor for delete command.
   * @param params is the string input for parameters
   * @param builder is the motion builder
   */
  public ElocCommand(String params, Motion.MotionBuilder builder) {
    String[] exANDey = params.split(",");

    try {
      if (exANDey.length == 1) {
        if (exANDey[0].equals("")) {
          this.endX = null;
          this.endY = null;
        } else {
          this.endX = null;
          this.endY = Integer.valueOf(exANDey[0]);
        }
      } else {
        this.endX = Integer.valueOf(exANDey[0]);
        this.endY = Integer.valueOf(exANDey[1]);
      }
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Invalid ending location parameters, can't parse" +
              "into int.");
    }

    this.builder = builder;
  }

  @Override
  public void build() {
    this.builder.setEndXY(endX, endY);
  }
}

