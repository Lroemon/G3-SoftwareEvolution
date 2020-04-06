/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

import java.util.Vector;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class PositionContainer implements Container<Position> {

    private ConcurrentHashMap<String, Position> positions;

    private int width;

    private int height;

    public PositionContainer(int width, int height) {
        this.positions = new ConcurrentHashMap<>(width * height);
        this.width = width;
        this.height = height;
    }

    /**
     * Retrieve a position by it's x and y coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     *
     * @return The position object.
     */
    public Position get(int x, int y) {
        Position val = positions.get(generateKey(x, y));
        if (val == null) {
            throw new IllegalArgumentException("The point " + x + "|" + y + " doesn't exist.");
        }
        return val;
    }

    @Override
    public Position get(int i) {
        // Not available here
        return null;
    }

    /**
     * Adds a position to the container. DO NOT USE THIS OUTSIDE {@link Map}.
     *
     * @param el The element to add.
     */
    public void add(Position el) {
        this.positions.put(generateKey(el), el);
    }

    /**
     * Adds the elements of another container of the same type.
     *
     * @param container The other container.
     */
    @Override
    public void add(Container<Position> container) {
        for (Position p : container) {
            this.add(p);
        }
    }

    /**
     * Generate a key based on the supplied values.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     *
     * @return The position object.
     */
    private String generateKey(int x, int y) {
        return x + "#" + y;
    }

    public String generateKey(Position pos) {
        return generateKey(pos.getX(), pos.getY());
    }

    public Vector<Position> getAll() {
        return new Vector<>(this.positions.values());
    }

    /**
     * Removes an element from the container.
     *
     * @param el The element to remove.
     */
    @Override
    public void remove(Position el) {
        this.positions.remove(generateKey(el.getX(), el.getY()));
    }

    public Iterator<Position> iterator() {
        return positions.values().iterator();
    }

    public boolean contains(Position p) {
        return this.positions.containsKey(this.generateKey(p));
    }

    public PositionContainer getRange(Position startPos, Position endPos) {
        int startX = startPos.getX();
        int startY = startPos.getY();
        int endX = endPos.getX();
        int endY = endPos.getY();

        PositionContainer ret = new PositionContainer(width, height);

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                ret.add(get(i, j));
            }
        }

        return ret;
    }

    public void removeAll() {
        this.positions.clear();
    }
}
