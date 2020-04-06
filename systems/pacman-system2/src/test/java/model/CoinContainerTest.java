/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

import controller.MainController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

/**
 * CoinContainerTest
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class CoinContainerTest {

    private CoinContainer instance;
    private Position pos;
    private Position pos2;

    @Before
    public void setUp() {
        MainController.reset();

        this.instance = new CoinContainer();
        this.pos = Game.getInstance().getMap().getPositionContainer().get(1, 2);
        this.pos2 = Game.getInstance().getMap().getPositionContainer().get(2, 2);
    }

    @After
    public void tearDown() {
        this.instance = null;
        MainController.reset();
    }

    @Test
    public void testAdd() {
        Coin c = new Coin(pos);
        instance.add(c);
    }

    @Test
    public void testGetPerIndex() {
        Coin c = new Coin(pos);

        instance.add(c);
        Coin retrievedCoin = instance.get(0);
        assertTrue(retrievedCoin != null);
        assertEquals(pos, retrievedCoin.getPosition());
    }

    @Test
    public void testGetPerPosition() {
        Coin c = new Coin(pos);

        instance.add(c);

        assertEquals(c, instance.get(pos));
    }

    @Test
    public void testGetAll() {
        Coin c = new Coin(pos);
        Coin c2 = new Coin(pos2);

        instance.add(c);
        instance.add(c2);

        Vector<Coin> list = instance.getAll();

        assertNotNull(list);

        for (Coin coin : list) {
            assertTrue(coin == c || coin == c2);
        }
    }

    @Test
    public void testRemove() {
        Coin c = new Coin(pos);
        Coin c2 = new Coin(pos2);

        instance.add(c);
        instance.add(c2);

        instance.remove(c2);

        for (Coin coin : instance) {
            assertNotSame(c2, coin);
        }
    }

    @Test
    public void testGetMax() {
        assertEquals(4, instance.getMax());
    }

    @Test
    public void testIterator() {
        assertNotNull(instance.iterator());
    }

    @Test
    public void testEquals() {
        assertEquals(new CoinContainer(), new CoinContainer());
    }
}
