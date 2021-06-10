public class BranchingState extends FSMstate {
    int next2;

    //constructor method
    public BranchingState(int num, int n1, int n2){
        state_no = num;
        next1 = n1;
        next2 = n2;
    }

    // gets the next state/s to load if this state passes
    public int[] getNext(){
        int[] states = {next1, next2};
        return states;
    }
}
