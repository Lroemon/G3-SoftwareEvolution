/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

/**
 * The position class represents a point on the map. IT SHOULD NOT BE CONSTRUCTED OUTSIDE THE {@link Map} CLASS.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class Position {

    private final int x;

    private final int y;

    private MapObjectContainer onPosition;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.onPosition = new MapObjectContainer();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public MapObjectContainer getOnPosition() {
        return onPosition;
    }

    @SuppressWarnings("unused")
    public void add(MapObject mapObject) {
        assert mapObject.getPosition() != null;
        this.onPosition.add(mapObject);
    }

    @SuppressWarnings("unused")
    public void remove(MapObject mapObject) {
        this.onPosition.remove(mapObject);
    }

    public boolean isMoveableTo() {
        for (MapObject mO : this.onPosition) {
            if (mO instanceof Wall) {
                return false;
            }
        }
        return true;
    }

    public double calcDistance(Position pos) {
        if (pos == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        // A little bit of math, using Pythagoras' Theorem
        return Math.sqrt(
                Math.pow(this.getX() - pos.getX(), 2) +
                        Math.pow(this.getY() - pos.getY(), 2)
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof Position) {
                Position p = (Position) obj;
                return p.getX() == this.getX()
                        && p.getY() == this.getY();
            }
        }

        return false;
    }

    public String toString() {
        return x + "|" + y;
    }

}
