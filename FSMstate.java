public abstract class FSMstate {
    protected int state_no, next1;
    public boolean wasExplored = false;

    //gets the next state/s to load if this state passes
    public abstract int[] getNext();
}
