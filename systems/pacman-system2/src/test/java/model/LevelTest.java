/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

import controller.MainController;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * LevelTest
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class LevelTest {

    private Level instance;

    @Before
    public void setUp() {
        MainController.reset();

        this.instance = Level.getInstance();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(Level.getInstance());
    }

    @Test
    public void testNextLevel() {
        assertEquals(1, instance.getLevel());
        instance.nextLevel();
        assertTrue("Assert that " + Game.getInstance().getRefreshRate() + " is greater than " + Game.BASIC_REFRESH_RATE, Game.getInstance().getRefreshRate() > Game.BASIC_REFRESH_RATE);
        assertEquals(2, instance.getLevel());
    }

    @Test
    public void testGetLevel() {
        assertEquals(1, instance.getLevel());
    }

    @Test
    public void testGetSecondsPerCoin() {
        assertThat(instance.getSecondsPerCoin(), is(7.0));
    }

    @Test
    public void testEquals() {
        assertThat(instance, is(instance));
    }
}
