/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

import model.exception.ListFullException;
import model.exception.ObjectAlreadyInListException;

import java.util.Vector;
import java.util.Iterator;

/**
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class PacmanContainer implements Container<Pacman> {

    /**
     * All pacman instances, for example Pacman and Mrs. Pacman
     */
    private Vector<Pacman> pacmans;

    public final int max = 2;

    public PacmanContainer() {
        this.pacmans = new Vector<>(max);
    }

    public void add(Pacman pacman) {
        if (!this.pacmans.contains(pacman)) {
            if (this.pacmans.size() < this.max) {
                this.pacmans.add(pacman);
            } else {
                throw new ListFullException(pacman.getClass().getCanonicalName());
            }
        } else {
            throw new ObjectAlreadyInListException(pacman.getClass().getCanonicalName());
        }
    }

    /**
     * Adds the elements of another container of the same type.
     *
     * @param container The other container.
     */
    @Override
    public void add(Container<Pacman> container) {
        for (Pacman p : container) {
            this.add(p);
        }
    }

    public Pacman get(int i) {
        return this.pacmans.get(i);
    }

    public Vector<Pacman> get(Position pos) {
        Vector<Pacman> pacmansOnPosition = new Vector<>(2);

        for (Pacman p : this.pacmans) {
            if (p.isOnPosition(pos)) {
                pacmansOnPosition.add(p);
            }
        }

        return pacmansOnPosition;
    }

    @SuppressWarnings("unchecked")
    public Vector<Pacman> getAll() {
        return (Vector<Pacman>) this.pacmans.clone();
    }

    /**
     * Removes an element from the container.
     *
     * @param el The element to remove.
     */
    @Override
    public void remove(Pacman el) {
        this.pacmans.remove(el);
    }

    public Iterator<Pacman> iterator() {
        return pacmans.iterator();
    }

    public boolean contains(Pacman p) {
        return this.pacmans.contains(p);
    }

}
