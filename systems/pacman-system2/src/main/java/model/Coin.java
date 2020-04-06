/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

/**
 * A coin represents the object our Pacman has to eat in order to be able to hunt the ghosts.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class Coin extends StaticTarget implements Scorable {

    public static final double PACMAN_AINT_EATER = -1;

    public static final double SECONDS_PER_COIN = 5.;

    private static double activeSeconds = PACMAN_AINT_EATER;

    public static void resetActiveSeconds() {
        Coin.activeSeconds = PACMAN_AINT_EATER;
    }

    public static double getActiveSeconds() {
        return activeSeconds;
    }

    public static void reduceActiveSeconds(double value) {
        double result = activeSeconds - value;
        if (result <= 0) {
            result = PACMAN_AINT_EATER;
        }
        activeSeconds = result;
    }

    public Coin(Position pos) {
        this.state = State.AVAILABLE;
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
            setVisible(false);
            if (Coin.activeSeconds == Coin.PACMAN_AINT_EATER) {
                Coin.activeSeconds = SECONDS_PER_COIN;
            } else {
                Coin.activeSeconds += SECONDS_PER_COIN;
            }
            for (Pacman p : Game.getInstance().getPacmanContainer()) {
                p.changeState(DynamicTarget.State.HUNTER);
            }
            for (Ghost g : Game.getInstance().getGhostContainer()) {
                if (g.getState() == DynamicTarget.State.HUNTER) {
                    g.changeState(DynamicTarget.State.HUNTED);
                }
            }
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
        return 50;
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

    public void gotEaten() {
        this.changeState(State.EATEN);
    }

    @Override
    public String toString() {
        return "Coin [" + position + ", " + state + ", visible: " + visible + "]";
    }

}
