package cs3500.animator.view;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.AnimatorModelState;
import cs3500.animator.model.AniCommand;
import cs3500.animator.model.Shape;
import cs3500.animator.model.MoveAniCommand;
import cs3500.animator.model.ColorAniCommand;
import cs3500.animator.model.ScaleAniCommand;

/**
 * Represents a human redable version of a simple animation. Each shape has commands associated with
 * it that are printed textually.
 */
public class TextAnimatorView extends AbstractTextAnimatorView {

  private final AnimatorModelState model;
  private final int tps;

  /**
   * Constructor for a textual view of the model.
   *
   * @param model the model to represent textually
   * @param tps   the ticks per second for the animation
   */
  public TextAnimatorView(AnimatorModelState model, int tps) {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    } else if (tps <= 0) {
      throw new IllegalArgumentException("tps can't be 0 or negative");
    }
    this.model = model;
    this.tps = tps;
  }

  @Override
  public String toString() {
    List<String> shapeIDs = model.getShapesInAnimation();
    StringBuilder ans = new StringBuilder();
    for (String s : shapeIDs) {
      ans.append(getShapeCommandsAsText(s, tps));
      ans.append('\n');
    }
    return ans.toString();
  }

  @Override
  public String getNewCommand() {
    //this view doesn't support new commands, so this function does nothing
    return null;
  }

  @Override
  public int getTempo() {
    //this view doesn't support slow-mo, so this function does nothing
    return -1;
  }

  @Override
  public void setNewCommand() {
    //this view doesn't support new commands, so this function does nothing
  }


  private String getShapeCommandsAsText(String shapeID, int tps) {
    if (!model.getShapesInAnimation().contains(shapeID)) {
      throw new IllegalArgumentException("shape id does not exist");
    } else if (tps <= 0) {
      throw new IllegalArgumentException("tps can't be 0 or negative");
    }
    String text = "";
    for (String shape : model.getShapesInAnimation()) {
      text = text + getAnimationsAsText(1 / (double) tps, shape);
    }
    return text;
  }


  private String getAnimationsAsText(double tickDur, String shape) {
    Shape startingShape = model.getShapeAsAniShape(shape).getStartingShape();
    double stime = model.getShapeAsAniShape(shape).getStartTime();
    double etime = model.getShapeAsAniShape(shape).getEndTime();
    StringBuilder ans = new StringBuilder();
    ans.append("Shape: ");
    ans.append(startingShape.getShapeID());
    ans.append(", ");
    ans.append(startingShape.getShapeType().toString());
    ans.append(", stime: ").append(stime * tickDur).append(", etime: ").append(etime * tickDur);
    ans.append('\n');
    //get the starting attributes
    ans.append("position(x,y): ").append(startingShape.getPosition().getX()).append(", ")
        .append(startingShape.getPosition().getY());
    ans.append(" width: ").append(startingShape.getWidth()).append(", height: ")
        .append(startingShape.getHeight());
    ans.append(" color(r,g,b): ").append(startingShape.getColor().getRed()).append(", ")
        .append(startingShape.getColor().getGreen()).append(", ")
        .append(startingShape.getColor().getBlue());
    ans.append('\n');
    //get the move cmd header
    List<AniCommand> animations = model.getShapeAsAniShape(shape).getCommandsOnShape();
    List<AniCommand> moveAnimations = new ArrayList<>();
    List<AniCommand> scaleAnimations = new ArrayList<>();
    List<AniCommand> colorAnimations = new ArrayList<>();
    for (AniCommand cmd : animations) {
      if (cmd instanceof MoveAniCommand) {
        moveAnimations.add(cmd);
      }
      if (cmd instanceof ScaleAniCommand) {
        scaleAnimations.add(cmd);
      }
      if (cmd instanceof ColorAniCommand) {
        colorAnimations.add(cmd);
      }
    }
    ans.append(getMoveCmdHeader());
    for (AniCommand cmd : moveAnimations) {
      ans.append(getMoveAnimationAsText(cmd, tickDur));
    }
    //get the color cmd header
    ans.append('\n');
    ans.append(getColorCmdHeader());
    for (AniCommand cmd : colorAnimations) {
      ans.append(getColorAnimationAsText(cmd, tickDur));
    }
    //get the scale cmd header
    ans.append('\n');
    ans.append(getScaleCmdHeader());
    for (AniCommand cmd : scaleAnimations) {
      ans.append(getScaleAnimationAsText(cmd, tickDur));
    }
    return ans.toString();
  }

  private String getMoveCmdHeader() {
    StringBuilder ans = new StringBuilder();
    ans.append("Move Commands\n");
    ans.append("\t\tStart\t->\tEnd\n");
    ans.append("\t------------------\n");
    ans.append("\t\tt \tx \ty \t-> \tt \tx \ty \n");
    return ans.toString();
  }

  private String getScaleAnimationAsText(AniCommand cmd, double tickDur) {
    StringBuilder ans = new StringBuilder();
    //ans.append("Scale ");
    ans.append("\t\t");
    ans.append(cmd.getStartTime() * (int) tickDur);
    ans.append(",\t");
    ans.append(String.format("%.2f", cmd.getStartWidth()));
    ans.append(",\t");
    ans.append(String.format("%.2f", cmd.getStartHeight()));
    ans.append("\t->\t");
    ans.append(cmd.getEndTime() * (int) tickDur);
    ans.append(",\t");
    ans.append(String.format("%.2f", cmd.getEndWidth()));
    ans.append(",\t");
    ans.append(String.format("%.2f", cmd.getEndHeight()));
    ans.append('\n');
    return ans.toString();
  }

  private String getMoveAnimationAsText(AniCommand cmd, double tickDur) {
    StringBuilder ans = new StringBuilder();
    //ans.append("Move ");
    ans.append("\t\t");
    ans.append(cmd.getStartTime() * (int) tickDur);
    ans.append(",\t");
    ans.append(String.format("%.2f", cmd.getStartPos().getX()));
    ans.append(",\t");
    ans.append(String.format("%.2f", cmd.getStartPos().getY()));
    ans.append(" \t->\t");
    ans.append(cmd.getEndTime() * (int) tickDur);
    ans.append(",\t");
    ans.append(String.format("%.2f", cmd.getEndPos().getX()));
    ans.append(",\t");
    ans.append(String.format("%.2f", cmd.getEndPos().getY()));
    ans.append('\n');
    return ans.toString();
  }

  private String getColorAnimationAsText(AniCommand cmd, double tickDur) {
    StringBuilder ans = new StringBuilder();
    //ans.append("Color ");
    ans.append("\t\t");
    ans.append(cmd.getStartTime() * (int) tickDur);
    ans.append(",\t");
    ans.append(cmd.getStartColor().getRed());
    ans.append(",\t");
    ans.append(cmd.getStartColor().getGreen());
    ans.append(",\t");
    ans.append(cmd.getStartColor().getBlue());
    ans.append(" \t->\t");
    ans.append(cmd.getEndTime() * (int) tickDur);
    ans.append(",\t");
    ans.append(cmd.getEndColor().getRed());
    ans.append(",\t");
    ans.append(cmd.getEndColor().getGreen());
    ans.append(",\t");
    ans.append(cmd.getEndColor().getBlue());
    ans.append('\n');
    return ans.toString();
  }

  private String getColorCmdHeader() {
    StringBuilder ans = new StringBuilder();
    ans.append("Color Commands\n");
    ans.append("\t\tStart\t->\tEnd\n");
    ans.append("\t------------------\n");
    ans.append("\t\tt \tr \tg \tb \t -> \tt \tr \tg \tb \n");
    return ans.toString();
  }

  private String getScaleCmdHeader() {
    StringBuilder ans = new StringBuilder();
    ans.append("Scale Commands\n");
    ans.append("\t\tStart\t->\tEnd\n");
    ans.append("\t------------------\n");
    ans.append("\t\tt");
    ans.append("\tsw\tsh");
    ans.append(" \t -> \t");
    ans.append("t");
    ans.append("\tew\teh");
    ans.append('\n');
    return ans.toString();
  }

}
