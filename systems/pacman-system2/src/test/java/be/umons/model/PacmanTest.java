package be.umons.model;

import be.umons.controller.MainController;
import be.umons.model.mapobject.DynamicTarget;
import be.umons.model.mapobject.Ghost;
import be.umons.model.mapobject.Pacman;
import be.umons.model.mapobject.Point;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Rémy Decocq
 *
 */
public class PacmanTest {

    private Pacman pacman;
    private Position initPos;

    @Before
    public void setUp() {
        MainController.reset();
        this.initPos = Map.getInstance().getActualPosition(new Position(0, 0));
        this.pacman = new Pacman(initPos, Pacman.Sex.MALE);
    }

    @Test
    public void testExistingPosition(){
        assertNotNull(pacman.getPosition());
        assertTrue(initPos.getOnPosition().contains(pacman));
    }

    @Test
    public void testScore(){
        assertNotNull(pacman.getScore());
        assertEquals(Score.INIT_SCORE, pacman.getScore().getScore());
        Point point = new Point(initPos);
        pacman.eat(point);
        assertEquals(Score.INIT_SCORE+point.getScore(), pacman.getScore().getScore());
    }

    @Test
    public void testState(){
        assertEquals(DynamicTarget.State.HUNTED, pacman.getState());
        int lives = Settings.getInstance().getInitPlayerLifes();
        assertEquals(lives, Game.getInstance().getPlayerLifes());
        pacman.changeState(DynamicTarget.State.MUNCHED);
        assertEquals(DynamicTarget.State.HUNTED, pacman.getState()); // If still alive, reset state before munched
        assertEquals(lives-1, Game.getInstance().getPlayerLifes());
        pacman.changeState(DynamicTarget.State.HUNTER);
        assertEquals(DynamicTarget.State.HUNTER, pacman.getState());
    }

    @Test
    public void testGotEatenByGhost(){
        Ghost g = new Ghost(initPos, Ghost.Colour.RED);

        assertEquals(DynamicTarget.State.HUNTED, pacman.getState());
        assertEquals(DynamicTarget.State.HUNTER, g.getState());
        int lives = Settings.getInstance().getInitPlayerLifes();
        assertEquals(lives, Game.getInstance().getPlayerLifes());

        g.eat(pacman);
        assertEquals(lives-1, Game.getInstance().getPlayerLifes());
        assertEquals(DynamicTarget.State.HUNTED, pacman.getState()); // If still alive, reset state before munched
        assertEquals(DynamicTarget.State.HUNTER, g.getState()); // If still alive, reset state before munched
    }

    @Test
    public void testResetAllTargetPosWhenEaten(){
        initPos.remove(pacman); // get rid of him because we'll spawn a new one
        MapPlacer.placeAllDynamicObjects(); // feed Game containers with Pacman/Ghosts
        for(Ghost g: Game.getInstance().getGhostContainer()){
            Position nextPosG = Map.getPositionByDirectionIfMovableTo(g.getPosition(), g.getHeadingTo());
            if (nextPosG != null)
                g.move(nextPosG); // move each ghost once
        }
        pacman = Game.getInstance().getPacmanContainer().get(0);
        Position nextPos = Map.getPositionByDirectionIfMovableTo(pacman.getPosition(), pacman.getHeadingTo());
        if (nextPos != null)
            pacman.move(nextPos);
        pacman.gotEaten(); // Should reset all positions
        assertTrue(Map.getInstance().isOnStartingPos(pacman));
        for(Ghost g: Game.getInstance().getGhostContainer()){
            assertTrue(Map.getInstance().isOnStartingPos(g));
        }
    }
}
