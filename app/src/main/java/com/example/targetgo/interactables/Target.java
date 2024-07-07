package com.example.targetgo.interactables;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.example.targetgo.helpers.GameConstants;

public class Target {

    private PointF pos;
    private int radius;
    private Paint redPaint;
    private long timeCreated;
    private boolean toBeRemoved;

    public Target(float x, float y, int radius) {
        pos = new PointF(x, y);
        this.radius = radius;
        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        toBeRemoved = false;
        timeCreated = System.currentTimeMillis();
    }

    public void updateTarget() {
        if(System.currentTimeMillis() - timeCreated >=
                GameConstants.Target_Constants.DURATION * 1000) {
            toBeRemoved = true;
        }
    }

    public void draw(Canvas c) {
        if(!toBeRemoved) {
            c.drawCircle(pos.x, pos.y, radius, redPaint);
        }
    }

    public boolean getToBeRemoved() {
        return toBeRemoved;
    }
}
