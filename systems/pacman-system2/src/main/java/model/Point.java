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
public class Point extends StaticTarget implements Scorable {

    public Point(Position pos) {
        this.state = State.AVAILABLE;
        this.setPosition(pos);
    }

    @Override
    public void changeState(State s) {
        if (s == null) {
            throw new IllegalArgumentException("A null state is not allowed.");
        } else if (state == s) {
            throw new IllegalArgumentException("The new state must differ from the old one.");
        }

        if (s == State.EATEN) {
            setVisible(false);
        } else if (s == State.AVAILABLE) {
            setVisible(true);
        }

        this.state = s;
    }

    @Override
    public int getScore() {
        return 10;
    }

    @Override
    public void gotEaten() {
        this.changeState(State.EATEN);
    }

    public String toString() {
        return "Point [" + position + ", " + state + ", visible: " + visible + "]";
    }
}
