package cs3500.animator.view;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.BasicColor;
import cs3500.animator.model.BasicShape;
import cs3500.animator.model.Shape;
import cs3500.animator.model.ShapeState;

import static org.junit.Assert.assertEquals;

public class AnimatorViewImplTest {
  AnimatorModel model;
  AnimatorView view;
  Shape s1;
  ShapeState ss1;
  ShapeState ss2;
  ShapeState ss3;
  Shape s2;
  ShapeState ss4;
  ShapeState ss5;


  @Before
  public void setUp() {
    model = new AnimatorModelImpl();
    s1 = new Shape("R", BasicShape.RECTANGLE);
    s2 = new Shape("C", BasicShape.OVAL);
    ss1 = new ShapeState(1, 50, 200, 50, 100, new BasicColor("Red"));
    ss2 = new ShapeState(10, 200, 200, 50, 100, new BasicColor("Red"));
    ss3 = new ShapeState(50, 300, 300, 50, 100, new BasicColor("Red"));
    ss4 = new ShapeState(6, 440, 70, 120, 60, new BasicColor("Blue"));
    ss5 = new ShapeState(40, 440, 70, 120, 60, new BasicColor("Blue"));
  }

  @Test
  public void testView() throws IOException {
    model.createShape("R", BasicShape.RECTANGLE);
    model.addMotion("R", BasicShape.RECTANGLE, ss1, ss2);
    model.addMotion("R", BasicShape.RECTANGLE, ss2, ss3);
    model.createShape("C", BasicShape.OVAL);
    model.addMotion("C", BasicShape.OVAL, ss4, ss5);
    view = new AnimatorViewImpl(model,System.out);
    view.viewState();
  }
}