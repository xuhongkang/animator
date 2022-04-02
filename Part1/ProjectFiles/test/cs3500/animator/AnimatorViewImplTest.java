package cs3500.animator;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.BasicColor;
import cs3500.animator.model.BasicShape;
import cs3500.animator.model.Motion;
import cs3500.animator.model.ShapeState;
import cs3500.animator.view.TextAnimatorView;
import cs3500.animator.view.TextAnimatorViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Test for animator view.
 */
public class AnimatorViewImplTest {
  private AnimatorModel model1;
  private AnimatorModel model2;
  private TextAnimatorView view1;
  private String tag1;
  private Motion m1;
  private Motion m2;
  private ShapeState ss1;
  private ShapeState ss2;
  private ShapeState ss3;
  private String tag2;
  private Motion m3;
  private ShapeState ss4;
  private ShapeState ss5;

  @Before
  public void setUp() {
    model1 = new AnimatorModelImpl();
    model2 = new AnimatorModelImpl();
    tag1 = "R";
    tag2 = "C";
    BasicShape bs1 = BasicShape.RECTANGLE;
    BasicShape bs2 = BasicShape.OVAL;
    m1 = new Motion.MotionBuilder().setTag(tag1, null).setTime(1, 10).setStartXY(50, 200)
            .setStartWH(50, 100).setStartColor(new BasicColor("Red"))
            .setEndXY(200, 200).build();
    m2 = new Motion.MotionBuilder().setTag(tag1, null).setTime(10, 50).setEndXY(300, 300).build();
    ss1 = new ShapeState(1, 50, 200, 50, 100, new BasicColor("Red"));
    ss2 = new ShapeState(10, 200, 200, 50, 100, new BasicColor("Red"));
    ss3 = new ShapeState(50, 300, 300, 50, 100, new BasicColor("Red"));

    m3 = new Motion.MotionBuilder().setTag(tag2, null).setTime(6, 40).setStartXY(440, 70)
            .setStartWH(120, 60).setStartColor(new BasicColor("Blue")).build();
    ss4 = new ShapeState(6, 440, 70, 120, 60, new BasicColor("Blue"));
    ss5 = new ShapeState(40, 440, 70, 120, 60, new BasicColor("Blue"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailedViewBuiltOne() {
    TextAnimatorView failedBCNullModel = new TextAnimatorViewImpl(null, System.out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFailedViewBuiltTwo() {
    TextAnimatorView failedBCNullOutput = new TextAnimatorViewImpl(
            new AnimatorModelImpl(), null);
  }

  @Test
  public void testSuccessfulBuilt() {
    TextAnimatorView successfulBuilt = new TextAnimatorViewImpl(
            new AnimatorModelImpl(), System.out);

    assertEquals(successfulBuilt.getClass(), TextAnimatorViewImpl.class);
  }

  @Test
  public void testViewMessageThrowsException() throws IOException {
    boolean exceptionCaught = false;

    view1 = new TextAnimatorViewImpl(model1, new FailingAppendable());

    try {
      view1.viewMessage("R");
    } catch (IOException ioe) {
      exceptionCaught = true;
    }

    assertEquals(true, exceptionCaught);
  }

  @Test
  public void testViewMessageSuccessful() throws IOException {

    StringBuilder output = new StringBuilder();

    view1 = new TextAnimatorViewImpl(model1, output);

    view1.viewMessage("R");

    assertEquals("R\n", output.toString());
  }

  @Test
  public void testViewStateThrowsException() throws IOException {
    boolean exceptionCaught = false;

    view1 = new TextAnimatorViewImpl(model1, new FailingAppendable());

    try {
      view1.viewState();
    } catch (IOException ioe) {
      exceptionCaught = true;
    }

    assertEquals(true, exceptionCaught);
  }

  @Test
  public void testViewStateSuccessful() throws IOException {

    StringBuilder output = new StringBuilder();

    model1.createShape("R", BasicShape.RECTANGLE);
    Motion.MotionBuilder motionBuilder = new Motion.MotionBuilder();
    motionBuilder.setTag("R", BasicShape.RECTANGLE);
    motionBuilder.setTime(0, 1);
    motionBuilder.setStartWH(3, 3);
    motionBuilder.setStartXY(3, 3);
    motionBuilder.setStartColor(new BasicColor("Red"));
    motionBuilder.setEndWH(4, 4);
    motionBuilder.setEndXY(4, 4);
    motionBuilder.setEndColor(new BasicColor("Red"));

    model1.addMotion(motionBuilder.build());

    view1 = new TextAnimatorViewImpl(model1, output);

    view1.viewState();

    assertEquals("# [LEGEND]\n" +
                    "# t == tick\n" +
                    "# (x,y) == position\n" +
                    "# (w,h) == dimensions\n" +
                    "# (r,g,b) == color (with values between 0 and 255)\n" + "\n" +
                    "shape Rectangle R" + "\n" + "           " +
                    "            start                               end\n" + "           " +
                    "-----------------------------     -----------------------------\n" +
                    "           " +
                    "t   x   y   w   h   r   g   b     t   x   y   w   h   r   g   b" + "\n" +
                    "motion R   0   3   3   3   3   255 0   0     1   4   4   4   4   255 0   0  " +
                    "\n" + "\n"
            , output.toString());
  }

  @Test
  public void testAddMotion() throws IOException {
    model1.createShape("R", BasicShape.RECTANGLE);
    model1.addMotion(m1);
    model1.addMotion(m2);
    model1.createShape("C", BasicShape.OVAL);
    model1.addMotion(m3);
    model2.createShape("R", BasicShape.RECTANGLE);
    model2.addState(tag1, ss1, ss2, ss3);
    model2.createShape("C", BasicShape.OVAL);
    model2.addState(tag2, ss4, ss5);

    Appendable a1 = new StringBuilder("");
    view1 = new TextAnimatorViewImpl(model1, a1);
    view1.viewState();

    Appendable a2 = new StringBuilder("");
    TextAnimatorView view2 = new TextAnimatorViewImpl(model2, a2);
    view2.viewState();

    assertEquals(a1.toString(), a2.toString());
  }
}