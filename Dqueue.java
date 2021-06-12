/*
This code was developed for Assignment 3 in COMPX301-21A
Authors:
    Narid Drake - 1363139
    Alessandra Macdonald - 1506517
*/
public class Dqueue {
    DQnode scan = new DQnode();

    // Pointers to the first and last nodes of the dqueue
    DQnode first = scan, last = scan;

    // Adds a new state to the 'queue' (the back of the dqueue) add
    public void add(FSMstate state){
        // Add a new node at the back of the queue
        DQnode newNode = new DQnode(null, first, state);

        // Needs to check if there is anything in the dqueue first
        if(last!=null){
            //set the new node as the final item in the dqueue
            last.setNext(newNode);
        }

        last = newNode;
    }

    // Pushes a new state onto the 'stack' (the frount of the dqueue)
    public void push(FSMstate state){
        
        DQnode newNode;

        if(state == null){
            newNode = new DQnode();
        }else{
            newNode = new DQnode(first, null, state);
        }
        
        // Update pointers
        first.setPrev(newNode);
        first = newNode;
        
    }

    // Pops the top state off the dqueue and returns it
    public FSMstate pop(){
        // First, unload the state held in the 'topmost' node
        FSMstate stateToReturn = first.unload();

        // Update pointers to remove the topmost node ONLY if it isn't null
        if( first.getNext() != null){
            DQnode successor = first.getNext();
            successor.setPrev(null);
            first = successor;
        }

        return stateToReturn;
    }


    // A method that empties the dqueue
    public void empty(){
        scan = new DQnode();
        first = scan; 
        last = scan;
    }
    
    // A node class that holds two pointers and a value (payload)
    class DQnode{
        private DQnode next, prev;  // Pointers to the next and previous nodes
        private FSMstate payload;

        // Constructor methods
        public DQnode(){} // Blank constructor, used for creating the 'scan'
        public DQnode(DQnode nxt, DQnode prv, FSMstate state){
            next = nxt;
            prev = prv;
            payload = state;
        }

        // Getter and setter methods
        public void setNext(DQnode nxt){ next = nxt; }
        public DQnode getNext(){ return next; }

        public void setPrev(DQnode prv){ prev = prv; }
        public DQnode getPrev(){ return prev; }

        public FSMstate unload(){ return payload; }

    
        
    }
}
