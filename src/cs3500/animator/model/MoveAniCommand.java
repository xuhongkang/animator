package cs3500.animator.model;

/**
 * Represents an animation on a shape that changes the position. Has a start and end time and can
 * interpolate to find the position of the shape at any time in-between.
 */
public class MoveAniCommand extends AbstractAniCommand {

  private final Position2D startPos;
  private final Position2D endPos; //the position of the shape at the end time

  /**
   * Constructor for a moveAniCommand for a shape. Animation changes the position of the shape.
   *
   * @param sTime the time at which this move command starts
   * @param eTime the time (in ticks) at which this move command completes
   * @param sPos  the starting position of the shape
   * @param ePos  the ending position of the shape
   */
  MoveAniCommand(int sTime, int eTime, Position2D sPos, Position2D ePos) {
    super(sTime, eTime);
    if (sPos == null || ePos == null) {
      throw new IllegalArgumentException("Positions cannot be null!");
    }
    this.startPos = sPos;
    this.endPos = ePos;
  }

  /**
   * Alternative MoveAniCommand Copy Constructor.
   * @param cmd is the move ani command to copy
   * @param xOffset is the x offset
   * @param yOffset is the y offset
   */
  MoveAniCommand(MoveAniCommand cmd, float xOffset, float yOffset) {
    super(cmd.startTime, cmd.endTime);
    this.startPos = new Position2D(cmd.startPos.getX() + xOffset, cmd.startPos.getY() + yOffset);
    this.endPos = new Position2D(cmd.endPos.getX() + xOffset, cmd.endPos.getY() + yOffset);
  }

  @Override
  public Shape applyAnimation(Shape shape) {
    return new Shape(shape, endPos);
    //return new Shape(shape.getShapeID(), shape.getShapeType(), endPos, shape.getColor(),
    // shape.getSizes());
    //return new Shape(shape.getShapeID(), shape.getShapeType(), endPos, shape.getColor(),
    // shape.getSizes());

  }

  @Override
  public Shape getIntermediateShape(Shape shape, int time) {
    float dx = endPos.getX() - startPos.getX();
    float dy = endPos.getY() - startPos.getY();
    float dt = super.getDT(time);
    //stime = 10, etime = 20, time = 15
    //5 / 10 = 0.5

    Position2D newPos = new Position2D((startPos.getX() + dx * dt), (startPos.getY() + dy * dt));
    return new Shape(shape, newPos);
    //return new Shape(shape.getShapeID(), shape.getShapeType(), newPos, shape.getColor(),
    // shape.getSizes());
  }

  @Override
  public boolean adjacentCommand(AniCommand that) {
    if (that == null) {
      return true;
    }
    if (that instanceof MoveAniCommand) {
      MoveAniCommand other = (MoveAniCommand) that;
      return this.startPos.equals(other.endPos);
    }
    throw new IllegalArgumentException("can only check adjacentCommand for another MoveAniCommand");
  }

  @Override
  public Position2D getStartPos() {
    return startPos;
  }

  @Override
  public Position2D getEndPos() {
    return endPos;
  }
}
