import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import cs3500.animator.model.Position2D;
import cs3500.animator.model.Shape;
import cs3500.animator.model.ShapeType;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for the Shape class in the model.
 */
public class ShapeTest {

  private Shape r1;
  private Shape c1;
  private Shape o1;
  private Shape s1;

  @Before
  public void setup() {
    r1 = new Shape("R", ShapeType.RECTANGLE,
        new Position2D(1, 2), Color.BLACK, 3, 4);
    c1 = new Shape("C", ShapeType.OVAL,
        new Position2D(3, 4), Color.CYAN, 3, 3);
    o1 = new Shape("O", ShapeType.OVAL,
        new Position2D(5, 6), Color.BLUE, 11, 9);
    s1 = new Shape("S", ShapeType.RECTANGLE,
        new Position2D(7, 8), Color.GREEN, 123, 123);
  }

  @Test
  public void testConstructor() {
    Position2D pos = new Position2D(15, 4);
    Color c = new Color(134, 18, 2);
    //5, 5 for the 1d shapes
    //8, 1 for the 2d shapes
    //test bad ShapeType
    try {
      Shape s = new Shape("hi", null, pos, c, 5, 5);
      fail("expected constructor exception for shape, didn't get");
    } catch (IllegalArgumentException iae) {
      assertEquals("can't have null inputs", iae.getMessage());
    }
    //test null Position2D
    try {
      Shape ci = new Shape("there", ShapeType.OVAL, null, c, 5, 5);
      fail("Expected constructor iae exception for null position, didnt' get");
    } catch (IllegalArgumentException iae) {
      assertEquals("can't have null inputs", iae.getMessage());
    }
    //test null Color
    try {
      Shape o = new Shape("t", ShapeType.OVAL, pos, null, 8, 1);
      fail("Expected constructor iae exception for null color, didn't get");
    } catch (IllegalArgumentException iae) {
      assertEquals("can't have null inputs", iae.getMessage());
    }

    //test size list has negatives
    try {
      Shape s = new Shape("g", ShapeType.RECTANGLE, pos, c, -1, 1);
      fail("expected error when making square with negative size");
    } catch (IllegalArgumentException iae) {
      assertEquals("width or height can't be negative", iae.getMessage());
    }
    try {
      Shape s = new Shape("g", ShapeType.RECTANGLE, pos, c, 1, -1);
      fail("expected error when making square with negative size");
    } catch (IllegalArgumentException iae) {
      assertEquals("width or height can't be negative", iae.getMessage());
    }
  }


  @Test
  public void testCopyConstructors() {
    //test the position constructor
    try {
      Position2D ps = null;
      Shape s = new Shape(r1, ps);
      fail("expected exception with copy constructor for position");
    } catch (IllegalArgumentException iae) {
      assertEquals("can't have null inputs", iae.getMessage());
    }
    //test color constructor
    try {
      Color c = null;
      Shape s = new Shape(c1, c);
      fail("expected exception with copy constructor for color");
    } catch (IllegalArgumentException iae) {
      assertEquals("can't have null inputs", iae.getMessage());
    }
    //test size copy constructor
    try {
      Shape s = new Shape(r1, -5, 1);
      fail("expected exception with copy constructor for position");
    } catch (IllegalArgumentException iae) {
      assertEquals("width or height can't be negative", iae.getMessage());
    }
    //test size copy constructor
    try {
      Shape s = new Shape(r1, 0, -1);
      fail("expected exception with copy constructor for position");
    } catch (IllegalArgumentException iae) {
      assertEquals("width or height can't be negative", iae.getMessage());
    }
    Shape ms = new Shape(s1, new Position2D(5, 10));
    assertNotEquals(ms, s1);
    assertEquals(ms.getPosition(), new Position2D(5, 10));

    Shape cs = new Shape(c1, Color.RED);
    assertNotEquals(cs, c1);
    assertEquals(cs.getColor(), Color.RED);

    Shape ss = new Shape(s1, 4.3f, 125.4f);
    assertNotEquals(ss, s1);
    assertEquals(ss.getWidth(), 4.3f, 0.001);
    assertEquals(ss.getHeight(), 125.4f, 0.001);
  }


  @Test
  public void testEqualsAndHash() {
    //Testing the SQUARE
    Shape newSquare = new Shape("S", ShapeType.RECTANGLE,
        new Position2D(7, 8), Color.GREEN, 123, 123);
    Shape badSquare1 = new Shape("s", ShapeType.RECTANGLE,
        new Position2D(7, 8), Color.GREEN, 123, 123);
    Shape badSquare2 = new Shape("S", ShapeType.OVAL,
        new Position2D(7, 8), Color.GREEN, 123, 123);
    Shape badSquare3 = new Shape("S", ShapeType.RECTANGLE,
        new Position2D(6, 8), Color.GREEN, 123, 123);
    Shape badSquare4 = new Shape("S", ShapeType.RECTANGLE,
        new Position2D(7, 8), Color.MAGENTA, 123, 123);
    Shape square5 = new Shape("S", ShapeType.RECTANGLE,
        new Position2D(7, 8), Color.GREEN, 123, 123);
    assertEquals(s1, newSquare);
    assertEquals(newSquare, s1);
    assertNotEquals(s1, badSquare1);
    assertNotEquals(s1, badSquare2);
    assertNotEquals(s1, badSquare3);
    assertNotEquals(s1, badSquare4);
    assertEquals(s1, square5);

    //test hashcode, can only really test that two of the same object hash to the same thing
    assertEquals(s1.hashCode(), newSquare.hashCode());
  }

  @Test
  public void testGetShapeID() {
    assertEquals("R", r1.getShapeID());
    assertEquals("C", c1.getShapeID());
    assertEquals("O", o1.getShapeID());
    assertEquals("S", s1.getShapeID());
  }

  @Test
  public void testGetShapeType() {
    assertEquals(ShapeType.RECTANGLE, r1.getShapeType());
    assertEquals(ShapeType.OVAL, c1.getShapeType());
    assertEquals(ShapeType.OVAL, o1.getShapeType());
    assertEquals(ShapeType.RECTANGLE, s1.getShapeType());
  }

  @Test
  public void testGetPosition() {
    assertEquals(new Position2D(1, 2), r1.getPosition());
    assertEquals(new Position2D(3, 4), c1.getPosition());
    assertEquals(new Position2D(5, 6), o1.getPosition());
    assertEquals(new Position2D(7, 8), s1.getPosition());
  }

  @Test
  public void testGetColor() {
    assertEquals(Color.BLACK, r1.getColor());
    assertEquals(Color.CYAN, c1.getColor());
    assertEquals(Color.BLUE, o1.getColor());
    assertEquals(Color.GREEN, s1.getColor());
  }

  @Test
  public void testGetSizes() {

    assertEquals(3, r1.getWidth(), 0.01);
    assertEquals(4, r1.getHeight(), 0.01);

    assertEquals(3, c1.getWidth(), 0.01);
    assertEquals(3, c1.getHeight(), 0.01);

    assertEquals(11, o1.getWidth(), 0.01);
    assertEquals(9, o1.getHeight(), 0.01);

    assertEquals(123, s1.getWidth(), 0.01);
    assertEquals(123, s1.getHeight(), 0.01);
  }

}
