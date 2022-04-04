package cs3500.animator.controller;

import java.io.IOException;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;
import cs3500.animator.model.TweenModelBuilder;
import cs3500.animator.model.TweenModelBuilderImpl;
import cs3500.animator.view.SVGAnimatorView;
import cs3500.animator.view.SVGAnimatorViewImpl;
import cs3500.animator.view.TextAnimatorView;
import cs3500.animator.view.TextAnimatorViewImpl;
import cs3500.animator.view.VisualAnimatorView;
import cs3500.animator.view.VisualAnimatorViewImpl;

public class MainArgsHandler {
  public MainArgsHandler(String inputFileName, String viewTypeString, String outputFileName,
                         int speed) {
    if (speed == 0) {
      speed = 1000;
    }
    try {
      AnimatorModel model = new AnimatorModelImpl();
      TweenModelBuilder modelBuilder = new TweenModelBuilderImpl(model);
      AnimationFileReader fileReader = new AnimationFileReader();
      model = (AnimatorModel) fileReader.readFile(inputFileName, modelBuilder);
      model = model.build();
      switch (viewTypeString) {
        case ("text"):
          TextAnimatorView textView = new TextAnimatorViewImpl(model);
          textView.writeToFile(outputFileName);
          textView.viewState();
          break;
        case ("visual"):
          int delay = (int) 1000/speed;
          VisualAnimatorView visualView = new VisualAnimatorViewImpl(model.getCanvasDim()[0], model.getCanvasDim()[1], delay);
          visualView.setValues(model.getValsAtInterval(delay), model.getShapesAtInterval(delay));
          visualView.refresh();
          break;
        case ("svg"):
          SVGAnimatorView svgView = new SVGAnimatorViewImpl(model);
          svgView.writeToFile(outputFileName);
          break;
        default:
          new IllegalArgumentException("Cannot Recognize Supplied File Type");
      }
    } catch (IOException ioe) {
      throw new IllegalArgumentException("IO Error Check File Name and Properties.");
    }
  }
}
