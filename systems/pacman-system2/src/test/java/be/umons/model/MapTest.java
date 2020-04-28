/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model;

import be.umons.controller.MainController;
import be.umons.model.Map.Direction;
import be.umons.model.container.ObjectContainer;
import be.umons.model.mapobject.MapObject;
import be.umons.model.mapobject.Wall;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * MapTest
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 * @author Rémy Decocq (modification)
 */
public class MapTest {

    private Map instance;

    @Before
    public void setUp() {
        MainController.reset();
        this.instance = Map.getInstance();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(Map.getInstance());
    }

    @Test
    public void testReset() {
        Map oldO = Map.getInstance();
        Map.reset();
        Map newO = Map.getInstance();
        assertNotSame(oldO, newO);
    }

    @Test
    public void testGetPositionContainer() {
        assertNotNull(instance.getPositionContainer());
    }

    @Test
    public void testPlaceAllObjects(){
        for (Position pos: instance.getPositionContainer()){
            assertEquals(0, pos.getOnPosition().size());
        }
        instance.placeObjects();
        assertTrue(instance.isObjectsPlaced());
        ArrayList<ObjectContainer<? extends MapObject>> conts = Game.getInstance().getAllMapobjecContainers();
        for (ObjectContainer<? extends MapObject> container: conts) {
            for (MapObject mo : container) {
                Position pos = mo.getPosition();
                assertNotNull(pos);
                assertTrue(instance.getPositionContainer().contains(pos));
            }
        }
    }

    @Test
    public void testGetPositionByDirectionIfMoveableTo() {
        for(Direction d: Direction.values()) {
            //Still nothing placed, being in the middle ..
            assertNotNull(Map.getPositionByDirectionIfMovableTo(instance.getPositionContainer().get(5, 5), d));
        }
        //On border
        assertNull(Map.getPositionByDirectionIfMovableTo(instance.getPositionContainer().get(0, 0), Direction.NORTH));
        assertNotNull(Map.getPositionByDirectionIfMovableTo(instance.getPositionContainer().get(0, 0), Direction.EAST));
        //Placing a Wall on previous free pos
        new Wall(instance.getPositionContainer().get(1,0));
        assertNull(Map.getPositionByDirectionIfMovableTo(instance.getPositionContainer().get(0, 0), Direction.EAST));
    }
}
