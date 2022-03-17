package cs3500.animator.view;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.Shape;
import cs3500.animator.model.ShapeState;

/**
 * Basic View Implementation for animator.
 */
public class AnimatorViewImpl implements AnimatorView {
  private AnimatorModel<Shape, ShapeState> model;
  private Appendable output;

  /**
   * Alternative constructor for Animator View Implementation.
   * @param model
   */
  public AnimatorViewImpl(AnimatorModel<Shape, ShapeState> model) {
    this(model, System.out);
  }

  /**
   * Constructor for Animator View Implementation.
   * @param model
   * @param output
   */
  public AnimatorViewImpl(AnimatorModel<Shape, ShapeState> model, Appendable output) {
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
    Set<Shape> shapes = this.model.getShapeSet();
    if (shapes.isEmpty()) {
      viewMessage("No Shapes or Motions Initialized.");
    } else {
      this.viewMessage(legend);
      for (Shape s : shapes) {
        this.viewMessage(s.toString());
        String sTag = s.getTag();
        String motionMessage = String.format("motion %s   ", sTag);
        String motionMessageIndent = " ".repeat(motionMessage.length());
        List<ShapeState> motions = this.model.getShapeMotions(s);
        if (motions.isEmpty()){
          // Skip a line
          this.viewMessage("");
        } else {
          // Print Table
          String table = motionMessageIndent + "            start                               end\n" +
                         motionMessageIndent + "-----------------------------     -----------------------------\n" +
                         motionMessageIndent + "t   x   y   w   h   r   g   b     t   x   y   w   h   r   g   b";
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
