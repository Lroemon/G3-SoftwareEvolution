/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

/**
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class Level {

    private static Level instance;

    private int level = 1;

    private double secondsPerCoin = 7;

    public static Level getInstance() {
        if (Level.instance == null) {
            Level.instance = new Level();
        }

        return Level.instance;
    }

    public void nextLevel() {
        // Reduce the amount of time the user has to munch a ghost
        this.secondsPerCoin *= 0.85;

        this.level++;

        if(Math.random() >= 0.7) {
            Game.getInstance().increasePlayerLifes();
        }

        // Change the refresh rate = How fast is the pacman moving
        Game.getInstance().changeRefreshRate(this);

        for (Ghost g : Game.getInstance().getGhostContainer()) {
            g.changeState(DynamicTarget.State.HUNTER);
        }

        for (Pacman p : Game.getInstance().getPacmanContainer()) {
            p.changeState(DynamicTarget.State.HUNTED);
        }

        Map.getInstance().onNextLevel();
        Game.getInstance().getEventHandlerManager().restartExecution();
    }

    public int getLevel() {
        return this.level;
    }

    private Level() {

    }

    public double getSecondsPerCoin() {
        return secondsPerCoin;
    }

    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof Level) {
                return this.getLevel() == ((Level) o).getLevel()
                        && this.getSecondsPerCoin() == ((Level) o).getSecondsPerCoin();
            }
        }
        return false;
    }


    public static void reset() {
        Level.instance = new Level();
    }
}
