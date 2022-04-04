package cs3500.animator.view;

import java.util.ArrayList;

import cs3500.animator.model.BasicShape;

public interface VisualAnimatorView {

  void makeVisible();

  void setValues(ArrayList<ArrayList<float[][]>> valuesList, ArrayList<ArrayList<BasicShape>> shapesList);

  void refresh();
}