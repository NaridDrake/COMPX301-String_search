public abstract class FSMstate {
    protected int state_no;
    protected FSMstate next1, next2;
    public boolean wasExplored = false;

    //gets the next state/s to load if this state passes
    public abstract int[] getNext();

    public void setNext1(FSMstate n1){
        next1 = n1;
    }
    public void setNext2(FSMstate n2){
        next2 = n2;
    }

    public abstract String print();
}
