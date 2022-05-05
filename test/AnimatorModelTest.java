import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.ShapeType;
import cs3500.animator.model.Shape;
import cs3500.animator.model.SimpleModel;
import cs3500.animator.model.Position2D;

import java.util.List;

import cs3500.animator.view.SimpleAnimatorView;
import cs3500.animator.view.TextAnimatorView;
import org.junit.Before;
import org.junit.Test;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * testing for animator model interface.
 */
public class AnimatorModelTest {

  private AnimatorModel testModel;
  private SimpleAnimatorView view;
  private Shape r1;
  private Shape o1;

  @Before
  public void setup() {
    testModel = new SimpleModel(1000, 1000);
    r1 = new Shape("r1", ShapeType.RECTANGLE, new Position2D(30, 30),
        Color.BLACK, 4, 5);
    o1 = new Shape("o1", ShapeType.OVAL, new Position2D(30, 30),
        Color.BLACK, 4, 5);
    view = new TextAnimatorView(testModel, 1);
  }

  @Test
  public void emptyModel() {
    testModel = new SimpleModel(100, 100);
    assertEquals("", view.toString());
  }

  @Test
  public void addShape() {
    assertEquals(testModel.getShapesInAnimation(), new ArrayList<String>());
    testModel.addShape(r1, 0, 30);
    assertEquals(testModel.getShapesInAnimation(), new ArrayList<>(Arrays.asList("r1")));
    testModel.addShape(o1, 0, 30);
    assertEquals(testModel.getShapesInAnimation(), new ArrayList<>(Arrays.asList("r1", "o1")));

    //test that adding the same shape again raises exception
    try {
      testModel.addShape(r1, 5, 20);
      fail("expected exception when adding shape with id already present in animation");
    } catch (IllegalArgumentException iae) {
      assertEquals("shape already exists in animation", iae.getMessage());
    }
  }

  @Test
  public void getTempoAt() {
    this.testModel.addSlowMo(2, 10, 6);
    assertEquals(this.testModel.getTempoAt(8), 6);
  }

  @Test
  public void addLayer() {
    ArrayList<String> shapeList = new ArrayList<>();
    shapeList.add(r1.getShapeID());
    this.testModel.addLayer("layer1", shapeList);
    this.testModel.addShape(r1, 1, 8);
    this.testModel.addShape(o1, 5, 10);
    List<Shape> shapes = this.testModel.getShapes(6);
    assertEquals(shapes.get(0), o1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addSlowMo() {
    this.testModel.addSlowMo(2, 10, 6);
    this.testModel.addSlowMo(8, 12, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addSlowMoIllegal() {
    this.testModel.addSlowMo(-1, 10, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addSlowMoIllegal2() {
    this.testModel.addSlowMo(1, -8, 6);
  }


  @Test(expected = IllegalArgumentException.class)
  public void addShapeError() {
    testModel.addShape(null, 2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addShapeError2() {
    testModel.addShape(r1, 4, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addShapeError3() {
    testModel.addShape(r1, -2, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addShapeError4() {
    testModel.addShape(r1, 5, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void removeShapeError() {
    testModel.removeShape("r1");
  }

  @Test
  public void removeShape() {
    testModel.addShape(r1, 2, 5);
    testModel.removeShape("r1");
    assertEquals(testModel.getShapesInAnimation(), new ArrayList<String>());
    testModel.addShape(r1, 2, 5);
    testModel.addShape(o1, 2, 5);
    testModel.removeShape("r1");
    assertEquals(testModel.getShapesInAnimation(), new ArrayList<>(Arrays.asList("o1")));
  }

  @Test(expected = IllegalArgumentException.class)
  public void simpleModelConstructor1() {
    new SimpleModel(-2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void simpleModelConstructor2() {
    new SimpleModel(2, -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addMoveCmdError1() {
    testModel.addShape(r1, 0, 10);
    testModel.addMoveCmd("r1", 0, 11, new Position2D(30, 30),
        new Position2D(1, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addMoveCmdError2() {
    testModel.addShape(r1, 5, 10);
    testModel.addMoveCmd("r1", 3, 10, new Position2D(30, 30),
        new Position2D(1, 2));
  }


  @Test
  public void addMoveCmd() {
    SimpleAnimatorView view = new TextAnimatorView(testModel, 1);
    testModel.addShape(r1, 0, 100);
    testModel.addMoveCmd("r1", 1, 8, new Position2D(30, 30),
        new Position2D(100, 100));
    assertTrue(view.toString().contains("\t\tt \tx \ty \t-> \tt \tx \ty \n" +
            "\t\t1,\t30.00,\t30.00 \t->\t8,\t100.00,\t100.00"));
    testModel.addMoveCmd("r1", 9, 10,
        new Position2D(100, 100), new Position2D(9, 9));
    assertTrue(view.toString().contains("t \tx \ty \t-> \tt \tx \ty \n" +
            "\t\t1,\t30.00,\t30.00 \t->\t8,\t100.00,\t100.00\n" +
            "\t\t9,\t100.00,\t100.00 \t->\t10,\t9.00,\t9.00"));
  }


  @Test(expected = IllegalArgumentException.class)
  public void addColorCmdError() {
    testModel.addShape(r1, 0, 10);
    testModel.addColorCmd("r1", 0, 11, Color.BLACK, Color.MAGENTA);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addColorCmdError2() {
    testModel.addColorCmd("r1", 0, 1, Color.BLACK, Color.MAGENTA);
  }


  @Test
  public void addColorCmd() {
    SimpleAnimatorView view = new TextAnimatorView(testModel, 1);
    testModel.addShape(r1, 0, 100);
    testModel.addColorCmd("r1", 1, 8, Color.BLACK, Color.BLUE);
    assertTrue(view.toString().contains(
            "t \tr \tg \tb \t -> \tt \tr \tg \tb \n" +
                    "\t\t1,\t0,\t0,\t0 \t->\t8,\t0,\t0,\t255"));
    testModel.addColorCmd("r1", 8, 100, Color.BLUE, Color.GREEN);
    assertTrue(view.toString().contains(
        "t \tr \tg \tb \t -> \tt \tr \tg \tb \n" +
                "\t\t1,\t0,\t0,\t0 \t->\t8,\t0,\t0,\t255\n" +
                "\t\t8,\t0,\t0,\t255 \t->\t100,\t0,\t255,\t0"));
  }

  @Test
  public void addSizeCmd() {
    SimpleAnimatorView view = new TextAnimatorView(testModel, 1);
    testModel.addShape(r1, 0, 100);
    testModel.addSizeCmd("r1", 10, 20,
        4, 5, 8, 10);
    assertTrue(view.toString().contains("t\tsw\tsh \t -> \tt\tew\teh\n" +
            "\t\t10,\t4.00,\t5.00\t->\t20,\t8.00,\t10.00"));
    testModel.addSizeCmd("r1", 21, 30,
        8, 10, 4, 5);
    assertTrue(view.toString().contains("t\tsw\tsh \t -> \tt\tew\teh\n" +
            "\t\t10,\t4.00,\t5.00\t->\t20,\t8.00,\t10.00\n" +
            "\t\t21,\t8.00,\t10.00\t->\t30,\t4.00,\t5.00"));
  }

  @Test
  public void getPossibleShapesAndDefinitions() {
    assertEquals(testModel.getPossibleShapesAndDefinitions(),
        new ArrayList<>(Arrays.asList("Rectangle - 2 size dimensions",
            "Oval - 2 size dimensions", "Square - 1 size dimension",
            "Circle - 1 size dimension")));
  }

  @Test
  public void testBadMovements() {
    Position2D posa = new Position2D(30, 30);
    Position2D pos = new Position2D(5, 6);
    testModel.addShape(r1, 1, 11);
    testModel.addMoveCmd("r1", 1, 3, posa, new Position2D(5, 6));
    //test adding move cmd when another command is already happening
    try {
      testModel.addMoveCmd("r1", 2, 5, pos, posa);
      fail("expected exception when getting overlapping move commands");
    } catch (IllegalArgumentException iae) {
      assertEquals("Command must occur after previously added commands", iae.getMessage());
    }
    //test adding the move cmd where times are out of order
    try {
      testModel.addMoveCmd("r1", 5, 3, pos, posa);
      fail("expected error where stime is before etime");
    } catch (IllegalArgumentException iae) {
      assertEquals("animation cannot end before it starts", iae.getMessage());
    }
    //test adding move cmd where beginning state isn't the same
    try {
      testModel.addMoveCmd("r1", 4, 5, posa, pos);
      fail("expected iae when move cmd starting pos isn't the same as prev cmd");
    } catch (IllegalArgumentException iae) {
      assertEquals("cannot add command due to overlap in time or teleporting shape values",
          iae.getMessage());
    }
  }

  @Test
  public void testBadColorCmds() {
    Color c1 = new Color(0, 0, 0);
    Color c2 = new Color(255, 255, 255);
    testModel.addShape(o1, 5, 50);
    testModel.addColorCmd("o1", 10, 20, c1, c2);

    //test add cmd before already existing command
    try {
      testModel.addColorCmd("o1", 6, 7, c2, c1);
      fail("expected iae with invalid addColorCmd");
    } catch (IllegalArgumentException iae) {
      assertEquals("Command must occur after previously added commands", iae.getMessage());
    }

    //test add where another color is already happening
    try {
      testModel.addColorCmd("o1", 15, 30, c2, c1);
      fail("expected iae with invalid addColorCmd");
    } catch (IllegalArgumentException iae) {
      assertEquals("Command must occur after previously added commands", iae.getMessage());
    }

    //test adding where color scale of prev isn't the same
    try {
      testModel.addColorCmd("o1", 30, 50, c1, c2);
      fail("expected iae with invalid addColorCmd");
    } catch (IllegalArgumentException iae) {
      assertEquals("cannot add command due to overlap in time or teleporting shape values",
          iae.getMessage());
    }
    //test bad color dest
    try {
      testModel.addColorCmd("o1", 30, 50, c1, new Color(0, 500, 2));
      fail("expected iae with invalid addColorCmd");
    } catch (IllegalArgumentException iae) {
      assertEquals("Color parameter outside of expected range: Green", iae.getMessage());
    }
  }

  @Test
  public void testBadScaleCmds() {
    testModel.addShape(o1, 5, 50);
    List<Integer> sl1 = Arrays.asList(4, 5);
    List<Integer> sl2 = Arrays.asList(3, 6);

    //test add scale cmd where cmd happens before shape exists
    try {
      testModel.addSizeCmd("o1", 4, 5, 4, 5, 3, 6);
      fail("expected iae when adding invalid scale command");
    } catch (IllegalArgumentException iae) {
      assertEquals("shape doesn't exist in the given time", iae.getMessage());
    }

    testModel.addSizeCmd("o1", 10, 20, 4, 5, 3, 6);

    //test add where another scale is already happening
    try {
      testModel.addSizeCmd("o1", 19, 40, 3, 6, 4, 5);
      fail("expected iae when adding invalid scale command");
    } catch (IllegalArgumentException iae) {
      assertEquals("Command must occur after previously added commands", iae.getMessage());
    }
    //test adding where ending scale of prev isn't the same
    try {
      testModel.addSizeCmd("o1", 20, 20, 4, 5, 3, 6);
      fail("expected iae when adding invalid scale command");
    } catch (IllegalArgumentException iae) {
      assertEquals("cannot add command due to overlap in time or teleporting shape values",
          iae.getMessage());
    }
    //test adding where scale goes negative
    try {
      testModel.addSizeCmd("o1", 20, 20, 3, 6, -1, 5);
      fail("expected iae when adding negative scale integer");
    } catch (IllegalArgumentException iae) {
      assertEquals("size can't be negative", iae.getMessage());
    }
    try {
      testModel.addSizeCmd("o1", 20, 20, 3, 6, 1, -5);
      fail("expected iae when adding negative scale integer");
    } catch (IllegalArgumentException iae) {
      assertEquals("size can't be negative", iae.getMessage());
    }
  }

  @Test
  public void testComplexCmds() {
    SimpleAnimatorView view = new TextAnimatorView(testModel, 1);
    testModel.addShape(r1, 5, 50);
    testModel.addShape(o1, 30, 80);
    //test adding a color cmd happening at the same time as a move cmd
    testModel.addMoveCmd("r1", 6, 10, new Position2D(30, 30), new Position2D(40, 40));
    testModel.addColorCmd("r1", 7, 11, Color.BLACK, Color.MAGENTA);
    String expected = "Shape: r1, RECTANGLE, stime: 5.0, etime: 50.0\n" +
            "position(x,y): 30.0, 30.0 width: 4.0, height: 5.0 color(r,g,b): 0, 0, 0\n" +
            "Move Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tx \ty \t-> \tt \tx \ty \n" +
            "\t\t6,\t30.00,\t30.00 \t->\t10,\t40.00,\t40.00\n" +
            "\n" +
            "Color Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tr \tg \tb \t -> \tt \tr \tg \tb \n" +
            "\t\t7,\t0,\t0,\t0 \t->\t11,\t255,\t0,\t255\n" +
            "\n" +
            "Scale Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt\tsw\tsh \t -> \tt\tew\teh\n" +
            "Shape: o1, OVAL, stime: 30.0, etime: 80.0\n" +
            "position(x,y): 30.0, 30.0 width: 4.0, height: 5.0 color(r,g,b): 0, 0, 0\n" +
            "Move Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tx \ty \t-> \tt \tx \ty \n" +
            "\n" +
            "Color Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tr \tg \tb \t -> \tt \tr \tg \tb \n" +
            "\n" +
            "Scale Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt\tsw\tsh \t -> \tt\tew\teh\n" +
            "\n" +
            "Shape: r1, RECTANGLE, stime: 5.0, etime: 50.0\n" +
            "position(x,y): 30.0, 30.0 width: 4.0, height: 5.0 color(r,g,b): 0, 0, 0\n" +
            "Move Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tx \ty \t-> \tt \tx \ty \n" +
            "\t\t6,\t30.00,\t30.00 \t->\t10,\t40.00,\t40.00\n" +
            "\n" +
            "Color Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tr \tg \tb \t -> \tt \tr \tg \tb \n" +
            "\t\t7,\t0,\t0,\t0 \t->\t11,\t255,\t0,\t255\n" +
            "\n" +
            "Scale Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt\tsw\tsh \t -> \tt\tew\teh\n" +
            "Shape: o1, OVAL, stime: 30.0, etime: 80.0\n" +
            "position(x,y): 30.0, 30.0 width: 4.0, height: 5.0 color(r,g,b): 0, 0, 0\n" +
            "Move Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tx \ty \t-> \tt \tx \ty \n" +
            "\n" +
            "Color Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tr \tg \tb \t -> \tt \tr \tg \tb \n" +
            "\n" +
            "Scale Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt\tsw\tsh \t -> \tt\tew\teh";
    assertTrue(view.toString().contains(expected));
    //test instant command and cmd right after prev command
    testModel.addColorCmd("r1", 11, 11, Color.MAGENTA, Color.CYAN);
    expected = "Shape: r1, RECTANGLE, stime: 5.0, etime: 50.0\n" +
            "position(x,y): 30.0, 30.0 width: 4.0, height: 5.0 color(r,g,b): 0, 0, 0\n" +
            "Move Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tx \ty \t-> \tt \tx \ty \n" +
            "\t\t6,\t30.00,\t30.00 \t->\t10,\t40.00,\t40.00\n" +
            "\n" +
            "Color Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tr \tg \tb \t -> \tt \tr \tg \tb \n" +
            "\t\t7,\t0,\t0,\t0 \t->\t11,\t255,\t0,\t255\n" +
            "\t\t11,\t255,\t0,\t255 \t->\t11,\t0,\t255,\t255\n" +
            "\n" +
            "Scale Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt\tsw\tsh \t -> \tt\tew\teh\n" +
            "Shape: o1, OVAL, stime: 30.0, etime: 80.0\n" +
            "position(x,y): 30.0, 30.0 width: 4.0, height: 5.0 color(r,g,b): 0, 0, 0\n" +
            "Move Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tx \ty \t-> \tt \tx \ty \n" +
            "\n" +
            "Color Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tr \tg \tb \t -> \tt \tr \tg \tb \n" +
            "\n" +
            "Scale Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt\tsw\tsh \t -> \tt\tew\teh\n" +
            "\n" +
            "Shape: r1, RECTANGLE, stime: 5.0, etime: 50.0\n" +
            "position(x,y): 30.0, 30.0 width: 4.0, height: 5.0 color(r,g,b): 0, 0, 0\n" +
            "Move Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tx \ty \t-> \tt \tx \ty \n" +
            "\t\t6,\t30.00,\t30.00 \t->\t10,\t40.00,\t40.00\n" +
            "\n" +
            "Color Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tr \tg \tb \t -> \tt \tr \tg \tb \n" +
            "\t\t7,\t0,\t0,\t0 \t->\t11,\t255,\t0,\t255\n" +
            "\t\t11,\t255,\t0,\t255 \t->\t11,\t0,\t255,\t255\n" +
            "\n" +
            "Scale Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt\tsw\tsh \t -> \tt\tew\teh\n" +
            "Shape: o1, OVAL, stime: 30.0, etime: 80.0\n" +
            "position(x,y): 30.0, 30.0 width: 4.0, height: 5.0 color(r,g,b): 0, 0, 0\n" +
            "Move Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tx \ty \t-> \tt \tx \ty \n" +
            "\n" +
            "Color Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt \tr \tg \tb \t -> \tt \tr \tg \tb \n" +
            "\n" +
            "Scale Commands\n" +
            "\t\tStart\t->\tEnd\n" +
            "\t------------------\n" +
            "\t\tt\tsw\tsh \t -> \tt\tew\teh";
    assertTrue(view.toString().contains(expected));
  }

  @Test
  public void testRemoveCommands() {
    SimpleAnimatorView view = new TextAnimatorView(testModel, 1);
    try {
      testModel.removeAnimations("hi there");
      fail("expected exception when trying to remove commands from not present shape");
    } catch (IllegalArgumentException iae) {
      assertEquals("shape ID doesn't exist", iae.getMessage());
    }
    testModel.addShape(r1, 5, 50);
    testModel.addMoveCmd("r1", 10, 20, new Position2D(30, 30), new Position2D(40, 40));
    String s1 = view.toString();
    testModel.removeAnimations("r1");
    String s2 = view.toString();
    assertNotEquals(s1, s2);
  }

  @Test
  public void testDurationExtension() {
    assertEquals(0, testModel.getAnimationDuration());
    testModel.addShape(r1, 50, 100);
    assertEquals(100, testModel.getAnimationDuration());
    testModel.addShape(o1, 10, 20);
    assertEquals(100, testModel.getAnimationDuration());
    Shape s = new Shape("o", ShapeType.OVAL, new Position2D(5, 10),
        Color.CYAN, 4.3f, 10.2f);
    testModel.addShape(s, 75, 146);
    assertEquals(146, testModel.getAnimationDuration());
  }

  @Test
  public void testFirstColorCmdException() {
    testModel.addShape(r1, 0, 100);
    try {
      testModel.addColorCmd("r1", 5, 10, Color.BLUE, Color.GREEN);
      fail("expected exception when adding first color command from different color");
    } catch (IllegalArgumentException iae) {
      assertEquals("starting shape color doesn't agree with cmd", iae.getMessage());
    }


  }

}
