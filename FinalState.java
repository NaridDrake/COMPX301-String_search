public class FinalState extends FSMstate{
    public FinalState(int num){
        state_no = num;
    }

    //gets the next state/s to load if this state passes
    public FSMstate[] getNext(){
        return null;
    }

    public String print() {
        return "" + state_no + ",'',-1,-1";
    }
}
