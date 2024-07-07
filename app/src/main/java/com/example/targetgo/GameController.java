package com.example.targetgo;

import android.adservices.adselection.RemoveAdSelectionOverrideRequest;
import android.graphics.Canvas;

import com.example.targetgo.interactables.Target;

import java.util.ArrayList;

public class GameController {
    private ArrayList<Target> targets;
    private long timeLastCreated;

    public GameController() {
        targets = new ArrayList<>();
        timeLastCreated = System.currentTimeMillis();
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

    private void clearExpiredTargets() {
        ArrayList<Target> removables = new ArrayList<>();
        for(Target target : targets) {
            if(target.getToBeRemoved()) {
                removables.add(target);
            }
        }
        targets.removeAll(removables);
    }

    public ArrayList<Target> getTargets() {
        return targets;
    }

    public long getTimeLastCreated() {
        return timeLastCreated;
    }
}
