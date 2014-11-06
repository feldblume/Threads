package tasks;

/**
 * Created by Jack on 11/6/14.
 */
public class Timer {
    static long startTime = System.currentTimeMillis();
    static long getTime(){
        return System.currentTimeMillis()-startTime;
    }
}
