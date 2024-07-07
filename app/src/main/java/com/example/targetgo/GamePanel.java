package com.example.targetgo;

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

import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private final SurfaceHolder holder;
    private final GameLoop gameLoop;
    private final GameController operator;

    private Paint textPaint;
    private float textSize;
    private float textHeight;

    private final Random rand;

    public GamePanel(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);

        gameLoop = new GameLoop(this);

        rand = new Random();
        operator = new GameController();

        textPaint = new Paint();
        textPaint.setColor(Color.YELLOW);
        textSize = MainActivity.GAME_HEIGHT/26f;
        textPaint.setTextSize(textSize);
        textHeight = MainActivity.GAME_HEIGHT/14f;
    }

    public void update(double delta) {
        operator.updateTargets();

        // create target based on constant delay
        if(System.currentTimeMillis() - operator.getTimeLastCreated() >=
                GameConstants.Operator_Constants.INITIAL_TARGET_CREATION_DELAY * 1000) {
            operator.addTarget(new Target(
                    rand.nextInt(MainActivity.GAME_WIDTH),
                    rand.nextInt(MainActivity.GAME_HEIGHT),
                    GameConstants.Target_Constants.RADIUS
            ));
        }

        // clear targets list every set amount of time
        if(System.currentTimeMillis() - operator.getTimeLastClearedTargets() >=
                GameConstants.Operator_Constants.TARGET_LIST_CLEAR_DELAY) {
            operator.clearExpiredTargets();
        }
    }

    public void render() {
        Canvas c = holder.lockCanvas();
        c.drawColor(Color.BLACK);

        // TODO: add switch case to allow menu creation and other settings

        operator.drawTargets(c);

        c.drawText("Score: " + (int)operator.getScore(),
                10, textHeight, textPaint);
        c.drawText("Last hit: " + (int)operator.getLastHit(),
                10, textHeight+textSize, textPaint);
        c.drawText("Targets: " + operator.getTotalTargetsHit(),
                10, textHeight+2*textSize, textPaint);
        c.drawText("Misses: " + operator.getTotalTargetsMissed(),
                10, textHeight+3*textSize, textPaint);

        holder.unlockCanvasAndPost(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // compute player touch event
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            operator.computeTouch(event);
        }
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
