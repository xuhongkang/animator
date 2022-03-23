package cs3500.animator;

import java.io.StringReader;

import cs3500.animator.controller.AnimatorController;
import cs3500.animator.controller.AnimatorControllerImpl;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;

public class Main {
  public static void main(String[] args) {
    String commands = "create/R,rect\n" +
            "tag/R time/5,10\n" +
            "time/10,80 eLoc/100,200 color/red";
    Readable input = new StringReader(commands);
    AnimatorModel model = new AnimatorModelImpl();
    AnimatorController controller = new AnimatorControllerImpl(input, System.out, model);
    controller.readInput();
  }
}
