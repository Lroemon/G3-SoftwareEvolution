/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model;

/**
 * A Level coupled with a Game instance determines the number of the current level and the difficulty parameters
 * associated with. As the level increases, difficulty does it as well through a modification of these parameters.
 * The transition between two levels and the evolution of parameters is handled here.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 * @author Rémy Decocq (modification)
 */
public class Level {

    private static Level instance;

    /**
     * The current level (correlated with difficulty)
     */
    private int level = 1;

    private static final double PROBA_NEW_LIFE = 0.3;

    private Level() {

    }

    public static Level getInstance() {
        if (Level.instance == null) {
            Level.instance = new Level();
        }

        return Level.instance;
    }

    public void nextLevel() {

        this.level++;

        // Pacman has a chance to get a new life
        if(Math.random() <= PROBA_NEW_LIFE) {
            Game.getInstance().increasePlayerLifes();
        }

        // Change the refresh rate = How fast is the pacman moving
        Game.getInstance().changeRefreshRate(this);

        Map.getInstance().onNextLevel(); // Reset all mapobjets states and positions
        Game.getInstance().getEventHandlerManager().restartExecution();
    }

    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof Level) {
                return this.getLevel() == ((Level) o).getLevel();
            }
        }
        return false;
    }

    public static void reset() {
        Level.instance = new Level();
    }

    public int getLevel() {
        return this.level;
    }

}
