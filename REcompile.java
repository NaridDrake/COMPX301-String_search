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

    private static String[] xpr;          //String containing the regex to be checked/compiled
    private static int j = 0;           //index variable representing the current character of the regex being examined
    private static int state = 1;       //keeps track of which state is currently being built

    private static void printStates(){
        for (int i = 0; i < ch.length && types[i] != null; i++){
            System.out.println(i + "," + types[i] + "," + "'" + ch[i] + "'," + n1[i] + "," + n2[1]);
        }
    }

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

    //attempts to evaluate the regex (or part of) as an expression
    private static int expression(){
        int r = term();
        if (j < xpr.length && (isVocab(xpr[j]) || xpr[j].equals("("))){
            int f = state;
            state++;
            int n = expression();

            setState("bridge", f, "", n, n);

            // for (int i = 0; i < ch.length; i++){
            //     if (n1[i] == f) n1[i] = n;
            //     if (n2[i] == f) n2[i] = n;
            // }
        }
        return r;
    }

    //attempts to evaluate a term within the regex
    private static int term(){
        int r = -1, t1 = -1, t2 = -1, f = -1;

        f = state-1;
        try{
            r = factor();
            t1 = r;
        }catch(Exception e){
            System.err.println(e);
        }

        //resolution for a closure symbol
        if (j < xpr.length && xpr[j].equals("*")){
            setState("branch", state, "", state+1, t1);
            j++;
            r = state;
            state++;
        }

        //resolution for an alternation symbol
        if (j < xpr.length && xpr[j].equals("|")){
            //if the preceeding state is a 2-state machine,
            //then set both its second output to this current state
            if (n1[f] == n2[f])
                n2[f] = state;
            //if the preceeding state is a branching machine,
            //set its first output to this state to this state;
            n1[f] = state;

            f = state-1;
            j++;
            r = state;
            state++;

            //resolve the second term in the alternation
            t2 = expression();
            setState("branch", r, "", t1, t2);
            if (n1[f] == n2[f])
                n2[f] = state;
            n1[f] = state;
        }
        return r;
    }

    //attempts to evaluate a factor within the regex
    private static int factor() throws Exception{
        int r;

        //first off, check that we're still in the array
        if (isVocab(xpr[j])) {
            setState("match", state, xpr[j], state+1, state+1);
            j++;
            r = state;
            state++;

        }else if (xpr[j].equals(".")) {
            //character is a wildcard, so create a wildcard state
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
                    throw new Exception("1: Could not compile the regex.");
                }
            } else {
                throw new Exception("2: Could not compile the regex.");
            }
        }
        return r;
    }
    
}