public class MatchingState extends FSMstate {
    private String symbol;  //the character or set of characters that need to be matched in the target

    //class constructor
    public MatchingState(int num, String sym, int n1){
        state_no = num;
        next1 = n1;
        symbol = sym;
    }

    //method that returns whether the target symbol in the text matches the symbol
    //for this state
    public boolean matches(String target){
        return symbol.contains(target);
    }

    // gets the next state/s to load if this state passes
    public int[] getNext(){
        if (wasExplored) return new int[0];
        int[] nextState = {next1};
        return nextState;
    }
}
