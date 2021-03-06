public class StartingState extends FSMstate {

    //constructor method
    public StartingState(FSMstate initial){
        state_no = 0;
        next1 = initial;
    }

    // gets the next state/s to load if this state passes
    public int[] getNext(){
        int[] states = {next1.state_no};
        return states;
    }

    public String print() {
        return "start," + state_no + ",''," + next1.state_no + "," + next1.state_no;
    }
}
