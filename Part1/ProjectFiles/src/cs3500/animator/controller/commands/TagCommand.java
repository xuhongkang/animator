package cs3500.animator.controller.commands;

import cs3500.animator.model.BasicShape;
import cs3500.animator.model.Motion;

/**
 * Command Handler for the tag command.
 */
public class TagCommand {
  String tag;
  BasicShape shape;
  Motion.MotionBuilder builder;

  /**
   * Constructor for tag command.
   * @param params is the string input for parameters
   * @param defaultTag is the default string tag
   * @param builder is the motion builder
   */
  public TagCommand(String params, String defaultTag, Motion.MotionBuilder builder) {
    String[] lop = params.split(",");
    if (lop.length == 1) {
      if (lop[0].equals("")) {
        this.tag = defaultTag;
      } else {
        this.tag = lop[0];
      }
      this.shape = null;
    } else {
      this.tag = lop[0];
      switch (lop[1]) {
        case "Rectangle":
        case "rect":
          this.shape = BasicShape.RECTANGLE;
          break;
        case "Oval":
        case "ovl":
          this.shape = BasicShape.OVAL;
          break;
        case "Triangle":
        case "tri":
          this.shape = BasicShape.TRIANGLE;
          break;
        default:
          throw new IllegalArgumentException("Invalid Shape, Cannot Identify input.");
      }
    }
    this.builder = builder;
  }

  public String getTag() {
    return this.tag;
  }

  public void build() {
    this.builder.setTag(this.tag, this.shape);
  }
}
