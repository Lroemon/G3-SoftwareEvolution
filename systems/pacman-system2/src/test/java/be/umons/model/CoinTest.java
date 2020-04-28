/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model;

import be.umons.controller.MainController;
import be.umons.model.mapobject.Coin;
import be.umons.model.mapobject.StaticTarget;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * CoinTest
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class CoinTest {

    private Coin instance;
    private Position pos;

    @Before
    public void setUp() {
        MainController.reset();

        pos = Map.getInstance().getPositionContainer().get(1, 5);

        instance = new Coin(pos);
    }

    @Test
    public void testGetState() {
        assertNotNull(instance.getState());
    }

    @Test
    public void testChangeState() {
        instance.changeState(StaticTarget.State.EATEN);
        assertEquals(StaticTarget.State.EATEN, instance.getState());
    }

    @Test
    public void testGetScore() {
        assertEquals(50, instance.getScore());
    }

    @Test
    public void testEquals() {
        Coin c = new Coin(Map.getInstance().getPositionContainer().get(0, 0));
        Coin c1 = instance;
        assertNotEquals(c, c1);
        assertEquals(instance, c1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructOnSamePosition() {
        new Coin(pos);
    }
}
