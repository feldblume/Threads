package main;

import tasks.*;

import java.io.*;

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

        GenerateCharsTask w = new GenerateCharsTask(h);
        SortingTask s = new SortingTask(h);
        WriterTask rw = new WriterTask(h);

        new Thread(rw,"Writer task").start();
        new Thread(s,"Sorter task").start();
        new Thread(w,"Generator task").start();
    }
}

