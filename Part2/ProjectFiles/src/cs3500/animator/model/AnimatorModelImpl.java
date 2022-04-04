package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class AnimatorModelImpl implements AnimatorModel {

  private Map<String, ShapeProperty> properties;
  int width = 0;
  int height = 0;

  @Override
  public int getMaxEndTime() {
    ArrayList<ShapeProperty> shapeProperties = new ArrayList<ShapeProperty>();
    for (String s : this.properties.keySet()) {
      shapeProperties.add(this.getShapeProperty(s));
    }
    ArrayList<Integer> endTimes = new ArrayList<Integer>();
    for (ShapeProperty sp : shapeProperties) {
      endTimes.add(sp.getDisApTime());
    }
    return Collections.max(endTimes);
  }

  /**
   * Copy constructor.
   * @param model     The model to be copied.
   */
  public AnimatorModelImpl(AnimatorModelImpl model) {
    this.properties = new LinkedHashMap<String, ShapeProperty>();
    for (String tag : model.properties.keySet()) {
      this.properties.put(tag, new ShapeProperty(model.properties.get(tag)));
    }
  }

  public AnimatorModelImpl() {
    this.properties = new LinkedHashMap<String, ShapeProperty>();
  }

  @Override
  public Set<String> getTags() {
    return this.properties.keySet();
  }

  @Override
  public AnimatorModel build() {
    AnimatorModelImpl rModel = new AnimatorModelImpl(this);
    for (ShapeProperty s : rModel.properties.values()) {
      s.build();
    }
    return rModel;
  }

  @Override
  public void setBounds(int width, int height) {
    this.width = width;
    this.height = height;
  }

  @Override
  public String getSVG() {
    String rString = "";
    rString += String.format("<svg width=\"%d\" height=\"%d\" version=\"1.1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\">\n", this.width, this.height);
    for (String tag : this.properties.keySet()) {
      rString += this.properties.get(tag).getSVG(tag);
    }
    rString += "</svg>";
    return rString;
  }

  @Override
  public void addOval(String name,
                      float cx, float cy,
                      float xRadius, float yRadius,
                      float red, float green, float blue,
                      int startOfLife, int endOfLife) {
    if (this.properties.containsKey(name)) {
      throw new IllegalArgumentException("Oval Already Present");
    }
    ShapeProperty nShapeProperty = new ShapeProperty(BasicShape.OVAL, cx, cy, xRadius, yRadius, red,
    green, blue, startOfLife, endOfLife);
    this.properties.put(name, nShapeProperty);
  }

  @Override
  public void addRectangle(String name,
                           float lx, float ly,
                           float width, float height,
                           float red, float green, float blue,
                           int startOfLife, int endOfLife) {
    if (this.properties.containsKey(name)) {
      throw new IllegalArgumentException("Rectangle Already Present");
    }
    ShapeProperty nShapeProperty = new ShapeProperty(BasicShape.RECTANGLE, lx, ly, width, height, red,
            green, blue, startOfLife, endOfLife);
    this.properties.put(name, nShapeProperty);
  }

  @Override
  public void addMove(String name,
                      float moveFromX, float moveFromY,
                      float moveToX, float moveToY,
                      int startTime, int endTime) {
    if (!this.properties.containsKey(name)) {
      throw new IllegalArgumentException("Target Shape Not Present.");
    }
    this.properties.get(name).addMove(moveFromX, moveFromY, moveToX, moveToY,
            startTime, endTime);
  }

  @Override
  public void addColorChange(String name,
                             float oldR, float oldG, float oldB,
                             float newR, float newG, float newB, int startTime, int endTime) {

    if (!this.properties.containsKey(name)) {
      throw new IllegalArgumentException("Target Shape Not Present.");
    }
    this.properties.get(name).addColorChange(oldR, oldG, oldB, newR, newG, newB,
            startTime, endTime);
  }

  @Override
  public void addScaleToChange(String name,
                               float fromSx, float fromSy,
                               float toSx, float toSy,
                               int startTime, int endTime) {
    if (!this.properties.containsKey(name)) {
      throw new IllegalArgumentException("Target Shape Not Present.");
    }
    this.properties.get(name).addScaleToChange(fromSx, fromSy, toSx, toSy, startTime, endTime);
  }

  @Override
  public ShapeProperty getShapeProperty(String key) {
    return new ShapeProperty(this.properties.get(key));
  }



}
