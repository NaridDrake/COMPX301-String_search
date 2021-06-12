public class BridgeState extends FSMstate {

    //constructor method
    public BridgeState(int num, FSMstate n1){
        state_no = num;
        next1 = n1;
    }
    
    // gets the next state/s to load if this state passes
    public int[] getNext(){
        int[] states = {next1.state_no};
        return states;
    }

    public String print(){
        return "bridge," + state_no + ",''," + next1.state_no + "," + next1.state_no;
    }
}
