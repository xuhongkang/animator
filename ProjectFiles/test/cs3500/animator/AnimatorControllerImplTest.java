package cs3500.animator;

import org.junit.Test;

import java.io.InputStreamReader;

import cs3500.animator.controller.AnimatorController;
import cs3500.animator.controller.AnimatorControllerImpl;
import cs3500.animator.model.AnimatorModelImpl;
import static org.junit.Assert.assertEquals;

/**
 * Test for the animator controller.
 */
public class AnimatorControllerImplTest {
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidBuiltControllerOne() {
    AnimatorController failedBCNullInput = new AnimatorControllerImpl(null, System.out,
            new AnimatorModelImpl());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidBuiltControllerTwo() {
    AnimatorController failedBCNullOutput = new AnimatorControllerImpl(
            new InputStreamReader(System.in), null, new AnimatorModelImpl());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidBuiltControllerThree() {
    AnimatorController failedBCNullModel = new AnimatorControllerImpl(
            new InputStreamReader(System.in), System.out,null);
  }

  @Test
  public void testSuccessfulBuilt() {
    AnimatorController successfulBuilt = new AnimatorControllerImpl(
            new InputStreamReader(System.in), System.out, new AnimatorModelImpl());

    assertEquals(successfulBuilt.getClass(), AnimatorControllerImpl.class);
  }





}
