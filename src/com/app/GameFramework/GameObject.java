package com.app.GameFramework;

import android.graphics.Canvas;

/**
 * Created with IntelliJ IDEA.
 * User: hung
 * Date: 8/2/13
 * Time: 1:31 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class GameObject {

    public String name;
    public float x, y;
    public float vx, vy;
    public boolean active;

    public abstract void update(GameView view);

    public abstract void draw(Canvas canvas);
}
