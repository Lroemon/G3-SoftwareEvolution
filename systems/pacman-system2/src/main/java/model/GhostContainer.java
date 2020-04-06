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
 * A container of {@link Ghost}'s.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class GhostContainer implements Container<Ghost> {

    /**
     * The ghosts.
     */
    private Vector<Ghost> ghosts;

    /**
     * The maximal amount of ghosts.
     */
    public final int max = 4;

    /**
     * Constructs a new GhostContainer.
     */
    public GhostContainer() {
        this.ghosts = new Vector<>(max);
    }

    /**
     * Adds a ghost to the List.
     *
     * @param ghost The ghost to add.
     *
     * @throws model.exception.ListFullException            When the list is already filled with {@link #max} ghosts.
     * @throws model.exception.ObjectAlreadyInListException When the submitted ghost is already in the list.
     */
    public void add(Ghost ghost) {
        if (!this.ghosts.contains(ghost)) {
            if (this.ghosts.size() < this.max) {
                this.ghosts.add(ghost);
            } else {
                throw new ListFullException(ghost.getClass().getCanonicalName());
            }
        } else {
            throw new ObjectAlreadyInListException(ghost.getClass().getCanonicalName());
        }
    }

    /**
     * Adds the elements of another container of the same type.
     *
     * @param container The other container.
     */
    @Override
    public void add(Container<Ghost> container) {
        for (Ghost g : container) {
            this.add(g);
        }
    }

    /**
     * Gets a ghost per index.
     *
     * @param i The index of the element.
     *
     * @return The ghost at position <i>i</i>.
     */
    public Ghost get(int i) {
        return this.ghosts.get(i);
    }

    /**
     * Gets all ghosts on the submitted position.
     *
     * @param pos The position to look for.
     *
     * @return All ghosts on <i>pos</i>.
     */
    public Vector<Ghost> get(Position pos) {
        Vector<Ghost> onPosition = new Vector<>();
        for (Ghost g : this.ghosts) {
            if (g.isOnPosition(pos)) {
                onPosition.add(g);
            }
        }

        return onPosition;
    }

    /**
     * Returns an Vector instance with all elements.
     *
     * @return A clone of the internal used Vector, so it can be mutated securely.
     * @see java.util.Vector#clone()
     */
    @SuppressWarnings("unchecked")
    public Vector<Ghost> getAll() {
        return (Vector<Ghost>) this.ghosts.clone();
    }

    /**
     * Removes an element from the container.
     *
     * @param el The element to remove.
     */
    @Override
    public void remove(Ghost el) {
        this.ghosts.remove(el);
    }

    /**
     * Gets the maximal amount of ghosts, fitting in this container.
     *
     * @return The {@link #max}imum.
     */
    public int getMax() {
        return max;
    }

    /**
     * Gets the iterator over all ghosts.
     *
     * @return An iterator over all elements in this container.
     * @see java.util.Vector#iterator()
     */
    public Iterator<Ghost> iterator() {
        return ghosts.iterator();
    }

    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof GhostContainer) {
                return this.getAll().equals(((GhostContainer) o).getAll())
                        && this.getMax() == ((GhostContainer) o).getMax();
            }
        }
        return false;
    }

    public boolean contains(Ghost g) {
        return this.ghosts.contains(g);
    }

}
