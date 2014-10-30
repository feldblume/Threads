package main;

import java.io.*;
import java.util.Random;

interface Const {
    int SIZE = 20;
    int ITERATIONS = 20;
}

public class Main {
    public static void main(String[] args) {
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
        s.run();
        w.run();
        rw.run();

    }
}

class Holder {
    char[] chars = new char[Const.SIZE];
    private int state;
    Holder(){
        state = 3;
    }
    public synchronized void  setState(int newState) {
        // 1 - filled
        // 2 - sorted
        // 3 - ready
        state = newState;
        System.out.println("state = " + state);
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
            synchronized (holder){
                while(holder.getState() != 3)
                    holder.wait();

                BufferedWriter out = new BufferedWriter(new FileWriter("mess.ascii",true));
                Random rand = new Random();
                out.write("<Writer start>");
                int j = Const.SIZE;
                while(j > 0) {
                    holder.chars[--j] = (char)(rand.nextInt(75) + '0');
                }
                out.write(holder.chars);
                out.write("<Writer end>");
                out.flush();
                out.close();
                holder.setState(1);
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
                    while (holder.getState() != 1)
                        holder.wait();
                    BufferedWriter out = new BufferedWriter(new FileWriter("mess.ascii", true));
                    out.write("<Sorter start>");
                    sort();
                    out.write("<Sorter end>");
                    out.flush();
                    out.close();
                    holder.setState(2);
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
                if((int)holder.chars[i] > (int)holder.chars[j]){
                    char temp = holder.chars[i];
                    holder.chars[i] = holder.chars[j];
                    holder.chars[j] = temp;
                }
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
                    while (holder.getState() != 2)
                        holder.wait();
                    BufferedWriter out = new BufferedWriter(new FileWriter("mess.ascii", true));
                    out.write("<Rewriter start>");
                    out.write(holder.chars);
                    out.write("<Rewriter end>");
                    out.flush();
                    out.close();
                    holder.setState(3);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

