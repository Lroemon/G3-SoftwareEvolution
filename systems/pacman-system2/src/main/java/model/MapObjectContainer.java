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

/**
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class MapObjectContainer implements Container<MapObject> {

    private Vector<MapObject> mapObjects;

    public MapObjectContainer() {
        this.mapObjects = new Vector<>();
    }

    public MapObject get(int i) {
        return this.mapObjects.get(i);
    }

    @SuppressWarnings("unchecked")
    public Vector<MapObject> getAll() {
        return (Vector<MapObject>) this.mapObjects.clone();
    }

    /**
     * Removes an element from the container.
     *
     * @param el The element to remove.
     */
    @Override
    public void remove(MapObject el) {
        this.mapObjects.remove(el);
    }

    public void add(MapObject el) {
        if (!this.mapObjects.contains(el)) {
            this.mapObjects.add(el);
        }
    }

    /**
     * Adds the elements of another container of the same type.
     *
     * @param container The other container.
     */
    @Override
    public void add(Container<MapObject> container) {
        for (MapObject mO : container) {
            this.add(mO);
        }
    }

    public Iterator<MapObject> iterator() {
        return mapObjects.iterator();
    }

    public int size() {
        return this.mapObjects.size();
    }

    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof MapObjectContainer) {
                return this.getAll().equals(((MapObjectContainer) o).getAll());
            }
        }
        return false;
    }

    public boolean contains(MapObject mO) {
        return this.mapObjects.contains(mO);
    }

}
