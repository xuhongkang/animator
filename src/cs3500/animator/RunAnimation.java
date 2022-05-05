package cs3500.animator;

import cs3500.animator.controller.AnimatorController;
import cs3500.animator.controller.InteractiveController;
import cs3500.animator.io.AnimationFileReader;
import cs3500.animator.io.TweenModelBuilderImpl;
import cs3500.animator.view.AnimatorViewCreator;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.SimpleAnimatorView;

import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * A main method that provides a way to create an animation from a pre-built file and display the
 * animation visually or write an output to a file. This main method should take in arguments that
 * define functionality.
 */
public class RunAnimation {

  /**
   * Main runnable method.
   *
   * @param args In any order (pair-wise): -in infile -out outifle -speed tps -view
   *             (svg/text/visual)
   */
  public static void main(String[] args) {
    //vars for looping through args
    String in_file = null;
    String out_file = null;
    String vs = null;

    //actual args
    int tps = 1;
    Readable input;
    Appendable output = null;
    SimpleAnimatorView view = null;
    AnimatorModel model;
    AnimatorController controller = null;
    FileWriter fw = null;

    //analyze the args
    int i = 0;
    try {
      while (i < args.length) {
        switch (args[i]) {
          case "-in":
            in_file = args[i + 1];
            i += 2;
            continue;
          case "-out":
            out_file = args[i + 1];
            i += 2;
            continue;
          case "-view":
            //options are text, visual, svg
            vs = args[i + 1];
            i += 2;
            continue;
          case "-speed":
            tps = Integer.parseInt(args[i + 1]);
            i += 2;
            continue;
          default:
            showErrorPane("Invalid arg: " + args[i]);
        }
      }
    } catch (InputMismatchException ime) {
      showErrorPane("invalid ticks per second, should be an integer");
    }

    //check for everything
    //input file and view pair is mandatory
    if (in_file == null) {
      showErrorPane("Must specify input file!");
    } else if (vs == null) {
      showErrorPane("Must specify view type!");
    } else if (tps <= 0) {
      showErrorPane("Ticks per second must be greater than 0!");
    }

    // Throw error if anything goes wrong
    AnimationFileReader afr = new AnimationFileReader();
    try {
      //output file if none provided
      if (out_file == null) {
        output = System.out;
      } else {
        fw = new FileWriter(out_file);
        output = fw;

      }
      //create the model, view, controller
      model = afr.readFile(in_file, new TweenModelBuilderImpl());
      view = AnimatorViewCreator.create(vs, model, tps);
      //todo fixme hardcode interactive controller?
      controller = new InteractiveController(model, view, tps);
    } catch (IOException | InputMismatchException | IllegalStateException
        | IllegalArgumentException ioe) {
      showErrorPane(ioe.getMessage());
    }
    //do the animation / print to output
    try {
      if (vs.equals("visual") || vs.equals("interactive")) {
        controller.playAnimation();
      } else {
        output.append(view.toString());
        //close file if necessary
        if (fw != null) {
          fw.close();
        }

      }
    } catch (IOException | NullPointerException ioe) {
      showErrorPane(ioe.getMessage());
    }
  }


  static void showErrorPane(String error) {
    JFrame frame = new JFrame();
    JOptionPane.showMessageDialog(frame, error, "Error!", JOptionPane.ERROR_MESSAGE);
    frame.setVisible(true);
    System.exit(-1);
  }

}
