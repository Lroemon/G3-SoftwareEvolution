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

import static org.junit.Assert.*;

/**
 * GameTest
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class GameTest {
    @Before
    public void setUp() {
        MainController.reset();
    }

    @After
    public void tearDown() {
        MainController.reset();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(Game.getInstance());
    }

    @Test
    public void testReset() {
        Game old = Game.getInstance();
        Game.reset();
        assertNotSame(old, Game.getInstance());
    }

    @Test
    public void testIsInitialized() {
        assertTrue(Game.isInitialized());
    }

    @Test
    public void testGetGhostContainer() {
        assertNotNull(Game.getInstance().getGhostContainer());
    }

    @Test
    public void testGetCoinContainer() {
        assertNotNull(Game.getInstance().getCoinContainer());
    }

    @Test
    public void testGetPointContainer() {
        assertNotNull(Game.getInstance().getPointContainer());
    }

    @Test
    public void testGetPacmanContainer() {
        assertNotNull(Game.getInstance().getPacmanContainer());
    }

    @Test
    public void testGetMap() {
        assertNotNull(Game.getInstance().getMap());
    }

    @Test
    public void testGetRefreshRate() {
        assertNotNull(Game.getInstance().getRefreshRate());
    }

    @Test
    public void testGetEventHandlerManager() {
        assertNotNull(Game.getInstance().getEventHandlerManager());
    }

    @Test
    public void testStart() {
        Game.getInstance().start();
    }

    @Test
    public void testPause() {
        Game.getInstance().pause();
    }
}
