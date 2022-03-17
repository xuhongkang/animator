package cs3500.animator.view;

import java.io.IOException;

/**
 * Basic view for the animator.
 */
public interface AnimatorView {

  /**
   * Renders an error message.
   */
  void viewMessage(String message) throws IOException;

  /**
   * Renders the state of the animator model.
   */
  void viewState() throws IOException;
}
