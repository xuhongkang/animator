package cs3500.animator.view;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.ShapeProperty;

/**
 * Basic View Implementation for animator.
 */
public class TextAnimatorViewImpl implements TextAnimatorView {
  private AnimatorModel model;
  private Appendable output;

  /**
   * Alternative constructor for Animator View Implementation.
   * @param model is the animator model
   */
  public TextAnimatorViewImpl(AnimatorModel model) {
    this(model.build(), System.out);
  }

  /**
   * Constructor for Animator View Implementation.
   * @param model is the animator model
   * @param output is the appendable output
   */
  public TextAnimatorViewImpl(AnimatorModel model, Appendable output) {
    if (model == null) {
      throw new IllegalArgumentException("Invalid Model, Model is Null.");
    }
    if (output == null) {
      throw new IllegalArgumentException("Invalid Output, Output is Null.");
    }
    this.model = model;
    this.output = output;
  }

  @Override
  public void viewMessage(String message) throws IOException {
    this.output.append(message + "\n");
  }

  private String getStateString() {
    String rString = "";
    String legend = "# [LEGEND]\n" +
            "# t == tick\n" +
            "# (x,y) == position\n" +
            "# (w,h) == dimensions\n" +
            "# (r,g,b) == color (with values between 0 and 255)\n";
    Set<String> tags = this.model.getTags();
    if (tags.isEmpty()) {
      rString += "No Shapes or Motions Initialized.";
    } else {
      rString += legend;
      for (String t: tags) {
        ShapeProperty sp = this.model.getShapeProperty(t);
        rString += String.format("shape %s %s", sp.getShape().toString(), t);
        String motionMessage = String.format("motion %s   ", t);
        String motionMessageIndent = " ".repeat(motionMessage.length());
        rString += sp.toString(motionMessageIndent);
      }
    }
    return rString;
  }

  @Override
  public void writeToFile(String filename) {
    try {
      FileWriter fw = new FileWriter(filename + ".txt");
      fw.write(this.getStateString());
      fw.close();
    } catch (IOException ioe) {
      throw new IllegalArgumentException("Cannot Write to File.");
    }
  }

  @Override
  public void viewState() throws IOException {
    this.viewMessage(this.getStateString());
  }
}
