package main;

import java.io.*;
import java.util.Random;


public class Main {
    public static void main(String[] args) {
        Writer w = new Writer();
        w.run();
        Sorter s = new Sorter();
        s.run();
        Rewriter rw = new Rewriter(s);
        rw.run();
    }
}
class Writer implements Runnable {
    @Override
    public void run() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("mess.ascii"));
            int size = 1000;
            char[] chars = new char[size];
            Random rand = new Random();
            out.write("_Writer start_");
            while(size > 0) {
                chars[--size] = (char)(rand.nextInt(75) + '0');
            }
            out.write(chars);
            out.flush();
            out.close();
            System.out.println("written!");
            notify();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class Sorter implements Runnable {
    int size = 1000;
    char[] chars = new char[size];
    @Override
    public void run() {
        try {
            this.wait();
            BufferedReader in = new BufferedReader(new FileReader("mess.ascii"));
            in.read(chars);
            in.close();
            this.sort();
            System.out.println(chars); // check
            this.notifyAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void sort(){
        for(int i = 0; i<size-1; i++)
            for(int j = i; j<size; j++)
                if((int)chars[i] > (int)chars[j]){
                    char temp = chars[i];
                    chars[i] = chars[j];
                    chars[j] = temp;
                }
    }
}
class Rewriter implements Runnable {
    int size = 1000;
    Sorter sorter;
    char[] chars = new char[size];
    Rewriter(Sorter s) {
        sorter = s;
        this.chars = s.chars.clone();
    }
    @Override
    public void run() {
        try {
            this.wait();
            BufferedWriter out = new BufferedWriter(new FileWriter("mess.ascii"));
            out.write(this.chars);
            out.flush();
            out.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

