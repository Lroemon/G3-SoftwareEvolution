package be.umons.model.mapobject;

import be.umons.model.Game;
import be.umons.model.Position;
import be.umons.model.Scorable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * If Pacman eats it, it becomes invisible for a certain period of time.
 * This invisibility allows Pacman to cross ghosts without being killed.
 */
public class Tomato extends StaticTarget implements Scorable {

    public int duration = 4000;

    public Tomato(Position pos) {
        this.state = StaticTarget.State.AVAILABLE;
        this.setPosition(pos);
    }

    @Override
    public void changeState(StaticTarget.State s) {
        if (s == null) {
            throw new IllegalArgumentException("A null state is not allowed.");
        } else if (state == s) {
            throw new IllegalArgumentException("The new state must differ from the old one.");
        }

        if (s == StaticTarget.State.EATEN) {
            setVisible(false);
        } else if (s == StaticTarget.State.AVAILABLE) {
            setVisible(true);
        }
        this.state = s;
    }

    @Override
    public void gotEaten() {
        this.changeState(StaticTarget.State.EATEN);
        for (Pacman p : Game.getInstance().getPacmanContainer()) {
            if (p.position == this.position) {
                makePacmanInvisible(p);
            }
        }
    }

    /**
     * Make the pacman invinsible, it makes him invulnerable
     */
    public void makePacmanInvisible(final Pacman pacman) {
        pacman.changeState(DynamicTarget.State.INVINSIBLE);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                pacman.changeState(DynamicTarget.State.HUNTED);
            }
        }, duration);
    }

    @Override
    public int getScore() {
        return 0;
    }
}