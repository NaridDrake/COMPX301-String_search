/*
This code was developed for Assignment 3 in COMPX301-21A
Authors:
    Narid Drake - 1363139
    Alessandra Macdonald - 1506517
*/
public class BranchingState extends FSMstate {

    //constructor method
    public BranchingState(int num, FSMstate n1, FSMstate n2){
        state_no = num;
        next1 = n1;
        next2 = n2;
    }

    // gets the next state/s to load if this state passes
    public int[] getNext(){
        int[] states = {next1.state_no, next2.state_no};
        return states;
    }

    public String print() {
        return "branch," + state_no + ",''," + next1.state_no + "," + next2.state_no;
    }
}
