package com.app.GameFramework;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Created with IntelliJ IDEA.
 * User: hung
 * Date: 8/2/13
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameController implements OnTouchListener {

    private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {


        public boolean onTouch(View view, MotionEvent event) {
            onTouchEvent(view, event);
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            onFlingEvent(e1, e2, velocityX, velocityY);
            return false;
        }
    }

    public void onTouchEvent(View view, MotionEvent event) {
        // empty method to be overwritten
    }

    public void onFlingEvent(MotionEvent e1, MotionEvent e2, float vx, float vy) {
        // empty method to be overwritten
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }
}
