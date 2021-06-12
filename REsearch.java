import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
/*
This code was developed for Assignment 3 in COMPX301-21A
Authors:
    Narid Drake - 1363139
    Alessandra Macdonald - 1506517
A class that takes a FSM and uses it to pattern search a text file
*/
public class REsearch {

    // This is the queue that we can use and the string
    private static Dqueue dqueue;
    // The list of possible states
    private static ArrayList<FSMstate> states;
    // Arrays that hold the next state pointers used to create the states
    private static ArrayList<Integer> n1Holder, n2Holder;
    // Holds the position of the start of our string search
    private static int pointer;

    public static void main(String[] args){

        // Initialise the lists and dqueue
        states = new ArrayList<FSMstate>();
        n1Holder = new ArrayList<Integer>();
        n2Holder = new ArrayList<Integer>();
        dqueue = new Dqueue();

        // Tries to read in the regex and recreate the states to search with
        try{
            Scanner systemReader = new Scanner(System.in);
            String stateLine;
            String[] lineParts = new String[5];
            FSMstate newState;
            int stateNum;
            String type;
            String symbol;
            int next1;
            int next2;

            // Checks there is a new line and reads it in at the same time
            while (systemReader.hasNextLine() && (stateLine = systemReader.nextLine()) != null) {

                // Splits the line and saves the different parts
                lineParts = stateLine.split(",");

                stateNum =  Integer. parseInt(lineParts[1]); 
                type = lineParts[0];
                symbol = lineParts[2];
                next1=  Integer. parseInt(lineParts[3]);
                next2=  Integer. parseInt(lineParts[4]);

                // If the next state is a starting state
                if(type.compareTo("start")==0){
                    newState = new StartingState(null);
                    states.add(newState);
                }
                // If the next state is a matching state
                else if(type.compareTo("match")==0){
                    newState = new MatchingState(stateNum, symbol, null);
                    states.add(newState);
                }
                // If the next state is a bridge state
                else if(type.compareTo("bridge")==0){
                    newState = new BridgeState(stateNum, null);
                    states.add(newState);
                }
                // If the next state is a branching state 
                else if(type.compareTo("branch")==0){
                    newState = new BranchingState(stateNum, null, null);
                    states.add(newState);
                }
                // If the next state is a finishing state 
                else if(type.compareTo("final")==0){
                    newState = new FinalState(stateNum);
                    states.add(newState);
                }else{
                    System.out.println("The state wasn't a start, match, finish, branch, bridge");
                }
                // Arrays to hold the next values of the states
                n1Holder.add(next1);
                n2Holder.add(next2);
            }

            // Checks the some sort of state has been added
            if(states.size()==0){
                System.err.println("1: Unable to search due to REcompile failure");
                System.exit(1);
            }
            systemReader.close();

            // Pass back through the list of states and fill in the blank next states
            for (int i = 0; i < states.size(); i++){
                int n1Index = n1Holder.get(i);
                int n2Index = n2Holder.get(i);

                if (n1Index >= 0)
                    states.get(i).setNext1(states.get(n1Index));
                if (n2Index >= 0)
                    states.get(i).setNext2(states.get(n2Index));
            }

        }
        catch(Exception e) {
            System.err.println("An error occurred in REsearch system in reading.");
            e.printStackTrace();
        }

        try {
            // Trys to create a file using the file name inputted from args
            File file=null; 
            try{
                file = new File(args[0]);
            }catch(Exception e){
                System.err.println("2: Please make sure you have entered a file to search through");
                System.exit(1);
            }

            Scanner myReader = new Scanner(file);

            // The start state of the machine get pushed onto the dqueue
            FSMstate start = states.get(0); 
            dqueue.push(states.get(start.getNext()[0]));

            // The current line that is being checked
            String line;

            // Checks and gets the next line at the same time
            while (myReader.hasNextLine()&&(line = myReader.nextLine())!=null) {

                // Sets the pointer to 0
                pointer = 0;

                // Reset all the states explored values so that they can re-explored
                for(int j=0;j<states.size();j++){
                    states.get(j).wasExplored = false;
                }

                //Emptys the dqueue
                dqueue.empty();
                dqueue.push(states.get(states.get(0).getNext()[0]));

                FSMstate currentState;
                String c;

                // Searching from the current pointer
                searchLine: for(int i=pointer;i<line.length();i++){ 

                    // Get the next character
                    c = Character.toString(line.charAt(i));

                    // Get state from top of dqueue
                    currentState = dqueue.pop();

                    // While the dqueue is not empty keep searching
                    while(currentState!=null){

                        // If the current state hasn't been explored yet, then explore it
                        if(!currentState.wasExplored){

                            // Checks if the pattern has been found and outputs the index
                            if(currentState instanceof FinalState){
                                // Output the line and break from the loop
                                System.out.println(line);
                                break searchLine;
                            }
                            // If current state is a matching state
                            if(currentState instanceof MatchingState){

                                MatchingState m = (MatchingState)currentState;
                                if(c!=null && m.matches(c)){
                                    // Adds the next state to the back of the dqueue
                                    dqueue.add(states.get(currentState.getNext()[0]));
                                }
                            }
                            // If it is a branch state pushs the next possible states to the front
                            else if(currentState instanceof BranchingState){
                                dqueue.push(states.get(currentState.getNext()[0]));
                                dqueue.push(states.get(currentState.getNext()[1]));
                            }
                            // If it is a bridge state push to the front
                            else if(currentState instanceof BridgeState){
                                dqueue.push(states.get(currentState.getNext()[0]));
                            }

                            // Sets the state to explored
                            currentState.wasExplored = true;
                        }
                        
                        // Gets the next state from the dqueue
                        currentState = dqueue.pop();

                        // If the dqueue top is empty set bottom to top and continue with a new character
                        if (currentState==null){
                            // Add on the scan to the dqueue (scan is just a null)
                            dqueue.add(null);

                            // Gets the next state from the dqueue
                            currentState = dqueue.pop();
                            // Checks if the pattern has been found and outputs the index
                            if(currentState instanceof FinalState){
                                // Output the line and break from the loop
                                System.out.println(line);
                                break searchLine;
                            }

                            i++;
                            //Check that there is more to the line
                            if(i<line.length()){
                                // Gets the next character to search for
                                c = Character.toString(line.charAt(i));
                            }else{
                                // If we have reached the end of the string then the character should be null
                                c = null;
                            }

                            // Reset all the states explored values so that they can re-explored
                            for(int j=0;j<states.size();j++){
                                states.get(j).wasExplored = false;
                            }
                        }
                        
                    }
                    
                    // If this fails move the pointer and start again from the pointer
                    pointer++;
                    i=pointer-1; //One will be added when the loop completes
                    
                    //Emptys the dqueue
                    dqueue.empty();
                    dqueue.push(states.get(states.get(0).getNext()[0]));
                    
                }
            }  
            myReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("An error occurred in REsearch file reading.");
            e.printStackTrace();
        }

    }

}
