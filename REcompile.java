/*
This code was developed for Assignment 3 in COMPX301-21A
Authors:
    Narid Drake - 1363139
    Alessandra Macdonald - 1506517
*/

public class REcompile{

    private static String[] types;
    private static String[] ch;
    private static int[] n1;
    private static int[] n2;

    private static String[] xpr;        //String containing the regex to be checked/compiled
    private static int j = 0;           //index variable representing the current character of the regex being examined
    private static int state = 1;       //keeps track of which state is currently being built (highest state in the machine)

    private static void printStates(){
        for (int i = 0; i < ch.length && types[i] != null; i++){
            System.out.println(i + "," + types[i] + "," + "'" + ch[i] + "'," + n1[i] + "," + n2[1]);
        }
    }

    // Takes the type of state that will be made, the state number, the matching symbol, and the two next states
    private static void setState(String type, int i, String c, int nxt1, int nxt2){
        types[i] = type;
        ch[i] = c;
        n1[i] = nxt1;
        n2[i] = nxt2;
    }

    private static boolean isVocab(String ch){   //Checks a certain character to see if it counts as a literal
        String blacklist = "*()[]+|?.";

        if (ch.equals("\\")){   //escapes character
            j++;
            return true;
        }

        return !(blacklist.contains(ch));
    }

    public static void main(String[] args){
        xpr = args[0].split("");
        types = new String[(int)(xpr.length * 2)];
        ch = new String[(int)(xpr.length * 2)];        
        n1 = new int[(int)(xpr.length * 2)];
        n2 = new int[(int)(xpr.length * 2)];

        int r = expression();           //get the intial state of our entire FSM
        setState("start", 0, "", r, r);          //set the start state to the FSM initial state
        setState("final", state, "", -1, -1);    //append a finishing state to the end of the machine

        printStates();
    }

    // Attempts to evaluate the regex (or part of) as an expression
    private static int expression(){
        int r = term();
        // The previous state (Only need to deal with alternation)
        int t2 = -1, t1 = r, f = -1, n=-1;
        // if (j < xpr.length && (isVocab(xpr[j]) || xpr[j].equals("("))){
        //     f = state;
        //     state++;
        //     n = expression();

        //     setState("bridge", f, "", n, n);

        // }
        // If we are looking at alternation symbol (must be an or symbol)
        if (j < xpr.length && xpr[j].equals("|")){
            // Holds the finishing state of t1
            int finishT1 = state-1;
            
            j++;
            // Call second expression
            t2 = expression();
            // Holds the finishing state of 21
            int finishT2 = state-1;

            // Create a new branching state that points to t1 and t2
            setState("branch", r, "", t1, t2);
            state++;

            // Creates a new finishing state
            setState("finish", state-1, "", 0, 0);
            state++;

            // Sets the finishing states of t1 and t2 to the new finishing state
            n1[finishT1] = state-1;
            n2[finishT1] = state-1;
            n1[finishT2] = state-1;
            n2[finishT2] = state-1;

            // // If the preceeding state is a 2-state machine,
            // // then set both its second output to this current state
            // if (n1[f] == n2[f])
            //     n2[f] = state;
            // // If the preceeding state is a branching machine,
            // // set its first output to this state to this state;
            // n1[f] = state;

            // f = state-1;
            // j++;
            // r = state;
            // state++;

            // // Resolve the second term in the alternation
            // t2 = expression();
            // if (n1[f] == n2[f])
            //     n2[f] = state;
            // n1[f] = state;
        }
        // If it is not the end of the expression then an error should occur
        else if(j < xpr.length){
            System.out.println("There was more in the regex that couldn't be evaluated");
            System.exit(1);
        }


        return r;
    }

    // Attempts to evaluate a term within the regex
    private static int term(){
        // r is the initial state of the term, t1 is term 1, previous state
        int r = -1, t1 = -1, f = -1;

        f = state-1;
        try{
            r = factor();
            t1 = r;
        }catch(Exception e){
            System.err.println(e);
            System.exit(1);
        }

        // Resolution for a closure symbol
        if (j < xpr.length && xpr[j].equals("*")){
            setState("branch", state, "", state+1, t1);
            j++;
            r = state;
            state++;
        }
        //resolution for "one or none" symbol
        if (j < xpr.length && xpr[j].equals("?")){
            //create a branching state that connects to the term and a next state past the term
            setState("branch", state, "", r, state+1);

            //go through the term and update all references to this branching state 
            //to point to the state after it
            explored = new int[state];
            updateForward(r, state);
        }
        return r;
    }

    static int[] explored;
    private static void updateForward(int thisState, int targetIndex){
        //first check if this state has already been explored
        if (explored[thisState] != 0) return;
        else explored[thisState] = 1;
        //check both "next" pointers for this state
        if (n1[thisState] == targetIndex){
            n1[thisState] = targetIndex + 1;
        }else{
            updateForward(n1[thisState], targetIndex);
        }
        if (n2[thisState] == targetIndex + 1){
            n2[thisState] = targetIndex + 1;
        }else{
            updateForward(n2[thisState], targetIndex);
        }
    }

    //attempts to evaluate a factor within the regex
    private static int factor() throws Exception{
        int r;

        // Check that we're still in the array
        if (isVocab(xpr[j])) {
            setState("match", state, xpr[j], state+1, state+1);
            j++;
            r = state;
            state++;

        }else if (xpr[j].equals(".")) {
            // If the character is a wildcard, so create a wildcard state
            setState("match", state, "..", state+1, state+1);
            j++;
            r = state;
            state++;
        } else {
            if (j < xpr.length && xpr[j].equals("(")) {
                j++;
                r = expression();
                if (j < xpr.length && xpr[j].equals(")")) {
                    j++;
                } else {
                    r=-1;
                    throw new Exception("1: Could not compile the regex.");
                }
            } else {
                r=-1;
                throw new Exception("2: Could not compile the regex.");
            }
        }
        return r;
    }
    
}