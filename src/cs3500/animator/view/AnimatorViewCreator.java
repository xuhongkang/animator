package cs3500.animator.view;

import cs3500.animator.model.AnimatorModelState;

/**
 * Factory builder for different Animation view implementations. Depending on what string is given,
 * different views will be provided.
 */
public class AnimatorViewCreator {

  /**
   * Constructs different types of Animator views depending on the type given.
   *
   * @param type  the type of animation to create. Current types are text, svg, and visual
   * @param model the model for the animation to represent.
   * @param tps   the ticks per second speed to play to animation
   * @return an instance of the view desired
   */
  public static SimpleAnimatorView create(String type, AnimatorModelState model, int tps) {
    if (type == null) {
      throw new IllegalArgumentException("view type cannot be null");
    }

    switch (type) {
      case "text":
        return new TextAnimatorView(model, tps);
      case "svg":
        return new SVGAnimatorView(model, tps);
      case "visual":
        return new AnimatorGraphicsView(model);
      case "interactive":
        return new InteractiveAnimatorView(model);
      default:
        throw new IllegalArgumentException("unrecognized animation view type");
    }
  }

}
