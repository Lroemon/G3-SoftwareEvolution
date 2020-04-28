/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model.mapobject;

import be.umons.model.Map;
import be.umons.model.Position;

/**
 * An object on the {@link Map} necessarily has a {@link Position} on it and a visibility status.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 * @author Rémy Decocq (modification)
 */

public abstract class MapObject {

    protected Position position;

    protected boolean visible = true;

    public boolean isOnPosition(Position pos) {
        return this.getPosition().equals(pos);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position pos) {
        if (pos == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
        Position oldPos = this.position;
        if (oldPos != null) {
            oldPos.remove(this);
            // Object has moved from its old position so render of it should be updated
            Map.positionsToRender.add(oldPos);
        }
        this.position = pos;
        // Register itself as located at the new position
        this.position.add(this);
    }

    public boolean isVisible() {
        return visible;
    }

    protected void setVisible(boolean value) {
        visible = value;
    }

    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof MapObject && this.getClass() == o.getClass()) {
                MapObject mO = (MapObject) o;
                return this.getPosition().equals(mO.getPosition());
            }
        }
        return false;
    }


}
