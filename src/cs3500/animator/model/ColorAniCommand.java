package cs3500.animator.model;

import java.awt.Color;

/**
 * Represents an animation on a shape that changes the color. Has a start and end time and can
 * interpolate to find the color of the shape at any time in-between.
 */
public class ColorAniCommand extends AbstractAniCommand {

  private final Color startColor;
  private final Color endColor;

  /**
   * Constructor for a ColorAniCommand for a shape. Animation changes the color of the shape during
   * the specified time.
   *
   * @param sTime  the starting time for the animation
   * @param eTime  the ending time for the animation
   * @param sColor the starting color for the animation
   * @param eColor the ending color for the animation
   */
  ColorAniCommand(int sTime, int eTime, Color sColor, Color eColor) {
    super(sTime, eTime);
    if (sColor == null || eColor == null) {
      throw new IllegalArgumentException("Colors cannot be null");
    }
    this.startColor = sColor;
    this.endColor = eColor;
  }

  @Override
  public Shape applyAnimation(Shape shape) {
    return new Shape(shape, endColor);
    //return new Shape(shape.getShapeID(), shape.getShapeType(), shape.getPosition(),
    // endColor, shape.getSizes());
  }

  @Override
  public Shape getIntermediateShape(Shape shape, int time) {
    int dr = endColor.getRed() - startColor.getRed();
    int dg = endColor.getGreen() - startColor.getGreen();
    int db = endColor.getBlue() - startColor.getBlue();
    float dt = super.getDT(time);
    Color newColor = new Color(startColor.getRed() + (int) Math.round(dr * dt),
        startColor.getGreen() + (int) Math.round(dg * dt),
        startColor.getBlue() + (int) Math.round(db * dt));
    return new Shape(shape, newColor);
    //return new Shape(shape.getShapeID(), shape.getShapeType(), shape.getPosition(),
    // newColor, shape.getSizes());
  }


  @Override
  public boolean adjacentCommand(AniCommand that) {
    if (that == null) {
      return true;
    }
    if (that instanceof ColorAniCommand) {
      return this.startColor.equals(((ColorAniCommand) that).endColor);
    }
    throw new IllegalArgumentException(
        "can only check adjacentCommand for another ColorAniCommand");
  }

  @Override
  public Color getStartColor() {
    return startColor;
  }

  @Override
  public Color getEndColor() {
    return endColor;
  }
}
