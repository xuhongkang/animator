import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cs3500.animator.model.AniCommand;
import cs3500.animator.model.ColorAniCommand;
import cs3500.animator.model.MoveAniCommand;
import cs3500.animator.model.Position2D;
import cs3500.animator.model.ScaleAniCommand;
import cs3500.animator.model.Shape;
import cs3500.animator.model.ShapeType;
import cs3500.animator.model.SimpleModel;
import cs3500.animator.model.AnimatorModel;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for an Animated command (all 3 types).
 */
public class AniCommandTest {

  private Shape r1;
  private Shape r2;
  private Shape o1;
  private Shape o2;

  private AniCommand mc1;
  private AniCommand cc1;
  private AniCommand mc2;
  private AniCommand sc1;
  private List<AniCommand> cmdlist;
  AnimatorModel model;

  @Before
  public void setup() {
    r1 = new Shape("r1", ShapeType.RECTANGLE,
        new Position2D(1.4f, 2.3f), Color.BLACK, 6, 7);
    r2 = new Shape("r2", ShapeType.RECTANGLE,
        new Position2D(145.2f, -84.3f), Color.CYAN, 25, 15);
    o1 = new Shape("o1", ShapeType.OVAL,
        new Position2D(7.3f, 9.0f), Color.MAGENTA, 5, 10);
    o2 = new Shape("o2", ShapeType.OVAL,
        new Position2D(-87.9f, 34.5f), Color.WHITE, 20.3f, 33);
    model = new SimpleModel(500, 400);
    model.addShape(r1, 5, 123);
    model.addShape(r2, 7, 200);
    model.addShape(o1, 30, 100);
    model.addShape(o2, 0, 50);
    model.addMoveCmd("r1", 10, 20, r1.getPosition(), new Position2D(-25, 17));
    model.addColorCmd("r2", 51, 67, r2.getColor(), Color.GREEN);
    model.addSizeCmd("o2", 2, 15, o2.getWidth(), o2.getHeight(), 4f, 5.5f);
    model.addMoveCmd("o2", 3, 5, o2.getPosition(), new Position2D(-1, -3));
    mc1 = model.getShapeAsAniShape("r1").getCommandsOnShape().get(0);
    cc1 = model.getShapeAsAniShape("r2").getCommandsOnShape().get(0);
    sc1 = model.getShapeAsAniShape("o2").getCommandsOnShape().get(1);
    mc2 = model.getShapeAsAniShape("o2").getCommandsOnShape().get(0);
    cmdlist = Arrays.asList(mc1, cc1, sc1, mc2);
  }

  @Test
  public void testSetupCode() {
    assertTrue(mc1 instanceof MoveAniCommand);
    assertTrue(mc2 instanceof MoveAniCommand);
    assertTrue(sc1 instanceof ScaleAniCommand);
    assertTrue(cc1 instanceof ColorAniCommand);
  }

  //can't test constructor, it's not public

  @Test
  public void testGetTimes() {
    int[] stime = {10, 51, 2, 3};
    int[] etime = {20, 67, 15, 5};
    for (int i = 0; i < 4; i++) {
      assertEquals(stime[i], cmdlist.get(i).getStartTime());
      assertEquals(etime[i], cmdlist.get(i).getEndTime());
    }
  }

  @Test
  public void testGetPositions() {
    Position2D[] spos = {new Position2D(1.4f, 2.3f), null, null, new Position2D(-87.9f, 34.5f)};
    Position2D[] epos = {new Position2D(-25, 17), null, null, new Position2D(-1, -3)};
    for (int i = 0; i < 4; i++) {
      assertEquals(spos[i], cmdlist.get(i).getStartPos());
      assertEquals(epos[i], cmdlist.get(i).getEndPos());
    }
  }

  @Test
  public void testGetColors() {
    Color[] scolor = {null, Color.CYAN, null, null};
    Color[] ecolor = {null, Color.GREEN, null, null};
    for (int i = 0; i < 4; i++) {
      assertEquals(scolor[i], cmdlist.get(i).getStartColor());
      assertEquals(ecolor[i], cmdlist.get(i).getEndColor());
    }
  }

  @Test
  public void testGetScales() {
    Float[] sw = {null, null, 20.3f, null};
    Float[] sh = {null, null, 33f, null};
    Float[] ew = {null, null, 4f, null};
    Float[] eh = {null, null, 5.5f, null};
    for (int i = 0; i < 4; i++) {
      assertEquals(sw[i], cmdlist.get(i).getStartWidth());
      assertEquals(sh[i], cmdlist.get(i).getStartHeight());
      assertEquals(ew[i], cmdlist.get(i).getEndWidth());
      assertEquals(eh[i], cmdlist.get(i).getEndHeight());
    }
  }

  @Test
  public void testAdjacentCommand() {
    try {
      cc1.adjacentCommand(mc1);
      fail("expected exception when trying to compare different anicommands");
    } catch (IllegalArgumentException iae) {
      assertEquals("can only check adjacentCommand for another ColorAniCommand", iae.getMessage());
    }
    try {
      sc1.adjacentCommand(cc1);
      fail("expected exception when trying to compare different anicommands");
    } catch (IllegalArgumentException iae) {
      assertEquals("Can only check adjacentCommand for another ScaleAniCommand", iae.getMessage());
    }
    try {
      mc1.adjacentCommand(cc1);
      fail("expected exception when trying to compare different anicommands");
    } catch (IllegalArgumentException iae) {
      assertEquals("can only check adjacentCommand for another MoveAniCommand", iae.getMessage());
    }

    assertFalse(mc1.adjacentCommand(mc2));
    model.addMoveCmd("o1", 35, 99, o1.getPosition(), new Position2D(5, 10));
    AniCommand mc3 = model.getShapeAsAniShape("o1").getCommandsOnShape().get(0);
    assertFalse(mc1.adjacentCommand(mc3));
    assertFalse(mc3.adjacentCommand(mc1));


  }

  @Test
  public void testApplyAnimation() {

    Shape ans = new Shape(r1, new Position2D(-25, 17));
    assertEquals(ans, mc1.applyAnimation(r1));
    ans = new Shape(o2, Color.GREEN);
    assertEquals(ans, cc1.applyAnimation(o2));
    ans = new Shape(r2, 4f, 5.5f);
    assertEquals(ans, sc1.applyAnimation(r2));

  }

  @Test
  public void testGetIntermediate() {
    Shape ans = new Shape(r1, new Position2D(-11.8f, 9.65f));
    assertEquals(ans, mc1.getIntermediateShape(r1, 15));
    ans = new Shape(o2, new Color(0, 255, 112));
    assertEquals(ans, cc1.getIntermediateShape(o2, 60));
    ans = new Shape(r2, 10.26923f, 16.076923f);
    assertEquals(ans, sc1.getIntermediateShape(r2, 10));

  }

}
