package cs3500.animator.controller;

import cs3500.animator.model.Motion;

public class EdimCommand implements AnimatorCommand {

  private Integer endW;
  private Integer endH;
  private Motion.MotionBuilder builder;

  public EdimCommand(String params, Motion.MotionBuilder mb) {
    String[] ewANDeh = params.split(",");

    try {
      if (ewANDeh.length == 1) {
        if (ewANDeh[0].equals("")) {
          this.endW = null;
          this.endH = null;
        } else {
          this.endW = null;
          this.endH = Integer.valueOf(ewANDeh[0]);
        }
      } else {
        this.endW = Integer.valueOf(ewANDeh[0]);
        this.endH = Integer.valueOf(ewANDeh[1]);
      }
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Invalid ending dimension parameters, can't parse" +
              "into int.");
    }
    this.builder = mb;
  }

  @Override
  public void build() {
    this.builder.setEndWH(endW, endH);
  }

}


