package be.umons.model;

import be.umons.model.event.Scheduler;
import be.umons.model.event.TimerProcess;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * @author Rémy Decocq
 *
 */
public class TimerTest {

    private Timer timer;

    @Before
    public void setUp(){
        Game.reset();
    }

    @Test
    public void testTimerAlone(){
        timer = new Timer();
        timer.resume_increment();
        for(int i=1; i <= 60;i++){
            timer.increment();
            assertEquals(1000*i, timer.getTime_ms());
        }
        assertEquals("01:00.00", timer.toString());
    }

    @Test
    public void testTimerProcessPaused() throws InterruptedException {
        Scheduler scheduler = new Scheduler();
        scheduler.register(new TimerProcess());
        scheduler.startExecution();
        TimeUnit.SECONDS.sleep(1);
        timer = Game.getInstance().getTimer();
        assertNotNull(timer);
        assertEquals("The timer should be paused by default", 0, timer.getTime_ms());
    }

    @Test
    public void testTimerProcessLaunched() throws InterruptedException {
        Scheduler scheduler = new Scheduler();
        scheduler.register(new TimerProcess());
        timer = Game.getInstance().getTimer();
        timer.resume_increment();
        scheduler.startExecution();
        TimeUnit.MILLISECONDS.sleep(1500);
        assertNotNull(timer);
        assertTrue("The timer should have been incremented (1x/sec)", timer.getTime_ms() > 0);
    }


}
