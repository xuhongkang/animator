import cs3500.animator.model.Position2D;
import cs3500.animator.model.Shape;
import cs3500.animator.model.ShapeType;
import cs3500.animator.model.SimpleModel;
import cs3500.animator.view.SVGAnimatorView;
import cs3500.animator.view.TextualAnimatorView;
import cs3500.animator.model.AnimatorModel;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests for SVG Animation.
 */
public class SVGAnimationViewTest {

  private AnimatorModel model;
  private TextualAnimatorView v;
  private Shape r;
  private Shape o;
  private Shape r2;
  private Shape o2;

  @Before
  public void setup() {
    this.model = new SimpleModel(500, 600);
    v = new SVGAnimatorView(model, 1000);
    r = new Shape("R", ShapeType.RECTANGLE, new Position2D(0, 0), Color.green, 10, 15);
    o = new Shape("O", ShapeType.OVAL, new Position2D(10, 10), Color.BLUE, 5, 5);
    r2 = new Shape("R2", ShapeType.RECTANGLE, new Position2D(10, 20), Color.BLACK, 3, 4);
    o2 = new Shape("O2", ShapeType.OVAL, new Position2D(20, 10), Color.BLUE, 5, 5);
  }

  @Test
  public void constructorError() {
    try {
      new SVGAnimatorView(null, 100);
      fail("expected an exception to be thrown because model is null");
    } catch (IllegalArgumentException iae) {
      assertEquals("model cannot be null", iae.getMessage());
    }
    try {
      new SVGAnimatorView(new SimpleModel(1, 1), 0);
      fail("expected an exception to be thrown because model is null");
    } catch (IllegalArgumentException iae) {
      assertEquals("tps cannot be 0 or negative", iae.getMessage());
    }
  }


  @Test
  public void widthAndHeight() {
    assertTrue(v.toString().contains("width=\"500\" height=\"600\""));
  }

  @Test
  public void close() {
    assertTrue(v.toString().contains("</svg>"));
  }

  @Test
  public void shapeNames() {
    assertFalse(v.toString().contains("ellipse"));
    assertFalse(v.toString().contains("rect"));
    model.addShape(r, 0, 10);
    assertEquals(2, countString(v.toString(), "rect"));
    model.addShape(r2, 5, 10);
    assertEquals(4, countString(v.toString(), "rect"));
    model.addShape(o, 10, 20);
    assertEquals(2, countString(v.toString(), "ellipse"));
    model.addShape(o2, 10, 20);
    assertEquals(4, countString(v.toString(), "ellipse"));

  }

  @Test
  public void shapeAttributes() {
    model.addShape(r, 0, 10);
    assertTrue(v.toString().contains("id=\"R\""));
    assertTrue(v.toString().contains("x=\"0.0\""));
    assertTrue(v.toString().contains("y=\"0.0\""));
    assertTrue(v.toString().contains("width=\"10.0\""));
    assertTrue(v.toString().contains("height=\"15.0\""));
    assertTrue(v.toString().contains("fill=\"rgb(0,255,0)\""));
    assertTrue(v.toString().contains("visibility=\"collapse\""));
  }

  @Test
  public void header() {
    assertTrue(v.toString().contains("<svg"));
  }

  @Test
  public void times() {
    model.addShape(r, 0, 10);
    assertTrue(v.toString().contains("begin=\"0ms\""));
    assertTrue(v.toString().contains("end=\"10ms\""));
  }

  @Test
  public void moveX() {
    model.addShape(r, 0, 10);
    model.addMoveCmd("R", 1, 2, new Position2D(0, 0), new Position2D(1, 1));
    assertTrue(v.toString().contains("attributeName=\"x\""));
    assertTrue(v.toString().contains("from=\"0.0\""));
    assertTrue(v.toString().contains("to=\"1.0\""));
    assertTrue(v.toString().contains("begin=\"1ms\""));
    assertTrue(v.toString().contains("dur=\"1ms\""));
  }

  @Test
  public void moveY() {
    model.addShape(r, 0, 10);
    model.addMoveCmd("R", 1, 2, new Position2D(0, 0), new Position2D(1, 5));

    assertTrue(v.toString().contains("attributeName=\"y\""));
    assertTrue(v.toString().contains("from=\"0.0\""));
    assertTrue(v.toString().contains("to=\"5.0\""));
    assertTrue(v.toString().contains("begin=\"1ms\""));
    assertTrue(v.toString().contains("dur=\"1ms\""));
  }

  @Test
  public void color() {
    model.addShape(r, 0, 10);
    model.addColorCmd("R", 1, 2, Color.green, Color.BLACK);
    assertTrue(v.toString().contains("attributeName=\"fill\""));
    assertTrue(v.toString().contains("from=\"rgb(0,255,0)\""));
    assertTrue(v.toString().contains("to=\"rgb(0,0,0)\""));
    assertTrue(v.toString().contains("begin=\"1ms\""));
    assertTrue(v.toString().contains("dur=\"1ms\""));
  }

  @Test
  public void width() {
    model.addShape(r, 0, 10);
    model.addSizeCmd("R", 1, 2, 10, 15, 20, 25);
    assertTrue(v.toString().contains("attributeName=\"width\""));
    assertTrue(v.toString().contains("from=\"10.0\""));
    assertTrue(v.toString().contains("to=\"20.0\""));
    assertTrue(v.toString().contains("begin=\"1ms\""));
    assertTrue(v.toString().contains("dur=\"1ms\""));
  }

  @Test
  public void height() {
    model.addShape(r, 0, 10);
    model.addSizeCmd("R", 1, 2, 10, 15, 20, 25);
    assertTrue(v.toString().contains("attributeName=\"height\""));
    assertTrue(v.toString().contains("from=\"15.0\""));
    assertTrue(v.toString().contains("to=\"25.0\""));
    assertTrue(v.toString().contains("begin=\"1ms\""));
    assertTrue(v.toString().contains("dur=\"1ms\""));
  }

  @Test
  public void numCommands() {
    model.addShape(r, 0, 10);
    assertFalse(v.toString().contains("<animate attributeType=\"xml\""));
    model.addMoveCmd("R", 2, 5, new Position2D(0, 0), new Position2D(10, 10));
    assertEquals(2, countString(v.toString(), "<animate attributeType=\"xml\""));
    model.addColorCmd("R", 3, 4, Color.GREEN, Color.BLACK);
    assertEquals(3, countString(v.toString(), "<animate attributeType=\"xml\""));
    model.addSizeCmd("R", 7, 8, 10, 15, 15, 25);
    assertEquals(5, countString(v.toString(), "<animate attributeType=\"xml\""));
  }

  private int countString(String s, String target) {
    int lastIndex = 0;
    int count = 0;
    while (lastIndex != -1) {
      lastIndex = s.indexOf(target, lastIndex);
      if (lastIndex != -1) {
        count++;
        lastIndex += target.length();
      }
    }
    return count;
  }
}
