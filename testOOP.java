import java.util.ArrayList;

public class testOOP {
    static ArrayList<FSMstate> collection;
    static int state = 0;
    static int iExpression = 0;
    static String[] expression;

    public static void main(String[] args){
        //start by converting the expression into a string array
        expression = args[0].split("");

        collection = new ArrayList<FSMstate>();
        StartingState initial = new StartingState(null);
        collection.add(initial);
        state++;

        FSM machine = expression();
        initial.setNext1(machine.sInitial);

        FinalState end = new FinalState(state);
        collection.add(end);
        machine.setFinal(end);

        for (FSMstate state : collection) {
            System.out.println(state.print());
        }

    }

    public static FSM expression(){
        FSM FirstTerm = term();
        return FirstTerm;
    }

    public static FSM term(){
        FSM firstFactor = factor();

        //check if we need to concatenate with another term
        if (iExpression < expression.length){
            firstFactor.cat(term());
        }
        return firstFactor;
    }

    public static FSM factor(){
        FSM factor = new FSM();
        if (isVocab(expression[iExpression])){
            MatchingState match = new MatchingState(state, expression[iExpression], null);
            collection.add(match);
            factor.add(match);

            //consume the symbol and increment state
            iExpression++;
            state++;
        }
        return factor;
    }

    private static boolean isVocab(String ch) { // Checks a certain character to see if it counts as a literal
        String blacklist = "*()[]+|?.";

        if (ch.equals("\\")) { // escapes character
            iExpression++;
            return true;
        }

        return !(blacklist.contains(ch));
    }

    static class FSM{
        FSMstate sInitial, sFinal;
        FSM nextMachine;

        ArrayList<FSMstate> children = new ArrayList<FSMstate>();

        public FSM(){

        }

        public void add(FSMstate newState){
            if (sInitial == null){
                sInitial = newState;
                newState.setNext1(sFinal);
            }
            children.add(newState);
        }

        //concatenates this machine with another
        public void cat(FSM next){
            //set this machine's final state to be the next machine's initial state
            setFinal(next.sInitial);
            nextMachine = next;

            System.err.println(sInitial.next1.state_no);
        }

        public void setFinal(FSMstate newFinal){
            sFinal = newFinal;
            for (FSMstate state : children) {
                if (state.next1 == null) state.setNext1(newFinal); 
                if (state.next2 == null) state.setNext2(newFinal);

            }

            if (nextMachine != null) nextMachine.setFinal(newFinal);
        }

        public FSMstate getFinal(){
            //check if this machine's final state doesn't link to another machine
            if (sFinal == null) return sFinal;
            //get the next machine's final state if there is a next machine
            return nextMachine.getFinal();
        }
    }
    
}
