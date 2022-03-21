package cs3500.animator.controller;

import cs3500.animator.model.Motion;

public class SlocCommand implements AnimatorCommand {

  private Integer startX;
  private Integer startY;
  private Motion.MotionBuilder builder;

  public SlocCommand(String params, Motion.MotionBuilder mb) {
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

    this.builder = mb;
  }

  @Override
  public void build() {
    this.builder.setStartWH(startX, startY);
  }
}
