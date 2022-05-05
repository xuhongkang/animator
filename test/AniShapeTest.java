import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs3500.animator.model.AniShape;
import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.Position2D;
import cs3500.animator.model.Shape;
import cs3500.animator.model.ShapeType;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.SimpleModel;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for an Animated Shape.
 */
public class AniShapeTest {
  private Shape r1;
  private Shape r2;
  private Shape o1;
  private Shape o2;
  private AniShape asR1;
  private AniShape asR2;
  private AniShape asO1;
  private AniShape asO2;
  private List<AniShape> asList;


  @Before
  public void setup() {
    r1 = new Shape("r1", ShapeType.RECTANGLE,
        new Position2D(1.4f, 2.3f), Color.BLACK, 6, 7);
    r2 = new Shape("r2", ShapeType.RECTANGLE,
        new Position2D(145.2f, -84.3f), Color.CYAN, 25, 15);
    o1 = new Shape("o1", ShapeType.OVAL,
        new Position2D(7.3f, 9.0f), Color.MAGENTA, 5, 10);
    o2 = new Shape("o2", ShapeType.OVAL,
        new Position2D(-87.9f, 34.5f), Color.WHITE, 20, 33);
    asR1 = new AnimatedShape(5, 123, r1);
    asR2 = new AnimatedShape(20, 124, r2);
    asO1 = new AnimatedShape(0, 3, o1);
    asO2 = new AnimatedShape(2, 15, o2);

    asList = Arrays.asList(asR1, asR2, asO1, asO2);

  }

  @Test
  public void testConstructor() {
    //before has successfull constructor tests
    AniShape tmp;
    //test for negative times
    try {
      tmp = new AnimatedShape(-1, 5, o1);
      fail("expected exception with AniShape with negative start time");
    } catch (IllegalArgumentException iae) {
      assertEquals("invalid time for shape to exist", iae.getMessage());
    }
    try {
      tmp = new AnimatedShape(20, -15, r2);
      fail("expected exception with AniShape with negative start time");
    } catch (IllegalArgumentException iae) {
      assertEquals("invalid time for shape to exist", iae.getMessage());
    }

    //test for etime before start time
    try {
      tmp = new AnimatedShape(20, 1, r1);
      fail("expected exception when creating AnimatedShape with endtime before start time");
    } catch (IllegalArgumentException iae) {
      assertEquals("invalid time for shape to exist", iae.getMessage());
    }

    //null shape
    try {
      tmp = new AnimatedShape(5, 6, null);
      fail("expected exception when creating AnimatedShape with null shape");
    } catch (IllegalArgumentException iae) {
      assertEquals("shape cannot be null", iae.getMessage());
    }
  }

  @Test
  public void testGetTimeMethods() {

    int[] stimeArr = {5, 20, 0, 2};
    int[] etimeArr = {123, 124, 3, 15};

    for (int i = 0; i < 4; i++) {
      assertEquals(stimeArr[i], asList.get(i).getStartTime());
      assertEquals(etimeArr[i], asList.get(i).getEndTime());
    }

  }

  @Test
  public void testGetStartingShape() {
    assertEquals(r1, asR1.getStartingShape());
    assertEquals(r2, asR2.getStartingShape());
    assertEquals(o1, asO1.getStartingShape());
    assertEquals(o2, asO2.getStartingShape());
  }

  @Test
  public void testGetCommandsOnShape() {
    AnimatorModel model = new SimpleModel(500, 400);
    model.addShape(r1, asR1.getStartTime(), asR1.getEndTime());
    model.addShape(r2, asR2.getStartTime(), asR2.getEndTime());
    model.addShape(o1, asO1.getStartTime(), asO1.getEndTime());
    model.addShape(o2, asO2.getStartTime(), asO2.getEndTime());
    model.addMoveCmd("r1", 10, 20, r1.getPosition(), new Position2D(-25, 17));
    model.addColorCmd("r2", 51, 67, r2.getColor(), Color.GREEN);
    model.addSizeCmd("o2", 2, 15, o2.getWidth(), o2.getHeight(), 4, 5);
    model.addMoveCmd("o2", 3, 5, o2.getPosition(), new Position2D(-1, -3));

    assertEquals(1, model.getShapeAsAniShape("r1").getCommandsOnShape().size());
    assertEquals(1, model.getShapeAsAniShape("r2").getCommandsOnShape().size());
    assertEquals(0, model.getShapeAsAniShape("o1").getCommandsOnShape().size());
    assertEquals(2, model.getShapeAsAniShape("o2").getCommandsOnShape().size());

  }


}
