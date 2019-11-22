public class ClosedHashSet extends SimpleHashSet {

    private String[] stringSet;
    private static String DELETED = "flag"; //flag for deleted strings
    private int size = 0;

    /**
     * A default constructor
     */
    public ClosedHashSet() {
        super();
        stringSet = new String[INITIAL_CAPACITY];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        stringSet = new String[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored. The new table has the default values of
     * initial capacity (16), upper load factor (0.75), and lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */
    public ClosedHashSet(java.lang.String[] data) {
        super();
        stringSet = new String[INITIAL_CAPACITY];
        addData(data);
    }

    /**
     * add data one string at a time
     *
     * @param data strings to be added
     */
    private void addData(java.lang.String[] data) {
        for (String string : data) {
            if (string != null && string != DELETED)
                add(string);
        }
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(java.lang.String newValue) {
        if (newValue == null) return true;
        if (contains(newValue)) return false;
        // resize if upper load factor exceeded
        if (upperLoadFactorExceeded()) {
            resizeTable(capacity() * FACTOR);
        }
        // add string
        int index;
        for (int i = 0; i < capacity(); i++) {
            index = clamp(newValue.hashCode() + (i + (i * i)) / 2);
            //if cell is empty or was deleted (using == to compare references), successful add
            if (stringSet[index] == null || stringSet[index] == DELETED) {
                stringSet[index] = newValue;
                this.size++;
                return true;
            }
        }
        return false;
    }

    /**
     * rehash data to new table
     */
    private void resizeTable(int newCapacity) {
        String[] data = stringSet; //previous data
        if (newCapacity < 1)
            newCapacity = 1;
        stringSet = new String[newCapacity];
        this.size = 0;
        addData(data);
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(java.lang.String searchVal) {
        if (searchVal == null) return false;
        int index;
        for (int i = 0; i < capacity(); i++) {
            index = clamp(searchVal.hashCode() + (i + (i * i)) / 2);
            if (stringSet[index] == null) return false;
            if (stringSet[index].equals(searchVal)) return true;
        }
        return false;
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(java.lang.String toDelete) {
        if (toDelete == null) return false;
//        if (!contains(toDelete)) return false;
        // delete element
        int index;
        for (int i = 0; i < capacity(); i++) {
            index = clamp(toDelete.hashCode() + (i + (i * i)) / 2);
            if (stringSet[index] == null) return false;
            if (stringSet[index].equals(toDelete)) { //successful deletion
                stringSet[index] = DELETED; //set deleted flag instead of deleted
                this.size--;
                if (lowerLoadFactorExceeded()) {  // if lower load factor exceeded, resize
                    resizeTable(capacity() / FACTOR);
                }
                return true;
            }
        }
        return false;
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
        return stringSet.length;
    }

    /**
     * @return the load factor after adding a new string
     */
    private double loadFactorAfterAddition() {
        return (double)(1+size()) / capacity();
    }

    /**
     * @return the load factor
     */
    private double curLoadFactor() {
        return (double) (size()) / capacity();
    }

    /**
     * @return true if lower load factor was exceeded after deletion, false otherwise
     */
    private boolean lowerLoadFactorExceeded() {
        return curLoadFactor() < getLowerLoadFactor();
    }

    /**
     * @return true if upper load factor was exceeded, false otherwise
     */
    private boolean upperLoadFactorExceeded() {
        return loadFactorAfterAddition() > getUpperLoadFactor();
    }

}
