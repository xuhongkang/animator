package cs3500.animator.controller.commands;

import cs3500.animator.model.Motion;

/**
 * Command Handler for sloc command.
 */
public class SlocCommand implements AnimatorCommand {

  private Integer startX;
  private Integer startY;
  private Motion.MotionBuilder builder;

  /**
   * Constructor for sloc command.
   * @param params is the string input for parameters
   * @param builder is the motion builder
   */
  public SlocCommand(String params, Motion.MotionBuilder builder) {
    String[] sxANDsy = params.split(",");

    try {
      if (sxANDsy.length == 1) {
        if (sxANDsy[0].equals("")) {
          this.startX = null;
          this.startY = null;
        } else {
          this.startX = null;
          this.startY = Integer.valueOf(sxANDsy[0]);
        }
      } else {
        this.startX = Integer.valueOf(sxANDsy[0]);
        this.startY = Integer.valueOf(sxANDsy[1]);
      }
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Invalid starting location parameters, can't parse" +
              "into int.");
    }
    this.builder = builder;
  }

  @Override
  public void build() {
    this.builder.setStartXY(startX, startY);
  }
}
