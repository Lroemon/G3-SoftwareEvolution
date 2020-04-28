/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model.mapobject;

import be.umons.model.Map.Direction;
import be.umons.model.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * The parent class of all MapObjects that can move by themselves.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 * @author Rémy Decocq
 * @see Ghost
 * @see Pacman
 */

public abstract class DynamicTarget extends Target {

    protected State state = State.WAITING;

    protected Bridge.BridgeState onBridgeState = Bridge.BridgeState.NOT_ON_BRIDGE;

    /**
     * The speed of the target in blocs to move per seconds from 0 to 10
     */
    private int initial_speed;
    private int actual_speed;
    /**
     * If true the speed is locked and cannot be be changed
     */
    protected boolean speedIsLocked = false;
    /**
     * The timer before a new action
     */
    private int next_action_timer;

    /**
     * The direction the object is heading to, e.g. moving to.
     */
    private Direction headingTo = Direction.NORTH;

    /**
     * Set the initial speed
     * @param speed the speed
     */
    public void setSpeed(int speed){
        this.initial_speed = speed;
        changeSpeed(speed);
    }

    /**
     * Lock the speed
     */
    public void lockSpeed(){
        this.speedIsLocked = true;
    }

    /**
     * Unlock the speed
     */
    public void unlockSpeed(){
        this.speedIsLocked = false;
    }


    /**
     * Change the onBridgeState
     * @param state the new state
     */
    public void changeBridgeState(Bridge.BridgeState state){
        onBridgeState = state;
    }

    /**
     * A dynamic object is visible if not on a bridge or above
     */
    public boolean isVisible() {
        return visible && this.onBridgeState.isVisible();
    }

    /**
     * Two objects can touch if they have the same bridge state.
     * @param dt the target
     * @return True if the targets can touch
     */
    public boolean canTouch(DynamicTarget dt){
        return dt.getBridgeState() == this.getBridgeState();
    }

    public boolean canTouch(StaticTarget st){
        return st.getBridgeState() == this.getBridgeState();
    }

    /**
     * @return the bridge state
     */
    public Bridge.BridgeState getBridgeState(){
        return onBridgeState;
    }

    /**
     * Change the speed of the target, 0 is a still object, and 10 is the max speed
     * @param speed the new speed
     */
    public void changeSpeed(int speed){
        if(!speedIsLocked) {
            if (0 <= speed && speed <= 10) {
                this.actual_speed = speed;
                if (speed == 0)
                    this.next_action_timer = 0;
                else
                    this.next_action_timer = 11 - speed;
            }
        }
    }

    /**
     *Get the initial speed
     */
    public int getInitialSpeed(){
        return this.initial_speed;
    }

    public int getActual_speed(){
        return this.actual_speed;
    }

    /**
     * Try to move the object to the new position if he can on this refresh.
     *
     * @param pos The new position of this object.
     */
    public void tryMoving(Position pos){
        Position prevPos = this.getPosition();
        if (canMove()) {
            move(pos);
            if (prevPos != this.getPosition() && this.getBridgeState() != Bridge.BridgeState.NOT_ON_BRIDGE){
                this.changeBridgeState(Bridge.BridgeState.NOT_ON_BRIDGE);
            }
        }
    }

    /**
     * Verify if the target will move
     */
    public boolean canMove() {
        if (next_action_timer == 1){
            this.next_action_timer = 11 - this.actual_speed;
            return true;
        } else {
            next_action_timer -= 1;
            return false;
        }
    }


    /**
     * Move the object to the new position.
     *
     * @param pos The new position of this object.
     */
    public void move(Position pos) {
        boolean wallOnPosition = false;
        boolean placeHolderOnPosition = false;
        for (MapObject m : pos.getOnPosition()) {
            if (m instanceof Wall) {
                wallOnPosition = true; // this position allow a dynamic target to be located on
                break;
            } else if (m instanceof Placeholder) {
                placeHolderOnPosition = true;
            }
        }
        if (!wallOnPosition && !placeHolderOnPosition) {
            this.setPosition(pos);
        } else if (placeHolderOnPosition && isHeadingTo(Direction.NORTH) && this instanceof Ghost) {
            this.setPosition(pos); // Ghosts allowed to go on PlaceHolder in order to leave their spawn
        }
    }

    /**
     * Let the object eat a subclass of Target.
     *
     * @param target The object to be eaten.
     */
    protected abstract void eat(Target target);

    /**
     *  This dynamic target got eaten, should implement behaviour from its point of view (changing current State)
     */
    public abstract void gotEaten();

    /**
     * Change the state and perform necessary actions in order to do this.
     *
     * @param state The new state.
     */
    public abstract void changeState(State state);

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

    public State getState() {
        return this.state;
    }

    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof DynamicTarget) {
                boolean sameHeading = this.getHeadingTo().equals(((DynamicTarget) o).getHeadingTo());
                boolean sameState = this.getState().equals(((DynamicTarget) o).getState());
                boolean samePos = this.getPosition().equals(((DynamicTarget) o).getPosition());
                return sameHeading && sameState && samePos;
            }
        }
        return false;
    }

    public List<Direction> getAllowedDirections(){
        List<Direction> directions = new ArrayList<Direction>();
        if (this.onBridgeState == Bridge.BridgeState.ON_NS_BRIDGE || this.onBridgeState == Bridge.BridgeState.UNDER_WE_BRIDGE){
            directions.add(Direction.NORTH);
            directions.add(Direction.SOUTH);
        } else if(this.onBridgeState == Bridge.BridgeState.UNDER_NS_BRIDGE || this.onBridgeState == Bridge.BridgeState.ON_WE_BRIDGE){
            directions.add(Direction.WEST);
            directions.add(Direction.EAST);
        } else {
            directions.add(Direction.WEST);
            directions.add(Direction.EAST);
            directions.add(Direction.SOUTH);
            directions.add(Direction.NORTH);
        }
        return directions;
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
        WAITING,

        /**
         * The object is stopped.
         */
        HUNTED_STOP,

        /**
         * The object is stopped.
         */
        HUNTER_STOP,

        /**
         * The object is invisible.
         */
        INVINSIBLE,

        /**
         * The object is on FIRE.
         */
        FIRE,

        /**
         * The object is killed.
         */
        KILLED
    }
}
