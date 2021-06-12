public class FinalState extends FSMstate{
    public FinalState(int num){
        state_no = num;
    }

    //gets the next state/s to load if this state passes
    public int[] getNext(){
        return new int[0];
    }

    public String print() {
        return "final," + state_no + ",'',-1,-1";
    }
}
