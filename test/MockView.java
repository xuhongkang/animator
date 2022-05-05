import cs3500.animator.controller.AnimationControllerFeatures;
import cs3500.animator.view.SimpleAnimatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * a mock of the view created for testing purposes.
 */
public class MockView implements SimpleAnimatorView {

  public List<String> log;

  /**
   * mock view constructor.
   */
  public MockView() {
    this.log = new ArrayList<>();
  }

  public List<String> getLog() {
    return log;
  }

  @Override
  public void makeVisible() {
    this.getLog();
    log.add("make visible");
    //append to log
  }

  @Override
  public void refresh(int tick) {
    log.add("refreshed at tick" + tick);
    //append to log
  }

  @Override
  public void setCallbacks(AnimationControllerFeatures controller) {
    log.add("set call backs");
    //get the code from actual impl, and then just write to log that you call the specific function
    //not sure how you test input...
  }

  @Override
  public String getNewCommand() {
    log.add("get new command");
    return null;
  }

  @Override
  public int getTempo() {
    log.add("get new temple");
    return -1;
  }

  @Override
  public void setNewCommand() {
    log.add("set new command");
  }
}
