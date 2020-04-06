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

import static model.Ghost.Colour;
import static org.junit.Assert.*;

/**
 * GhostTest
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class GhostTest {

    private Ghost instance;
    private Position pos;

    @Before
    public void setUp() {
        MainController.reset();
        this.pos = Map.getInstance().getPositionContainer().get(0, 0);
        this.instance = new Ghost(pos, Colour.RED);
    }

    @After
    public void tearDown() {
        MainController.reset();
    }

    @Test
    public void testGetColour() {
        assertNotNull(instance.getColour());
    }

    @Test
    public void testSetColour() {
        instance.setColour(Colour.BLUE);
        assertSame(Colour.BLUE, instance.getColour());
    }

    @Test
    public void testSetAndGetName() {
        instance.setName("DudeThisNameIsAwful");
        assertEquals("DudeThisNameIsAwful", instance.getName());
    }

    @Test
    public void testEat() {
        Pacman p = new Pacman(pos, Pacman.Sex.MALE);

        assertSame(DynamicTarget.State.HUNTER, instance.getState());
        assertSame(DynamicTarget.State.HUNTED, p.getState());
        instance.eat(p);
        assertSame(DynamicTarget.State.HUNTER, instance.getState());
        assertSame(DynamicTarget.State.HUNTED, p.getState());
        // As our Pacman gets immediately respawned, it won't have a different state
    }

    @Test
    public void testGetScore() {
        assertEquals(200, instance.getScore());
    }

    // TODO Implement

    @Test
    public void testChangeState() {

    }

    @Test
    public void testGotEaten() {

    }

    @Test
    public void testReduceWaitingSeconds() {

    }
}
