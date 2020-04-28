/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model;

import be.umons.controller.MainController;
import be.umons.model.mapobject.Ghost;
import be.umons.model.mapobject.Pacman;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * HighscoreTest
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class ScoreTest {

    private Score instance;
    private Position pos;
    private Pacman pac;

    @Before
    public void setUp() {
        MainController.reset();
        pos = Game.getInstance().getMap().getPositionContainer().get(0, 0);
        pac = new Pacman(pos, Pacman.Sex.MALE);
        instance = pac.getScore();
    }

    @After
    public void tearDown() {
        MainController.reset();
    }

    @Test
    public void testGetScore() {
        assertEquals(0, instance.getScore());
    }

    @Test
    public void testAddToScore() {
        assertEquals(0, instance.getScore());
        Ghost g = new Ghost(pos, Ghost.Colour.BLUE);
        instance.addToScore(g);
        assertEquals(g.getScore(), instance.getScore());
    }
    @Test
    public void testEquals() {
        assertEquals(instance, pac.getScore());
    }
}
