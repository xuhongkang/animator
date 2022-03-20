package cs3500.animator.controller;

import cs3500.animator.model.Motion;

public class TimeCommand implements AnimatorCommand {
  private Integer sTime;
  private Integer eTime;
  private Motion.MotionBuilder builder;

  public TimeCommand(String params, Motion.MotionBuilder mb) {
    String[] lop = params.split(",");
    try {
      if (lop.length == 1) {
        if (lop[0].equals("")) {
          this.sTime = null;
          this.eTime = null;
        } else {
          this.sTime = null;
          this.eTime = Integer.valueOf(lop[0]);
        }
      } else {
        this.sTime = Integer.valueOf(lop[0]);
        this.eTime = Integer.valueOf(lop[1]);
      }
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Invalid Time Parameters, Cannot Parse to Int.");
    }
    this.builder = mb;
  }

  public void build() {
    this.builder.setTime(sTime, eTime);
  }
}
