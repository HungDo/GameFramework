package com.app.GameFramework;

import android.graphics.Canvas;
import android.os.Handler;
import android.view.SurfaceHolder;

import java.security.SecureRandom;

/**
 * Created with IntelliJ IDEA.
 * User: hung
 * Date: 8/2/13
 * Time: 10:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameThread extends Thread {

    public boolean isRunning = true;
    protected SurfaceHolder sh;
    protected GameView view;
    protected Canvas canvas;

    public Handler mHandler;

    public SecureRandom randomizer;

    public GameThread(SurfaceHolder holder, GameView surfaceView) {
        sh = holder;
        view = surfaceView;
        randomizer = new SecureRandom();
        mHandler = new Handler();
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void run() {
        while(isRunning) {
            canvas = null;
            try {
                canvas = sh.lockCanvas(null);
                synchronized(sh) {
                    view.onDraw(canvas);
                }
            } finally {
                if(canvas != null) {
                    sh.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public Canvas getCanvas() {
        if(canvas != null) {
            return canvas;
        } else {
            return null;
        }
    }
}
