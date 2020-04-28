package be.umons.model;


import be.umons.model.container.PositionContainer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Rémy Decocq
 */
public class PositionContainerTest {

    private PositionContainer pc;

    @Before
    public void setUp(){
        pc = new PositionContainer(2, 2);
    }

    @Test
    public void testAdd(){
        Position p = new Position(0,0);
        Position pSameCoord = new Position(0,0);
        assertEquals(0, pc.size());
        pc.add(p);
        assertEquals(1, pc.size());
        assertTrue(pc.contains(p));
        pc.add(pSameCoord);
        assertEquals(1, pc.size());
        assertTrue(pc.contains(p)); // contains() relative to index resulting from coordinates
        assertTrue(pc.contains(pSameCoord));
        assertSame(pSameCoord, pc.get(0, 0));
        assertNotSame(p, pc.get(0, 0)); // but referenced instance has effectively been changed
    }

    @Test
    public void testGet(){
        Position p = new Position(0,0);
        pc.add(p);
        assertSame(p, pc.get(0 ,0));
        //assertThrows(IllegalArgumentException.class, () -> { pc.get(0,1);});
    }

    @Test
    public void testRemove(){
        Position p = new Position(0,0);
        pc.add(p);
        assertEquals(1, pc.size());
        pc.remove(p);
        assertEquals(0, pc.size());
    }

    @Test
    public void testGetRange(){
        pc.fill();
        PositionContainer subset = pc.getRange(pc.get(0,0), pc.get(1, 0));
        assertTrue(subset.contains(pc.get(0,0)));
        assertFalse(subset.contains(pc.get(1,1)));
    }

}
