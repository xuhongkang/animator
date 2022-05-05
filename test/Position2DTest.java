import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.animator.model.Position2D;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for a Position2D value class.
 */
public class Position2DTest {

  private Position2D p1;
  private Position2D p2;
  private Position2D p3;

  @Before
  public void setup() {
    p1 = new Position2D(0, 0);
    p2 = new Position2D(5, 2);
    p3 = new Position2D(3, 10);
  }

  @Test
  public void testConstructor() {
    //no invalid tests because all ints are valid
    //successfull constructors
    Position2D a = new Position2D(0, 0);
    Position2D b = new Position2D(1, 2);
    Position2D c = new Position2D(1000, 5000);
    Position2D d = new Position2D(6000, 345);
    assertEquals(0, a.getX(), 0.01);
    assertEquals(1, b.getX(), 0.01);
  }

  @Test
  public void testGetXandY() {
    assertEquals(0, p1.getX(), 0.01);
    assertEquals(0, p1.getY(), 0.01);
    assertEquals(5, p2.getX(), 0.01);
    assertEquals(2, p2.getY(), 0.01);
    assertEquals(3, p3.getX(), 0.01);
    assertEquals(10, p3.getY(), 0.01);
  }

  @Test
  public void testEqualsAndHash() {
    //equality
    Position2D a = new Position2D(0, 0);
    assertEquals(a, p1);
    Position2D b = new Position2D(5, 2);
    assertEquals(b, p2);
    Position2D c = new Position2D(3, 10);
    assertEquals(c, p3);

    //simple inequality
    assertNotEquals(a, b);
    assertNotEquals(a, c);
    assertNotEquals(c, b);

    //only 1 difference
    Position2D aa = new Position2D(0, 1);
    Position2D aaa = new Position2D(2, 0);
    assertNotEquals(a, aa);
    assertNotEquals(a, aaa);
    assertNotEquals(aa, aaa);

    //test hash code
    assertEquals(a.hashCode(), p1.hashCode());
    assertEquals(b.hashCode(), p2.hashCode());
    assertEquals(c.hashCode(), p3.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("Pos2D(0.0, 0.0)", p1.toString());
    assertEquals("Pos2D(5.0, 2.0)", p2.toString());
    assertEquals("Pos2D(3.0, 10.0)", p3.toString());

  }

}
