/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

import model.Map.Direction;

/**
 * The parent class of all MapObjects that can move by themselves.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 * @see Ghost
 * @see Pacman
 */
@SuppressWarnings("unused")
public abstract class DynamicTarget extends Target {

    protected State state = State.WAITING;

    /**
     * The direction the object is heading to, e.g. moving to.
     */
    private Direction headingTo = Direction.NORTH;

    /**
     * Move the object to the new position.
     *
     * @param pos The new position of this object.
     */
    public void move(Position pos) {
        boolean placeholderOnPosition = false;
        for(MapObject m : pos.getOnPosition()){
            if(m instanceof Placeholder){
                placeholderOnPosition = true;
            }
        }
        if(!placeholderOnPosition || this.isHeadingTo(Map.Direction.NORTH)){
            this.setPosition(pos);
        }
    }

    /**
     * Let the object eat a subclass of Target.
     *
     * @param target The object to be eaten.
     */
    protected abstract void eat(Target target);

    /**
     * Return the direction this object is heading to.
     *
     * @return The direction.
     */
    public Direction getHeadingTo() {
        return headingTo;
    }

    /**
     * Check, if this object is heading to <i>direction</i>.
     * Similar to {@code obj.getHeadingTo() == direction}.
     *
     * @param direction The direction to be checked against.
     *
     * @return <code>True</code> if the position is equal, <code>false</code> if not.
     */
    public boolean isHeadingTo(Direction direction) {
        return this.headingTo == direction;
    }

    /**
     * Change the direction this object is heading to.
     *
     * @param direction The new direction.
     */
    public void setHeadingTo(Direction direction) {
        this.headingTo = direction;
    }

    /**
     * Change the state and perform necessary actions in order to do this.
     *
     * @param state The new state.
     */
    public abstract void changeState(State state);

    public State getState() {
        return this.state;
    }

    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof DynamicTarget) {
                return this.getHeadingTo().equals(((DynamicTarget) o).getHeadingTo())
                        && this.getState().equals(((DynamicTarget) o).getState())
                        && this.getPosition().equals(((DynamicTarget) o).getPosition());
            }
        }
        return false;
    }

    public enum State {

        /**
         * The object is on the hunt.
         */
        HUNTER,

        /**
         * The object is getting hunted.
         */
        HUNTED,

        /**
         * The object was munched / eaten.
         */
        MUNCHED,

        /**
         * The object is waiting, for example to respawn.
         */
        WAITING

    }
}
