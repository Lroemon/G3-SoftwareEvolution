/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

import controller.MainController;
import model.Ghost.Colour;
import model.exception.ObjectAlreadyInListException;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * GhostContainerTest
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class GhostContainerTest {

    private GhostContainer instance;

    private Ghost g1;
    private Ghost g2;

    private Position pos1;
    private Position pos2;

    @Before
    public void setUp() {
        MainController.reset();
        instance = new GhostContainer();

        pos1 = Game.getInstance().getMap().getPositionContainer().get(0, 0);
        pos2 = Game.getInstance().getMap().getPositionContainer().get(0, 1);

        g1 = new Ghost(pos1, Colour.ORANGE);
        g2 = new Ghost(pos2, Colour.PINK);
    }

    @Test
    public void testAdd() {
        instance.add(g1);
    }

    @Test(expected = ObjectAlreadyInListException.class)
    public void testAddObjectAlreadyInList() {
        instance.add(g1);
        instance.add(g1);
    }

    @Test
    public void testGetPerIndex() {
        instance.add(g1);
        assertSame(g1, instance.get(0));
    }

    @Test
    public void testGetPerPosition() {
        instance.add(g1);

        for (Ghost gg : instance) {
            System.out.println(gg);
        }

        Vector<Ghost> retrieved = instance.get(g1.getPosition());

        assertTrue(retrieved.contains(g1));
        assertEquals(1, retrieved.size());
    }

    @Test
    public void testGetAll() {
        instance.add(g1);
        instance.add(g2);

        Vector<Ghost> list = instance.getAll();
        assertNotNull(list);
        assertEquals(2, list.size());
        assertTrue(list.contains(g1));
        assertTrue(list.contains(g2));
    }

    @Test
    public void testRemove() {
        instance.add(g1);
        instance.remove(g1);
        assertFalse(instance.getAll().contains(g1));
    }

    @Test
    public void testGetMax() {
        assertEquals(4, instance.getMax());
    }

    @Test
    public void testIterator() {
        instance.add(g1);
        instance.add(g2);

        Iterator<Ghost> it = instance.iterator();
        assertNotNull(it);
        Ghost gg = null;
        while (it.hasNext()) {
            gg = it.next();
            assertTrue(gg.equals(g1) || gg.equals(g2));
        }

    }

    @Test
    public void testEquals() {
        assertEquals(instance, new GhostContainer());
    }
}
