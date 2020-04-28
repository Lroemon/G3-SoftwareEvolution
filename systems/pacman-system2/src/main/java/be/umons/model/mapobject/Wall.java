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
 * A wall is limiting the area a {@link Target} can move to.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */

public class Wall extends MapObject {

    public Wall(Position position) {
        this.setPosition(position);
    }

    public String toString() {
        return "Wall [" + position + "]";
    }

}
