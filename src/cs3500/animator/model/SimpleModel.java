package cs3500.animator.model;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * A simple model represents the model for a simple animation. animation starts at time zero, and is
 * bounded by the duration shapes can be added or removed from the model. All types of animation
 * commands can be added to a shape.
 */

public class SimpleModel implements AnimatorModel {

  private final int width; //the width of the animation in pixels.
  private final int height; //the height of the animation in pixels.
  private int duration; //duration of the animation, start time is assumed to be tick 0
  private final Map<String, AnimatedShape> shapes; //map shapeID to the specific AnimatedShape obj.
  private final NavigableMap<Long, String> shapeOrder;
  private final LinkedHashMap<String, List<String>> layers;
  private final TreeSet<Integer> discreteTime;
  private final TreeMap<Integer, Integer> tIntervals;
  private final ArrayList<Integer> tempoList;
  private Long nextShapeID;

  /**
   * constructor for a simple model.
   *
   * @param width  animation width
   * @param height animation height
   */
  public SimpleModel(int width, int height) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("width, height, and duration have to be greater than 0");
    }
    this.width = width;
    this.height = height;
    this.duration = 0;
    this.shapes = new HashMap<>();
    this.nextShapeID = 0L;
    this.shapeOrder = new TreeMap<>();
    this.layers =  new LinkedHashMap<>();
    this.layers.put("default", new ArrayList<>());
    this.discreteTime = new TreeSet<>();
    this.tIntervals = new TreeMap<>();
    this.tempoList = new ArrayList<>();
  }

  @Override
  public void addShape(Shape shape, int startTime, int endTime) {
    if (shape == null) {
      throw new IllegalArgumentException("shape can't be null");
    } else if (startTime < 0 || endTime < 0) {
      throw new IllegalArgumentException("invalid time arguments");
    } else if (startTime > endTime) {
      throw new IllegalArgumentException("shape cannot start after it ends");
    } else if (shapes.containsKey(shape.getShapeID())) {
      throw new IllegalArgumentException("shape already exists in animation");
    }

    if (endTime > this.duration) {
      //if the shape exists for longer than the animation has previously been,
      // extend the animation duration
      this.duration = endTime;
    }
    //add the shape to the map of shapes, as well as the ordering of shapes (at the end)
    shapeOrder.put(getNextShapeID(), shape.getShapeID());
    shapes.put(shape.getShapeID(), new AnimatedShape(startTime, endTime, shape));
    if (!this.inLayer(shape.getShapeID())) {
      this.layers.get("default").add(shape.getShapeID());
    }
    this.discreteTime.add(startTime);
    this.discreteTime.add(endTime);
  }

  @Override
  public void addLayer(String layerName, ArrayList<String> shapeIDs) {
    if (layerName.equals("") || layerName == null || shapeIDs.equals("") || shapeIDs == null) {
      throw new IllegalArgumentException("Null Layer Inputs");
    }
    if (this.layers.containsKey(layerName)) {
      this.layers.get(layerName).addAll(shapeIDs);
    } else {
      this.layers.put(layerName, shapeIDs);
    }
  }


  @Override
  public void removeShape(String shapeID) {
    if (!shapes.containsKey(shapeID)) {
      throw new IllegalArgumentException("shape ID does not exist");
    }
    shapes.remove(shapeID);
    //remove it from the ordering as well
    for (Long l : shapeOrder.keySet()) {
      if (shapeOrder.get(l).equals(shapeID)) {
        shapeOrder.remove(l);
        break;
      }
    }
  }

  private void checkShapeAndTime(String sid, int stime, int etime) {
    if (!shapes.containsKey(sid)) {
      throw new IllegalArgumentException("shape ID doesn't exist");
    }
    if (etime > shapes.get(sid).getEndTime() || stime < shapes.get(sid).getStartTime()) {
      throw new IllegalArgumentException("shape doesn't exist in the given time");
    }
    if (stime > etime) {
      throw new IllegalArgumentException("animation cannot end before it starts");
    }
  }

  @Override
  public void addMoveCmd(String shapeID, int stime, int etime, Position2D start, Position2D dest) {
    checkShapeAndTime(shapeID, stime, etime);
    //any position is valid, even if it's outside the animation
    shapes.get(shapeID).addAniCommand(new MoveAniCommand(stime, etime, start, dest));
    this.discreteTime.add(stime);
    this.discreteTime.add(etime);
  }

  @Override
  public void addColorCmd(String shapeID, int stime, int etime, Color start, Color dest) {
    checkShapeAndTime(shapeID, stime, etime);
    shapes.get(shapeID).addAniCommand(new ColorAniCommand(stime, etime, start, dest));
    this.discreteTime.add(stime);
    this.discreteTime.add(etime);
  }

  @Override
  public void addSizeCmd(String shapeID, int stime, int etime, float sw, float sh,
      float ew, float eh) {
    checkShapeAndTime(shapeID, stime, etime);
    if (ew < 0 || eh < 0) {
      throw new IllegalArgumentException("size can't be negative");
    }
    shapes.get(shapeID).addAniCommand(new ScaleAniCommand(stime, etime,
        sw, sh, ew, eh));
    this.discreteTime.add(stime);
    this.discreteTime.add(etime);
  }

  @Override
  public void removeAnimations(String shapeID) {
    if (!shapes.containsKey(shapeID)) {
      throw new IllegalArgumentException("shape ID doesn't exist");
    }
    shapes.get(shapeID).removeAllAnimations();
  }

  @Override
  public List<String> getPossibleShapesAndDefinitions() {
    return new ArrayList<>(Arrays.asList("Rectangle - 2 size dimensions",
        "Oval - 2 size dimensions", "Square - 1 size dimension", "Circle - 1 size dimension"));
  }

  @Override
  public AniShape getShapeAsAniShape(String shapeID) {
    if (!shapes.containsKey(shapeID)) {
      throw new IllegalArgumentException("shape doesn't exist in the animation!");
    }
    return shapes.get(shapeID);
  }

  @Override
  public List<Shape> getShapes(int time) {
    ArrayList<Shape> rList = new ArrayList<>();
    ArrayList<String> shapeList = new ArrayList<>();
    for (String shapeId : shapeOrder.values()) {
      if (time >= shapes.get(shapeId).getStartTime() && time < shapes.get(shapeId).getEndTime()) {
        shapeList.add(shapeId);
      }
    }
    for (String layer : this.layers.keySet()) {
      List<String> shapeIdsAtLayer = this.layers.get(layer);
      for (String shapeId : shapeIdsAtLayer) {
        if (shapeList.contains(shapeId)) {
          rList.add(this.shapes.get(shapeId).getShapeAtTime(time));
        }
      }
    }
    return rList;
  }

  @Override
  public TreeSet<Integer> getDiscreteTime() {
    return this.discreteTime;
  }

  @Override
  public int getTempoAt(int time) {
    Integer last = this.tIntervals.floorKey(time);
    if (last != null) {
      if (time < this.tIntervals.get(last)) {
        ArrayList<Integer> rList = new ArrayList<Integer>();
        rList.addAll(this.tIntervals.keySet());
        int index = rList.indexOf(last);
        return this.tempoList.get(index);
      }
    }
    return -1;
  }

  @Override
  public void addSlowMo(int sTime, int eTime, int tempo) {
    if (sTime < 0 || eTime < 0) {
      throw new IllegalArgumentException("Negative Inputs");
    }
    if (eTime < sTime) {
      throw new IllegalArgumentException("End Time Smaller than Start Time");
    }
    Integer last = this.tIntervals.floorKey(sTime);
    Integer next = this.tIntervals.ceilingKey(sTime);
    int index = -1;
    if (last != null) {
      Integer lastEnd = this.tIntervals.get(last);
      if (lastEnd > sTime) {
        throw new IllegalArgumentException("Overlapping Tempo Intervals");
      }
    }
    if (next != null) {
      if (next < eTime) {
        throw new IllegalArgumentException("Overlapping Tempo Intervals");
      }
      ArrayList<Integer> sList = new ArrayList<Integer>();
      sList.addAll(this.tIntervals.keySet());
      index = sList.indexOf(next);
    }
    this.tIntervals.put(sTime, eTime);
    if (index != -1) {
      this.tempoList.add(index, tempo);
    } else {
      this.tempoList.add(tempo);
    }
  }

  @Override
  public Shape getShapeAtTime(String shapeID, int time) {
    if (!shapes.containsKey(shapeID)) {
      throw new IllegalArgumentException("shape ID doesn't exist");
    }
    if (shapes.get(shapeID).getStartTime() > time || time > shapes.get(shapeID).getEndTime()) {
      throw new IllegalArgumentException("shape does not exist at that time");
    }
    return shapes.get(shapeID).getShapeAtTime(time);
  }

  @Override
  public int getAnimationDuration() {
    return duration;
  }

  @Override
  public List<String> getShapesInAnimation() {
    List<String> shapeIDs = new ArrayList<>();
    for (String shapeID : shapeOrder.values()) {
      shapeIDs.add(shapeID);
    }
    return shapeIDs;
  }

  @Override
  public int getWidthOfAnimation() {
    return width;
  }

  @Override
  public int getHeightOfAnimation() {
    return height;
  }

  private Long getNextShapeID() {
    Long ans = nextShapeID;
    nextShapeID += 1;
    return ans;
  }

  private boolean inLayer(String tar) {
    for (String layer : this.layers.keySet()) {
      for (String shapeId : this.layers.get(layer)) {
        if (tar.equals(shapeId)) {
          return true;
        }
      }
    }
    return false;
  }
}
