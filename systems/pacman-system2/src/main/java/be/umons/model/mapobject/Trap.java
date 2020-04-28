package be.umons.model.mapobject;

import be.umons.model.Position;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A Trap makes the {@link Target} stuck for a period of time, just on person can be stuck at a time.
 *
 */
public class Trap extends Boxes {

    public int trapTime = 3000;
    private int someoneOn = 0;

    public Trap(Position position) {
        this.setPosition(position);
    }

    public String toString() {
        return "Trap [" + position + "]";
    }

    @Override
    public void action(final Pacman pacman) {
        // Pacman on have 3 values (2,1,0) because performCollisions is done before leaving a case
        // So a True/False would not work properly if the timer was doing pacmanOn = False
        if (someoneOn == 0) {
            someoneOn = 2;
            pacman.changeSpeed(0);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    pacman.changeSpeed(pacman.getInitialSpeed());
                    someoneOn -= 1;
                }
            }, trapTime);
        } else if (someoneOn == 1) {
            someoneOn -= 1;
        }
    }

    @Override
    public void action(final Ghost ghost) {
        if (someoneOn == 0) {
            someoneOn = 2;
            ghost.changeSpeed(0);
            ghost.lockSpeed();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    ghost.unlockSpeed();
                    ghost.changeSpeed(ghost.getInitialSpeed());
                    someoneOn -= 1;
                }
            }, trapTime);
        } else if (someoneOn == 1) {
            someoneOn = 0;
        }
    }
}
