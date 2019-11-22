import java.util.LinkedList;

public class OpenHashSet extends SimpleHashSet {

    private LinkedListWrapper[] linkedListSet;
    private int size = 0;

    /**
     * A default constructor
     */
    public OpenHashSet() {
        super();
        linkedListSet = new LinkedListWrapper[INITIAL_CAPACITY];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        linkedListSet = new LinkedListWrapper[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75), and
     * lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */
    public OpenHashSet(java.lang.String[] data) {
        super();
        linkedListSet = new LinkedListWrapper[INITIAL_CAPACITY];
        addData(data);
    }

    /**
     * add data one string at a time
     *
     * @param data strings to be added
     */
    private void addData(java.lang.String[] data) {
        for (String string : data) {
            if (string != null)
                add(string);
        }
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(String newValue) {
        if (newValue == null) return true;
        if (contains(newValue)) return false;
        // resize if upper load factor exceeded
        if (upperLoadFactorExceeded()) {
            resizeTable(capacity() * FACTOR);
        }
        // add string
        int index = getHash(newValue);
        if (linkedListSet[index] == null)
            createNewList(index);
        if(linkedListSet[index].add(newValue))
            this.size++;
        return true;
    }

    /**
     * create a new linkedList in given index, when the string is about to be added for the first time to this
     * index in the array.
     * @param index where linkedList should be created
     */
    private void createNewList(int index) {
        LinkedList<String> list = new LinkedList<>();
        linkedListSet[index] = new LinkedListWrapper(list);
    }

    private int getHash(String string) {
        return clamp(string.hashCode());
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(String searchVal) {
        int index = clamp(searchVal.hashCode());
        if (linkedListSet[index] == null) return false;
        return linkedListSet[index].contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(String toDelete) {
        if (toDelete == null) return false;
        if (!contains(toDelete)) return false;
        // delete element
        int index = getHash(toDelete);
        if (linkedListSet[index].delete(toDelete))
            this.size--;
        // if lower load factor exceeded, resize
        if (lowerLoadFactorExceeded())
            resizeTable(capacity() / FACTOR);
        return true;
    }

    /**
     * @return The number of elements currently in the set
     */
    public int size() {
        return this.size;
    }

    /**
     * @return The current capacity (number of cells) of the table.
     */
    public int capacity() {
        return linkedListSet.length;
    }

    /**
     * rehash data to new table
     */
    private void resizeTable(int newCapacity) {
        LinkedListWrapper[] data = linkedListSet; //previous data
        if (newCapacity < 1)
            newCapacity = 1;
        linkedListSet = new LinkedListWrapper[newCapacity];
        this.size = 0;
        for (LinkedListWrapper linkedList : data) {
            if (linkedList != null) {
                for (String string : linkedList) {
                    if (string != null) {
                        add(string);
                    }
                }
            }
        }
    }

    /**
     * @return the load factor after adding a new string
     */
    private double additionLoadFactor() {
        return (double) (size() + 1) / capacity();
    }

    /**
     * @return the load factor
     */
    private double curLoadFactor() {
        return (double) size() / capacity();
    }

    /**
     * @return true if upper load factor was exceeded after add, false otherwise
     */
    private boolean upperLoadFactorExceeded() {
        return additionLoadFactor() > getUpperLoadFactor();
    }

    /**
     * @return true if lower load factor was exceeded after deletion, false otherwise
     */
    private boolean lowerLoadFactorExceeded() {
        return curLoadFactor() < getLowerLoadFactor();
    }
}

