package be.umons.model;

/**
 * @author Rémy Decocq
 *
 */
public class Timer {

    private static Timer instance = new Timer();

    /**
     * Increment each second
     */
    public final static int ms_increment = 1000;

    private long time_ms = 0;
    private boolean pause_incr = true;

    public Timer(){

    }

    public static Timer getInstance(){
        return instance;
    }

    public static void reset(){
        instance = new Timer();
    }

    public void increment(){
        if (!this.pause_incr)
            this.time_ms += ms_increment;
    }

    public void pause_increment(){
        this.pause_incr = true;
    }

    public void resume_increment(){
        this.pause_incr = false;
    }

    public long getTime_ms(){
        return this.time_ms;
    }

    private String timeValueToStr(long val){
        if (val < 10)
            return "0" + val;
        else
            return String.valueOf(val);
    }

    public String toString(){
        long remainder;
        long min = this.time_ms / 60000;
        remainder = this.time_ms % 60000;
        long sec = remainder / 1000;
        remainder = remainder / 1000;
        long cent = remainder / 10;
        return timeValueToStr(min) + ":" + timeValueToStr(sec) + "." + timeValueToStr(cent);
    }

}
