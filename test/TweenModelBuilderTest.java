
import cs3500.animator.io.AnimationFileReader;
import cs3500.animator.io.TweenModelBuilder;
import cs3500.animator.io.TweenModelBuilderImpl;
import cs3500.animator.model.Position2D;
import cs3500.animator.model.Shape;
import cs3500.animator.model.ShapeType;

import java.awt.Color;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import cs3500.animator.model.AnimatorModel;

import static org.junit.Assert.assertEquals;

/**
 * Personal tester used to launch different views, doesn't test anything.
 */
public class TweenModelBuilderTest {

  private AnimatorModel demoModel;

  @Before
  public void testBuilder() {
    AnimationFileReader afr = new AnimationFileReader();
    TweenModelBuilder<AnimatorModel> builder = new TweenModelBuilderImpl();
    AnimatorModel buildingModel = null;
    AnimatorModel bigBangModel = null;
    demoModel = null;
    AnimatorModel toh3 = null;
    AnimatorModel toh5 = null;
    AnimatorModel toh8 = null;
    AnimatorModel toh12 = null;

    try {
      demoModel = afr.readFile("resources/smalldemo.txt", builder);
      // buildingModel = afr.readFile("resources/buildings.txt", builder);
      // bigBangModel = afr.readFile("resources/big-bang-big-crunch.txt", builder);
      // toh3 = afr.readFile("resources/toh-3.txt", builder);
      //toh5 = afr.readFile("resources/toh-5.txt", builder);
      //toh8 = afr.readFile("resources/toh-8.txt", builder);
      toh12 = afr.readFile("resources/toh-12.txt", builder);
    } catch (IOException ioe) {
      System.out.println("error: " + ioe.getMessage());
    }
    // TextualAnimatorView tv = new TextAnimatorView(buildingModel, 10);
    //TextualAnimatorView v2 = new SVGAnimatorView(model, 10);
    // TextualAnimatorView v = new SVGAnimatorView(toh3, 10);
    // ComplexAnimatorView swingview = new AnimatorGraphicsView(toh3);
    //swingview.makeVisible();
    // AnimatorController controller = new SimpleAnimatorController(toh3, swingview, 15);
    // controller.playAnimation();
    // try {
    // FileWriter writer = new FileWriter("resources/testoutput.svg");
    // writer.write(v.toString());
    //  writer.close();
    //  } catch (IOException e) {
    //  e.printStackTrace();
    // }
    //System.out.println(v.toString());
    // System.out.println(tv.toString());

  }

  @Test
  public void numShapes() {
    assertEquals(0, demoModel.getShapes(0).size());
    assertEquals(1, demoModel.getShapes(2).size());
    assertEquals(2, demoModel.getShapes(30).size());
    assertEquals(2, demoModel.getShapesInAnimation().size());
  }

  @Test
  public void shapes() {
    assertEquals(new Shape("R", ShapeType.RECTANGLE, new Position2D(200, 200),
            new Color(255, 0, 0), 50, 100),
        demoModel.getShapeAtTime("R", 1));
    assertEquals(new Shape("C", ShapeType.OVAL, new Position2D(500, 100),
            new Color(0, 0, 255), 60, 30),
        demoModel.getShapeAtTime("C", 6));
  }

  @Test
  public void numCommands() {
    assertEquals(3, demoModel.getShapeAsAniShape("R").getCommandsOnShape().size());
    assertEquals(2, demoModel.getShapeAsAniShape("C").getCommandsOnShape().size());
  }

  @Test
  public void commands() {
    assertEquals(new Shape("R", ShapeType.RECTANGLE, new Position2D(300, 300),
            new Color(255, 0, 0), 50, 100),
        demoModel.getShapeAtTime("R", 50));
    assertEquals(new Shape("R", ShapeType.RECTANGLE, new Position2D(300, 300),
            new Color(255, 0, 0), 25, 100),
        demoModel.getShapeAtTime("R", 70));
    assertEquals(new Shape("R", ShapeType.RECTANGLE, new Position2D(250, 250),
            new Color(255, 0, 0), 25, 100),
        demoModel.getShapeAtTime("R", 85));

    assertEquals(new Shape("C", ShapeType.OVAL, new Position2D(500, 400),
            new Color(0, 170, 85), 60, 30),
        demoModel.getShapeAtTime("C", 70));
    assertEquals(new Shape("C", ShapeType.OVAL, new Position2D(500, 400),
            new Color(0, 255, 0), 60, 30),
        demoModel.getShapeAtTime("C", 80));
  }
}
