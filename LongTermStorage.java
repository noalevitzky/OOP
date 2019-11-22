import oop.ex3.spaceship.Item;

/**
 * this class represents a long term storage locker that could be found in starship USS Discovery.
 */
public class LongTermStorage extends Storage {
    private static final int LONG_TERM_CAPACITY = 1000;

    public LongTermStorage() {
        super(LONG_TERM_CAPACITY);
    }

    /**
     * add item to storage
     *
     * @param item object of a specific type
     * @param n    num of items
     * @return 0 upon success, -1 upon failure
     */
    public int addItem(Item item, int n) {
        String type = item.getType();
        if (allItemsCanBeAdded(item, n)) {
            storeItems(type, n);
            reduceFromAvailableCapacity(getItemVolume(item, n));
            return 0;
        } // no items can be added
        System.out.println("Error: Your request cannot be completed at this time. " +
                "Problem: no room for " + n + " Items of type " + type);
        return -1;
    }

    /**
     * reset inventory and avail capacity
     */
    public void resetInventory() {
        super.resetInventory();
        super.resetAvailableCapacity();
    }

    /**
     * @param item requested items
     * @param n    num of items
     * @return true if all items could be added, false otherwise
     */
    private boolean allItemsCanBeAdded(Item item, int n) {
        int itemsVolume = getTypeTotalVolume(item, n);
        return (itemsVolume <= getAvailableCapacity());
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

}
