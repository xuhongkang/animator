package cs3500.animator.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import cs3500.animator.model.TweenModelBuilder;

/**
 * This class represents a file reader for the animation file. This reads in the
 * file in the prescribed file format, and relies on a model builder interface.
 * The user of this class should create a model builder that implements this
 * interface.
 */

public class AnimationFileReader {

  /**
   * Read the animation file and use the builder to build a model.
   *
   * @param fileName the path of the file to be read
   * @param builder  the builder used to build the model
   * @param <T>      the type of model
   * @return the model
   * @throws FileNotFoundException  if the specified file cannot be read
   * @throws InputMismatchException if some data value is not of the expected
   *                                type
   * @throws IllegalStateException  if an illegal token is read from the file
   */
  public <T> T readFile(String fileName, TweenModelBuilder<T> builder) throws
          FileNotFoundException, IllegalStateException, InputMismatchException {
    Scanner sc;

    sc = new Scanner(new FileInputStream(fileName));

    while (sc.hasNext()) {
      String command = sc.next();
      ShapeInfo shapeInfo;
      switch (command) {
        case "canvas":
          CanvasInfo canvasInfo = readCanvasInfo(sc);
          builder.setBounds(canvasInfo.getWidth(), canvasInfo.getHeight());
          break;
        case "rectangle":
          RectangleInfo rinfo = readRectangleInfo(sc);
          builder.addRectangle(
                  rinfo.getName(),
                  rinfo.getX(), rinfo.getY(),
                  rinfo.getWidth(), rinfo.getHeight(),
                  rinfo.getR(), rinfo.getG(), rinfo.getB(),
                  rinfo.getStart(), rinfo.getEnd());
          break;
        case "oval":
          OvalInfo cinfo = readOvalInfo(sc);
          builder.addOval(
                  cinfo.getName(),
                  cinfo.getX(), cinfo.getY(),
                  cinfo.getXRadius(), cinfo.getYRadius(),
                  cinfo.getR(), cinfo.getG(), cinfo.getB(),
                  cinfo.getStart(), cinfo.getEnd());
          break;
        case "move":
          MoveInfo minfo = readMoveInfo(sc);
          builder.addMove(
                  minfo.getName(),
                  minfo.getFromX(),
                  minfo.getFromY(),
                  minfo.getToX(),
                  minfo.getToY(),
                  minfo.getStart(),
                  minfo.getEnd());
          break;
        case "change-color":
          ChangeColorInfo colorInfo = readChangeColorInfo(sc);
          builder.addColorChange(colorInfo.name,
                  colorInfo.getFromR(),
                  colorInfo.getFromG(),
                  colorInfo.getFromB(),
                  colorInfo.getToR(),
                  colorInfo.getToG(),
                  colorInfo.getToB(),
                  colorInfo.getStart(),
                  colorInfo.getEnd());
          break;
        case "scale":
          ScaleByInfo scaleByInfo = readScaleByInfo(sc);
          builder.addScaleToChange(scaleByInfo.name,
                  scaleByInfo.getFromXScale(),
                  scaleByInfo.getFromYScale(),
                  scaleByInfo.getToXScale(),
                  scaleByInfo.getToYScale(),
                  scaleByInfo.getStart(),
                  scaleByInfo.getEnd());
          break;
        default:
          throw new IllegalStateException("Unidentified token " + command + " "
                  + "read from file");
      }
    }
    return builder.build();
  }
  
  private CanvasInfo readCanvasInfo(Scanner sc) {
    CanvasInfo info = new CanvasInfo();
    info.setWidth(sc.nextInt());
    info.setHeight(sc.nextInt());
    return info;
  }

  private RectangleInfo readRectangleInfo(Scanner sc) throws
          IllegalStateException, InputMismatchException {
    RectangleInfo info = new RectangleInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "min-x":
          info.setX(sc.nextFloat());
          break;
        case "min-y":
          info.setY(sc.nextFloat());
          break;
        case "width":
          info.setWidth(sc.nextFloat());
          break;
        case "height":
          info.setHeight(sc.nextFloat());
          break;
        case "color":
          info.setR(sc.nextFloat());
          info.setG(sc.nextFloat());
          info.setB(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
                  + "rectangle");
      }
    }

    return info;
  }

  private OvalInfo readOvalInfo(Scanner sc) throws
          IllegalStateException, InputMismatchException {
    OvalInfo info = new OvalInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "center-x":
          info.setX(sc.nextFloat());
          break;
        case "center-y":
          info.setY(sc.nextFloat());
          break;
        case "x-radius":
          info.setXRadius(sc.nextFloat());
          break;
        case "y-radius":
          info.setYRadius(sc.nextFloat());
          break;
        case "color":
          info.setR(sc.nextFloat());
          info.setG(sc.nextFloat());
          info.setB(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
                  + "oval");
      }
    }

    return info;
  }

  private MoveInfo readMoveInfo(Scanner sc) throws
          IllegalStateException, InputMismatchException {
    MoveInfo info = new MoveInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "moveto":
          info.setFromX(sc.nextFloat());
          info.setFromY(sc.nextFloat());
          info.setToX(sc.nextFloat());
          info.setToY(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
                  + "move");
      }
    }

    return info;
  }

  private ChangeColorInfo readChangeColorInfo(Scanner sc) throws
          IllegalStateException, InputMismatchException {
    ChangeColorInfo info = new ChangeColorInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "colorto":
          info.setFromR(sc.nextFloat());
          info.setFromG(sc.nextFloat());
          info.setFromB(sc.nextFloat());
          info.setToR(sc.nextFloat());
          info.setToG(sc.nextFloat());
          info.setToB(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
                  + "change-color");
      }
    }

    return info;
  }

  private ScaleByInfo readScaleByInfo(Scanner sc) throws
          IllegalStateException, InputMismatchException {
    ScaleByInfo info = new ScaleByInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "scaleto":
          info.setFromXScale(sc.nextFloat());
          info.setFromYScale(sc.nextFloat());
          info.setToXScale(sc.nextFloat());
          info.setToYScale(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
                  + "scale-to");
      }
    }

    return info;
  }

  class CanvasInfo {
    private int width = 800;
    private int height = 600;
    
    void setWidth(int w) {
      width = w;
    }
    
    void setHeight(int h) {
      height = h;
    }
    
    int getWidth() {
      return width;
    }
    
    int getHeight() {
      return height;
    }
  }

  class Inputable {
    protected Map<String, Boolean> valueFlags;

    public Inputable() {
      valueFlags = new HashMap<String, Boolean>();

    }

    public boolean isAllInitialized() {
      for (Map.Entry<String, Boolean> entry : valueFlags.entrySet()) {
        if (!entry.getValue()) {
          return false;
        }
      }
      return true;
    }
  }

  class ShapeInfo extends Inputable {
    private String name;
    private float r;
    private float g;
    private float b;
    private int start;
    private int end;


    ShapeInfo() {
      super();
      valueFlags.put("name", false);
      valueFlags.put("r", false);
      valueFlags.put("g", false);
      valueFlags.put("b", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);
    }

    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    void setR(float r) {
      this.r = r;
      valueFlags.replace("r", true);
    }

    void setG(float g) {
      this.g = g;
      valueFlags.replace("g", true);
    }


    void setB(float b) {
      this.b = b;
      valueFlags.replace("b", true);
    }

    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }

    float getR() {
      return r;
    }

    float getG() {
      return g;
    }

    float getB() {
      return b;
    }

    String getName() {
      return name;
    }

    public int getStart() {
      return start;
    }

    public int getEnd() {
      return end;
    }


  }

  class RectangleInfo extends ShapeInfo {
    private float x;
    private float y;
    private float width;
    private float height;

    RectangleInfo() {
      super();
      valueFlags.put("x", false);
      valueFlags.put("y", false);
      valueFlags.put("width", false);
      valueFlags.put("height", false);
    }

    void setX(float x) {
      this.x = x;
      valueFlags.replace("x", true);
    }

    void setY(float y) {
      this.y = y;
      valueFlags.replace("y", true);
    }

    void setWidth(float width) {
      this.width = width;
      valueFlags.replace("width", true);
    }

    void setHeight(float height) {
      this.height = height;
      valueFlags.replace("height", true);
    }

    float getX() {
      return x;
    }

    float getY() {
      return y;
    }

    float getWidth() {
      return width;
    }

    float getHeight() {
      return height;
    }
  }

  class OvalInfo extends ShapeInfo {
    private float cx;
    private float cy;
    private float xradius;
    private float yradius;

    OvalInfo() {
      super();
      valueFlags.put("cx", false);
      valueFlags.put("cy", false);
      valueFlags.put("xradius", false);
      valueFlags.put("yradius", false);
    }

    void setX(float x) {
      this.cx = x;
      valueFlags.replace("cx", true);
    }

    void setY(float y) {
      this.cy = y;
      valueFlags.replace("cy", true);
    }

    void setXRadius(float radius) {
      this.xradius = radius;
      valueFlags.replace("xradius", true);
    }

    void setYRadius(float radius) {
      this.yradius = radius;
      valueFlags.replace("yradius", true);
    }

    float getX() {
      return cx;
    }

    float getY() {
      return cy;
    }

    float getXRadius() {
      return xradius;
    }

    float getYRadius() {
      return yradius;
    }

  }

  class MoveInfo extends Inputable {
    private String name;
    private float fromX;
    private float fromY;
    private float toX;
    private float toY;
    private int start;
    private int end;

    MoveInfo() {
      super();

      valueFlags.put("name", false);
      valueFlags.put("fromx", false);
      valueFlags.put("fromy", false);
      valueFlags.put("tox", false);
      valueFlags.put("toy", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);

    }

    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    void setFromX(float x) {
      this.fromX = x;
      valueFlags.replace("fromx", true);
    }

    void setFromY(float y) {
      this.fromY = y;
      valueFlags.replace("fromy", true);
    }


    void setToX(float x) {
      this.toX = x;
      valueFlags.replace("tox", true);
    }

    void setToY(float y) {
      this.toY = y;
      valueFlags.replace("toy", true);
    }

    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }

    String getName() {
      return name;
    }

    float getFromX() {
      return fromX;
    }

    float getFromY() {
      return fromY;
    }


    float getToX() {
      return toX;
    }

    float getToY() {
      return toY;
    }

    int getStart() {
      return start;
    }

    int getEnd() {
      return end;
    }
  }

  class ChangeColorInfo extends Inputable {
    private String name;
    private float fromR;
    private float fromG;
    private float fromB;
    private float toR;
    private float toG;
    private float toB;
    private int start;
    private int end;

    ChangeColorInfo() {
      super();

      valueFlags.put("name", false);
      valueFlags.put("tor", false);
      valueFlags.put("tog", false);
      valueFlags.put("tob", false);
      valueFlags.put("fromr", false);
      valueFlags.put("fromg", false);
      valueFlags.put("fromb", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);

    }

    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    void setFromR(float r) {
      this.fromR = r;
      valueFlags.replace("fromr", true);
    }

    void setFromG(float g) {
      this.fromG = g;
      valueFlags.replace("fromg", true);
    }

    void setFromB(float b) {
      this.fromB = b;
      valueFlags.replace("fromb", true);
    }


    void setToR(float r) {
      this.toR = r;
      valueFlags.replace("tor", true);
    }

    void setToG(float g) {
      this.toG = g;
      valueFlags.replace("tog", true);
    }

    void setToB(float b) {
      this.toB = b;
      valueFlags.replace("tob", true);
    }

    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }

    String getName() {
      return name;
    }

    float getFromR() {
      return fromR;
    }

    float getFromG() {
      return fromG;
    }

    float getFromB() {
      return fromB;
    }


    float getToR() {
      return toR;
    }

    float getToG() {
      return toG;
    }

    float getToB() {
      return toB;
    }

    int getStart() {
      return start;
    }

    int getEnd() {
      return end;
    }
  }

  class ScaleByInfo extends Inputable {
    private String name;
    private float fromSx;
    private float fromSy;
    private float toSx;
    private float toSy;
    private int start;
    private int end;

    ScaleByInfo() {
      super();

      valueFlags.put("name", false);
      valueFlags.put("fromsx", false);
      valueFlags.put("fromsy", false);
      valueFlags.put("tosx", false);
      valueFlags.put("tosy", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);

    }

    void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }

    void setFromXScale(float sx) {
      this.fromSx = sx;
      valueFlags.replace("fromsx", true);
    }

    void setFromYScale(float sy) {
      this.fromSy = sy;
      valueFlags.replace("fromsy", true);
    }


    void setToXScale(float sx) {
      this.toSx = sx;
      valueFlags.replace("tosx", true);
    }

    void setToYScale(float sy) {
      this.toSy = sy;
      valueFlags.replace("tosy", true);
    }


    void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }

    String getName() {
      return name;
    }

    float getFromXScale() {
      return fromSx;
    }

    float getFromYScale() {
      return fromSy;
    }


    float getToXScale() {
      return toSx;
    }

    float getToYScale() {
      return toSy;
    }


    int getStart() {
      return start;
    }

    int getEnd() {
      return end;
    }
  }
}
