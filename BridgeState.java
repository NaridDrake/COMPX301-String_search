public class BridgeState extends FSMstate {

    //constructor method
    public BridgeState(int num, FSMstate n1){
        state_no = num;
        next1 = n1;
    }
    
    // gets the next state/s to load if this state passes
    public FSMstate[] getNext(){
        FSMstate[] states = {next1};
        return states;
    }

    public String print(){
        return "" + state_no + ",''," + next1.state_no + "," + next1.state_no;
    }
}
