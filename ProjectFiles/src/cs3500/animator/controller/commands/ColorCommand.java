package cs3500.animator.controller.commands;

import cs3500.animator.model.BasicColor;
import cs3500.animator.model.Motion;

/**
 * Command Handler for color command.
 */
public class ColorCommand implements AnimatorCommand {
  private BasicColor startC ;
  private BasicColor endC ;
  private Motion.MotionBuilder builder;

  /**
   * Constructor for color command.
   * @param params is the string input for parameters
   * @param builder is the motion builder
   */
  public ColorCommand(String params, Motion.MotionBuilder builder) {

    String[] scANDec = params.split(",");

    String strStartC = null;
    String strEndC = null;

    if (scANDec.length == 1) {
      if (scANDec[0].equals("")) {
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

    this.startC = new BasicColor(0, 0, 0);
    this.endC = new BasicColor(0, 0, 0);

    try {
      this.startC.setRGBValue(strStartC);
    } catch (IllegalArgumentException iae) {
      throw new IllegalArgumentException("Please enter a valid starting color.");
    } catch (NullPointerException npe) {
      this.startC = null;
    }

    try {
      this.endC.setRGBValue(strEndC);
    } catch (IllegalArgumentException iae) {
      throw new IllegalArgumentException("Please enter a valid ending color.");
    } catch (NullPointerException npe) {
      this.endC = null;
    }
    this.builder = builder;
  }

  @Override
  public void build() {
    this.builder.setStartColor(this.startC);
    this.builder.setEndColor(this.endC);
  }
}
