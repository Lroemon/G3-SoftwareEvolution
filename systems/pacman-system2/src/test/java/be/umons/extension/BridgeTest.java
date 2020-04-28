package be.umons.extension;

import be.umons.controller.MainController;
import be.umons.model.Map;
import be.umons.model.Position;
import be.umons.model.mapobject.Bridge;
import be.umons.model.mapobject.Ghost;
import be.umons.model.mapobject.Pacman;
import be.umons.model.mapobject.Teleporter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BridgeTest {
    private Bridge bridge;
    private Position pos;

    @Before
    public void setUp() {
        MainController.reset();
    }


    @Test
    public void testBridge() {
        Position pos = Map.getInstance().getPositionContainer().get(0, 0);
        Pacman pacman = new Pacman(pos, Pacman.Sex.MALE);
        Ghost ghost = new Ghost(pos, Ghost.Colour.BLUE);
        pacman.setHeadingTo(Map.Direction.NORTH);
        ghost.setHeadingTo(Map.Direction.WEST);
        Bridge bridge = new Bridge(pos, Bridge.Type.NORTH_SOUTH);

        assertSame(pacman.getBridgeState(), Bridge.BridgeState.NOT_ON_BRIDGE);
        assertTrue(pacman.getAllowedDirections().contains(Map.Direction.EAST));
        assertTrue(pacman.getAllowedDirections().contains(Map.Direction.NORTH));
        assertTrue(pacman.getAllowedDirections().contains(Map.Direction.WEST));
        assertTrue(pacman.getAllowedDirections().contains(Map.Direction.SOUTH));
        assertTrue(pacman.canTouch(ghost));

        bridge.action(pacman);
        assertSame(pacman.getBridgeState(), Bridge.BridgeState.ON_NS_BRIDGE);
        assertTrue(pacman.getAllowedDirections().contains(Map.Direction.NORTH));
        assertTrue(pacman.getAllowedDirections().contains(Map.Direction.SOUTH));

        bridge.action(ghost);
        assertSame(ghost.getBridgeState(), Bridge.BridgeState.UNDER_NS_BRIDGE);
        assertTrue(ghost.getAllowedDirections().contains(Map.Direction.EAST));
        assertTrue(ghost.getAllowedDirections().contains(Map.Direction.WEST));

        assertFalse(pacman.canTouch(ghost));
    }
}
