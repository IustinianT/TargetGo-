package com.example.targetgo;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private GameLoop gameLoop;

    private Random rand;
    private Paint redPaint;

    public GamePanel(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);

        gameLoop = new GameLoop(this);

        rand = new Random();
        redPaint = new Paint();
        redPaint.setColor(Color.RED);
    }

    public void update(double delta) {

    }

    public void render() {
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);

        c.drawCircle(
                MainActivity.GAME_WIDTH/2,
                MainActivity.GAME_HEIGHT/2,
                MainActivity.GAME_WIDTH/4,
                redPaint);

        holder.unlockCanvasAndPost(c);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop.startGameLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }
}
