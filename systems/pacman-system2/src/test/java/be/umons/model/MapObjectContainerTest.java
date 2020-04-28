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
import be.umons.model.mapobject.Ghost;
import be.umons.model.mapobject.Ghost.Colour;
import be.umons.model.Map.Direction;
import be.umons.model.container.ObjectContainer;
import be.umons.model.mapobject.MapObject;
import be.umons.model.mapobject.Pacman;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * MapObjectContainerTest
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class MapObjectContainerTest {

    private ObjectContainer<MapObject> instance;
    private Position pos;
    private Position otherPos;
    private Pacman pac;
    private Ghost ghost;

    @Before
    public void setUp() {
        MainController.reset();
        this.pos = Map.getInstance().getPositionContainer().get(1, 1);
        this.otherPos = Map.getPositionByDirectionIfMovableTo(pos, Direction.EAST);
        this.pac = new Pacman(pos, Pacman.Sex.MALE);
        this.ghost = new Ghost(pos, Colour.PINK);
        this.instance = pos.getOnPosition();  // Container of MapObjects at this pos (the pacman and the ghost for now).
    }


    @Test
    public void testGet() {
        assertNotNull(instance.get(0));
    }

    @Test
    public void testGetAll() {
        assertNotNull(instance.getAll());
    }

    @Test
    public void testSize() {
        assertEquals(2, instance.size()); // Pacman and a ghost
    }

    @Test
    public void testRemove() {
        pac.move(otherPos);
        assertFalse(instance.contains(pac));
    }

    @Test
    public void testAdd() {
        new Ghost(pos, Colour.BLUE);
        new Ghost(pos, Colour.RED);
        new Coin(pos);
        assertEquals(5, instance.size());
    }

    @Test(expected = be.umons.model.exception.ObjectAlreadyInListException.class)
    public void testAddAlreadyPresent() {
        new Pacman(pos, Pacman.Sex.MALE);
    }

    @Test
    public void testIterator() {
        assertNotNull(instance.iterator());
    }

    @Test
    public void testContains() {
        assertTrue(instance.contains(pac));
        assertTrue(instance.contains(ghost));
        assertFalse(instance.contains(new Ghost(otherPos, Colour.RED)));
    }
}
