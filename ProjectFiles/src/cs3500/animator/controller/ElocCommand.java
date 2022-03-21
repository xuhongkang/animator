package cs3500.animator.controller;

import cs3500.animator.model.Motion;

public class ElocCommand implements AnimatorCommand {

  private Integer endX;
  private Integer endY;
  private Motion.MotionBuilder builder;

  public ElocCommand(String params, Motion.MotionBuilder mb) {
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

    this.builder = mb;
  }

  @Override
  public void build() {
    this.builder.setEndWH(endX, endY);
  }
}

