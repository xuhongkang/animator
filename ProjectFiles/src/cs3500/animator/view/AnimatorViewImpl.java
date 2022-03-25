package cs3500.animator.view;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.ShapeState;

/**
 * Basic View Implementation for animator.
 */
public class AnimatorViewImpl implements AnimatorView {
  private AnimatorModel model;
  private Appendable output;

  /**
   * Alternative constructor for Animator View Implementation.
   * @param model is the animator model
   */
  public AnimatorViewImpl(AnimatorModel model) {
    this(model, System.out);
  }

  /**
   * Constructor for Animator View Implementation.
   * @param model is the animator model
   * @param output is the appendable output
   */
  public AnimatorViewImpl(AnimatorModel model, Appendable output) {
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

  @Override
  public void viewState() throws IOException {
    String legend = "# [LEGEND]\n" +
                    "# t == tick\n" +
                    "# (x,y) == position\n" +
                    "# (w,h) == dimensions\n" +
                    "# (r,g,b) == color (with values between 0 and 255)\n";
    Set<String> tags = this.model.getTags();
    if (tags.isEmpty()) {
      viewMessage("No Shapes or Motions Initialized.");
    } else {
      this.viewMessage(legend);
      for (String t: tags) {
        this.viewMessage(String.format("shape %s %s", model.getShapes().get(t).toString(), t));
        String motionMessage = String.format("motion %s   ", t);
        String motionMessageIndent = " ".repeat(motionMessage.length());
        List<ShapeState> motions = this.model.getMotions(t);
        if (motions.isEmpty()) {
          // Skip a line
          this.viewMessage("No Animations");
        } else {
          // Print Table
          String table = motionMessageIndent + "            start"
              + "                               end\n"
              + motionMessageIndent + "-----------------------------"
              + "     -----------------------------\n"
              + motionMessageIndent + "t   x   y   w   h   r   g   b"
              + "     t   x   y   w   h   r   g   b";
          this.viewMessage(table);

          // Print First
          String lines = motionMessage + motions.get(0).toString();

          // Cycle till last
          int motionNum = motions.size() - 1;
          for (int i = 1; i <= motionNum; i++) {
            if (i == motionNum) {
              lines += "   " + motions.get(i).toString();
            } else {
              lines += "   " + motions.get(i).toString() + "\n";
              lines += motionMessage + motions.get(i).toString();
            }
          }
          this.viewMessage(lines + "\n");
        }
      }
    }
  }
}
