package jalal.khan.todoapp;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Jalal on 8/4/2017.
 */

public class SwipeDetector implements View.OnTouchListener {

    public static enum Action {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT,
        None
    }

    private static final int MIN_DISTANCE = 100;
    private float downX, upX;
    private Action mSwipeDetected = Action.None;

    public boolean swipeDetected() {
        return mSwipeDetected != Action.None;
    }

    public Action getAction() {
        return mSwipeDetected;
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                mSwipeDetected = Action.None;
                return false; // allow other events like Click to be processed
            }
            case MotionEvent.ACTION_MOVE: {
                upX = event.getX();

                float deltaX = downX - upX;

                // horizontal swipe detection
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // left or right
                    if (deltaX < 0) {
                        mSwipeDetected = Action.LEFT_TO_RIGHT;
                        return true;
                    }
                    if (deltaX > 0) {
                        mSwipeDetected = Action.RIGHT_TO_LEFT;
                        return true;
                    }
                }
                return true;
            }
        }
        return false;
    }
}

