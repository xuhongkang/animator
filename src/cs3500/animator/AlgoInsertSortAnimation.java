package cs3500.animator;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class to make an animation of the insert sort algorithm. Animation is created algorithmically
 * with several constants that define the animation size and look.
 */
public class AlgoInsertSortAnimation {

  //todo make all fields private if possible
  private static final int NUM_BARS = 15;
  private static final int BAR_WIDTH = 50; //width of each bar in pixels
  private static final int BAR_HEIGHT = 20; //pixel bar height, actual height is (this * bar value)
  private static final int BAR_WIDTH_OFFSET = 10; //offset to left and right of the bars
  private static final int BAR_HEIGHT_OFFSET = 10; //ofset from top of canvas to the bars
  private static final int STEP_DURATION = 50; //num ticks for each move animation
  private static final int PAUSE_DURATION = 5;
  private static final int CANVAS_WIDTH = BAR_WIDTH * NUM_BARS + BAR_WIDTH_OFFSET * 2;
  private static final int CANVAS_HEIGHT = (BAR_HEIGHT * NUM_BARS) + BAR_HEIGHT_OFFSET;
  private static final int ANIMATION_DURATION = STEP_DURATION * (NUM_BARS - 1) + 10 * (NUM_BARS);
  private static final Color UNSORTED_BAR_COLOR = new Color(180, 0, 0); //Color.BLACK;
  private static final Color SORTING_BAR_COLOR = new Color(80, 100, 255); //Color.BLUE;
  private static final Color SORTED_BAR_COLOR = new Color(60, 180, 100); //Color.GREEN;
  private static final Map<Integer, Color> barColors = new HashMap<>();

  /**
   * Main method to create this animation and run it with visuals.
   *
   * @param args any args passed in, program doesn't require any.
   */
  public static void main(String[] args) {
    List<Integer> bars;
    //create the list of ints
    bars = new ArrayList<>();
    //fill the list with numbers 1 : NUM_BARS
    for (int i = 0; i < NUM_BARS; i++) {
      bars.add(i + 1);
      barColors.put(i + 1, UNSORTED_BAR_COLOR);
    }
    //randomize the order of the bars
    Collections.shuffle(bars);

    int[] intarr = new int[NUM_BARS];
    for (int i = 0; i < bars.size(); i++) {
      intarr[i] = bars.get(i);
    }

    FileWriter fw = null;
    try {
      fw = new FileWriter("./resources/insert-sort-animation.txt");
    } catch (IOException ioe) {
      throw new IllegalStateException("Couldn't open output file");
    }
    //write to the output
    try {
      fw.write(constructInitialPicture(intarr));
      fw.write(sortList(intarr));
      fw.close();
    } catch (IOException ioe) {
      throw new IllegalStateException("couldn't write to output file");
    }
  }

  private static String constructInitialPicture(int[] bars) {
    StringBuilder ans = new StringBuilder();
    ans.append("canvas ").append(CANVAS_WIDTH);
    ans.append(" ").append(CANVAS_HEIGHT).append('\n');

    for (int i = 0; i < bars.length; i++) {
      ans.append("rectangle name B").append(bars[i]);
      ans.append(" min-x ").append(i * BAR_WIDTH + BAR_WIDTH_OFFSET);
      ans.append(" min-y ").append(CANVAS_HEIGHT - (BAR_HEIGHT * bars[i]));
      ans.append(" width ").append(BAR_WIDTH);
      ans.append(" height ").append(BAR_HEIGHT * bars[i]);

      //the first bar in the list is always sorted
      if (i == 0) {
        ans.append(" color ");
        ans.append((float) SORTED_BAR_COLOR.getRed() / 255).append(" ");
        ans.append((float) SORTED_BAR_COLOR.getGreen() / 255).append(" ");
        ans.append((float) SORTED_BAR_COLOR.getBlue() / 255).append(" ");
      } else {
        ans.append(" color ");
        ans.append((float) UNSORTED_BAR_COLOR.getRed() / 255).append(" ");
        ans.append((float) UNSORTED_BAR_COLOR.getGreen() / 255).append(" ");
        ans.append((float) UNSORTED_BAR_COLOR.getBlue() / 255).append(" ");
      }

      ans.append("from 1 to ").append(ANIMATION_DURATION).append('\n');
    }

    return ans.toString();
  }

  private static String sortList(int[] arr) {
    StringBuilder ans = new StringBuilder();

    int[] beforeArr = arr.clone();
    //java insert sort algorithm source: https://www.geeksforgeeks.org/insertion-sort/
    int n = arr.length;
    for (int i = 1; i < n; ++i) {
      int key = arr[i];
      int j = i - 1;

      /* Move elements of arr[0..i-1], that are
         greater than key, to one position ahead
         of their current position */
      while (j >= 0 && arr[j] > key) {
        arr[j + 1] = arr[j];
        j = j - 1;
      }
      arr[j + 1] = key;
      //generate the move commands for this step
      //num ticks to do nothing in between each move animation

      ans.append(genMoveCommands(beforeArr, arr,
          (((i - 1) * STEP_DURATION) + (i * PAUSE_DURATION)), key));
      //set beforeArr to the arr
      beforeArr = arr.clone();
    }
    //end algorithm
    return ans.toString();
  }

  /**
   * Generate the string move commands necessary to transform the before array into the after one.
   *
   * @param before the array of ints before change
   * @param after  the array of ints after change
   * @param stime  the starting time for the move commands, duration is defined by the constant
   * @return the string of move commands needed to transform before into after
   */
  private static String genMoveCommands(int[] before, int[] after, int stime, int keybar) {
    StringBuilder ans = new StringBuilder();
    for (int i = 0; i < before.length; i++) {

      //if it's color was blue, it was being sorted, so it's now sorted, change it's color to sorted
      if (barColors.get(before[i]).equals(SORTING_BAR_COLOR)) {
        ans.append(craftColorCmd(before[i], SORTING_BAR_COLOR, SORTED_BAR_COLOR, stime));
        barColors.put(before[i], SORTED_BAR_COLOR);
        //if it is the bar that is being sorted, change it's color to sorting
      } else if (before[i] == keybar) {
        //change the key bar to blue to show that it's being sorted
        ans.append(craftColorCmd(keybar, UNSORTED_BAR_COLOR, SORTING_BAR_COLOR, stime));
        barColors.put(keybar, SORTING_BAR_COLOR);
      }
      if (before[i] != after[i]) {
        //possibly do some checking and potential color changing of bars?
        int eindex = getIndexOf(before[i], after);
        //make the move command
        ans.append(craftMoveCmd(before[i], i, eindex, stime));
      }
    }
    return ans.toString();
  }

  /**
   * Find the index of the given value.
   *
   * @param val the value to find
   * @param arr the arr to search
   * @return the index of the desired val, or -1 if not found
   */
  private static int getIndexOf(int val, int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      if (val == arr[i]) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Craft an individual move command, moving the shape from slocation to elocation horizontally.
   *
   * @param barval the value of the bar, used to find the yvalue
   * @param sindex the starting x index of the bar
   * @param eindex the desired ending x index of the bar
   * @param stime  the starting time of the movement
   * @return the string command moving the shape from the start to the end
   */
  private static String craftMoveCmd(int barval, int sindex, int eindex, int stime) {
    StringBuilder ans = new StringBuilder();
    ans.append("move name B").append(barval);
    //starting x, starting y, ending x, ending y
    int yval = CANVAS_HEIGHT - (barval * BAR_HEIGHT);
    ans.append(" moveto ").append(sindex * BAR_WIDTH + BAR_WIDTH_OFFSET).append(" ");
    ans.append(yval).append(" ");
    ans.append(eindex * BAR_WIDTH + BAR_WIDTH_OFFSET).append(" ");
    ans.append(yval);
    ans.append(" from ").append(stime).append(" to ").append(stime + STEP_DURATION).append('\n');
    return ans.toString();
  }

  private static String craftColorCmd(int barval, Color scolor, Color ecolor, int stime) {
    StringBuilder ans = new StringBuilder();
    ans.append("change-color name B").append(barval);
    ans.append(" colorto ");
    ans.append((float) scolor.getRed() / 255).append(" ");
    ans.append((float) scolor.getGreen() / 255).append(" ");
    ans.append((float) scolor.getBlue() / 255).append(" ");
    ans.append((float) ecolor.getRed() / 255).append(" ");
    ans.append((float) ecolor.getGreen() / 255).append(" ");
    ans.append((float) ecolor.getBlue() / 255).append(" ");
    ans.append("from ").append(stime).append(" to ").append(stime + 1).append('\n');
    return ans.toString();
  }

}
