package cs3500.animator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Simple Implementation of the animator model.
 */
public class AnimatorModelImpl implements AnimatorModel {
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
  public void deleteShape(String tag) {
    this.isValidTag(tag);
    if (!shapes.containsKey(tag)) {
      throw new IllegalArgumentException("The targeted shape hasn't been created yet");
    }
    this.shapes.remove(tag);
    this.animations.remove(tag);
  }

  @Override
  public void addMotion(Motion m) {
    String tag = m.getTag();
    if (tag == null) {
      throw new IllegalArgumentException("Tag cannot be null.");
    }
    if (!this.shapes.containsKey(tag)) {
      throw new IllegalArgumentException("Animations does not contain target Shape, " +
              "Please Initialize First.");
    }
    BasicShape shape = m.getShape();
    if (shape != null) {
      if (!this.shapes.get(tag).equals(shape)) {
        throw new IllegalArgumentException("BasicShape does not match corresponding Tag.");
      }
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
      } catch (IllegalStateException ise) {
        throw new IllegalArgumentException("Insufficient Parameters.");
      }
    }
  }

  @Override
  public void removeLastMotion(String tag) {
    this.isValidTag(tag);
    if (!shapes.containsKey(tag)) {
      throw new IllegalArgumentException("The specific shape isn't created yet.");
    }

    ArrayList<ShapeState> statesSoFar = this.animations.get(tag);

    if (statesSoFar.size() == 2) {
      statesSoFar.remove(0);
      statesSoFar.remove(0);
    }
    if (statesSoFar.size() == 0) {
      // Do Nothing
    }
    else {
      statesSoFar.remove(statesSoFar.size() - 1);
    }
  }

  @Override
  public Set<String> getTags() {
    return this.animations.keySet();
  }

  @Override
  public List<ShapeState> getMotions(String tag) {
    List<ShapeState> forCopying = this.animations.get(tag);
    List<ShapeState> forReturning = new ArrayList<ShapeState>();
    for (ShapeState s : forCopying) {
      forReturning.add(s.copy());
    }
    return forReturning;
  }

  @Override
  public HashMap<String, ArrayList<ShapeState>> getAnimations() {
    HashMap<String, ArrayList<ShapeState>> copy = new HashMap<String, ArrayList<ShapeState>>();
    for (Map.Entry<String, ArrayList<ShapeState>> entry : this.animations.entrySet()) {
      ArrayList<ShapeState> entryValues = new ArrayList<ShapeState>();
      for (ShapeState s : entry.getValue()) {
        entryValues.add(s.copy());
      }
      copy.put(entry.getKey(), entryValues);
    }
    return copy;
  }

  @Override
  public HashMap<String, BasicShape> getShapes() {
    HashMap<String, BasicShape> copy = new HashMap<String, BasicShape>();
    for (Map.Entry<String, BasicShape> entry : this.shapes.entrySet()) {
      BasicShape basicShape = entry.getValue();
      copy.put(entry.getKey(), basicShape.copy());
    }
    return copy;
  }

  @Override
  public void addState(String tag, ShapeState... states) {
    for (ShapeState state : states) {
      this.animations.get(tag).add(state);
    }
  }

  private Motion shortcut(ShapeState prev, Motion m) {
    if (prev == null) {
      if (m.getStartX() == null || m.getStartY() == null || m.getStartH() == null ||
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
    if (tag == null) {
      throw new IllegalArgumentException("Invalid tag, tag is null.");
    }
    if (tag.equals("")) {
      throw new IllegalArgumentException("Invalid tag, tag is empty.");
    }
  }
}
