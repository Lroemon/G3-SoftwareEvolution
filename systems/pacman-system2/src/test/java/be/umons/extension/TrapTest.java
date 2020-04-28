package be.umons.extension;

import be.umons.controller.MainController;
import be.umons.model.*;
import be.umons.model.event.WorkerProcess;
import be.umons.model.mapobject.*;
import org.junit.Before;
import org.junit.Test;

import static be.umons.model.mapobject.Ghost.Colour;
import static org.junit.Assert.*;


public class TrapTest {

    private Ghost ghost;
    private Trap trap;
    private Position pos;

    @Before
    public void setUp() {
        MainController.reset();
        this.pos = Map.getInstance().getPositionContainer().get(0, 0);
        this.ghost = new Ghost(pos, Colour.RED, 10);
        this.trap = new Trap(pos);
    }


    @Test
    public void testPacmanOnTrap() throws InterruptedException {
        // Set the speed to 10 so pacman normally moves all the time
        Pacman pacman = new Pacman(pos, Pacman.Sex.MALE, 10);
        assertTrue(pacman.canMove());

        trap.action(pacman);
        assertFalse(pacman.canMove());
        Thread.sleep(trap.trapTime + 1000) ;
        assertTrue(pacman.canMove());
    }
    @Test
    public void testGhostOnTrap() throws InterruptedException {
        // Set the speed to 10 so pacman normally moves all the time
        assertTrue(this.ghost.canMove());

        trap.action(this.ghost);
        assertFalse(this.ghost.canMove());
        Thread.sleep(trap.trapTime + 1000) ;
        assertTrue(this.ghost.canMove());
    }
}
