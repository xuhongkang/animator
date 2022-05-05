package cs3500.animator.model;

/**
 * Represents an animation on a shape that changes the scale/size. Has a start and end time and can
 * interpolate to find the width/height of the shape at any time in-between.
 */
public class ScaleAniCommand extends AbstractAniCommand {

  private final float sw; //starting width
  private final float sh; //starting height
  private final float ew; //ending width
  private final float eh; //ending height

  /**
   * Constructor for a scaling AniCommand. This animation changes the size of the shape via its
   * respective dimensions.
   *
   * @param sTime       the starting time for the animation.
   * @param eTime       the ending time for the animation.
   * @param startWidth  the starting width of the shape
   * @param startHeight the starting height of the shape
   * @param endWidth    the ending width of the shape
   * @param endHeight   the ending height of the shape
   */
  ScaleAniCommand(int sTime, int eTime, float startWidth, float startHeight,
      float endWidth, float endHeight) {
    super(sTime, eTime);
    this.sw = startWidth;
    this.sh = startHeight;
    this.ew = endWidth;
    this.eh = endHeight;
    //eg: make rectangle 10,10
  }

  @Override
  public Shape applyAnimation(Shape shape) {
    return new Shape(shape, this.ew, this.eh);
    //return new Shape(shape.getShapeID(), shape.getShapeType(), shape.getPosition(),
    // shape.getColor(), this.endScale);
  }

  @Override
  public Shape getIntermediateShape(Shape shape, int time) {
    float dw = ew - sw;
    float dh = eh - sh;
    float dt = super.getDT(time);

    return new Shape(shape, sw + (dt * dw), sh + (dt * dh));
    //return new Shape(shape.getShapeID(), shape.getShapeType(), shape.getPosition(),
    // shape.getColor(), newSizes);
  }


  @Override
  public boolean adjacentCommand(AniCommand that) {
    if (that == null) {
      return true;
    }
    if (that instanceof ScaleAniCommand) {
      //todo is this the correct usage?
      ScaleAniCommand other = (ScaleAniCommand) that;
      return Float.compare(this.sw, other.ew) == 0
          && Float.compare(this.sh, other.eh) == 0;
    }
    throw new IllegalArgumentException(
        "Can only check adjacentCommand for another ScaleAniCommand");
  }

  @Override
  public Float getStartWidth() {
    return sw;
  }

  @Override
  public Float getStartHeight() {
    return sh;
  }

  @Override
  public Float getEndWidth() {
    return ew;
  }

  @Override
  public Float getEndHeight() {
    return eh;
  }
}
