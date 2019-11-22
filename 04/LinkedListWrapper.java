import java.util.*;

/**
 * Wraps a LinkedList<String> and serves to create an array of linked lists.
 */
public class LinkedListWrapper implements SimpleSet, Iterable<String> {

    private LinkedList<String> linkedList;

    /**
     * Creates a new facade wrapping a linked list.
     * @param linkedList The linked list to wrap.
     */
    public LinkedListWrapper(LinkedList<String> linkedList){
        this.linkedList = linkedList;
    }

    @Override
    public Iterator<String> iterator() {
        return this.linkedList.iterator();
    }

    @Override
    public boolean add(String newValue) {
       return linkedList.add(newValue);
    }

    @Override
    public boolean contains(String searchVal) {
        if(this.linkedList==null) return false;
        return linkedList.contains(searchVal);
    }

    @Override
    public boolean delete(String toDelete) {
        if(this.linkedList==null) return false;
        return linkedList.remove(toDelete);
    }

    @Override
    public int size() {
        if(this.linkedList==null) return 0;
        return linkedList.size();
    }

    @Override
    public String toString() {
        return "Wrapper ["+ this.linkedList.toString()+ "]";
    }
}
