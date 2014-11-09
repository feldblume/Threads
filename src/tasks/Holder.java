package tasks;

/**
 * Created by Jack on 11/6/14.
 */
/**
 * A class that contains shared data for task classes
 */
public class Holder {
    char[] chars = new char[Const.SIZE];
    private int state;

    /**
     * A constructor. Sets holder state to 'Ready'
     */
    public Holder(){
        state = Const.READY;
    }

    /**
     * Sets holder state
     * @param newState - new holder state
     */
    public void  setState(int newState) {
        // 1 - filled
        // 2 - sorted
        // 3 - ready
        state = newState;
//        System.out.println("state = " + state);
        this.notifyAll();
    }

    /**
     * Returns current Holder state
     * @return current state
     */
    public int getState() {
        return state;
    }
}
