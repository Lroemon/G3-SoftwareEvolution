/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model.mapobject;

import be.umons.model.Game;
import be.umons.model.Position;
import be.umons.model.Scorable;

/**
 * A coin represents the object our Pacman has to eat in order to be able to hunt the ghosts.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 * @author Rémy Decocq (modification)
 */
public class Coin extends StaticTarget implements Scorable {

    public static final double PACMAN_AINT_EATER = -1;

    public static final double SECONDS_PER_2_FIRST_COIN = 7.;
    public static final double SECONDS_PER_2_LAST_COIN = 5.;
    public static int nbr_eaten = 0;

    private static double activeSeconds = PACMAN_AINT_EATER;

    public static int nbr_ghosts_eaten_in_a_row = 0;

    private static final int SCORE_PER_COIN = 50;

    /**
     * Reset global state related to coins, as it should be set at the beginning of a new level
     */
    public static void resetCoinsState(){
        Coin.resetActiveSeconds();
        Coin.nbr_eaten = 0;
        Coin.nbr_ghosts_eaten_in_a_row = 0;
    }

    /**
     * Reset state to the fact that Coin effect isn't active, ie it does not give invincibility to Pacman
     */
    public static void resetActiveSeconds() {
        Coin.activeSeconds = PACMAN_AINT_EATER;
    }

    /**
     * Get the number of seconds remaining while Coin effect is active, global to all Coins (eating any Coin increase
     * the common remaining time)
     * @return the number of seconds the effect is still active or -1 if it is not
     */
    public static double getActiveSeconds() {
        return activeSeconds;
    }

    /**
     * Reduce current invincibility time due to this Coin by an amount of time (seconds), leading to a <= 0 value
     * implies the effect is no longer active.
     * @param value A number of seconds
     */
    public static void reduceActiveSeconds(double value) {
        double result = activeSeconds - value;
        if (result <= 0) {
            Coin.switchToPacmanNonEater();
        }else {
            activeSeconds = result;
        }
    }

    public static double getSecondsGain(){
        return Coin.nbr_eaten <= 2 ? SECONDS_PER_2_FIRST_COIN : SECONDS_PER_2_LAST_COIN;
    }

    private static void switchToPacmanEater(){
        Coin.activeSeconds = Coin.getSecondsGain(); // a current value != -1 means pacman is hunting
        Game.getInstance().getTimer().pause_increment();
    }

    private static void switchToPacmanNonEater(){
        activeSeconds = PACMAN_AINT_EATER; // pacman is no more hunting. RUN LITTLE PACMAN !!
        Coin.nbr_ghosts_eaten_in_a_row = 0;
        Game.getInstance().getTimer().resume_increment();
    }

    public Coin(Position pos) {
        this.state = State.AVAILABLE; // State from StaticTarget
        this.setPosition(pos);
    }

    /**
     * Change the state and perform necessary actions in order to do this, f.e. increasing the highscore.
     *
     * @param state The new state.
     */
    @Override
    public void changeState(State state) {
        if (state == null) {
            throw new IllegalArgumentException("A null state is not allowed.");
        } else if (state == this.state) {
            throw new IllegalArgumentException("State must differ from previous one");
        }
        if (state == State.EATEN) {
            Coin.nbr_eaten++;
            setVisible(false);
            if (Coin.activeSeconds == Coin.PACMAN_AINT_EATER) {
                Coin.switchToPacmanEater();
            } else {
                Coin.activeSeconds += Coin.getSecondsGain(); // adding more to current seconds
            }
            this.makePacmansHunters();
        } else if (state == State.AVAILABLE) {
            setVisible(true);
        }

        this.state = state;
    }

    /**
     * Return the score this objects gives to the player who is eating it.
     *
     * @return The score.
     */
    @Override
    public int getScore() {
        return SCORE_PER_COIN;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof Coin) {
                // Check if the state and position of both Coins are equal
                Coin c = ((Coin) o);

                return this.getPosition().equals(c.getPosition())
                        && this.getState().equals(c.getState());
            }
        }

        return false;
    }

    /**
     * From {@link Target#gotEaten()}, a Coin being eaten switch to EATEN State.
     */
    public void gotEaten() {
        this.changeState(State.EATEN);
    }

    private void makePacmansHunters(){
        for (Pacman p : Game.getInstance().getPacmanContainer()) {
            p.changeState(DynamicTarget.State.HUNTER);
        }
        for (Ghost g : Game.getInstance().getGhostContainer()) {
            if (g.getState() == DynamicTarget.State.HUNTER) { // TODO : Waiting ghosts won't be hunted if went out Waiting state before end of hunting by pacman
                g.changeState(DynamicTarget.State.HUNTED);
            }else if (g.getState() == DynamicTarget.State.HUNTER_STOP) {
                g.changeState(DynamicTarget.State.HUNTED_STOP);
            }
        }
    }

    @Override
    public String toString() {
        return "Coin [" + position + ", " + state + ", visible: " + visible + "]";
    }

}
