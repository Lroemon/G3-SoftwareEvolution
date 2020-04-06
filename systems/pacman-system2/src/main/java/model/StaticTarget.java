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
public abstract class StaticTarget extends Target {

    protected State state;

    public State getState() {
        return state;
    }

    public abstract void changeState(State state);

    @Override
    protected void setPosition(Position pos) {
        this.position = pos; // Set the position now to prevent the equals() method of the respective object to cause a NullPointerException
        if (pos.getOnPosition().contains(this)) {
            throw new IllegalArgumentException("There cannot be more than one StaticTarget on a position!" +
                                                "other targets: " + pos.getOnPosition());
        } else {
            super.setPosition(pos);
        }
    }

    public void deSpawn() {
        this.position.getOnPosition().remove(this);
    }

    public enum State {
        EATEN, AVAILABLE
    }

}
