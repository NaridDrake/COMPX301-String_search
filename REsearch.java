import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;
/*
This code was developed for Assignment 3 in COMPX301-21A
Authors:
    Narid Drake - 1363139
    Alessandra Macdonald - 1506517
*/
public class REsearch {

    // This is the queue that we can use and the string
    private static Dqueue dqueue;
    // The list of possible states
    private static ArrayList<FSMstate> states;
    // Holds the position of the start of our string search
    private static int pointer;

    public static void main(String[] args){

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

            while (systemReader.hasNextLine()) {
                // Gets the next line
                stateLine = systemReader.nextLine();
                // Need to split the line
                lineParts = stateLine.split(",");
                stateNum =  Integer. parseInt(lineParts[0]); 
                type = lineParts[1];
                symbol = lineParts[2];
                next1=  Integer. parseInt(lineParts[3]);
                next2=  Integer. parseInt(lineParts[4]);

                // If the next state is a starting state
                if(type.compareTo("start")==0){
                    newState = new StartingState(stateNum);
                    states.add(newState);
                }
                // If the next state is a matching state
                else if(type.compareTo("match")==0){
                    newState = new MatchingState(stateNum, symbol, next1);
                    states.add(newState);
                }
                // If the next state is a bridge state
                else if(type.compareTo("bridge")==0){
                    newState = new BridgeState(stateNum, next1);
                    states.add(newState);
                }
                // If the next state is a branching state 
                else if(type.compareTo("branch")==0){
                    newState = new BranchingState(stateNum, next1, next2);
                    states.add(newState);
                }
                // If the next state is a finishing state 
                else if(type.compareTo("finish")==0){
                    newState = new FinalState(stateNum);
                    states.add(newState);
                }else{
                    System.out.println("The state wasn't a start, match, finish, branch, bridge");
                }
                // try{
                //     states.add(newState);
                // }catch(Exception e){
                //     System.out.println("An state wasn't a start, match, finish, branch, bridge");
                //     e.printStackTrace();
                // }
            }
            systemReader.close();

        }
        catch(Exception e) {
            System.out.println("An error occurred in REsearch system in reading.");
            e.printStackTrace();
        }

        try {
            // Creates a file using the file name inputted from args
            File file = new File(args[0]);
            Scanner myReader = new Scanner(file);

            // The start state of the machine get pushed onto the dqueue
            FSMstate start = states.get(0); 
            dqueue.push(states.get(start.getNext()[0]));

            // The current line that is being checked
            String line;
            boolean found; /// This can be deleted 

            // Checks and gets the next line at the same time
            while (myReader.hasNextLine()&&(line = myReader.nextLine())!=null) {

                // Sets the pointer to 0
                pointer = 0;
                found = false;

                FSMstate currentState;
                String c;

                // Searching from the current pointer
                for(int i=pointer;i<line.length();i++){ 

                    // If it has been found just exit
                    if(found) break;

                    // Get the next character
                    c = Character.toString(line.charAt(i));

                    // Get state from top of dqueue
                    currentState = dqueue.pop();

                    // While the dqueue is not empty keep searching
                    while(currentState!=null && !found){

                        // Checks if the pattern has been found and outputs the index
                        if(currentState instanceof FinalState){
                            // Output the line and break from the loop
                            System.out.println(line);
                            found = true;
                        }

                        // If current state is a matching state
                        if(currentState instanceof MatchingState){

                            MatchingState m = (MatchingState)currentState;
                            if(m.matches(c)){
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

                        // Gets the next state from the dqueue
                        currentState = dqueue.pop();

                        // If the dqueue top is empty set bottom ot top and continue
                        if (currentState==null){
                            // Add on the scan to the dqueue (scan is just a null)
                            dqueue.add(null);

                            // Gets the next state from the dqueue
                            currentState = dqueue.pop();

                            // Gets the next character to search for
                            i++;
                            c = Character.toString(line.charAt(i));
                        }
                    }
                    
                    // If this fails move the pointer and start again
                    pointer++;
                    // Reset all the boolean values so that they can re-explored (loop that sets wasExplored to false)
                    for(int j=0;j<states.size();j++){
                        states.get(i).wasExplored = false;
                    }
                }

            }  
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred in REsearch file reading.");
            e.printStackTrace();
        }

    }

}
