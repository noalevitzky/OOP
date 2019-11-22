import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

/***
 * this class is a general storage class
 */
public abstract class Storage {
    private final int STORAGE_CAPACITY;
    private int availableCapacity;
    private Map<String, Integer> storageItems;

    public Storage(int capacity) {
        this.STORAGE_CAPACITY = capacity;
        resetAvailableCapacity();
        resetInventory();
    }

    /***
     * adding n items to locker
     * @param item object of a specific type
     * @param n num of items
     * @return int that symbolizes success/failure
     */
    public abstract int addItem(Item item, int n);

    /**
     * @param type key that represent a specific item type
     * @return num of items in storage
     */
    public int getItemCount(String type) {
        if (this.storageItems.containsKey(type))
            return this.storageItems.get(type);
        else
            return 0;
    }

    /**
     * @return storage capacity
     */
    public int getCapacity() {
        return this.STORAGE_CAPACITY;
    }

    /**
     * @return storage available capacity
     */
    public int getAvailableCapacity() {
        return this.availableCapacity;
    }

    /**
     * @return a hashmap representation of items in storage & num of items
     */
    public Map<String, Integer> getInventory() {
        return this.storageItems;
    }

    /**
     * adds item to inventory
     *
     * @param type item type
     * @param n    num of items
     */
    protected void putItemInInventory(String type, int n) {
        this.storageItems.put(type, n);
    }

    /**
     * remove item from inventory
     *
     * @param type item type
     */
    protected void removeItemFromInventory(String type) {
        this.storageItems.remove(type);
    }

    /**
     * add removed items' volume to available capacity
     *
     * @param volume removed item's volume
     */
    protected void addToAvailableCapacity(int volume) {
        this.availableCapacity += volume;
    }

    /**
     * remove added items' volume from available capacity
     *
     * @param volume added items' volume
     */
    protected void reduceFromAvailableCapacity(int volume) {
        this.availableCapacity -= volume;
    }

    /**
     * reset inventory by defining a new hashmap object
     */
    protected void resetInventory() {
        this.storageItems = new HashMap<>();
    }

    /**
     * reset avail capacity to storage capacity
     */
    protected void resetAvailableCapacity() {
        this.availableCapacity = this.STORAGE_CAPACITY;
    }

    /**
     * @param item requested item
     * @param n    num of items to add
     * @return volume of all type items after addition
     */
    protected int getTypeTotalVolume(Item item, int n) {
        int prevItemVolume = getItemVolume(item, getItemCount(item.getType()));
        int addItemVolume = getItemVolume(item, n);
        return prevItemVolume + addItemVolume;
    }

    /**
     * @param item requested item
     * @param n    num of items
     * @return total volume
     */
    protected int getItemVolume(Item item, int n) {
        return item.getVolume() * n;
    }
}
