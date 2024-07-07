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

    public GameController() {
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

        // TODO: calculate points based on distance from the target center

        if(targetsHit.isEmpty()) {
            // TODO: remove points if no target is hit
            System.out.println("NO TARGETS HIT!");
        }
        else {
            System.out.println("HIT: "+targetsHit.size()+" TARGETS!!!");
        }
    }

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

    public void clearExpiredTargets() {
        ArrayList<Target> removables = new ArrayList<>();
        for(Target target : targets) {
            if(target.getToBeRemoved()) {
                removables.add(target);
            }
        }
        targets.removeAll(removables);
    }

    public long getTimeLastCreated() {
        return timeLastCreated;
    }

    public long getTimeLastClearedTargets() {
        return timeLastClearedTargets;
    }
}
