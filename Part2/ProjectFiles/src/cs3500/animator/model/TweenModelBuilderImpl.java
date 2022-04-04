package cs3500.animator.model;

public class TweenModelBuilderImpl implements TweenModelBuilder<AnimatorModel> {
  private AnimatorModel model;

  public TweenModelBuilderImpl(AnimatorModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Null Model.");
    }
    this.model = model;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> setBounds(int width, int height) {
    model.setBounds(width, height);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addOval(String name, float cx, float cy,
                                                  float xRadius, float yRadius,
                                                  float red, float green, float blue,
                                                  int startOfLife, int endOfLife) {
    model.addOval(name, cx, cy, xRadius, yRadius, red, green, blue, startOfLife, endOfLife);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addRectangle(String name, float lx, float ly,
                                                       float width, float height,
                                                       float red, float green, float blue,
                                                       int startOfLife, int endOfLife) {
    model.addRectangle(name, lx, ly, width, height, red, green, blue, startOfLife, endOfLife);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addMove(String name, float moveFromX, float moveFromY,
                                                  float moveToX, float moveToY,
                                                  int startTime, int endTime) {
    model.addMove(name, moveFromX, moveFromY, moveToX, moveToY, startTime, endTime);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addColorChange(String name,
                                                         float oldR, float oldG, float oldB,
                                                         float newR, float newG, float newB,
                                                         int startTime, int endTime) {
    model.addColorChange(name, oldR, oldG, oldB, newR, newG, newB, startTime, endTime);
    return this;
  }

  @Override
  public TweenModelBuilder<AnimatorModel> addScaleToChange(String name, float fromSx,
                                                           float fromSy, float toSx, float toSy,
                                                           int startTime, int endTime) {
    model.addScaleToChange(name, fromSx, fromSy, toSx, toSy, startTime, endTime);
    return this;
  }

  @Override
  public AnimatorModel build() {
    model = model.build();
    return model;
  }
}
