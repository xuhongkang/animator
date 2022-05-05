import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cs3500.animator.model.SimpleModel;
import cs3500.animator.view.AnimatorGraphicsView;
import cs3500.animator.view.AnimatorViewCreator;
import cs3500.animator.view.InteractiveAnimatorView;
import cs3500.animator.view.SVGAnimatorView;
import cs3500.animator.view.SimpleAnimatorView;
import cs3500.animator.view.TextAnimatorView;
import org.junit.Test;

/**
 * Testing or Factory method for the different views.
 */
public class AnimatorViewCreatorTest {

  private SimpleAnimatorView view;

  @Test
  public void testTextCreator() {
    view = AnimatorViewCreator.create("text", new SimpleModel(1, 1), 10);
    assertTrue(view instanceof TextAnimatorView);
  }

  @Test
  public void testSVGCreator() {
    view = AnimatorViewCreator.create("svg", new SimpleModel(1, 2), 10);
    assertTrue(view instanceof SVGAnimatorView);
  }

  @Test
  public void testVisualCreator() {
    view = AnimatorViewCreator.create("visual", new SimpleModel(1, 5), 10);
    assertTrue(view instanceof AnimatorGraphicsView);
  }

  @Test
  public void testInteractiveCreator() {
    view = AnimatorViewCreator.create("interactive", new SimpleModel(1, 5), 10);
    assertTrue(view instanceof InteractiveAnimatorView);
  }

  @Test
  public void testBadRequests() {
    SimpleAnimatorView v;
    try {
      v = AnimatorViewCreator.create(null, new SimpleModel(1, 1), 10);
      fail("expected exception when making a View with a null string");
    } catch (IllegalArgumentException iae) {
      assertEquals("view type cannot be null", iae.getMessage());
    }
    try {
      v = AnimatorViewCreator.create("hi", new SimpleModel(1, 5), 10);
      fail("expected exception when making a view with a bad string");
    } catch (IllegalArgumentException iae) {
      assertEquals("unrecognized animation view type", iae.getMessage());
    }
    try {
      v = AnimatorViewCreator.create("text", new SimpleModel(1, 5), 0);
      fail("expected exception when making a text view with a bad tps");
    } catch (IllegalArgumentException iae) {
      assertEquals("tps can't be 0 or negative", iae.getMessage());
    }
  }
}
