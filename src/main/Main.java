package main;

import com.sun.glass.ui.Size;

import java.io.*;
import java.util.Random;

interface Const {
    int SIZE = 20;
}

public class Main {
    public static void main(String[] args) {
        Writer w = new Writer();
        Sorter s = new Sorter();
        s.run();
        w.run();
        Rewriter rw = new Rewriter(s);
        rw.run();
    }
}

class Holder {
    char[] chars = new char[Const.SIZE];
}

class Writer implements Runnable {
    Holder holder;
    Writer(Holder h){
        holder = h;
    }
    @Override
    public void run() {
        try {
            int i = 20;
            while(i-- >0){
                BufferedWriter out = new BufferedWriter(new FileWriter("mess.ascii"));
                Random rand = new Random();
                out.write("<Writer start>");
                int i = Const.SIZE;
                while(i > 0) {
                    holder.chars[--i] = (char)(rand.nextInt(75) + '0');
                }
                out.write(holder.chars);
                out.write("<Writer end>");
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class Sorter implements Runnable {
    Holder holder;
    Sorter(Holder h){
        holder = h;
    }
    @Override
    public  void run() {
        int i = 0;
        while(i++ <20) {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("mess.ascii", true));
                out.write("<Sorter start>");
                sort();
                out.write("<Sorter end>");
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
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

class Rewriter implements Runnable {
    Holder holder;
    Rewriter(Holder h){
        holder = h;
    }
    Rewriter(Sorter s) {
        sorter = s;
        this.chars = s.chars.clone();
    }
    @Override
    public synchronized void run() {
        try {
//            this.wait();
            BufferedWriter out = new BufferedWriter(new FileWriter("mess.ascii",true));
            out.write("_Rewriter start_");
            out.write(this.chars);
            out.write("_Rewriter end_");
            out.flush();
            out.close();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

