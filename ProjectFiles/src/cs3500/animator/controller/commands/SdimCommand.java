package cs3500.animator.controller.commands;

import cs3500.animator.model.Motion;

/**
 * Command Handler for sdim command.
 */
public class SdimCommand implements AnimatorCommand {
  private Integer startW;
  private Integer startH;
  private Motion.MotionBuilder builder;

  /**
   * Constructor for sdim command.
   * @param params is the string input for parameters
   * @param mb is the motion builder
   */
  public SdimCommand(String params, Motion.MotionBuilder mb) {
    String[] swANDsh = params.split(",");

    try {
      if (swANDsh.length == 1) {
        if (swANDsh[0].equals("")) {
          this.startW = null;
          this.startH = null;
        } else {
          this.startW = null;
          this.startH = Integer.valueOf(swANDsh[0]);
        }
      } else {
        this.startW = Integer.valueOf(swANDsh[0]);
        this.startH = Integer.valueOf(swANDsh[1]);
      }
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Invalid starting dimention parameters, can't parse" +
              "into int.");
    }
    this.builder = mb;
  }

  @Override
  public void build() {
    this.builder.setStartWH(startW, startH);
  }

}
