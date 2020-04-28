/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model.container;

import java.util.Vector;

/**
 * The basic Container interface.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */

public interface Container<E> extends Iterable<E> {

    /**
     * The per index getter.
     *
     * @param i The index of the element.
     *
     * @return Element on index i.
     */
    E get(int i);

    /**
     * Adds an element to the container.
     *
     * @param el The element to add.
     *
     * @throws model.exception.ListException                Parent class of all following exceptions.
     * @throws model.exception.ObjectAlreadyInListException When the object <i>el</i> is already in the list.
     * @throws model.exception.ListFullException            When the list is already filled with the maximum amount of elements.
     */

    /**
     * Returns an Vector instance with all elements.
     *
     * @return A clone of the internal used ArrayList, so it can be mutated securely.
     * @see java.util.Vector#clone()
     */
    Vector<E> getAll();

    void add(E el);

    /**
     * Adds the elements of another container of the same type.
     *
     * @param container The other container.
     */
    void add(Container<E> container);

    /**
     * Removes an element from the container.
     *
     * @param el The element to remove.
     */
    void remove(E el);

    /**
     * Empty the container.
     */
    void removeAll();

    /**
     * Returns whether an object is in the container (compares by the .equals() method, so be sure to implement it!).
     *
     * @param o The object to check again.
     *
     * @return Whether <i>o</i> is contained in this container.
     */
    boolean contains(E o);

    /**
     * Get the container size in term of contained elements
     *
     * @return the number of elements currently maintained
     */
    int size();
}
