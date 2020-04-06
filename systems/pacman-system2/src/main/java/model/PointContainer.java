/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

import model.exception.ObjectAlreadyInListException;

import java.util.Vector;
import java.util.Iterator;

/**
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class PointContainer implements Container<Point> {

    private Vector<Point> points;

    public final int max;

    public PointContainer() {
        this.max = 4;
        this.points = new Vector<>(max);
    }

    public void add(Point point) {
        if (!this.points.contains(point)) {
            this.points.add(point);
        } else {
            throw new ObjectAlreadyInListException(points.getClass().getCanonicalName());
        }
    }

    /**
     * Adds the elements of another container of the same type.
     *
     * @param container The other container.
     */
    @Override
    public void add(Container<Point> container) {
        for (Point p : container) {
            this.add(p);
        }
    }

    public Point get(int i) {
        return this.points.get(i);
    }

    public Point get(Position pos) {
        for (Point p : this.points) {
            if (p.getPosition().equals(pos)) {
                return p;
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public Vector<Point> getAll() {
        return (Vector<Point>) this.points.clone();
    }

    /**
     * Removes an element from the container.
     *
     * @param el The element to remove.
     */
    @Override
    public void remove(Point el) {
        this.points.remove(el);
    }

    public Iterator<Point> iterator() {
        return points.iterator();
    }

    public boolean contains(Point p) {
        return this.points.contains(p);
    }

    public int size() {
        return this.points.size();
    }

    public void removeAll() {
        for (Point p : this) {
            p.deSpawn();
        }

        this.points.clear();
    }
}
