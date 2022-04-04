package cs3500.animator;

import java.io.FileNotFoundException;
import cs3500.animator.controller.MainArgsHandler;

public class Main {
  public static void main(String[] args) throws FileNotFoundException {
    boolean inFlag = false;
    boolean viewFlag = false;
    boolean outFlag = false;
    boolean speedFlag = false;
    String inputFileName = null;
    String viewTypeString = null;
    String outputFileName = null;
    int tickSpeed = 0;
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
          inputFileName = args[i + 1];
          break;
        case "-view":
          if (viewFlag) {
            throw new IllegalArgumentException("Attempting to set View Twice");
          }
          viewFlag = true;
          viewTypeString = args[i+1];
          break;
        case "-out":
          if (outFlag) {
            throw new IllegalArgumentException("Attempting to set Output Twice");
          }
          outFlag = true;
          outputFileName = args[i+1];
          break;
        case "-speed":
          if (speedFlag) {
            throw new IllegalArgumentException("Attempting to set Speed Twice");
          }
          speedFlag = true;
          try {
            tickSpeed = Integer.parseInt(args[i+1]);
          } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("None Integer Supplied to Speed");
          }
          break;
        default:
          throw new IllegalArgumentException("Command Not Recognized");
      }
    }

    if (!inFlag) {
      throw new IllegalArgumentException("Input File cannot be Null.");
    }

    if(!viewFlag) {
      throw new IllegalArgumentException("ViewType cannot be Null");
    }
    MainArgsHandler handler = new MainArgsHandler(inputFileName, viewTypeString, outputFileName,
            tickSpeed);
    System.exit(0);
  }
}
