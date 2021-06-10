public class StartingState extends FSMstate {

    //constructor method
    public StartingState(int initial){
        state_no = 0;
        next1 = initial;
    }

    // gets the next state/s to load if this state passes
    public int[] getNext(){
        int[] states = {next1};
        return states;
    }
}
