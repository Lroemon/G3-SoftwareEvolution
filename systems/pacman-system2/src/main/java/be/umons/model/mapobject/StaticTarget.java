/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model.mapobject;

import be.umons.model.Position;

/**
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 * @author Rémy Decocq (modification)
 */
public abstract class StaticTarget extends Target {

    protected State state;
    protected Bridge.BridgeState onBridgeState = Bridge.BridgeState.NOT_ON_BRIDGE;

    public State getState() {
        return state;
    }

    public abstract void changeState(State state);

    @Override
    public void setPosition(Position pos) {
        this.position = pos; // Set the position now to prevent the equals() method of the respective object to cause a NullPointerException
        if (pos.getOnPosition().contains(this)) {
            String err = "There cannot be more than one StaticTarget on a position! other targets: " + pos.getOnPosition();
            throw new IllegalArgumentException(err);
        } else {
            super.setPosition(pos);
        }
    }

    public void deSpawn() {
        this.position.getOnPosition().remove(this);
    }

    /**
     * @return the bridge state
     */
    public Bridge.BridgeState getBridgeState(){
        return onBridgeState;
    }

    public enum State {
        EATEN, AVAILABLE
    }

}
