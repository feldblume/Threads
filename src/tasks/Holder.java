package tasks;

import tasks.Const;

/**
 * Created by Jack on 11/6/14.
 */
public class Holder {
    char[] chars = new char[Const.SIZE];
    private int state;
    public Holder(){
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
