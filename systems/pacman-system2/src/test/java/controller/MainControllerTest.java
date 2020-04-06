/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package controller;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * MainControllerTest
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class MainControllerTest {

    @Before
    public void beforeTest() {
        MainController.reset();
    }

    @Test
    public void testMain() {
        MainController.main(new String[0]);
    }

    @Test
    public void testGetGui() {
        assertNotNull(MainController.getInstance().getGui());
    }

    @Test
    public void testGetInstance() {
        assertNotNull(MainController.getInstance());
    }

    @Test
    public void testStartAndPause() {
        MainController.getInstance().startGame();
        MainController.getInstance().pauseGame();
    }
}
