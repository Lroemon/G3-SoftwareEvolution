/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model;

import be.umons.controller.MainController;
import be.umons.model.event.WorkerProcess;
import be.umons.model.mapobject.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static be.umons.model.mapobject.Ghost.Colour;
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
        // We don't call MainController.start() so no objects are placed on Map, but Positions are instantiated in
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
        assertEquals(Colour.RED, instance.getColour());
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
    public void testGetScore() {
        Pacman p = new Pacman(pos, Pacman.Sex.MALE);
        assertEquals(200, instance.getScore()); // Should firstly give 200 score eaten once
        p.changeState(DynamicTarget.State.HUNTER);
        instance.changeState(DynamicTarget.State.HUNTED);
        p.eat(instance);
        assertEquals(200, p.getScore().getScore());
        assertEquals(200, instance.getScore());
        p.eat(instance);
        assertEquals(200 + 400, p.getScore().getScore());
        assertEquals(400, instance.getScore()); // Consecutive ghost increase given score value
        p.eat(instance);
        assertEquals(200 + 400 + 800, p.getScore().getScore());
        assertEquals(800, instance.getScore());
        p.eat(instance);
        assertEquals(200 + 400 + 800 + 1600, p.getScore().getScore());
        assertEquals(1600, instance.getScore());
    }

    @Test
    public void testChangeState() {
        // A Ghost spawn as hunter, see its constructor overloading DynamicTarget's one (which dflt WAITING)
        assertEquals(DynamicTarget.State.HUNTER, instance.getState());
        assertEquals(-1., instance.getWaitingSeconds(), 0);

        instance.changeState(DynamicTarget.State.WAITING);
        assertEquals(DynamicTarget.State.WAITING, instance.getState());
        assertEquals(Ghost.WAIT_SECONDS, instance.getWaitingSeconds(), 0);

        assertEquals(Coin.nbr_ghosts_eaten_in_a_row, 0);
        instance.changeState(DynamicTarget.State.MUNCHED);
        assertEquals(DynamicTarget.State.MUNCHED, instance.getState());
        assertEquals(Coin.nbr_ghosts_eaten_in_a_row, 1);

        instance.changeState(DynamicTarget.State.HUNTED);
        assertEquals(DynamicTarget.State.HUNTED, instance.getState());
    }

    @Test
    public void testReduceWaitingSeconds() {
        assertEquals(-1., instance.getWaitingSeconds(), 0); // HUNTER State
        instance.reduceWaitingSeconds(1.);
        assertEquals(-1, instance.getWaitingSeconds(), 0);
        instance.changeState(DynamicTarget.State.WAITING);
        assertEquals(Ghost.WAIT_SECONDS, instance.getWaitingSeconds(),0);
        instance.reduceWaitingSeconds(Ghost.WAIT_SECONDS);
        assertEquals(0, instance.getWaitingSeconds(),0);
        instance.reduceWaitingSeconds(Ghost.WAIT_SECONDS);
        assertEquals(0, instance.getWaitingSeconds(),0);
    }

    @Test
    public void testEat() {
        Pacman p = new Pacman(pos, Pacman.Sex.MALE);

        assertSame(DynamicTarget.State.HUNTER, instance.getState());
        assertSame(DynamicTarget.State.HUNTED, p.getState());
        assertEquals(Settings.getInstance().getInitPlayerLifes(), Game.getInstance().getPlayerLifes());
        instance.eat(p); // ghost eats pacman
        assertSame(DynamicTarget.State.HUNTER, instance.getState());
        assertSame(DynamicTarget.State.HUNTED, p.getState());
        // As our Pacman gets immediately respawned, it won't have a different state
        assertEquals(Settings.getInstance().getInitPlayerLifes() - 1, Game.getInstance().getPlayerLifes());
    }

    @Test
    public void testGotEaten() {
        Pacman p = new Pacman(pos, Pacman.Sex.MALE);

        p.changeState(DynamicTarget.State.HUNTER);
        instance.changeState(DynamicTarget.State.HUNTED);

        assertEquals(Settings.getInstance().getInitPlayerLifes(), Game.getInstance().getPlayerLifes());
        p.eat(instance); // pacman eats ghost
        assertSame(DynamicTarget.State.MUNCHED, instance.getState());
        assertSame(DynamicTarget.State.HUNTER, p.getState());
        // Pacman keep all his lives
        assertEquals(Settings.getInstance().getInitPlayerLifes(), Game.getInstance().getPlayerLifes());
    }

    @Test
    public void testMove(){
        Position newPos = Map.getInstance().getPositionContainer().get(1,1);
        assertTrue(newPos.isMoveableTo());
        instance.move(newPos);
        assertTrue(newPos.getOnPosition().contains(instance));
        assertFalse(pos.getOnPosition().contains(instance));
    }

    @Test
    public void testMoveBlocked(){
        Position newPos = Map.getInstance().getPositionContainer().get(0,1);
        assertTrue(newPos.isMoveableTo());
        Position wallPos = Map.getInstance().getPositionContainer().get(1, 0);
        new Wall(wallPos); // register Wall instance to Position
        assertFalse(wallPos.isMoveableTo());

        assertEquals(1, Map.freeNeighbourFields(pos));
        assertSame(newPos, Map.getPositionByDirectionIfMovableTo(pos, Map.Direction.SOUTH));

        instance.move(wallPos);
        assertSame(pos, instance.getPosition()); // Should not have moved since there was a wall on the target pos

        instance.move(newPos);
        assertSame(newPos, instance.getPosition()); // Should have moved well this time !
        assertNotSame(pos, instance.getPosition());
    }

    @Test
    public void testMoveToStartingPos(){
        MapPlacer.replaceDynamicObject(instance); // Reset the Ghost position to predefined one for regular spawning
        assertNotSame(pos, instance.getPosition());
        Map m = Map.getInstance();
        assertSame(m.getStartingPosition(instance), instance.getPosition());
        assertTrue(m.isOnStartingPos(instance));
    }

    @Test
    public void testMoveToStartingPosWhenEaten(){
        Pacman p = new Pacman(pos, Pacman.Sex.MALE);

        instance.changeState(DynamicTarget.State.HUNTED);
        p.changeState(DynamicTarget.State.HUNTER);
        assertSame(DynamicTarget.State.HUNTED, instance.getState()); // Ghost is running away, coward !
        assertSame(DynamicTarget.State.HUNTER, p.getState());

        Game.getInstance().getPointContainer().add(new Point(new Position(0,0))); // avoid passing to next lvl when running
        Game.getInstance().getPacmanContainer().add(p);
        Game.getInstance().getGhostContainer().add(instance);
        WorkerProcess wp = new WorkerProcess();
        wp.onLoad();
        wp.run();
        assertEquals(DynamicTarget.State.WAITING, instance.getState());

        assertTrue(Map.getInstance().isOnStartingPos(instance)); // Ghost is sent back to his home, goodbye ghost !
        assertSame(pos, p.getPosition()); // Pacman stays here, victorious !
    }

}
