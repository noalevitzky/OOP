import oop.ex3.spaceship.*;

/**
 * this class represents a storage locker that could be found in starship USS Discovery.
 */
public class Locker extends Storage {
    private final double MAX_ITEM_CAPACITY;
    private final double REMAINING_ITEM_CAPACITY;
    protected static LongTermStorage longTermStorage;

    public Locker(int capacity) {
        super(capacity);
        this.MAX_ITEM_CAPACITY = 0.5 * capacity;
        this.REMAINING_ITEM_CAPACITY = 0.2 * capacity;
        longTermStorage = new LongTermStorage();
    }

    /**
     * add item to locker
     *
     * @param item object of a specific type
     * @param n    num of items
     * @return -2 for contradicting types, 0 upon success, 1 for semisuccess (some items moved to longterm),
     * -1 upon failure
     */
    public int addItem(Item item, int n) {
        String type = item.getType();
        if (contradictingTypes(type)) {
            System.out.println("Error: Your request cannot be completed at this time. Problem: the locker " +
                    "cannot contain items of type " + type + ", as it contains a contradicting item");
            return -2;
        }
        if (n == 0) { //adding 0 items
            return 0;
        }
        if (allItemsCanBeAdded(item, n)) { //adding n items
            storeItems(type, n);
            reduceFromAvailableCapacity(getItemsTotalVolume(item, n));
            return 0;
        }
        int some = someItemsCanBeAdded(item, n);
        if (some > 0) { //adding some items
            storeItems(item.getType(), some);
            reduceFromAvailableCapacity(getItemsTotalVolume(item, some));
            return 1;
        }
        // no items can be added
        System.out.println("Error: Your request cannot be completed at this time. " +
                "Problem: no room for " + n + " items of type " + type);
        return -1;
    }

    /**
     * @param type type of added item
     * @return true if there are contradicting types in locker, false otherwise
     */
    private boolean contradictingTypes(String type) {
        if (type.equals("football") && super.getItemCount("baseball bat") > 0)
            return true;
        else return type.equals("baseball bat") && super.getItemCount("football") > 0;
    }

    /**
     * remove items from locker
     *
     * @param item items to be removed
     * @param n    num of items to remove
     * @return 0 upon success, -1 for failure due to illegal input or less items that exist in locker than
     * requested
     */
    public int removeItem(Item item, int n) {
        String type = item.getType();
        int itemValue = getItemCount(item.getType());
        if (n == 0)
            return 0;
        if (0 < n && n <= itemValue) { //successful
            removeItemFromInventory(type);
            if (n != itemValue)
                putItemInInventory(type, (itemValue - n));
            addToAvailableCapacity(getItemsTotalVolume(item, n));
            return 0;
        }
        if (n > itemValue) { //unsuccessful
            System.out.println("Error: Your request cannot be completed at this time. " +
                    "Problem: the locker does not contain " + n + " items of type " + type);
            return -1;
        }
        //n<0 illegal input
        System.out.println("Error: Your request cannot be completed at this time. " +
                "Problem: cannot remove a negative number of items of type " + type);
        return -1;
    }

    /**
     * add items to storage
     *
     * @param type items' type
     * @param n    num of items
     */
    private void storeItems(String type, int n) {
        int prevValue = getItemCount(type);
        int newValue = prevValue + n;
        removeItemFromInventory(type);
        putItemInInventory(type, newValue);
    }

    /**
     * @param item requested item
     * @param n    num of items
     * @return items' volume
     */
    private int getItemsTotalVolume(Item item, int n) {
        return item.getVolume() * n;
    }

    /**
     * @param item requested item
     * @param n    num of items
     * @return true if all items can be added, false otherwise
     */
    private boolean allItemsCanBeAdded(Item item, int n) {
        int itemsVolume = getTypeTotalVolume(item, n);
        return (itemsVolume <= this.MAX_ITEM_CAPACITY && itemsVolume <= getAvailableCapacity());
    }

    /**
     * @param item requested item
     * @param n    num of items
     * @return true if items'volume is bellow the remaining item capacity bar, false if exceeded.
     */
    private boolean belowRemainingCapacity(Item item, int n) {
        int itemsVolume = getTypeTotalVolume(item, n);
        return (itemsVolume <= this.REMAINING_ITEM_CAPACITY && itemsVolume <= getAvailableCapacity());
    }

    /**
     * @param item requested item
     * @param n    num of items
     * @return num of items that could be added
     */
    private int someItemsCanBeAdded(Item item, int n) {
        for (int some = n-1; some > 0; some--) {
            int moveToLT = n - some;
            if (belowRemainingCapacity(item, some)) {
                if (longTermStorage.addItem(item, (moveToLT)) == 0) {
                    return some;
                }
            }
        }
        return 0;
    }

}

