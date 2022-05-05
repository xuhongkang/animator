package cs3500.animator.view;

import cs3500.animator.controller.AnimationControllerFeatures;

/**
 * Represents a limited version of a textual view, doesn't support the makeVisible and refresh
 * operations, but does support toString. Doesn't implement toString, but forces the children to
 * implement.
 */
abstract class AbstractTextAnimatorView implements SimpleAnimatorView {

  @Override
  public void makeVisible() {
    throw new UnsupportedOperationException("Cannot make a textual view visible");
  }

  @Override
  public void refresh(int tick) {
    throw new UnsupportedOperationException("Can't refresh a text view");
  }

  @Override
  public void setCallbacks(AnimationControllerFeatures controller) {
    throw new UnsupportedOperationException("Can't set callbacks on a textual view");
  }

  @Override
  public abstract String toString();
}
