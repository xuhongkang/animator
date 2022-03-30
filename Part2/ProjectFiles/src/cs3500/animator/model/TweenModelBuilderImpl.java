package cs3500.animator.model;

public class TweenModelBuilderImpl implements TweenModelBuilder<AnimatorModel> {
  

  @Override
  public TweenModelBuilder<AnimatorModel> setBounds(int width, int height) {
    return null;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addOval(String name, float cx, float cy,
                                                  float xRadius, float yRadius,
                                                  float red, float green, float blue,
                                                  int startOfLife, int endOfLife) {
    return null;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addRectangle(String name, float lx, float ly,
                                                       float width, float height,
                                                       float red, float green, float blue,
                                                       int startOfLife, int endOfLife) {
    return null;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addMove(String name, float moveFromX, float moveFromY,
                                                  float moveToX, float moveToY,
                                                  int startTime, int endTime) {
    return null;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addColorChange(String name,
                                                         float oldR, float oldG, float oldB,
                                                         float newR, float newG, float newB,
                                                         int startTime, int endTime) {
    return null;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addScaleToChange(String name, float fromSx,
                                                           float fromSy, float toSx, float toSy,
                                                           int startTime, int endTime) {
    return null;
  }

  @Override
  public AnimatorModel build() {
    return null;
  }
}
