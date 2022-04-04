package cs3500.animator.view;

import java.io.FileWriter;
import java.io.IOException;

import cs3500.animator.model.AnimatorModel;

public class SVGAnimatorViewImpl implements SVGAnimatorView {
  private AnimatorModel model;

  public SVGAnimatorViewImpl(AnimatorModel model) {
    this.model = model;
  }

  @Override
  public void writeToFile(String filename) {
    try {
      FileWriter fw = new FileWriter(filename + ".svg");
      fw.write(model.getSVG());
      fw.close();
    } catch (IOException ioe) {

    }
  }
}
