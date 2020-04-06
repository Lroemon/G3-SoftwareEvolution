/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model.event;

import model.Game;

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class Timer {

    private ArrayList<Process> eventObjects;

    private ScheduledThreadPoolExecutor executor;

    private int startPoolSize = 3;

    public Timer() {
        this.eventObjects = new ArrayList<>(startPoolSize);
        this.executor = new ScheduledThreadPoolExecutor(startPoolSize);
    }

    public boolean register(Process p) {
        try {
            this.eventObjects.add(p);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    public void startExecution() {
        if (this.executor == null) {
            this.executor = new ScheduledThreadPoolExecutor(startPoolSize);
        }
        for (Process e : this.eventObjects) {
            // Given the refreshRate 5, our handler has to be run 5 times in one second, so 5 times in 1000 milliseconds.
            // 1000 / 5 = 200
            e.onLoad();
            this.executor.scheduleAtFixedRate(e, e.getStartupDelay(), e.getTiming(), TimeUnit.MILLISECONDS);
        }
    }

    public void pauseExecution() {
        if(this.executor != null) {
            this.executor.shutdown();
            this.executor = null;
        }
    }

    public void restartExecution() {
        if(this.executor != null){
            this.executor.shutdownNow();
            this.executor = null;
        }
        this.startExecution();
    }
}
