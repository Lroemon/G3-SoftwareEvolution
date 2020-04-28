package be.umons.model.event;

import be.umons.model.Game;
import be.umons.model.Timer;

/**
 * @author Rémy Decocq
 *
 */
public class TimerProcess implements Process {

    private Timer timer;

    @Override
    public long getTiming() {
        return Timer.ms_increment;
    }

    @Override
    public long getStartupDelay() {
        return 0;
    }

    @Override
    public void onLoad() {
        this.timer = Game.getInstance().getTimer();
    }

    @Override
    public void run() {
        this.timer.increment();
    }
}
