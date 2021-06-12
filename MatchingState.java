/*
This code was developed for Assignment 3 in COMPX301-21A
Authors:
    Narid Drake - 1363139
    Alessandra Macdonald - 1506517
*/
public class MatchingState extends FSMstate {
    private String symbol;  //the character or set of characters that need to be matched in the target

    //class constructor
    public MatchingState(int num, String sym, FSMstate n1){
        state_no = num;
        next1 = n1;
        symbol = sym;
    }

    //method that returns whether the target symbol in the text matches the symbol
    //for this state
    public boolean matches(String target){
        if(symbol.compareTo("..")==0) return true;
        return symbol.contains(target);
    }

    // gets the next state/s to load if this state passes
    public int[] getNext(){
        if (wasExplored) return new int[0];
        int[] nextState = {next1.state_no};
        return nextState;
    }

    public String print() {
        return "match," + state_no + "," + symbol + "," + next1.state_no + "," + next1.state_no;
    }
}
