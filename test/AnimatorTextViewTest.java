import cs3500.animator.model.Position2D;
import cs3500.animator.model.Shape;
import cs3500.animator.model.ShapeType;
import cs3500.animator.model.SimpleModel;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.view.SimpleAnimatorView;
import cs3500.animator.view.TextualAnimatorView;
import cs3500.animator.view.TextAnimatorView;
import cs3500.animator.view.AnimatorGraphicsView;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Testing class for the animator text view.
 */
public class AnimatorTextViewTest {

  private TextualAnimatorView view;
  private AnimatorModel m;
  private Shape s1;
  private Shape r1;
  private TextualAnimatorView newView;

  @Before
  public void setup() {

    m = new SimpleModel(100, 100);
    s1 = new Shape("S", ShapeType.RECTANGLE,
        new Position2D(0, 0), Color.CYAN, 5, 5);
    r1 = new Shape("R", ShapeType.RECTANGLE,
        new Position2D(20, 25), Color.BLACK, 5, 10);
    view = new TextAnimatorView(m, 1000);
    newView = new TextAnimatorView(m, 1000);
  }

  @Test
  public void constructorErrors() {
    //null model
    try {
      SimpleAnimatorView view = new TextAnimatorView(null, 100);
      fail("expected constructor exception for null model, didn't get");
    } catch (IllegalArgumentException iae) {
      assertEquals("model cannot be null", iae.getMessage());
    }
    //tps <=0
    try {
      SimpleAnimatorView view = new TextAnimatorView(m, 0);
      fail("expected constructor exception for null model, didn't get");
    } catch (IllegalArgumentException iae) {
      assertEquals("tps can't be 0 or negative", iae.getMessage());
    }
  }


  @Test
  public void newTextView() {
    m.addShape(s1, 0, 100);
    m.addSizeCmd("S", 10, 20, 5, 5, 10, 10);
    m.addMoveCmd("S", 10, 20, new Position2D(0, 0), new Position2D(10, 10));
    m.addColorCmd("S", 10, 20, Color.CYAN, Color.white);
    assertEquals(newView.toString(), view.toString());
  }


  @Test
  public void completeOutput() {
    m.addShape(s1, 0, 100);
    m.addMoveCmd("S", 5, 6, new Position2D(0, 0), new Position2D(1, 1));
    m.addColorCmd("S", 7, 8, Color.CYAN, Color.white);
    m.addSizeCmd("S", 9, 10, 5, 5, 10, 10);
    String expected = "Shape: S, RECTANGLE, stime: 0.0, etime: 0.1\n" +
            "position(x,y): 0.0, 0.0 width: 5.0, height: 5.0 color(r,g,b): 0, 255, 255\n" +
            "Move Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tx \ty \t-> \tt \tx \ty \n" +
            "\t\t0,\t0.00,\t0.00 \t->\t0,\t1.00,\t1.00\n" +
            "\n" +
            "Color Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tr \tg \tb \t -> \tt \tr \tg \tb \n" +
            "\t\t0,\t0,\t255,\t255 \t->\t0,\t255,\t255,\t255\n" +
            "\n" +
            "Scale Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt\tsw\tsh \t -> \tt\tew\teh\n" +
            "\t\t0,\t5.00,\t5.00\t->\t0,\t10.00,\t10.00";
    assertTrue(view.toString().contains(expected));
  }

  @Test
  public void testTickConversion100() {
    AnimatorModel model = new SimpleModel(500, 600);
    //all ticks should be multiplied by 10 for ms
    SimpleAnimatorView v = new TextAnimatorView(model, 10);
    model.addShape(r1, 1, 50);
    model.addMoveCmd("R", 2, 3, new Position2D(20, 25), new Position2D(30, 35));
    model.addColorCmd("R", 4, 5, Color.BLACK, Color.WHITE);
    model.addSizeCmd("R", 6, 7, 5f, 10f, 5.6f, 10.7f);
    String output = v.toString();

    //tick 1 -> 10
    assertTrue(output.contains("0.1"));
    //tick 50 -> 500
    assertTrue(output.contains("5"));
  }

  @Test
  public void testTickConversion1() {
    AnimatorModel model = new SimpleModel(500, 600);
    //all ticks should be multiplied by 10 for ms
    SimpleAnimatorView v = new TextAnimatorView(model, 1);
    model.addShape(r1, 1, 50);
    model.addMoveCmd("R", 2, 3, new Position2D(20, 25), new Position2D(30, 35));
    model.addColorCmd("R", 4, 5, Color.BLACK, Color.WHITE);
    model.addSizeCmd("R", 6, 7, 5f, 10f, 5.6f, 10.7f);
    String output = v.toString();

    //tick 1 -> 10
    assertTrue(output.contains("1"));
    //tick 50 -> 500
    assertTrue(output.contains("5"));
    //move ticks
    assertTrue(output.contains("2"));
    assertTrue(output.contains("3"));
    //color ticks
    assertTrue(output.contains("4"));
    assertTrue(output.contains("5"));
    //view ticks
    assertTrue(output.contains("6"));
    assertTrue(output.contains("7"));

  }

  @Test
  public void testTickConversion2() {
    AnimatorModel model = new SimpleModel(500, 600);
    //all ticks should be multiplied by 10 for ms
    SimpleAnimatorView v = new TextAnimatorView(model, 2);
    model.addShape(r1, 1, 50);
    model.addMoveCmd("R", 2, 3, new Position2D(20, 25), new Position2D(30, 35));
    model.addColorCmd("R", 4, 5, Color.BLACK, Color.WHITE);
    model.addSizeCmd("R", 6, 7, 5f, 10f, 5.6f, 10.7f);
    String output = v.toString();

    //tick 1 -> 10
    assertTrue(output.contains("0.5"));
    //tick 50 -> 500
    assertTrue(output.contains("25"));
  }

  @Test
  public void animatorGraphicsViewConstructor() {
    try {
      new AnimatorGraphicsView(null);
      fail("a null model should throw an exception");
    } catch (IllegalArgumentException iae) {
      assertEquals(iae.getMessage(), "model can't be null");
    }
  }
}
