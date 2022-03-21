package cs3500.animator.view;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.BasicColor;
import cs3500.animator.model.BasicShape;
import cs3500.animator.model.Motion;
import cs3500.animator.model.ShapeState;

import static org.junit.Assert.assertEquals;

public class AnimatorViewImplTest {
  private AnimatorModel model1;
  private AnimatorModel model2;
  private AnimatorView view1;
  private AnimatorView view2;
  private String tag1;
  private BasicShape bs1;
  private Motion m1;
  private Motion m2;
  private ShapeState ss1;
  private ShapeState ss2;
  private ShapeState ss3;
  private String tag2;
  private BasicShape bs2;
  private Motion m3;
  private ShapeState ss4;
  private ShapeState ss5;


  @Before
  public void setUp() {
    model1 = new AnimatorModelImpl();
    model2 = new AnimatorModelImpl();
    tag1 = "R";
    tag2 = "C";
    bs1 = BasicShape.RECTANGLE;
    bs2 = BasicShape.OVAL;
    m1 = new Motion.MotionBuilder().setTag(tag1, null).setTime(1,10).setStartXY(50,200)
            .setStartWH(50,100).setStartColor(new BasicColor("Red"))
            .setEndXY(200,200).build();
    m2 = new Motion.MotionBuilder().setTag(tag1, null).setTime(10,50).setEndXY(300,300).build();
    ss1 = new ShapeState(1, 50, 200, 50, 100, new BasicColor("Red"));
    ss2 = new ShapeState(10, 200, 200, 50, 100, new BasicColor("Red"));
    ss3 = new ShapeState(50, 300, 300, 50, 100, new BasicColor("Red"));

    m3 = new Motion.MotionBuilder().setTag(tag2, null).setTime(6,40).setStartXY(440,70)
            .setStartWH(120,60).setStartColor(new BasicColor("Blue")).build();
    ss4 = new ShapeState(6, 440, 70, 120, 60, new BasicColor("Blue"));
    ss5 = new ShapeState(40, 440, 70, 120, 60, new BasicColor("Blue"));
  }

  @Test
  public void testView() throws IOException {
    model1.createShape("R", BasicShape.RECTANGLE);
    model1.addMotion(m1);
    model1.addMotion(m2);
    model1.createShape("C", BasicShape.OVAL);
    model1.addMotion(m3);
    view1 = new AnimatorViewImpl(model1,System.out);
    view1.viewState();
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
    view1 = new AnimatorViewImpl(model1, a1);
    view1.viewState();

    Appendable a2 = new StringBuilder("");
    view2 = new AnimatorViewImpl(model2, a2);
    view2.viewState();

    assertEquals(a1.toString(), a2.toString());
  }

  @Test
  public void xiaoshiyan() {
    String ss = "";
    String[] arr_ss = ss.split(",");
    assertEquals(arr_ss[0],"");
  }
}