package test;

/**
 * Created by Jack on 30.10.2014.
 */
public class Main2 {
    public static void main(String[] args) {
        Semaphore s = new Semaphore();
        MainRoad mr = new MainRoad(s);
        SecondaryRoad sr = new SecondaryRoad(s);

        while(true){
            mr.run();
            mr.run();
            sr.run();
        }

    }
}

class Semaphore {
    boolean greenLight = false;
    public void setGreenLight(boolean v){
        greenLight = v;
        notifyAll();
    }
}


class MainRoad implements Runnable {
    final Semaphore sem;
    MainRoad(Semaphore s){
        sem = s;
    }
    @Override
    public void run() {
        synchronized (sem) {
            do {
                try {
                    sem.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (sem.greenLight != true);
            System.out.println("Secondary Go!");
            sem.setGreenLight(true);
        }
    }
}

class SecondaryRoad implements Runnable {
    final Semaphore sem;
    SecondaryRoad(Semaphore s){
        sem = s;
    }
    @Override
    public void run() {
        synchronized (sem) {
            do {
                try {
                    sem.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (sem.greenLight != false);
            System.out.println("Main Go!");
            sem.setGreenLight(false);
        }
    }
}