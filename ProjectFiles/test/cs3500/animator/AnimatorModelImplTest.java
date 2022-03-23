package cs3500.animator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.BasicColor;
import cs3500.animator.model.BasicShape;
import cs3500.animator.model.Motion;
import cs3500.animator.model.ShapeState;

public class AnimatorModelImplTest {

  private AnimatorModel model;
  private Motion.MotionBuilder motionBuilder;
  private Motion.MotionBuilder secondMotionBuilder;

  @Before
  public void setup() {
    this.model = new AnimatorModelImpl();
    this.motionBuilder = new Motion.MotionBuilder();
    this.secondMotionBuilder = new Motion.MotionBuilder();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateShapeEmptyTag() {
    BasicShape bshape = BasicShape.OVAL;
    model.createShape("", bshape);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateShapeNullTag() {
    model.createShape(null, BasicShape.OVAL);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateShapeNullBShape() {
    model.createShape("R", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateShapeOccupiedTag() {
    model.createShape("R", BasicShape.RECTANGLE);
    model.createShape("R", BasicShape.OVAL);
  }

  @Test
  public void testSuccessfulCreateShape() {
    model.createShape("R", BasicShape.RECTANGLE);

    HashMap<String, BasicShape> shapes = model.getShapes();

    assertEquals(BasicShape.RECTANGLE, shapes.get("R"));

    HashMap<String, ArrayList<ShapeState>> animations = model.getAnimations();

    assertEquals(new ArrayList<ShapeState>(), animations.get("R"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeleteShapeEmptyTag() {
    model.deleteShape("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeleteShapeNullTag() {
    model.deleteShape(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTagNotFromShapesDS() {
    model.createShape("R", BasicShape.RECTANGLE);
    model.deleteShape("C");
  }

  @Test
  public void testSuccessfulDeleteShape() {
    model.createShape("R", BasicShape.RECTANGLE);
    model.createShape("O", BasicShape.OVAL);

    model.deleteShape("O");

    HashMap<String, BasicShape> shapes = model.getShapes();

    assertEquals(1, shapes.size());
    assertEquals(BasicShape.RECTANGLE, shapes.get("R"));
    assertEquals(null, shapes.get("O"));

    HashMap<String, ArrayList<ShapeState>> animations = model.getAnimations();

    assertEquals(1, animations.size());
    assertEquals(null, animations.get("O"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullMotionTag() {
    Motion motion = new Motion(motionBuilder);
    model.addMotion(motion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTagNotFromShapes() {
    motionBuilder.setTag("R", BasicShape.RECTANGLE);
    Motion motion = new Motion(motionBuilder);
    model.addMotion(motion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShapeNotFromShapes() {
    motionBuilder.setTag("R", BasicShape.RECTANGLE);
    Motion motion = new Motion(motionBuilder);
    model.createShape("R", BasicShape.OVAL);

    model.addMotion(motion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullParametersAtBeginning() {
    model.createShape("R", BasicShape.RECTANGLE);

    motionBuilder.setTag("R", BasicShape.RECTANGLE);
    Motion motion = new Motion(motionBuilder);

    model.addMotion(motion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartMotionNullParameters() {
    motionBuilder.setTag("R", BasicShape.RECTANGLE);
    motionBuilder.setTime(3, 3);
    motionBuilder.setStartWH(3, 3);
    motionBuilder.setStartXY(3, 3);
    motionBuilder.setStartColor(new BasicColor("Red"));

    motionBuilder.setEndWH(3, 3);
    // motionBuilder.setEndXY(3, 3);
    motionBuilder.setEndColor(new BasicColor("Red"));

    Motion motion = new Motion(motionBuilder);

    model.createShape("R", BasicShape.RECTANGLE);

    model.addMotion(motion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSecondMotionInvalidParameterNullEndTime() {
    model.createShape("R", BasicShape.RECTANGLE);

    motionBuilder.setTime(0, 1);
    motionBuilder.setTag("R", BasicShape.RECTANGLE);
    motionBuilder.setStartWH(3, 3);
    motionBuilder.setStartXY(3, 3);
    motionBuilder.setStartColor(new BasicColor("Red"));
    motionBuilder.setEndWH(4, 4);
    motionBuilder.setEndXY(4, 4);
    motionBuilder.setEndColor(new BasicColor("Red"));

    Motion lastEnd = new Motion(motionBuilder);

    model.addMotion(lastEnd);

    secondMotionBuilder.setTag("R", BasicShape.RECTANGLE);

    Motion secondMotion = new Motion(secondMotionBuilder);

    model.addMotion(secondMotion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSecondMotionInvalidParameterNegEndTime() {
    model.createShape("R", BasicShape.RECTANGLE);

    motionBuilder.setTime(0, 1);
    motionBuilder.setTag("R", BasicShape.RECTANGLE);
    motionBuilder.setStartWH(3, 3);
    motionBuilder.setStartXY(3, 3);
    motionBuilder.setStartColor(new BasicColor("Red"));
    motionBuilder.setEndWH(4, 4);
    motionBuilder.setEndXY(4, 4);
    motionBuilder.setEndColor(new BasicColor("Red"));

    Motion lastEnd = new Motion(motionBuilder);

    model.addMotion(lastEnd);

    secondMotionBuilder.setTag("R", BasicShape.RECTANGLE);
    secondMotionBuilder.setTime(3, -2);

    Motion secondMotion = new Motion(secondMotionBuilder);

    model.addMotion(secondMotion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSecondMotionInvalidParameterNegStartTime() {
    model.createShape("R", BasicShape.RECTANGLE);

    motionBuilder.setTime(0, 1);
    motionBuilder.setTag("R", BasicShape.RECTANGLE);
    motionBuilder.setStartWH(3, 3);
    motionBuilder.setStartXY(3, 3);
    motionBuilder.setStartColor(new BasicColor("Red"));
    motionBuilder.setEndWH(4, 4);
    motionBuilder.setEndXY(4, 4);
    motionBuilder.setEndColor(new BasicColor("Red"));

    Motion lastEnd = new Motion(motionBuilder);

    model.addMotion(lastEnd);

    secondMotionBuilder.setTag("R", BasicShape.RECTANGLE);
    secondMotionBuilder.setTime(-3, 2);

    Motion secondMotion = new Motion(secondMotionBuilder);

    model.addMotion(secondMotion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSecondMotionInvalidParameterStBiggerThanEt() {
    model.createShape("R", BasicShape.RECTANGLE);

    motionBuilder.setTime(0, 1);
    motionBuilder.setTag("R", BasicShape.RECTANGLE);
    motionBuilder.setStartWH(3, 3);
    motionBuilder.setStartXY(3, 3);
    motionBuilder.setStartColor(new BasicColor("Red"));
    motionBuilder.setEndWH(4, 4);
    motionBuilder.setEndXY(4, 4);
    motionBuilder.setEndColor(new BasicColor("Red"));

    Motion lastEnd = new Motion(motionBuilder);

    model.addMotion(lastEnd);

    secondMotionBuilder.setTag("R", BasicShape.RECTANGLE);
    secondMotionBuilder.setTime(1000, 2);

    Motion secondMotion = new Motion(secondMotionBuilder);

    model.addMotion(secondMotion);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartDoesntMatchWithLastEnd() {
    model.createShape("R", BasicShape.RECTANGLE);

    motionBuilder.setTime(0, 1);
    motionBuilder.setTag("R", BasicShape.RECTANGLE);
    motionBuilder.setStartWH(3, 3);
    motionBuilder.setStartXY(3,3);
    motionBuilder.setStartColor(new BasicColor("Red"));
    motionBuilder.setEndWH(4, 4);
    motionBuilder.setEndXY(4, 4);
    motionBuilder.setEndColor(new BasicColor("Red"));

    Motion lastEnd = new Motion(motionBuilder);

    secondMotionBuilder.setTime(1, 2);
    secondMotionBuilder.setTag("R", BasicShape.RECTANGLE);
    secondMotionBuilder.setStartWH(1, 1);
    secondMotionBuilder.setStartXY(1, 1);
    secondMotionBuilder.setStartColor(new BasicColor("Red"));
    secondMotionBuilder.setEndWH(10, 10);
    secondMotionBuilder.setEndXY(10, 10);
    secondMotionBuilder.setEndColor(new BasicColor("Red"));

    Motion secondMotion = new Motion(secondMotionBuilder);

    model.addMotion(lastEnd);

    model.addMotion(secondMotion);
  }

  @Test
  public void testSuccessfulAddMotion() {
    model.createShape("R", BasicShape.RECTANGLE);

    motionBuilder.setTime(0, 1);
    motionBuilder.setTag("R", BasicShape.RECTANGLE);
    motionBuilder.setStartWH(3, 3);
    motionBuilder.setStartXY(3,3);
    motionBuilder.setStartColor(new BasicColor("Red"));
    motionBuilder.setEndWH(4, 4);
    motionBuilder.setEndXY(4, 4);
    motionBuilder.setEndColor(new BasicColor("Red"));

    Motion lastEnd = new Motion(motionBuilder);

    secondMotionBuilder.setTime(1, 2);
    secondMotionBuilder.setTag("R", BasicShape.RECTANGLE);
    secondMotionBuilder.setStartWH(4, 4);
    secondMotionBuilder.setStartXY(4, 4);
    secondMotionBuilder.setStartColor(new BasicColor("Red"));
    secondMotionBuilder.setEndWH(10, 10);
    secondMotionBuilder.setEndXY(10, 10);
    secondMotionBuilder.setEndColor(new BasicColor("Red"));

    Motion secondMotion = new Motion(secondMotionBuilder);

    model.addMotion(lastEnd);
    model.addMotion(secondMotion);

    ShapeState first = new ShapeState(0, 3, 3, 3, 3, new BasicColor("Red"));
    ShapeState second = new ShapeState(1, 4, 4, 4, 4, new BasicColor("Red"));
    ShapeState third = new ShapeState(2, 10, 10, 10, 10, new BasicColor("Red"));

    List<ShapeState> shapeStates = new ArrayList<ShapeState>();
    shapeStates.add(first);
    shapeStates.add(second);
    shapeStates.add(third);

    Set<String> tags = new HashSet<String>(Collections.singleton("R"));

    assertEquals(tags, model.getTags());
    assertEquals(shapeStates, model.getMotions("R"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReverseMotionEmptyTag() {
    model.removeLastMotion("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReverseMotionNullTag() {
    model.removeLastMotion(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReverseMotionTagNotFromShapes() {
    model.createShape("R", BasicShape.RECTANGLE);
    model.removeLastMotion("O");
  }

  @Test
  public void testSuccessfulReverseMotion() {
    model.createShape("R", BasicShape.RECTANGLE);
    model.removeLastMotion("R");


  }

  @Test
  public void testGetTags() {
    model.createShape("R", BasicShape.RECTANGLE);
    model.createShape("O", BasicShape.OVAL);

    String[] tags = {"R", "O"};
    Set<String> tagsSet = new HashSet<String>(Arrays.asList(tags));

    assertEquals(tagsSet, model.getTags());
  }

  @Test
  public void testGetMotions() {

    model.createShape("R", BasicShape.RECTANGLE);

    motionBuilder.setTime(0, 1);
    motionBuilder.setTag("R", BasicShape.RECTANGLE);
    motionBuilder.setStartWH(3, 3);
    motionBuilder.setStartXY(3,3);
    motionBuilder.setStartColor(new BasicColor("Red"));
    motionBuilder.setEndWH(4, 4);
    motionBuilder.setEndXY(4, 4);
    motionBuilder.setEndColor(new BasicColor("Red"));

    Motion firstMotion = new Motion(motionBuilder);

    model.addMotion(firstMotion);


    ShapeState firstR = new ShapeState(0, 3, 3, 3, 3, new BasicColor("Red"));
    ShapeState secondR = new ShapeState(1, 4, 4, 4, 4, new BasicColor("Red"));

    List<ShapeState> shapeStates = new ArrayList<ShapeState>();
    shapeStates.add(firstR);
    shapeStates.add(secondR);

    assertEquals(shapeStates, model.getMotions("R"));
  }

  @Test
  public void testGetAnimations() {

    model.createShape("R", BasicShape.RECTANGLE);

    motionBuilder.setTime(0, 1);
    motionBuilder.setTag("R", BasicShape.RECTANGLE);
    motionBuilder.setStartWH(3, 3);
    motionBuilder.setStartXY(3,3);
    motionBuilder.setStartColor(new BasicColor("Red"));
    motionBuilder.setEndWH(4, 4);
    motionBuilder.setEndXY(4, 4);
    motionBuilder.setEndColor(new BasicColor("Red"));

    Motion firstMotion = new Motion(motionBuilder);

    model.addMotion(firstMotion);

    ShapeState firstR = new ShapeState(0, 3, 3, 3, 3, new BasicColor("Red"));
    ShapeState secondR = new ShapeState(1, 4, 4, 4, 4, new BasicColor("Red"));

    ArrayList<ShapeState> shapeStates = new ArrayList<ShapeState>();
    shapeStates.add(firstR);
    shapeStates.add(secondR);

    HashMap<String, ArrayList<ShapeState>> animations =
            new HashMap<String, ArrayList<ShapeState>>();
    animations.put("R", shapeStates);

    assertEquals(animations, model.getAnimations());
  }

  @Test
  public void testGetShapes() {
    model.createShape("R", BasicShape.RECTANGLE);
    model.createShape("O", BasicShape.OVAL);

    HashMap<String, BasicShape> shapes = new HashMap<String, BasicShape>();
    shapes.put("R", BasicShape.RECTANGLE);
    shapes.put("O", BasicShape.OVAL);

    assertEquals(shapes, model.getShapes());
  }




}
