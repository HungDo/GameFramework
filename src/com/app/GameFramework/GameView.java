package com.app.GameFramework;

import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: hung
 * Date: 8/2/13
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    protected Context context;
    protected Canvas canvas;
    protected GameController controller;

    public ArrayList<GameObject>[] layers = new ArrayList[5];
    public GameThread thread;
    public int numObjects = 0;

    public float width;
    public float height;

    public static final int BKGND_LAYER = 0;
    public static final int PWUP_LAYER = 1;
    public static final int HOSTILE_LAYER = 2;
    public static final int OTHER_LAYER = 3;
    public static final int PLAYER_LAYER = 4;

    public float accelX, accelY, accelZ;

    public Iterator <GameObject> iterator;
    public GameObject tmp;

    /**
     *  0 - not started yet
     *  1 - started
     *  2 - pause
     *  3 - stop/lose
     */
    public int gamestate = 0;

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public GameView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public void init() {
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);

        layers[BKGND_LAYER]   = new ArrayList<GameObject>(); // background
        layers[PWUP_LAYER]    = new ArrayList<GameObject>(); // power ups
        layers[HOSTILE_LAYER] = new ArrayList<GameObject>(); // hostile obj
        layers[OTHER_LAYER]   = new ArrayList<GameObject>(); // other stuff
        layers[PLAYER_LAYER]  = new ArrayList<GameObject>(); // player

        DisplayMetrics display = context.getResources().getDisplayMetrics();
        width = (float) display.widthPixels;
        height = (float) display.heightPixels;
        // Log.i("screen", width + "x" + height);
    }

    public void restart() {
        clearLayer(BKGND_LAYER);
        clearLayer(PWUP_LAYER);
        clearLayer(HOSTILE_LAYER);
        clearLayer(OTHER_LAYER);
        clearLayer(PLAYER_LAYER);
    }

    public void clearLayer(int layer) {
        if (layer > -1 && layer < layers.length) {
            layers[layer].clear();
        }
    }

    public void add(GameObject gameObject, int layer) {
        if (gameObject != null && layers[layer] != null) {
            layers[layer].add(gameObject);
        }
    }

    public void removeInactive() {
        int num = layers[0].size() + layers[1].size() + layers[2].size() +
                layers[3].size() + layers[4].size();
        Log.d("Remove inactive", "original: " + num);
        for (int i = 0; i < layers.length; i++) {
            Iterator <GameObject> iterator = layers[i].iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().active) {
                    iterator.remove();
                }
            }
        }
        num = layers[0].size() + layers[1].size() + layers[2].size() +
                layers[3].size() + layers[4].size();
        Log.d("Remove inactive", "after: " + num);
    }

    public void onDraw(Canvas canvas) {
        if (canvas == null) { return; }
        update(canvas);
    }

    public void update(Canvas canvas) {
        numObjects = 0;
        this.canvas = canvas;

        /* - below is an example of what the update function implemented should look like
         * - this method should be called by the child class
         *
         */
        /*
        // go through layers
        for (int i = 0; i < layers.length; i++) {
            iterator = layers[i].iterator();
            numObjects += layers[i].size();
            // call update and draw on all objects
            while (iterator.hasNext()) {
                tmp = iterator.next();
                if (tmp != null) {

                    // flag objects outside of gameView
                    if (!getHolder().getSurfaceFrame().contains((int)tmp.x, (int)tmp.y)) {
                        tmp.active = false;
                        continue;
                    }
                    // if game is running update
                    if (gamestate == 1) {
                        // update object
                        tmp.update(this);
                    }
                    // draw object
                    tmp.draw(canvas);
                }
            }
        }//*/
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (controller != null) {
            controller.onTouchEvent(this, event);
        }
        return true;
    }


    public void handleSensorData(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelX = event.values[0];
            accelY = event.values[1];
            accelZ = event.values[2];
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (thread == null) { return; }

        thread.setRunning(true);
        thread.start();
        gamestate = 1;
        canvas = thread.getCanvas();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        thread.setRunning(false);

        while(retry) {
            try {
                thread.join();
                retry = false;
            } catch(InterruptedException ie) {

            }
            break;
        }
        thread = null;
    }
}
