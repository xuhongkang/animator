package cs3500.animator.view;

import java.awt.*;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.AnimatorModelImpl;

public interface VisualAnimatorView {

  void switchAnimatorModel(AnimatorModelImpl model);

  void paintComponent(Graphics g);

  void refresh();
}