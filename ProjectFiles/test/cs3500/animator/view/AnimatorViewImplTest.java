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
  AnimatorModel model1;
  AnimatorModel model2;
  AnimatorView view1;
  AnimatorView view2;
  String tag1;
  BasicShape bs1;
  Motion m1;
  Motion m2;
  ShapeState ss1;
  ShapeState ss2;
  ShapeState ss3;
  String tag2;
  BasicShape bs2;
  Motion m3;
  ShapeState ss4;
  ShapeState ss5;


  @Before
  public void setUp() {
    model1 = new AnimatorModelImpl();
    model2 = new AnimatorModelImpl();
    tag1 = "R";
    tag2 = "C";
    bs1 = BasicShape.RECTANGLE;
    bs2 = BasicShape.OVAL;
    m1 = new Motion.MotionBuilder().setTime(1,10).setStartXY(50,200)
            .setStartWH(50,100).setStartColor(new BasicColor("Red"))
            .setEndXY(200,200).build();
    m2 = new Motion.MotionBuilder().setTime(10,50).setEndXY(300,300).build();
    ss1 = new ShapeState(1, 50, 200, 50, 100, new BasicColor("Red"));
    ss2 = new ShapeState(10, 200, 200, 50, 100, new BasicColor("Red"));
    ss3 = new ShapeState(50, 300, 300, 50, 100, new BasicColor("Red"));

    m3 = new Motion.MotionBuilder().setTime(6,40).setStartXY(440,70)
            .setStartWH(120,60).setStartColor(new BasicColor("Blue")).build();
    ss4 = new ShapeState(6, 440, 70, 120, 60, new BasicColor("Blue"));
    ss5 = new ShapeState(40, 440, 70, 120, 60, new BasicColor("Blue"));
  }

  @Test
  public void testView() throws IOException {
    model1.createShape("R", BasicShape.RECTANGLE);
    model1.addMotion(tag1, m1);
    model1.addMotion(tag1, m2);
    model1.createShape("C", BasicShape.OVAL);
    model1.addMotion(tag2, m3);
    view1 = new AnimatorViewImpl(model1,System.out);
    view1.viewState();
  }

  @Test
  public void testAddMotion() throws IOException {
    model1.createShape("R", BasicShape.RECTANGLE);
    model1.addMotion(tag1, m1);
    model1.addMotion(tag1, m2);
    model1.createShape("C", BasicShape.OVAL);
    model1.addMotion(tag2, m3);
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
}