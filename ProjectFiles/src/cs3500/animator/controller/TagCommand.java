package cs3500.animator.controller;

import cs3500.animator.model.BasicShape;
import cs3500.animator.model.Motion;

public class TagCommand {
  String tag;
  BasicShape shape;
  Motion.MotionBuilder builder;

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

  public void build() {
    this.builder.setTag(this.tag, this.shape);
  }
}
