package cs3500.animator.view;

import cs3500.animator.model.AniCommand;
import cs3500.animator.model.AniShape;
import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.AnimatorModelState;
import cs3500.animator.model.ColorAniCommand;
import cs3500.animator.model.MoveAniCommand;
import cs3500.animator.model.Position2D;
import cs3500.animator.model.ScaleAniCommand;
import cs3500.animator.model.Shape;
import cs3500.animator.model.ShapeType;

import java.awt.Color;
import java.util.List;

/**
 * Represents an SVG view of an animation. SVG is a standardized description of an animation that
 * can be played in a browser.
 */
public class SVGAnimatorView extends AbstractTextAnimatorView {

  private final AnimatorModelState model;
  private final int tickDur; //duration of a tick in milliseconds

  /**
   * Constructor of an svg view of the model.
   *
   * @param model model to represents as an SVG file
   * @param tps   ticks per second for the animatior.
   */
  public SVGAnimatorView(AnimatorModelState model, int tps) {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    if (tps <= 0) {
      throw new IllegalArgumentException("tps cannot be 0 or negative");
    }
    this.model = model;
    //ticks per second, the speed of the animation
    //10 tps, each tick is 100 ms
    this.tickDur = 1000 / tps;

  }

  @Override
  public String toString() {
    //make the header of the animation
    StringBuilder ans = new StringBuilder();
    ans.append(constructHeader());

    //get all the shapeIDs in the animation
    List<String> shapeIDs = model.getShapesInAnimation();

    //loop through the shapeID's, getting their textual description, pass to parser
    for (String sid : shapeIDs) {
      ans.append(parseShape(model.getShapeAsAniShape(sid)));
      //ans.append(parseDescription(model.getShapeCommandsAsText(sid, tps)));
    }

    ans.append(constructTail());
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

  private String parseCross(AniShape shape) {
    Shape s = shape.getStartingShape();
    StringBuilder v = new StringBuilder();
    StringBuilder h = new StringBuilder();
    Shape sv = new Shape(s.getShapeID(), ShapeType.RECTANGLE,
            new Position2D(s.getPosition().getX() + 0.25f * s.getWidth(),
            s.getPosition().getY()), s.getColor(), 0.5f * s.getWidth(), s.getHeight());
    Shape sh = new Shape(s.getShapeID(), ShapeType.RECTANGLE,
            new Position2D(s.getPosition().getX(), s.getPosition().getY() + 0.25f * s.getHeight()),
            s.getColor(), s.getWidth(), 0.5f * s.getHeight());
    return parseShape(new AnimatedShape((AnimatedShape) shape, sv)) +
            parseShape(new AnimatedShape((AnimatedShape) shape, sh));
  }

  private String parseShape(AniShape shape) {
    String shapeType;
    String xval;
    String yval;
    String width;
    String height;

    Shape startingShape = shape.getStartingShape();
    if (startingShape.getShapeType() == ShapeType.CROSS) {
      return this.parseCross(shape);
    } else if (startingShape.getShapeType() == ShapeType.RECTANGLE) {
      //do something
      shapeType = "rect";
      xval = "x";
      yval = "y";
      width = "width";
      height = "height";
    } else {
      //do another thing
      shapeType = "ellipse";
      xval = "cx";
      yval = "cy";
      width = "rx";
      height = "ry";
    }
    StringBuilder ans = new StringBuilder();
    ans.append(createShapeHeader(shapeType, xval, yval, width, height, startingShape));

    //append the visibility command
    ans.append(createSVGVisibilityAnimation(shape.getStartTime(), shape.getEndTime()));
    ans.append('\n');

    //loop through all the animations for the shape
    List<AniCommand> commands = shape.getCommandsOnShape();
    for (AniCommand ac : commands) {
      ans.append('\n');
      if (ac instanceof MoveAniCommand) {
        ans.append(createSVGAnimation(xval, ac.getStartTime(), ac.getEndTime(),
            ac.getStartPos().getX(), ac.getEndPos().getX()));
        ans.append('\n');
        ans.append(createSVGAnimation(yval, ac.getStartTime(), ac.getEndTime(),
            ac.getStartPos().getY(), ac.getEndPos().getY()));
      } else if (ac instanceof ColorAniCommand) {
        ans.append(createSVGColorAnimation(ac.getStartTime(), ac.getEndTime(),
            ac.getStartColor(), ac.getEndColor()));
      } else if (ac instanceof ScaleAniCommand) {
        ans.append(createSVGAnimation(width, ac.getStartTime(), ac.getEndTime(),
            ac.getStartWidth(), ac.getEndWidth()));
        ans.append('\n');
        ans.append(createSVGAnimation(height, ac.getStartTime(), ac.getEndTime(),
            ac.getStartHeight(), ac.getEndHeight()));
      } else {
        throw new IllegalStateException("found AniCommand that can't be interpreted");
      }
    }

    //construct the shape tail
    ans.append(createShapeTail(shapeType));
    return ans.toString() + "\n";
  }

  private String createShapeHeader(String type, String x, String y, String width, String height,
      Shape shpe) {
    StringBuilder ans = new StringBuilder();
    ans.append("<").append(type);
    ans.append(" id=").append(quote(shpe.getShapeID()));
    ans.append(" ").append(x).append('=').append(quote(Float.toString(shpe.getPosition().getX())));
    ans.append(" ").append(y).append('=').append(quote(Float.toString(shpe.getPosition().getY())));
    ans.append(" ").append(width).append('=').append(quote(Float.toString(shpe.getWidth())));
    ans.append(" ").append(height).append('=').append(quote(Float.toString(shpe.getHeight())));
    ans.append(" fill=").append(colorWrap(shpe.getColor().getRed(),
        shpe.getColor().getGreen(), shpe.getColor().getBlue()));
    //make the shape initially hidden, will add times to make visible and hidden
    ans.append(" visibility=\"collapse\" >");
    ans.append('\n');
    return ans.toString();
  }

  private String createShapeTail(String type) {
    return "\n</" + type + ">" + "\n";
  }

  private String createSVGAnimation(String attrName, int stick, int etick, float start, float end) {
    StringBuilder ans = new StringBuilder();
    ans.append("\t<animate attributeType=\"xml\" attributeName=").append(quote(attrName));
    ans.append(" from=").append(quote(Float.toString(start)));
    ans.append(" to=").append(quote(Float.toString(end)));
    ans.append(" begin=").append(quote(stick * tickDur + "ms"));
    ans.append(" dur=").append(quote((etick - stick) * tickDur + "ms"));
    ans.append(" fill=\"freeze\"");
    ans.append(" />");
    return ans.toString();
  }

  private String createSVGColorAnimation(int stick, int etick, Color sc, Color ec) {
    StringBuilder ans = new StringBuilder();
    ans.append("\t<animate attributeType=\"xml\" attributeName=\"fill\"");
    ans.append(" from=").append(colorWrap(sc.getRed(), sc.getGreen(), sc.getBlue()));
    ans.append(" to=").append(colorWrap(ec.getRed(), ec.getGreen(), ec.getBlue()));
    ans.append(" begin=").append(quote(stick * this.tickDur + "ms"));
    ans.append(" dur=").append(quote((etick - stick) * this.tickDur + "ms"));
    ans.append(" fill=\"freeze\"");
    ans.append(" />");
    return ans.toString();
  }

  private String createSVGVisibilityAnimation(int stick, int etick) {
    StringBuilder ans = new StringBuilder();
    ans.append("\t<set attributeName=\"visibility\" to=\"visible\"");
    ans.append(" begin=").append(quote(stick * tickDur + "ms"));
    ans.append(" end=").append(quote(etick * tickDur + "ms"));
    ans.append(" />");
    return ans.toString();
  }

  private String colorWrap(int r, int g, int b) {
    return "\"rgb(" + r + ',' + g + ',' + b + ")\"";
  }

  private String constructHeader() {
    StringBuilder ans = new StringBuilder();
    ans.append("<svg width=\"");
    ans.append(model.getWidthOfAnimation() * 2);
    ans.append("\" height=\"");
    ans.append(model.getHeightOfAnimation() * 2);
    ans.append("\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">");
    ans.append('\n');
    return ans.toString();
  }

  private String quote(String in) {
    return '\"' + in + '\"';
  }

  private String constructTail() {
    return "\n</svg>";
  }

}

