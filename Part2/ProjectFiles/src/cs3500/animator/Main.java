package cs3500.animator;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;

public class Main {
  public static void main(String[] args) throws IOException {
    boolean inFlag = false;
    String nameOfAnimationFile = null;
    Readable input;
    boolean viewFlag = false;
    String typeOfView = null;
    boolean outFlag = false;
    String whereOutputShowGo = null;
    Appendable output = null;
    boolean speedFlag = false;
    int integerTicksPerSecond = 1;
    if (args.length % 2 != 0) {
      throw new IllegalArgumentException("Odd Length, Arguments must be paired.");
    }
    for (int i = 0; i < args.length; i += 2) {
      switch (args[i]) {
        case "-in":
          if (inFlag) {
            throw new IllegalArgumentException("Attempting to set Input Twice");
          }
          inFlag = true;
          nameOfAnimationFile = args[i + 1];
          break;
        case "-view":
          if (viewFlag) {
            throw new IllegalArgumentException("Attempting to set View Twice");
          }
          viewFlag = true;
          typeOfView = args[i+1];
          break;
        case "-out":
          if (outFlag) {
            throw new IllegalArgumentException("Attempting to set Output Twice");
          }
          outFlag = true;
          whereOutputShowGo = args[i+1];
          break;
        case "-speed":
          if (speedFlag) {
            throw new IllegalArgumentException("Attempting to set Speed Twice");
          }
          speedFlag = true;
          try {
            integerTicksPerSecond = Integer.parseInt(args[i+1]);
          } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("None Integer Supplied to Speed");
          }
          break;
        default:
          throw new IllegalArgumentException("Command Not Recognized");
      }
    }

    if (nameOfAnimationFile == null) {
      throw new IllegalArgumentException("Input File cannot be Null.");
    } else {
      input = new FileReader(nameOfAnimationFile);
    }

    if (whereOutputShowGo == null) {
      output = System.out;
    } else {
      output = new FileWriter(whereOutputShowGo);
    }

    AnimatorModel am = new AnimatorModelImpl();
    switch (typeOfView) {
      case "text":
        break;
      case "visual":
        break;
      case "svg":
        break;
      default:
        throw new IllegalArgumentException("Cannot Recognize Supplied View Type");
    }
  }
}
