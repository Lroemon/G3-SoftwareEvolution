package be.umons.model.mapobject;

import be.umons.model.Game;
import be.umons.model.Position;
import be.umons.model.Scorable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * If Pacman eats it, the speed of the ghosts increases for a certain predetermined period of time.
 */
public class Potato extends StaticTarget implements Scorable {

    private final int duration = 5000;

    public Potato(Position pos) {
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
        makeGhostsFaster();
    }

    /**
     * Makes the ghost faster for a period of time
     */
    private void makeGhostsFaster() {
        for (final Ghost g : Game.getInstance().getGhostContainer()) {
            g.changeSpeed(7);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    g.changeSpeed(g.getInitialSpeed());
                }
            }, duration);
        }

    }

    @Override
    public int getScore() {
        return 0;
    }
}