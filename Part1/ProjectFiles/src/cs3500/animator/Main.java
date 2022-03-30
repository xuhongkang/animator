package cs3500.animator;

import java.io.StringReader;

import cs3500.animator.controller.AnimatorController;
import cs3500.animator.controller.AnimatorControllerImpl;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;

/**
 * This is the main class, duh.
 */
public class Main {
  /**
   * Below is an example command input snippet for testing.
   * @param args refers to an array of string arguments.
   */
  public static void main(String[] args) {
    String commands = "create/R,rect\n"
            + "create/C,Oval\n"
            + "del/C\n"
            + "tag/R time/5,10 sloc/50,50 sdim/80,80 color/Red,Blue\n"
            + "time/10,80 eloc/100,200 color/Red";
    Readable input = new StringReader(commands);
    AnimatorModel model = new AnimatorModelImpl();
    AnimatorController controller = new AnimatorControllerImpl(input, System.out, model);
    controller.readInput();
  }
}
