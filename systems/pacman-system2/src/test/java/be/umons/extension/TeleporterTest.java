package be.umons.extension;

import be.umons.controller.MainController;
import be.umons.model.Map;
import be.umons.model.Position;
import be.umons.model.mapobject.Ghost;
import be.umons.model.mapobject.Pacman;
import be.umons.model.mapobject.Teleporter;
import be.umons.model.mapobject.Trap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TeleporterTest {

    private Teleporter linked;
    private Teleporter entry;
    private Position pos;
    private Position pos2;

    @Before
    public void setUp() {
        MainController.reset();
        this.pos = Map.getInstance().getPositionContainer().get(0, 0);
        this.pos2 = Map.getInstance().getPositionContainer().get(2, 0);
        this.linked = new Teleporter(pos2);
        this.entry = new Teleporter(pos, linked);
    }


    @Test
    public void testPacmanOnTeleporter() {
        Pacman pacman = new Pacman(pos, Pacman.Sex.MALE, 10);

        assertTrue(pacman.getPosition() == entry.getPosition());
        entry.action(pacman);
        assertTrue(pacman.getPosition() == linked.getPosition());
    }
}
