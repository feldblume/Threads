package tasks;

import tasks.Timer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static tasks.Const.*;

/**
 * Created by Jack on 11/6/14.
 */
public class GenerateCharsTask implements Runnable {
    final Holder holder;

    /**
     * This is the constructor which creates GenerateCharsTask based on holder object
     * @param h holder object
     */
    public GenerateCharsTask(Holder h){
        holder = h;
    }


    @Override
    /**
     * Runs a task
     */
    public void run() {
        try {
            int i = 0;
            while(i++ < ITERATIONS) {
                synchronized (holder){
                    while(holder.getState() != READY) {
                        System.out.println(Timer.getTime() + "ms : GenerateCharsTask is waiting");
                        holder.wait();
                    }
                    System.out.println(Timer.getTime() + "ms : GenerateCharsTask is GO");
                    BufferedWriter out = new BufferedWriter(new FileWriter("mess.ascii",true));
                    Random rand = new Random();
                    out.write("<" + Timer.getTime() + "ms: GenerateCharsTask start>");

                    int j = Const.SIZE;
                    while(j > 0) {
                        holder.chars[--j] = (char)(rand.nextInt(75) + '0');
                    }
                    out.write(holder.chars);
                    out.write("<" + Timer.getTime() + "ms: GenerateCharsTask end> \n");
                    out.flush();
                    out.close();
                    holder.setState(FILLED);
                    System.out.println(Timer.getTime() + "ms : GenerateCharsTask gives way to SortingTask");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
