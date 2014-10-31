package main;

import java.io.*;
import java.util.Random;

interface Const {
    int SIZE = 100;
    int ITERATIONS = 5;
}
class Timer {
    static long startTime = System.currentTimeMillis();
    static long getTime(){
        return System.currentTimeMillis()-startTime;
    }
}
public class Main {
    public static void main(String[] args) {
        new Timer();
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("mess.ascii"));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Holder h = new Holder();

        Writer w = new Writer(h);
        Sorter s = new Sorter(h);
        ReWriter rw = new ReWriter(h);

        new Thread(rw,"name").start();
        new Thread(s,"name").start();
        new Thread(w,"name").start();
    }
}
class Holder {
    char[] chars = new char[Const.SIZE];
    private int state;
    Holder(){
        state = 3;
    }
    public void  setState(int newState) {
        // 1 - filled
        // 2 - sorted
        // 3 - ready
        state = newState;
//        System.out.println("state = " + state);
        this.notifyAll();
    }
    public int getState() {
        return state;
    }
}
class Writer implements Runnable {
    final Holder holder;
    Writer(Holder h){
        holder = h;
    }
    @Override
    public void run() {
        try {
            int i = 0;
            while(i++ < Const.ITERATIONS) {
                synchronized (holder){
                    while(holder.getState() != 3) {
                        System.out.println(Timer.getTime() + "ms : Writer is waiting");
                        holder.wait();
                    }
                    System.out.println(Timer.getTime() + "ms : Writer is GO");
                    BufferedWriter out = new BufferedWriter(new FileWriter("mess.ascii",true));
                    Random rand = new Random();
                    out.write("<" + Timer.getTime() + "ms: Writer start>");

                    int j = Const.SIZE;
                    while(j > 0) {
                        holder.chars[--j] = (char)(rand.nextInt(75) + '0');
                    }
                    out.write(holder.chars);
                    out.write("<" + Timer.getTime() + "ms: Writer end> \n");
                    out.flush();
                    out.close();
                    holder.setState(1);
                    System.out.println(Timer.getTime() + "ms : Writer gives way to Sorter");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class Sorter implements Runnable {
    final Holder holder;
    Sorter(Holder h){
        holder = h;
    }
    @Override
    public  void run() {
        int i = 0;
        while(i++ < Const.ITERATIONS) {
            try {
                synchronized (holder) {
                    while (holder.getState() != 1) {
                        System.out.println(Timer.getTime() + "ms : Sorter is waiting");
                        holder.wait();
                    }
                    System.out.println(Timer.getTime() + "ms : Sorter is GO");
                    BufferedWriter out = new BufferedWriter(new FileWriter("mess.ascii", true));
                    out.write("<" + Timer.getTime() + "ms: Sorter start>");
                    sort();
                    out.write("<" + Timer.getTime() + "ms: Sorter end> \n");
                    out.flush();
                    out.close();
                    holder.setState(2);
                    System.out.println(Timer.getTime() + "ms : Sorter gives way to ReWriter");
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
class ReWriter implements Runnable {
    final Holder holder;
    ReWriter(Holder h){
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
                    System.out.println(Timer.getTime() + "ms : ReWriter is GO");
                    BufferedWriter out = new BufferedWriter(new FileWriter("mess.ascii", true));
                    out.write("<" + Timer.getTime() + "ms: Rewriter start>");
                    out.write(holder.chars);
                    out.write("<" + Timer.getTime() + "ms: Rewriter end>  \n");
                    out.flush();
                    out.close();
                    holder.setState(3);
                    System.out.println(Timer.getTime() + "ms : ReWriter gives way to Writer");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

