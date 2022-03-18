package cs3500.animator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Simple Implementation of the animator model.
 */
public class AnimatorModelImpl implements AnimatorModel<BasicShape, ShapeState> {
  private HashMap<String, ArrayList<ShapeState>> animations;
  private HashMap<String, BasicShape> shapes;

  /**
   * Constructor for the animator model implementation.
   * @implNote Invariant: If tag is in animations, it is in shapes, and vice versa
   */
  public AnimatorModelImpl() {
    this.animations = new HashMap<String, ArrayList<ShapeState>>();
    this.shapes = new HashMap<String, BasicShape>();
  }

  @Override
  public void createShape(String tag, BasicShape bShape) {
    this.isValidTag(tag);
    if (bShape == null) {
      throw new IllegalArgumentException("Invalid shape, shape is Null.");
    }
    if (shapes.containsKey(tag)) {
      throw new IllegalArgumentException("Tag already Exists, Use new Tag.");
    }
    this.shapes.put(tag, bShape);
    this.animations.put(tag, new ArrayList<ShapeState>());
  }

  @Override
  public void doNothing(String tag, int startTime, int endTime) {
    this.isValidTag(tag);
    if (!this.shapes.containsKey(tag)) {
      throw new IllegalArgumentException("Animations does not contain target Shape, " +
              "Please Initialize First.");
    }
    ArrayList<ShapeState> motions = this.animations.get(tag);
    if (motions.isEmpty()) {
      throw new IllegalArgumentException("Cannot stall when not Initialized, " +
              "Please Initialize First");
    } else {
      ShapeState lastState = motions.get(motions.size() - 1);
      motions.add(lastState.stall(startTime, endTime));
    }
  }

  @Override
  public void addMotion(String tag, Motion m) {
    if (!this.shapes.containsKey(tag)) {
      throw new IllegalArgumentException("Animations does not contain target Shape, " +
              "Please Initialize First.");
    }
    ArrayList<ShapeState> motions = this.animations.get(tag);
    if (motions.isEmpty()) {
      this.shortcut(null, m);
      try {
        ShapeState start = new ShapeState(m.getStartTime(), m.getStartX(), m.getStartY(),
                m.getStartW(), m.getStartH(), m.getStartC());
        ShapeState end = new ShapeState(m.getEndTime(), m.getEndX(), m.getEndY(),
                m.getEndW(), m.getEndH(), m.getEndC());
        motions.add(start);
        motions.add(end);
      } catch (NullPointerException npe) {
        throw new IllegalArgumentException("Insufficient Parameters.");
      }
    } else {
      ShapeState lastState = motions.get(motions.size() - 1);
      this.shortcut(lastState, m);
      try {
        ShapeState start = new ShapeState(m.getStartTime(), m.getStartX(), m.getStartY(),
                m.getStartW(), m.getStartH(), m.getStartC());
        ShapeState end = new ShapeState(m.getEndTime(), m.getEndX(), m.getEndY(),
                m.getEndW(), m.getEndH(), m.getEndC());
        if (!start.equals(lastState)) {
          throw new IllegalArgumentException("Starting Parameters did not match the last state.");
        } else {
          motions.add(end);
        }
      } catch (NullPointerException npe) {
        throw new IllegalArgumentException("Insufficient Parameters.");
      }
    }
  }

  @Override
  public Set<String> getTags() {
    return this.animations.keySet();
  }

  @Override
  public List<ShapeState> getMotions(String tag) {
    return (ArrayList<ShapeState>)this.animations.get(tag).clone();
  }

  @Override
  public HashMap<String, ArrayList<ShapeState>> getAnimations() {
    HashMap<String, ArrayList<ShapeState>> copy = new HashMap<String, ArrayList<ShapeState>>();
    for (Map.Entry<String, ArrayList<ShapeState>> entry : this.animations.entrySet())
    {
      copy.put(entry.getKey(), new ArrayList<ShapeState>(entry.getValue()));
    }
    return copy;
  }

  @Override
  public HashMap<String, BasicShape> getShapes() {
    HashMap<String, BasicShape> copy = new HashMap<String, BasicShape>();
    for (Map.Entry<String, BasicShape> entry : this.shapes.entrySet())
    {
      copy.put(entry.getKey(), entry.getValue());
    }
    return copy;
  }

  /**
   * ONLY FOR TESTING PLZ DELETE FOR SUBMISSION
   */
  public void addState(String tag, ShapeState... states) {
    for (ShapeState state : states) {
      this.animations.get(tag).add(state);
    }
  }

  private Motion shortcut(ShapeState prev, Motion m) {
    if (prev == null) {
      if (m.getStartX() == null | m.getStartY() == null | m.getStartH() == null |
              m.getStartW() == null | m.getStartC() == null) {
        throw new IllegalArgumentException("Cannot skip start params when not Initialized, " +
                "Please Initialize First");
      }
    }
    if (m.getStartTime() == null) {
      m.setStartTime(prev.getTime());
    }
    if (m.getStartX() == null) {
      m.setStartX(prev.getCtrX());
    }
    if (m.getStartY() == null) {
      m.setStartY(prev.getCtrY());
    }
    if (m.getStartW() == null) {
      m.setStartW(prev.getDimW());
    }
    if (m.getStartH() == null) {
      m.setStartH(prev.getDimH());
    }
    if (m.getStartC() == null) {
      m.setStartC(prev.getColor());
    }
    if (m.getEndTime() == null) {
      throw new IllegalArgumentException("End Time must be provided, given Null.");
    }
    if (m.getEndX() == null) {
      m.setEndX(m.getStartX());
    }
    if (m.getEndY() == null) {
      m.setEndY(m.getStartY());
    }
    if (m.getEndW() == null) {
      m.setEndW(m.getStartW());
    }
    if (m.getEndH() == null) {
      m.setEndH(m.getStartH());
    }
    if (m.getEndC() == null) {
      m.setEndC(m.getStartC());
    }
    if (m.getStartTime() >= m.getEndTime() | m.getStartTime() < 0 | m.getEndTime() < 0) {
      throw new IllegalArgumentException("Invalid Time Parameters.");
    }
    return m;
  }

  private void isValidTag(String tag) {
    if (tag.isEmpty() | tag.equals(null)) {
      throw new IllegalArgumentException("Invalid tag, tag is Null.");
    }
  }
}
