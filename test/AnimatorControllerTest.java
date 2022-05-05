import cs3500.animator.controller.AnimatorController;
import cs3500.animator.controller.InteractiveController;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.Position2D;
import cs3500.animator.model.Shape;
import cs3500.animator.model.ShapeType;
import cs3500.animator.model.SimpleModel;
import cs3500.animator.view.SimpleAnimatorView;
import cs3500.animator.view.TextAnimatorView;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * testing class for the animator controller interface.
 */
public class AnimatorControllerTest {

  AnimatorModel model;
  SimpleAnimatorView view;
  AnimatorController controller;
  MockView mock;

  @Before
  public void setup() {
    model = new SimpleModel(500, 500);
    view = new TextAnimatorView(model, 6);
    mock = new MockView();

    controller = new InteractiveController(model, mock, 10);
  }

  @Test
  public void testConstructor() {
    try {
      new InteractiveController(null, null, 5);
      fail("null model or view should throw an exception");
    } catch (IllegalArgumentException iae) {
      assertEquals(iae.getMessage(), "model and view cannot be null");
    }
    try {
      new InteractiveController(model, view, 0);
      fail("tps has to be > 0");
    } catch (IllegalArgumentException iae) {
      assertEquals(iae.getMessage(), "tps cannot be less than 1");
    }
  }

  @Test
  public void refreshAndPlay() {
    Shape s1 = new Shape("s1", ShapeType.RECTANGLE, new Position2D(10, 5), Color.CYAN, 5, 4);
    AnimatorModel m = new SimpleModel(500, 500);
    m.addShape(s1, 5, 50);
    assertEquals(50, m.getAnimationDuration());
    AnimatorController c = new InteractiveController(m, mock, 10);
    c.playAnimation();
    try {
      Thread.sleep(10000);
    } catch (InterruptedException ie) {
      //shouldn't happen
    }
    assertEquals(104, mock.getLog().size());
  }
}
