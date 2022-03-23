package cs3500.animator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Basic Model for Animator, Handles data and operations.
 */
public interface AnimatorModel {

  /**
   * Creates a new shape in the model.
   * @throws IllegalArgumentException if parameters are invalid.
   * @param tag is the tag for the target shape
   * @param bShape is a basic shape
   */
  void createShape(String tag, BasicShape bShape);

  /**
   * Delete the shape represented by the given tag.
   * @param tag     the tag standing for the target shape to be deleted.
   */
  void deleteShape(String tag);

  /**
   * Adds a new motion to an existing shape.
   * @throws IllegalArgumentException if shape is not initialized or if parameters are invalid.
   */
  void addMotion(Motion m);

  /**
   * To reverse the motion you just gave.
   * @param tag     represent the specific shape that you wanna reverse motion for.
   */
  void removeLastMotion(String tag);

  /**
   * To get the tags you created.
   * @return     A set of tags you created.
   */
  Set getTags();

  /**
   * To get the motions you have for the specified shape.
   * @param tag     represent the tag corresponding to the shape you wants to specify.
   * @return     The motions of the specified shape.
   */
  List getMotions(String tag);

  /**
   * For retrieving the animations info of the model.
   * The HashMap's key represents the tag, while its value represents the motions.
   * @return     ALl the animations info of the model.
   */
  HashMap<String, ArrayList<ShapeState>> getAnimations();

  /**
   * For retrieving the shapes info of the model.
   * The HashMap's key represents the tag, while its value represents the shape.
   * @return     All the shapes info of the model.
   */
  HashMap<String, BasicShape> getShapes();

  /**
   * ONLY FOR TESTING MAJOR VULNERABILITY DELETE ON SUBMISSION!
   */
  void addState(String tag, ShapeState... states);
}
