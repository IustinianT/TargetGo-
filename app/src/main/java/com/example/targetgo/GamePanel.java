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

import com.example.targetgo.helpers.GameConstants;
import com.example.targetgo.interactables.Target;

import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private GameLoop gameLoop;
    private GameController operator;

    private Random rand;

    public GamePanel(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);

        gameLoop = new GameLoop(this);

        rand = new Random();
        operator = new GameController();

        for(int i = 0; i < GameConstants.Operator_Constants.TARGET_AMOUNT; i++) {
            operator.addTarget(new Target(
                    rand.nextInt(MainActivity.GAME_WIDTH),
                    rand.nextInt(MainActivity.GAME_HEIGHT),
                    GameConstants.Target_Constants.RADIUS
                    ));
        }
    }

    public void update(double delta) {
        operator.updateTargets();
    }

    public void render() {
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);

        operator.drawTargets(c);

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
