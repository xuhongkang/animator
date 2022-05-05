import cs3500.animator.model.ShapeType;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.SimpleModel;
import cs3500.animator.model.Position2D;
import cs3500.animator.model.Shape;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * tests for animator model state interface.
 */
public class TestAnimatorModelState {

  private AnimatorModel testModel;
  private Shape s1;
  private Shape s2;

  @Before
  public void setup() {
    testModel = new SimpleModel(500, 600);
    s1 = new Shape("r1", ShapeType.RECTANGLE, new Position2D(3, 3),
        Color.BLACK, 10, 20);
    s2 = new Shape("o1", ShapeType.OVAL, new Position2D(30, 30),
        Color.BLACK, 10, 20);
  }


  @Test
  public void getShapes() {
    assertEquals(new ArrayList<Shape>(), testModel.getShapes(6));
    testModel.addShape(s1, 10, 20);
    testModel.addShape(s2, 15, 25);
    assertEquals(new ArrayList<Shape>(), testModel.getShapes(9));
    assertEquals(new ArrayList<Shape>(), testModel.getShapes(25));
    assertEquals(new ArrayList<>(Arrays.asList(s1)), testModel.getShapes(10));
    assertEquals(new ArrayList<>(Arrays.asList(s2)), testModel.getShapes(20));
    //tests getting the shapes in the correct order based on when they were added to the animation
    assertEquals(new ArrayList<>(Arrays.asList(s1, s2)), testModel.getShapes(15));
    testModel.addMoveCmd("r1", 11, 14,
        s1.getPosition(), new Position2D(500, 300));
    Shape s3 = new Shape(s1.getShapeID(), s1.getShapeType(), new Position2D(500, 300),
        s1.getColor(), s1.getWidth(), s1.getHeight());
    assertEquals(Arrays.asList(s3, s2), testModel.getShapes(15));
  }


  @Test
  public void testBadTPS() {
    testModel.addShape(s1, 5, 20);
    try {
      testModel.getShapeAtTime("r1", -1000);
      fail("expected exception when passing bad tps into model");
    } catch (IllegalArgumentException iae) {
      assertEquals("shape does not exist at that time", iae.getMessage());
    }
    //also try with zero
    try {
      testModel.getShapeAtTime("r1", 0);
      fail("expected exception when passing bad tps into model");
    } catch (IllegalArgumentException iae) {
      assertEquals("shape does not exist at that time", iae.getMessage());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void getShapeAtTimeError() {
    testModel.getShapeAtTime("a", 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getShapeAtTimeError2() {
    testModel.addShape(s1, 10, 20);
    testModel.getShapeAtTime("r1", 9);
    testModel.getShapeAtTime("r1", 21);
  }

  @Test
  public void getShapeAtTime() {
    testModel.addShape(s1, 10, 20);
    testModel.addMoveCmd("r1", 15, 18, new Position2D(3, 3),
        new Position2D(5, 5));
    assertEquals(testModel.getShapeAtTime("r1", 10), s1);
    assertEquals(testModel.getShapeAtTime("r1", 16),
        new Shape("r1", ShapeType.RECTANGLE, new Position2D(3.6666667f, 3.6666667f),
            Color.BLACK, 10, 20));
    assertEquals(testModel.getShapeAtTime("r1", 19),
        new Shape("r1", ShapeType.RECTANGLE, new Position2D(5, 5), Color.BLACK,
            10, 20));
    //test for invalid shapeID and time
    //test for different types of shapes, during the middle of commands and after commands
  }


  @Test(expected = IllegalArgumentException.class)
  public void getShapeAtTimeInvalidTime() {
    testModel.addShape(s1, 10, 15);
    testModel.getShapeAtTime("r1", 15);
    testModel.getShapeAtTime("r1", 9);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getShapeAtTimeInvalidID() {
    testModel.getShapeAtTime("circle", 3);
  }

  @Test
  public void getAnimationDuration() {
    assertEquals(0, testModel.getAnimationDuration());
    testModel.addShape(s1, 5, 50);
    assertEquals(50, testModel.getAnimationDuration());
  }

  @Test
  public void getShapesInAnimation() {
    assertEquals(testModel.getShapesInAnimation(), new ArrayList<>());
    testModel.addShape(s1, 0, 10);
    assertEquals(testModel.getShapesInAnimation(), new ArrayList<>(Arrays.asList("r1")));
    testModel.addShape(s2, 0, 10);
    assertEquals(testModel.getShapesInAnimation(), new ArrayList<>(Arrays.asList("r1", "o1")));
    //simple, should be a list of string, same length and content as the shapes added
  }

  @Test
  public void getWidthOfAnimation() {
    assertEquals(500, testModel.getWidthOfAnimation());
  }

  @Test
  public void getHeightOfAnimation() {
    assertEquals(600, testModel.getHeightOfAnimation());
  }
}