package cs3500.animator.view;

import java.io.IOException;

/**
 * Basic view for the animator.
 */
public interface AnimatorView {
  /**
   * Renders an error message.
   * @throws IOException if there's a problem with handling inputs and outputs.
   */
  void viewMessage(String message) throws IOException;

  /**
   * Renders the state of the animator model.
   * @throws IOException if there's a problem with handling inputs and outputs.
   */
  void viewState() throws IOException;
}
