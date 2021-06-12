/*
This code was developed for Assignment 3 in COMPX301-21A
Authors:
    Narid Drake - 1363139
    Alessandra Macdonald - 1506517
*/
// A FSMstate is an abstract class used to simulate a state in a finite state machine
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
