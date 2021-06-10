public class Dqueue {
    DQnode scan = new DQnode();

    // Pointers to the first and last nodes of the dqueue
    DQnode first = scan, last = scan;

    // Adds a new state to the 'queue' (the back of the dqueue) add
    public void add(FSMstate state){
        //queue a new node at the back of the queue
        DQnode newNode = new DQnode(null, first, state);
        //set the new node as the final item in the dqueue
        last.setNext(newNode);
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

        // Update pointers to remove the topmost node
        DQnode successor = first.getNext();
        successor.setPrev(null);
        first = successor;

        return stateToReturn;
    }

    
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
