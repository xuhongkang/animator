import org.junit.Before;
import org.junit.Test;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.BasicShape;
import cs3500.animator.model.ShapeProperty;

import static org.junit.Assert.assertEquals;

public class AnimatorModelImplTest {

  private AnimatorModel model;

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

    AnimatorModelImpl modelHere = (AnimatorModelImpl) model;
    assertEquals(expectedShapeProp, modelHere.getProperty("Oval1"));
  }








}
