package be.umons.extension;

import be.umons.controller.MainController;
import be.umons.model.Map;
import be.umons.model.Position;
import be.umons.model.mapobject.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BasicFruitTest {
    private Position pos;

    @Before
    public void setUp() {
        MainController.reset();
        this.pos = Map.getInstance().getPositionContainer().get(0, 0);
    }


    @Test
    public void testEatTomato() throws InterruptedException {
        Pacman pacman = new Pacman(pos, Pacman.Sex.MALE, 10);
        Tomato tomato = new Tomato(pos);
        assertSame(pacman.getState(), DynamicTarget.State.HUNTED);

        tomato.makePacmanInvisible(pacman);
        assertSame(pacman.getState(), DynamicTarget.State.INVINSIBLE);
        Thread.sleep(tomato.duration + 1000);
        assertSame(pacman.getState(), DynamicTarget.State.HUNTED);
    }

    @Test
    public void testEatPepper() throws InterruptedException {
        Pacman pacman = new Pacman(pos, Pacman.Sex.MALE, 5);
        Pepper pepper = new Pepper(pos);

        assertEquals(5, pacman.getActual_speed());
        pepper.makePacmanFaster(pacman);
        assertEquals(10, pacman.getActual_speed());
        Thread.sleep(pepper.duration + 1000);
        assertEquals(5, pacman.getActual_speed());
    }

    @Test
    public void testEatFish() throws InterruptedException {
        Pacman pacman = new Pacman(pos, Pacman.Sex.MALE, 5);
        Fish fish = new Fish(pos);

        assertEquals(5, pacman.getActual_speed());
        fish.stopPacman(pacman);
        assertEquals(0, pacman.getActual_speed());
        Thread.sleep(fish.duration + 1000);
        System.out.println(pacman.getActual_speed());
        assertEquals(5, pacman.getActual_speed());
    }

    @Test
    public void testEatRedBean() throws InterruptedException {
        Pacman pacman = new Pacman(pos, Pacman.Sex.MALE, 10);
        RedBean redBean = new RedBean(pos);
        assertSame(pacman.getState(), DynamicTarget.State.HUNTED);

        redBean.makePacmanThrowFire(pacman);
        assertSame(pacman.getState(), DynamicTarget.State.FIRE);
        Thread.sleep(redBean.duration + 1000);
        assertSame(pacman.getState(), DynamicTarget.State.HUNTED);
    }
}
