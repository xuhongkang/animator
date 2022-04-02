import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.BasicShape;
import cs3500.animator.model.ShapeProperty;

import static org.junit.Assert.assertEquals;

public class AnimatorModelImplTest {

  private AnimatorModelImpl model;

  @Before
  public void setup() {
    this.model = new AnimatorModelImpl();
  }

  @Test (expected = IllegalArgumentException.class)
  public void testAddOvalOvalAlreadyExist() {
    model.addOval("Oval1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addOval("Oval1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
  }

  @Test
  public void testSuccessfulAddOval() {
    model.addOval("Oval1", 1, 1, 1, 1, 255, 0, 0, 0, 1);

    ShapeProperty expectedShapeProp = new ShapeProperty(BasicShape.OVAL, 1, 1, 1, 1, 255,
            0, 0, 0, 1);

    assertEquals(expectedShapeProp, model.getShapeProperty("Oval1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddRectRectAlreadyExist() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
  }

  @Test
  public void testSuccessfulAddRect() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);

    ShapeProperty expectedShapeProp = new ShapeProperty(BasicShape.RECTANGLE, 1, 1, 1, 1, 255, 0, 0,
            0, 1);

    assertEquals(expectedShapeProp, model.getShapeProperty("Rectangle1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMoveTargetShapeNotExist() {
    model.addMove("Rectangle1", 1, 1, 2, 2, 0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMoveSTBiggerThanET(){
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1 );
    model.addMove("Rectangle1", 1, 1, 2, 2, 100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMoveNegST() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addMove("Rectangle1", 1, 1, 2, 2, -1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMoveNegET() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addMove("Rectangle1", 1, 1, 2, 2, 0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMoveETBiggerThanDisApTime() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addMove("Rectangle1", 1, 1, 2, 2, 0, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMoveIncoherentMove() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addMove("Rectangle1", 100, 100, 200, 200, 0, 1);
  }

  @Test
  public void testSuccessfulAddMove() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);

    model.addMove("Rectangle1", 1, 1, 2, 2, 0, 1);

    ShapeProperty expectedShapeProperty = model.getShapeProperty("Rectangle1");

    ArrayList<Integer> expectedMoveTime = new ArrayList<Integer>();


  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorChangeTargetShapeNotExist() {
    model.addColorChange("Rectangle1", 255, 0, 0, 0, 255, 0, 0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorChangeSTBiggerThanET() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addColorChange("Rectangle1", 255, 0, 0, 0, 255, 0, 0, 1 );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorChangeETBiggerThanDisApTime() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addColorChange("Rectangle1", 255, 0, 0, 0, 0, 255, 0, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddColorChangeNegST() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addColorChange("Rectangle1", 255, 0, 0, 0, 255, 0, -1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddColorChangeNegET() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addColorChange("Rectangle1", 255, 0, 0, 0, 255, 0, 0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddColorChangeIncoherentChange() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addColorChange("Rectangle1", 0, 255, 0, 0, 0, 255, 0, 1);
  }

  @Test
  public void testSuccessfulColorChange() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addColorChange("Rectangle1", 255, 0, 0, 0, 255, 0, 0, 1);

    ShapeProperty expectedShapeProperty = model.getShapeProperty("Rectangle1");

  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddScaleToChangeTargetShapeNotFound() {
    model.addScaleToChange("Rectangle1", 1, 1, 2, 2, 0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddScaleToChangeSTBiggerThanET() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addScaleToChange("Rectangle1", 1, 1, 2, 2, 100, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddScaleToChangeETBiggerThanDisApTime() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addScaleToChange("Rectangle1", 1, 1, 2, 2, 0, 100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddScaleToChangeNegST() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addScaleToChange("Rectangle1", 1, 1, 2, 2, -1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddScaleToChangeNegET() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addScaleToChange("Rectangle1", 1, 1, 2, 2, 0, -1);
  }



  @Test(expected = IllegalArgumentException.class)
  public void testAddScaleToChangeIncoherentChange() {
    model.addRectangle("Rectangle1", 1, 1, 1, 1, 255, 0, 0, 0, 1);
    model.addScaleToChange("Rectangle1", 100, 100, 200, 200, 0, 1);
  }

  @Test
  public void testSuccessfulAddScaleToChange(){

  }



}
