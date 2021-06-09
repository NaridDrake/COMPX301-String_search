public class BridgeState extends FSMstate {

    //constructor method
    public BridgeState(int num, int n1){
        state_no = num;
        next1 = n1;
    }
    
    // gets the next state/s to load if this state passes
    public int[] getNext(){
        int[] states = {next1};
        return states;
    }
}
