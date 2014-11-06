package tasks;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Jack on 11/6/14.
 */
public class WriterTask implements Runnable {
    final Holder holder;
    public WriterTask(Holder h){
        holder = h;
    }
    @Override
    public void run() {
        try {
            int i = 0;
            while(i++ < Const.ITERATIONS) {
                synchronized (holder) {
                    while (holder.getState() != 2) {
                        System.out.println(Timer.getTime() + "ms : Rewriter is waiting");
                        holder.wait();
                    }
                    System.out.println(Timer.getTime() + "ms : WriterTask is GO");
                    BufferedWriter out = new BufferedWriter(new FileWriter("mess.ascii", true));
                    out.write("<" + Timer.getTime() + "ms: Rewriter start>");
                    out.write(holder.chars);
                    out.write("<" + Timer.getTime() + "ms: Rewriter end>  \n");
                    out.flush();
                    out.close();
                    holder.setState(3);
                    System.out.println(Timer.getTime() + "ms : WriterTask gives way to GenerateCharsTask");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
