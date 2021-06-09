public class FinalState extends FSMstate{
    public FinalState(int num){
        state_no = num;
        next1 = -1;
    }

    //gets the next state/s to load if this state passes
    public int[] getNext(){
        return new int[0];
    }
}
