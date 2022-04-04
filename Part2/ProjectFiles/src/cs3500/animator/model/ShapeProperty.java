package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Collections;

public class ShapeProperty {
  private BasicShape shape;
  private int disApTime;
  private ArrayList<Integer> moveTime;
  private ArrayList<float[]> moveTar;
  private ArrayList<Integer> colorTime;
  private ArrayList<float[]> colorTar;
  private ArrayList<Integer> scaleTime;
  private ArrayList<float[]> scaleTar;

  /**
   * Copy Constructor for Shape Property
   * @param sp is the shape property to copy
   */
  public ShapeProperty(ShapeProperty sp) {
    this.shape = sp.shape;
    this.disApTime = sp.disApTime;
    this.moveTime = this.dCopyTList(sp.moveTime);
    this.moveTar = this.dCopyTars(sp.moveTar);
    this.colorTime = this.dCopyTList(sp.colorTime);
    this.colorTar = this.dCopyTars(sp.colorTar);
    this.scaleTime = this.dCopyTList(sp.scaleTime);
    this.scaleTar = this.dCopyTars(sp.scaleTar);
  }

  public int getStartTime() {
    return this.moveTime.get(0);
  }

  public int getDisApTime() {
    return this.disApTime;
  }

  private String getSVGLineM() {
    String rString = "";
    if (this.shape.equals(BasicShape.RECTANGLE)) {
      for (int t = 0; t < moveTime.size(); t++) {
        if (t == moveTime.size() - 1) {
          break;
        }
        rString += String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" " +
                        "attributeName=\"x\" from=\"%f\" to=\"%f\" fill=\"freeze\" />\n",
                moveTime.get(t), moveTime.get(t+1) - moveTime.get(t), moveTar.get(t)[0],
                moveTar.get(t+1)[0]);
        rString += String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" " +
                        "attributeName=\"y\" from=\"%f\" to=\"%f\" fill=\"freeze\" />\n",
                moveTime.get(t), moveTime.get(t+1) - moveTime.get(t), moveTar.get(t)[1],
                moveTar.get(t+1)[1]);
      }
    } else {
      for (int t = 0; t < moveTime.size(); t++) {
        if (t == moveTime.size() - 1) {
          break;
        }
        rString += String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" " +
                        "attributeName=\"cx\" from=\"%f\" to=\"%f\" fill=\"freeze\" />\n", moveTime.get(t),
                moveTime.get(t+1) - moveTime.get(t), moveTar.get(t)[0], moveTar.get(t+1)[0]);
        rString += String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" " +
                        "attributeName=\"cy\" from=\"%f\" to=\"%f\" fill=\"freeze\" />\n",
                moveTime.get(t), moveTime.get(t+1) - moveTime.get(t), moveTar.get(t)[1],
                moveTar.get(t+1)[1]);
      }
    }
    return rString;
  }

  private String getSVGLineC() {
    String rString = "";
    for (int t = 0; t < colorTime.size(); t++) {
      if (t == colorTime.size() - 1) {
        break;
      }
      rString += String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" " +
                      "attributeName=\"fill\" from=\"rgb(%f%%,%f%%,%f%%)\" to=\"rgb(%f%%,%f%%,%f%%)\" " +
                      "fill=\"freeze\" />\n", colorTime.get(t),
              colorTime.get(t+1) - colorTime.get(t), colorTar.get(t)[0] * 100, colorTar.get(t)[1] * 100,
              colorTar.get(t)[2] * 100, colorTar.get(t+1)[0] * 100, colorTar.get(t+1)[1] * 100, colorTar.get(t+1)[2] * 100);
    }
    return rString;
  }

  private String getSVGLineS() {
    String rString = "";
    if (this.shape.equals(BasicShape.RECTANGLE)) {
      for (int t = 0; t < scaleTime.size(); t++) {
        if (t == scaleTime.size() - 1) {
          break;
        }
        rString += String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" " +
                        "attributeName=\"width\" from=\"%f\" to=\"%f\" fill=\"freeze\" />\n",
                scaleTime.get(t), scaleTime.get(t+1) - scaleTime.get(t), scaleTar.get(t)[0],
                scaleTar.get(t+1)[0]);
        rString += String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" " +
                        "attributeName=\"height\" from=\"%f\" to=\"%f\" fill=\"freeze\" />\n",
                scaleTime.get(t), scaleTime.get(t+1) - scaleTime.get(t), scaleTar.get(t)[1],
                scaleTar.get(t+1)[1]);
      }
    } else {
      for (int t = 0; t < scaleTime.size(); t++) {
        if (t == scaleTime.size() - 1) {
          break;
        }
        rString += String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" " +
                        "attributeName=\"rx\" from=\"%f\" to=\"%f\" fill=\"freeze\" />\n",
                scaleTime.get(t), scaleTime.get(t+1) - scaleTime.get(t),
                scaleTar.get(t)[0], scaleTar.get(t+1)[0]);
        rString += String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" " +
                        "attributeName=\"ry\" from=\"%f\" to=\"%f\" fill=\"freeze\" />\n",
                scaleTime.get(t), scaleTime.get(t+1) - scaleTime.get(t), scaleTar.get(t)[1],
                scaleTar.get(t+1)[1]);
      }
    }
    return rString;
  }

  public String getSVG(String tag) {
    String rString = "";
    if (this.shape.equals(BasicShape.RECTANGLE)) {
      rString += String.format("<rect id=\"%s\" x=\"%f\" y=\"%f\" width=\"%f\" " +
              "height=\"%f\" fill=\"rgb(%f,%f,%f)\" visibility=\"visible\" >\n", tag, moveTar.get(0)[0],
              moveTar.get(0)[1], scaleTar.get(0)[0], scaleTar.get(0)[1], colorTar.get(0)[0],
              colorTar.get(0)[1], colorTar.get(0)[2]);
    } else {
      rString += String.format("<ellipse id=\"%s\" cx=\"%f\" cy=\"%f\" rx=\"%f\" " +
              "ry=\"%f\" fill=\"rgb(%f,%f,%f)\" visibility=\"visible\" >\n", tag, moveTar.get(0)[0],
              moveTar.get(0)[1], scaleTar.get(0)[0], scaleTar.get(0)[1], colorTar.get(0)[0],
              colorTar.get(0)[1], colorTar.get(0)[2]);

    }
    rString += this.getSVGLineM();
    rString += this.getSVGLineC();
    rString += this.getSVGLineS();
    if (this.shape.equals(BasicShape.RECTANGLE)) {
      rString += "</rect>";
    } else {
      rString += "</ellipse>";
    }
    return rString;
  }

  public BasicShape getShape() {
    return this.shape;
  }

  public float[][] getValsAt(int timeStep) {
    return new float[][] {this.getSpecValAt(this.moveTime, this.moveTar, timeStep),
            this.getSpecValAt(this.colorTime, this.colorTar, timeStep),
            this.getSpecValAt(this.scaleTime, this.scaleTar, timeStep)};
  }

  private float[] getSpecValAt(ArrayList<Integer> intTSteps, ArrayList<float[]> vals, int timeStep) {
    if (intTSteps.contains(timeStep)) {
      return vals.get(intTSteps.indexOf(timeStep));
    } else {
      int rightBound = 0;
      for (int i = 0; i < intTSteps.size(); i++) {
        if (intTSteps.get(i) > timeStep) {
          break;
        } else {
          rightBound += 1;
        }
      }
      if (rightBound == 0) {
        throw new IllegalArgumentException("Invalid timeStep, starts before shape startTime.");
      }
      int rightTime = intTSteps.get(rightBound);
      int leftTime = intTSteps.get(rightBound - 1);
      float[] right = vals.get(rightBound);
      float[] left = vals.get(rightBound - 1);
      float[] rList = new float[right.length];
      for (int j = 0; j < right.length; j++) {
        rList[j] = left[j] + (right[j] - left[j]) / (rightTime - leftTime);
      }
      return rList;
    }
  }

  private ArrayList<Integer> getCombTList(ArrayList<Integer>... tLists) {
    ArrayList<Integer> rTList = new ArrayList<Integer>();
    if (tLists.length == 0) {
      return rTList;
    }
    rTList.addAll(tLists[0]);
    for (int i = 1; i < tLists.length; i++) {
      ArrayList<Integer> currentTList = tLists[i];
      rTList.removeAll(currentTList);
      rTList.addAll(currentTList);
    }
    Collections.sort(rTList);
    return rTList;
  }

  public String toString(String indent) {
    String rString = "";
    String table = indent + "                           start"
            + "                                                          end\n"
            + indent + "--------------------------------------------------------"
            + "       --------------------------------------------------------\n"
            + indent + "t      x      y      w      h      r      g      b"
            + "             t      x      y      w      h      r      g      b\n";
    rString += table;
    ArrayList<Integer> t1 = this.dCopyTList(this.moveTime);
    ArrayList<Integer> t2 = this.dCopyTList(this.colorTime);
    ArrayList<Integer> t3 = this.dCopyTList(this.scaleTime);
    ArrayList<Integer> tList = this.getCombTList(t1, t2, t3);
    for (int i = 0; i < tList.size(); i ++) {
      int cTime = tList.get(i);
      float[] xy = this.getSpecValAt(this.moveTime, this.moveTar, cTime);
      float[] rgb = this.getSpecValAt(this.colorTime, this.colorTar, cTime);
      float[] wh = this.getSpecValAt(this.scaleTime, this.scaleTar, cTime);
      String valString = String.format("%-6.2f %-6.2f %-6.2f %-6.2f %-6.2f %-6.2f %-6.2f %-6.2f",
              Double.valueOf(cTime/1000), xy[0], xy[1], wh[0], wh[1], rgb[0], rgb[1], rgb[2]);
      if (i > 0 && i != tList.size() - 1) {
        rString += "        " + valString;
        rString += "\n" + indent + valString;
      } else if (i == 0) {
        rString += indent + valString;
      } else {
        rString += "        " + valString;
      }
    }
    return rString;
  }

  public ShapeProperty(BasicShape shape,
                       float cx, float cy,
                       float sw, float sh,
                       float red, float green, float blue,
                       int startTime, int endTime) {
    if (shape == null) {
      throw new IllegalArgumentException("Null Shape in ShapeProperty Constructor.");
    }

    this.checkInputSETime(startTime, endTime);
    this.shape = shape;
    this.disApTime = endTime;

    this.moveTime = new ArrayList<>();
    this.moveTime.add(startTime);

    this.moveTar = new ArrayList<>();
    float[] nPos = new float[] {cx, cy};
    this.moveTar.add(nPos);

    this.colorTime = new ArrayList<>();
    this.colorTime.add(startTime);

    this.colorTar = new ArrayList<>();
    float[] nColor = new float[] {red, green, blue};
    this.colorTar.add(nColor);

    this.scaleTime = new ArrayList<>();
    this.scaleTime.add(startTime);

    this.scaleTar = new ArrayList<>();
    float[] nScale = new float[] {sw, sh};
    this.scaleTar.add(nScale);
  }

  private ArrayList<Integer> dCopyTList(ArrayList<Integer> og) {
    ArrayList<Integer> rList = new ArrayList<Integer>();
    for (int i = 0; i < og.size(); i++) {
      rList.add(og.get(i));
    }
    return rList;
  }

  private ArrayList<float[]> dCopyTars(ArrayList<float[]> og) {
    ArrayList<float[]> rList = new ArrayList<float[]>();
    for (int i = 0; i < og.size(); i++) {
      float[] ptInfo = og.get(i);
      float[] cptInfo = new float[ptInfo.length];
      for (int j = 0; j < ptInfo.length; j++) {
        cptInfo[j] = ptInfo[j];
      }
      rList.add(cptInfo);
    }
    return rList;
  }

  public void build() {
    this.addEnd(this.moveTime, this.moveTar);
    this.addEnd(this.colorTime, this.colorTar);
    this.addEnd(this.scaleTime, this.scaleTar);
  }

  public void addEnd(ArrayList<Integer> tSteps, ArrayList<float[]> vals) {
    if (!tSteps.contains(disApTime)) {
      tSteps.add(disApTime);
      float[] lVal = vals.get(vals.size() - 1);
      vals.add(lVal);
    }
  }

  public void addMove(float moveFromX, float moveFromY,
                      float moveToX, float moveToY,
                      int startTime, int endTime) {
    float[] fromVal = new float[] {moveFromX, moveFromY};
    float[] toVal = new float[] {moveToX, moveToY};
    this.addChange(this.moveTime, this.moveTar, fromVal, toVal, startTime, endTime,
            "Invalid moveTime, Out of Bounds.");
  }

  public void addColorChange(float oldR, float oldG, float oldB,
                             float newR, float newG, float newB,
                             int startTime, int endTime) {
    float[] fromVal = new float[] {oldR, oldG, oldB};
    float[] toVal = new float[] {newR, newG, newB};
    this.addChange(this.colorTime, this.colorTar, fromVal, toVal, startTime, endTime,
            "Invalid colorTime, Out of Bounds.");
  }

  public void addScaleToChange(float fromSx, float fromSy,
                               float toSx, float toSy,
                               int startTime, int endTime) {
    float[] fromVal = new float[] {fromSx, fromSy};
    float[] toVal = new float[] {toSx, toSy};
    this.addChange(this.scaleTime, this.scaleTar, fromVal, toVal, startTime, endTime,
            "Invalid scaleTime, Out of Bounds.");
  }

  public void addChange(ArrayList<Integer> tSteps, ArrayList<float[]> vals,
                        float[] fromVal, float[] toVal,
                        int startTime, int endTime, String message) {
    this.checkInputSETime(startTime, endTime);
    this.checkAddSETime(tSteps, startTime, endTime, message);

    boolean startPresent = this.checkMatchCondition(tSteps, vals, startTime, fromVal);
    boolean endPresent = this.checkMatchCondition(tSteps, vals, endTime, toVal);
    if (!startPresent) {
      int tarIndex = this.findAddIndex(tSteps, vals, startTime);
      tSteps.add(tarIndex, startTime);
      vals.add(tarIndex, fromVal);
    }
    if (!endPresent) {
      int tarIndex = this.findAddIndex(tSteps, vals, endTime);
      tSteps.add(tarIndex, endTime);
      vals.add(tarIndex, toVal);
    }
  }

  private int findAddIndex(ArrayList<Integer> tSteps, ArrayList<float[]> vals, int timeStep) {
    // Cannot be smaller than start, already checked
    for (int i = 1; i < tSteps.size(); i++) {
      if (tSteps.get(i) > timeStep) {
        return i;
      }
    }
    return tSteps.size();
  }

  private boolean checkMatchCondition(ArrayList<Integer> tList, ArrayList<float[]> vals, int timeStep,
                                   float[] tar) {
    int matchPos = tList.indexOf(timeStep);
    if (matchPos == -1) {
      return false;
    } else {
      float[] tarVal = vals.get(matchPos);
      for (int i = 0; i < tar.length; i++) {
        if (tar[i] != tarVal[i]) {
          throw new IllegalArgumentException("Does not match current settings");
        }
      }
      return true;
    }
  }

  private void checkAddSETime(ArrayList<Integer> tList, int startTime, int endTime, String message) {
    int srtApTime = tList.get(0);
    if (startTime < srtApTime) {
      throw new IllegalArgumentException(message);
    }
    if (endTime > this.disApTime) {
      throw new IllegalArgumentException(message);
    }
  }

  private void checkInputSETime(int startTime, int endTime) {
    if (startTime < 0 || endTime < 0) {
      throw new IllegalArgumentException("Negative TimeStep in ShapeProperty Constructor.");
    }
    if (startTime >= endTime) {
      throw new IllegalArgumentException("EndTime precedes StartTime in ShapeProperty Constructor.");
    }
  }

  @Override
  public boolean equals(Object other) {
    ShapeProperty otherShapeProp = (ShapeProperty) other;

    return otherShapeProp.shape == this.shape
            && otherShapeProp.disApTime == this.disApTime
            && otherShapeProp.moveTime.equals(this.moveTime)
            && otherShapeProp.moveTar.equals(this.moveTar)
            && otherShapeProp.colorTime.equals(this.colorTime)
            && otherShapeProp.colorTar.equals(this.colorTar)
            && otherShapeProp.scaleTime.equals(this.scaleTime)
            && otherShapeProp.scaleTar.equals(this.scaleTar);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(shape, disApTime, moveTime, moveTar, colorTime, colorTar,
            scaleTime, scaleTar);
  }
}
