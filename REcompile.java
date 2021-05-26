/*
This code was developed for Assignment 3 in COMPX301-21A
Authors:
    Narid Drake - 1363139
    Alessandra Macdonald - 1506517
*/
public class REcompile{

    private static char[] xpr;          //String containing the regex to be checked/compiled
    private static int j = 0;           //index variable representing the current character of the regex being examined
    private static boolean isVocab(char ch){   //Checks a certain character to see if it counts as a literal
        String blacklist = "*()[]+|?";
        return !(blacklist.contains(String.valueOf(ch)));
    }

    public static void main(String[] args){
        xpr = args[0].toCharArray();
        System.out.println(expression());
    }

    //attempts to evaluate the regex (or part of) as an expression
    private static boolean expression(){
        // System.err.println("j is pointing at: " + j);
        if(j >= xpr.length) return true;
        if (!term()) return false;
        if(j >= xpr.length) return true;
        if (xpr[j] != '(' && !isVocab(xpr[j])) return false;
        // System.err.println("Checkpoint 1");
        return expression();
    }

    //attempts to evaluate a term within the regex
    private static boolean term(){
        // System.err.println("Checkpoint 2");
        if(!factor()) return false;
        // System.err.println("Checkpoint 3");
        if (j >= xpr.length) return true;
        if (xpr[j] == '*'){
            j++;
            return true;
        }
        if (xpr[j] == '|'){
            j++;
            return term();
        }
        return true;
    }

    //attempts to evaluate a factor within the regex
    private static boolean factor(){
        //first off, check that we're still in the array
        if (j >= xpr.length) return false;
        if (isVocab(xpr[j])){
            j++;
            return true;
        }else{
            if (xpr[j] == '('){
                j++;
                expression();
                if (j < xpr.length && xpr[j] == ')'){
                    j++;
                    return true;
                }
            }
        }
        return false;
    }
    
}