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

<<<<<<< HEAD
        new Thread(rw,"Writer task").start();
        new Thread(s,"Sorter task").start();
        new Thread(w,"Generator task").start();
=======
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
                    System.out.println("sic!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
>>>>>>> 38257a588c6da6da3295585f3cb6a10552dec697
    }
}

