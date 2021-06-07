public class Dqueue {
    DQnode scan = new DQnode();

    //pointers to the first and last nodes of the dqueue
    DQnode first = scan, last = scan;

    //adds a new state to the 'queue'
    public void enqueue(FSMstate state){
        //queue a new node at the back of the queue
        DQnode newNode = new DQnode(null, first, state);
        //set the new node as the final item in the dqueue
        last.setNext(newNode);
        last = newNode;
    }

    //pushes a new state onto the 'stack'
    public void push(FSMstate state){
        DQnode newNode = new DQnode(first, null, state);
        
        //update pointers
        first.setPrev(newNode);
        first = newNode;
    }

    public FSMstate pop(){
        //first, unload the state held in the 'topmost' node
        FSMstate stateToReturn = first.unload();

        //update pointers to remove the topmost node
        DQnode successor = first.getNext();
        successor.setPrev(null);
        first = successor;

        return stateToReturn;
    }

    
    class DQnode{
        private DQnode next, prev;  //pointers to the next and previous nodes
        private FSMstate payload;

        //constructor methods
        public DQnode(){} //blank constructor, used for creating the 'scan'
        public DQnode(DQnode nxt, DQnode prv, FSMstate state){
            next = nxt;
            prev = prv;
            payload = state;
        }

        //getter and setter methods
        public void setNext(DQnode nxt){ next = nxt; }
        public DQnode getNext(){ return next; }

        public void setPrev(DQnode prv){ prev = prv; }
        public DQnode getPrev(){ return prev; }

        public FSMstate unload(){ return payload; }
        
    }
}
