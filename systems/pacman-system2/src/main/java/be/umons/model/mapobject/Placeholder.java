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
 * A Placeholder stands for a Position that isn't intended to contain a Wall but where no DynamicTarget should
 * be movable to. An exception is allowed for ghosts respawning, see {@link DynamicTarget#move}.
 *
 * @author Philipp Winter
 */
public class Placeholder extends MapObject {

    public Placeholder(Position pos) {
        this.setPosition(pos);
    }

}
