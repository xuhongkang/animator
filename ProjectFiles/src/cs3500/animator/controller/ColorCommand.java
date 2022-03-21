package cs3500.animator.controller;

import cs3500.animator.model.BasicColor;
import cs3500.animator.model.Motion;

public class ColorCommand implements AnimatorCommand {

  private BasicColor startC ;
  private BasicColor endC ;
  private Motion.MotionBuilder builder;

  public ColorCommand(String params, Motion.MotionBuilder builder) {

    String[] scANDec = params.split(",");

    String strStartC = null;
    String strEndC = null;

    if (scANDec.length == 1) {
      if (scANDec[0] == "") {
        strStartC = null;
        strEndC = null;
      } else {
        strStartC = null;
        strEndC = scANDec[0];
      }
    } else {
      strStartC = scANDec[0];
      strEndC = scANDec[1];
    }

    BasicColor startC = new BasicColor(0, 0, 0);
    BasicColor endC = new BasicColor(0, 0, 0);

    try {
      startC.setValue(strStartC);
    } catch (IllegalArgumentException iae) {
      throw new IllegalArgumentException("Please enter a valid starting color.");
    }

    try {
      endC.setValue(strEndC);
    } catch (IllegalArgumentException iae) {
      throw new IllegalArgumentException("Please enter a valid ending color.");
    }
  }

  @Override
  public void build() {
    this.builder.setStartColor(startC);
    this.builder.setEndColor(endC);
  }

}
