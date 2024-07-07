package com.example.targetgo;

import android.adservices.adselection.RemoveAdSelectionOverrideRequest;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.example.targetgo.interactables.Target;

import java.util.ArrayList;

public class GameController {
    private ArrayList<Target> targets;
    private long timeLastCreated;
    private long timeLastClearedTargets;

    private double score;
    private double lastHit;
    private int totalTargetsHit;
    private int totalTargetsMissed;

    public GameController() {
        score = 0;
        lastHit = 0;
        totalTargetsHit = 0;
        totalTargetsMissed = 0;
        targets = new ArrayList<>();
        timeLastCreated = System.currentTimeMillis();
        timeLastClearedTargets = System.currentTimeMillis();
    }

    public void computeTouch(MotionEvent event) {
        /* TODO: check if touch was on option button
        if yes: open options page
        else: operations below
        */
        ArrayList<Target> targetsHit = getTargetsHit(event);

        // TODO: add effect for hitting/missing targets

        double points = 0;
        if(targetsHit.isEmpty()) {
            // player has missed a target
            totalTargetsMissed++;
        }
        else {
            // player has hit a (or multiple) target(s)
            for(Target target : targetsHit) {
                points = target.calculatePointsForDistance(new PointF(event.getX(), event.getY()));
                score += points;
                totalTargetsHit++;
            }
        }
        lastHit = points;
    }

    /** Get list of targets hit on event, and set the target(s) to be removed. **/
    private ArrayList<Target> getTargetsHit(MotionEvent event) {
        ArrayList<Target> targetsHit = new ArrayList<>();
        for(Target target : targets) {
            if(target.isHit(new PointF(event.getX(), event.getY()))) {
                targetsHit.add(target);
                target.setToBeRemoved();
            }
        }
        return targetsHit;
    }

    public void updateTargets() {
        for(Target target : targets) {
            target.updateTarget();
        }
    }

    public void drawTargets(Canvas c) {
        for(Target target : targets) {
            target.draw(c);
        }
    }

    public void addTarget(Target target) {
        targets.add(target);
        timeLastCreated = System.currentTimeMillis();
    }

    /** Remove all targets from the targets list once they have expired. **/
    public void clearExpiredTargets() {
        ArrayList<Target> removables = new ArrayList<>();
        for(Target target : targets) {
            if(target.getToBeRemoved()) {
                removables.add(target);
            }
        }
        targets.removeAll(removables);
    }

    public double getAverageAccuracyOnHits() {
        return score / totalTargetsHit;
    }

    public long getTimeLastCreated() {
        return timeLastCreated;
    }

    public long getTimeLastClearedTargets() {
        return timeLastClearedTargets;
    }

    public double getScore() {
        return score;
    }

    public int getTotalTargetsHit() {
        return totalTargetsHit;
    }

    public int getTotalTargetsMissed() {
        return totalTargetsMissed;
    }

    public double getLastHit() {
        return lastHit;
    }
}
