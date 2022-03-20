package cs3500.animator.controller;

import java.util.Scanner;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.ShapeState;
import cs3500.animator.model.BasicShape;
import cs3500.animator.model.BasicColor;
import cs3500.animator.model.Motion;
import cs3500.animator.view.AnimatorView;
import cs3500.animator.view.AnimatorViewImpl;

/**
 * Simple Implementation of the Controller for animator.
 */
public class AnimatorControllerImpl implements AnimatorController {
  private Readable input;
  private Appendable output;
  private AnimatorView view;
  private AnimatorModel<String, ShapeState> model;

  /**
   * Constructor for animator controller implementation.
   * @param input     Represent the input from the user.
   * @param output     Represent the Appendable to output with.
   * @param model     Represent the model to call actions on.
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
    this.output = output;
    this.model = model;

    this.view = new AnimatorViewImpl(this.model, this.output);
  }

  @Override
  public void readInput() {

    Scanner scan = new Scanner(this.input);

    String commands = scan.nextLine();

    String[] arrOfCommands = commands.split(" ");

    int arrOfCommandsLength = arrOfCommands.length;

    if (arrOfCommandsLength != 7) {
      throw new IllegalArgumentException("Please check your input to make sure you didn't miss " +
              "any block.");
    }

    // ----------------------------------------------------------------
    // For checking the Create block; and extract informations from it.
    String createBlock = arrOfCommands[0];

    if (createBlock.substring(0, 7) != "create/") {
      throw new IllegalArgumentException("create/ is expected in the first block.");
    }

    int createBlockLength = createBlock.length();

    String tagANDshape = createBlock.substring(7, createBlockLength);

    String[] arrTagAndShape = tagANDshape.split(",");

    String tag = arrTagAndShape[0];

    String shape = arrTagAndShape[1];

    // Calling the createShape method from the controller:
    if (shape == "rectangle") {
      model.createShape(tag, BasicShape.RECTANGLE);
    }
    if (shape == "oval") {
      model.createShape(tag, BasicShape.OVAL);
    }
    if (shape == "triangle") {
      model.createShape(tag, BasicShape.TRIANGLE);
    }

    // ----------------------------------------------
    // For creating a MotionBuilder, for later usage.
    Motion.MotionBuilder motionBuilder = new Motion.MotionBuilder();

    // --------------------------------------------------------------
    // For checking the Time block; and extract informations from it.
    String timeBlock = arrOfCommands[1];

    if (timeBlock.substring(0, 5) != "time/") {
      throw new IllegalArgumentException("time/ is expected in the second block.");
    }

    int timeBlockLength = timeBlock.length();

    String startT;
    String endT;

    if (timeBlockLength == 5) {
      startT = null;
      endT = null;
    } else {

      String startTANDendT = timeBlock.substring(5, timeBlockLength);

      if (startTANDendT.contains(",")) {
        String[] arrStartTandEndT = startTANDendT.split(",");

        startT = arrStartTandEndT[0];

        endT = arrStartTandEndT[1];
      } else {
        startT = null;
        endT = startTANDendT;
      }
    }

    int intStartT;
    int intEndT;

    try {
      intStartT = Integer.parseInt(startT);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Please enter a number for the starting time.");
    }

    try {
      intEndT = Integer.parseInt(endT);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Please enter a number for the ending time.");
    }

    motionBuilder.setTime(intStartT, intEndT);


    // -----------------------------------------------------------------------
    // For checking the StartLocation block; and extract informations from it.
    String slocBlock = arrOfCommands[2];

    if (slocBlock.substring(0, 5) != "sloc/") {
      throw new IllegalArgumentException("sloc/ is expected in the third block.");
    }

    int slocBlockLength = slocBlock.length();

    String sxValue;
    String syValue;

    if (slocBlockLength == 5) {
      sxValue = null;
      syValue = null;
    } else {

      String sxANDsy = slocBlock.substring(5, slocBlockLength);

      if (sxANDsy.contains(",")) {
        String[] arrXandY = sxANDsy.split(",");

        sxValue = arrXandY[0];

        syValue = arrXandY[1];
      } else {
        sxValue = sxANDsy;
        syValue = null;
      }
    }

    int intSxValue;
    int intSyValue;

    try {
      intSxValue = Integer.parseInt(sxValue);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Please enter a number for the start X value.");
    }

    try {
      intSyValue = Integer.parseInt(syValue);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Please enter a number for the start Y value.");
    }

    motionBuilder.setStartXY(intSxValue, intSyValue);


    // ---------------------------------------------------------------------
    // For checking the EndLocation block; and extract informations from it.
    String elocBlock = arrOfCommands[3];

    if (elocBlock.substring(0, 5) != "eloc/") {
      throw new IllegalArgumentException("eloc/ is expected in the fourth block.");
    }

    int elocBlockLength = elocBlock.length();

    String exValue;
    String eyValue;

    if (elocBlockLength == 5) {
      exValue = null;
      eyValue = null;
    } else {

      String exANDey = elocBlock.substring(5, elocBlockLength);

      if (exANDey.contains(",")) {
        String[] arrEXandEY = exANDey.split(",");

        exValue = arrEXandEY[0];

        eyValue = arrEXandEY[1];
      } else {
        exValue = exANDey;
        eyValue = null;
      }
    }

    int intExValue;
    int intEyValue;

    try {
      intExValue = Integer.parseInt(exValue);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Please enter a valid number for the end X value.");
    }

    try {
      intEyValue = Integer.parseInt(eyValue);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Please enter a valid number for the end Y value.");
    }

    motionBuilder.setEndXY(intExValue, intEyValue);


    // -------------------------------------------------------------------
    // For checking the StartSize block; and extract informations from it.
    String ssizeBlock = arrOfCommands[4];

    if (ssizeBlock.substring(0, 6) != "ssize/") {
      throw new IllegalArgumentException("ssize/ is expected in the fifth block.");
    }

    int ssizeBlockLength = ssizeBlock.length();

    String swValue;
    String shValue;

    if (ssizeBlockLength == 6) {
      swValue = null;
      shValue = null;
    } else {

      String swANDsh = ssizeBlock.substring(6, ssizeBlockLength);

      if (swANDsh.contains(",")) {
        String[] arrSWandSH = swANDsh.split(",");

        swValue = arrSWandSH[0];

        shValue = arrSWandSH[1];
      } else {
        swValue = swANDsh;
        shValue = null;
      }
    }

    int intSwValue;
    int intShValue;

    try {
      intSwValue = Integer.parseInt(swValue);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Please enter a valid number for the start w value.");
    }

    try {
      intShValue = Integer.parseInt(shValue);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Please enter a valid number for the start h value.");
    }

    motionBuilder.setStartWH(intSwValue, intShValue);


    // -----------------------------------------------------------------
    // For checking the EndSize block; and extract informations from it.
    String esizeBlock = arrOfCommands[5];

    if (esizeBlock.substring(0, 6) != "esize/") {
      throw new IllegalArgumentException("esize/ is expected in the sixth block.");
    }

    int esizeBlockLength = esizeBlock.length();

    String ewValue;
    String ehValue;

    if (esizeBlockLength == 6) {
      ewValue = null;
      ehValue = null;
    } else {

      String ewANDeh = esizeBlock.substring(6, esizeBlockLength);

      if (ewANDeh.contains(",")) {
        String[] arrEWandEH = ewANDeh.split(",");

        ewValue = arrEWandEH[0];

        ehValue = arrEWandEH[1];
      } else {
        ewValue = ewANDeh;
        ehValue = null;
      }
    }

    int intEwValue;
    int intEhValue;

    try  {
      intEwValue = Integer.parseInt(ewValue);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Please enter a number for the ending w value.");
    }

    try  {
      intEhValue = Integer.parseInt(ehValue);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException("Please enter a number for the ending h value.");
    }

    motionBuilder.setEndWH(intEwValue, intEhValue);

    // ---------------------------------------------------------------
    // For checking the Color block; and extract informations from it.
    String colorBlock = arrOfCommands[6];

    if (colorBlock.substring(0, 6) != "color/") {
      throw new IllegalArgumentException("color/ is expected in the seventh block.");
    }

    int colorBlockLength = colorBlock.length();

    String scValue;
    String ecValue;

    // for the case in which no color information(s) are provided:
    if (colorBlockLength == 6) {
      scValue = null;
      ecValue = null;
    } else {

      String scANDec = colorBlock.substring(6, colorBlockLength);

      // for the case in which both start color and end color are provided:
      if (scANDec.contains(",")) {
        String[] arrSCandEC = scANDec.split(",");

        scValue = arrSCandEC[0];

        ecValue = arrSCandEC[1];
      }
      // for the case in which only the start color is provided:
      else {
        scValue = null;
        ecValue = scANDec;
      }
    }

    BasicColor forStartC = new BasicColor(0, 0, 0);
    forStartC.setValue(scValue);
    motionBuilder.setStartColor(forStartC);

    BasicColor forEndC = new BasicColor(0, 0, 0);
    forEndC.setValue(ecValue);
    motionBuilder.setEndColor(forEndC);

    // ---------------------------------------------
    // For passing the overall motion into the model.

    Motion overallMotion = motionBuilder.build();

    model.addMotion(tag, overallMotion);
  }
}
