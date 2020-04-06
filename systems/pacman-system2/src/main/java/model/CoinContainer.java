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
import java.util.Vector;

/**
 * The container of all coins on the map.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class CoinContainer implements Container<Coin> {

    /**
     * A list of all coins.
     */
    private Vector<Coin> coins;

    public final int max = 4;

    /**
     * Constructs a new CoinContainer.
     */
    public CoinContainer() {
        this.coins = new Vector<>(max);
    }

    /**
     * Adds an element to the list if it isn't there yet.
     *
     * @param coin The element to add.
     *
     * @throws ListFullException            When the list is already filled with {@link CoinContainer#max} elements.
     * @throws ObjectAlreadyInListException When the object is already in the list.
     */
    public void add(Coin coin) {
        if (!this.coins.contains(coin)) {
            if (this.coins.size() < max) {
                this.coins.add(coin);
            } else {
                throw new ListFullException(coin.getClass().getCanonicalName());
            }
        } else {
            throw new ObjectAlreadyInListException(coin.getClass().getCanonicalName());
        }

    }

    /**
     * Adds the elements of another container of the same type.
     *
     * @param container The other container.
     */
    @Override
    public void add(Container<Coin> container) {
        for (Coin c : container) {
            this.add(c);
        }
    }

    /**
     * Returns a Coin element per index.
     *
     * @param i The index.
     *
     * @return The coin object on the index.
     */
    public Coin get(int i) {
        return this.coins.get(i);
    }

    /**
     * Returns a Coin element on the position, or null if none is found.
     *
     * @param pos The position.
     *
     * @return The coin on the position.
     */
    public Coin get(Position pos) {
        for (Coin c : this.coins) {
            if (c.isOnPosition(pos)) {
                return c;
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public Vector<Coin> getAll() {
        return (Vector<Coin>) this.coins.clone();
    }

    /**
     * Removes an element from the container.
     *
     * @param el The element to remove.
     */
    public void remove(Coin el) {
        this.coins.remove(el);
    }

    /**
     * Returns the maximum elements that are allowed in this list.
     *
     * @return The maximum amount of elements.
     */
    public int getMax() {
        return max;
    }

    /**
     * Iterate over all coins.
     *
     * @return An iterator over all coin objects.
     * @see java.util.Vector
     */
    @Override
    public Iterator<Coin> iterator() {
        return coins.iterator();
    }

    public boolean contains(Coin mO) {
        return this.coins.contains(mO);
    }

    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof CoinContainer) {
                CoinContainer gC = (CoinContainer) o;
                return this.coins.equals(gC.getAll());
            }
        }
        return false;
    }

    public void removeAll() {
        for(Coin c : this) {
            c.deSpawn();
        }

        this.coins.clear();
    }
}
