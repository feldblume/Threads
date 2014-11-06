package tasks;

import tasks.Timer;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Jack on 11/6/14.
 */
public class SortingTask implements Runnable {
    final Holder holder;
    public SortingTask(Holder h){
        holder = h;
    }
    @Override
    public  void run() {
        int i = 0;
        while(i++ < Const.ITERATIONS) {
            try {
                synchronized (holder) {
                    while (holder.getState() != 1) {
                        System.out.println(Timer.getTime() + "ms : SortingTask is waiting");
                        holder.wait();
                    }
                    System.out.println(Timer.getTime() + "ms : SortingTask is GO");
                    BufferedWriter out = new BufferedWriter(new FileWriter("mess.ascii", true));
                    out.write("<" + Timer.getTime() + "ms: SortingTask start>");
                    sort();
                    out.write("<" + Timer.getTime() + "ms: SortingTask end> \n");
                    out.flush();
                    out.close();
                    holder.setState(2);
                    System.out.println(Timer.getTime() + "ms : SortingTask gives way to WriterTask");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void sort(){
        for(int i = 0; i<Const.SIZE-1; i++)
            for(int j = i; j<Const.SIZE; j++)
                if((int)holder.chars[i]+100000*getType(holder.chars[i]) > (int)holder.chars[j]+100000*getType(holder.chars[j])){
                    char temp = holder.chars[i];
                    holder.chars[i] = holder.chars[j];
                    holder.chars[j] = temp;
                }
    }
    private int getType(char c) {
        if(c>'0' && c<'9') return 1;
        if((c>'A' && c<'Z') || (c>'a' && c<'z')) return 2;
        return 3;
    }
}
