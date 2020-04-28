/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model;

import be.umons.controller.MainController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

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

    @After
    public void tearDown() {
        MainController.reset();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(Level.getInstance());
    }

    @Test
    public void testGetLevel() {
        assertEquals(1, instance.getLevel());
    }

    @Test
    public void testNextLevel() {
        // Need to place objects manually since we didnt call GameController.start() that does it normally. If we don't
        // the nbr of Points is 0, and the WorkerProcess relaunched by Level.nextLevel() will fall in the condition
        // coins = 0, meaning player finished the level and will so get player to next levels indefinitely.
        Map.getInstance().placeObjects();
        assertEquals(1, instance.getLevel());
        instance.nextLevel();
        assertEquals(2, instance.getLevel());
        assertTrue("Assert that " + Game.getInstance().getRefreshRate() + " > than " + Game.BASIC_REFRESH_RATE,
                Game.getInstance().getRefreshRate() > Game.BASIC_REFRESH_RATE);
    }

    @Test
    public void testEquals() {
        assertEquals(instance, instance);
    }
}
