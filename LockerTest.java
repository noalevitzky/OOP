import oop.ex3.spaceship.*;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.Random;

/**
 * Tests the implementation of a Locker class.
 */
public class LockerTest {
    private Random Random = new Random();
    private int randomIdx;
    private static Item[] allLegalItems;
    private static Locker zeroLocker, bigLocker;
    private static int MAX_VOLUME, BIG_LOCKER_CAPACITY;

    @BeforeClass
    public static void createTestObjects() {
        allLegalItems = ItemFactory.createAllLegalItems();
        MAX_VOLUME = findMaxVolume();
        BIG_LOCKER_CAPACITY = (MAX_VOLUME * allLegalItems.length * 10);

    }

    private static int findMaxVolume() {
        int max = 0;
        for (Item item : allLegalItems) {
            if (item.getVolume() > max)
                max = item.getVolume();
        }
        return max;
    }

    @Before
    public void resetTestObjects() {
        zeroLocker = new Locker(0);
        bigLocker = new Locker(BIG_LOCKER_CAPACITY);
        randomIdx = Random.nextInt(allLegalItems.length);
        Locker.longTermStorage.resetInventory();
    }

    @Test
    public void capacityTest() {
        assertEquals("get capacity returned unexpected value",
                BIG_LOCKER_CAPACITY, bigLocker.getCapacity());
        assertEquals("get capacity returned unexpected value", 0, zeroLocker.getCapacity());
    }

    @Test
    public void initAvailableCapacityTest() {
        assertEquals("when a locker with 0 capacity is initialized, available capacity should be " +
                "equal to locker capacity", zeroLocker.getAvailableCapacity(), zeroLocker.getCapacity());
        assertEquals("when a locker with positive capacity is initialized, available capacity should" +
                " be equal to locker capacity", bigLocker.getAvailableCapacity(), bigLocker.getCapacity());
    }

    @Test
    public void availableCapacityUpdateAfterAddItem() {
        Item randomItem = allLegalItems[randomIdx];
        zeroLocker.addItem(randomItem, 1);
        assertEquals(0, zeroLocker.getAvailableCapacity());

        bigLocker.addItem(randomItem, randomIdx);
        int actualAvailableCapacity = bigLocker.getCapacity() - (randomItem.getVolume() * randomIdx);
        assertEquals("calculation of available capacity is incorrect",
                actualAvailableCapacity, bigLocker.getAvailableCapacity());
    }

    @Test
    public void getItemCountAfterAddItem() {
        Item addedItem = allLegalItems[randomIdx];
        String addedType = addedItem.getType();
        int added_n = randomIdx;

        bigLocker.addItem(addedItem, added_n);
        assertEquals("getItemCount method didn't return the number of items of a specific type",
                added_n, bigLocker.getItemCount(addedType));
    }

    @Test
    public void illegalTypeGetItemCount() {
        String randomType = allLegalItems[randomIdx].getType();
        // bigLocker is initiated with no items
        assertEquals("getItemCount should return 0 if it has no items of given type",
                0, bigLocker.getItemCount(randomType));
    }

    @Test
    public void initGetInventory() {
        assertTrue("inventory should be empty at initiation", bigLocker.getInventory().isEmpty());
    }

    @Test
    public void getInventoryUpdateAfterAddItem() {
        Item item1 = allLegalItems[randomIdx];
        int added_n = randomIdx + 1; //positive num

        //adding items of type1
        bigLocker.addItem(item1, added_n);
        assertTrue("inventory should hold item's type as key after it's been added to locker",
                bigLocker.getInventory().containsKey(item1.getType()));
        assertEquals("inventory's values should be the Integer number of items of a specific item" +
                " type", bigLocker.getItemCount(item1.getType()), added_n);

        //adding more items of type1. key value should be updated
        bigLocker.addItem(item1, 1);
        assertEquals("Item's value should be updated after adding more items of the same type",
                bigLocker.getItemCount(item1.getType()), (added_n + 1));
    }

    @Test
    public void successfulRemoveItem() {
        Item item1 = allLegalItems[randomIdx];
        int k = randomIdx + 1; //positive number

        bigLocker.addItem(item1, k);
        assertEquals("removing item successfully should return 0",
                bigLocker.removeItem(item1, 1), 0);
        assertEquals("inventory should be updated after removing an item",
                bigLocker.getItemCount(item1.getType()), (k - 1));
    }

    @Test
    public void unsuccessfulRemoveItem() {
        Item item = allLegalItems[randomIdx];
        int k = randomIdx;

        bigLocker.addItem(item, k);
        assertEquals("removing item unsuccessfully should return -1", -1,
                bigLocker.removeItem(item, (k + 10)));
        assertEquals("inventory should not be updated after an unsuccessful removing of an item",
                k, bigLocker.getItemCount(item.getType()));
    }

    @Test
    public void illegalRemoveItem() {
        Item item1 = allLegalItems[randomIdx];
        int k = randomIdx + 1; //adding 1 to avoid randomIdx=0, so the removing -k value would be negative

        bigLocker.addItem(item1, k);
        assertEquals("trying to remove a negative num of item is illegal, and method should return " +
                "-1", bigLocker.removeItem(item1, -k), -1);
        assertEquals("inventory should not be updated after an unsuccessful removing of an item",
                bigLocker.getItemCount(item1.getType()), k);

    }

    @Test
    public void successfulAddItem() {
        Item item = allLegalItems[randomIdx];
        int k = randomIdx + 1; //positive number

        assertEquals("successful addition should return 0", bigLocker.addItem(item, k), 0);
        assertEquals("inventory should be updated after a successful adding of an item",
                bigLocker.getItemCount(item.getType()), k);
    }

    @Test
    public void zeroAddItemSuccess() {
        Item item = allLegalItems[randomIdx];
        assertEquals("zero items addition should return 0 (counts as a successful addition",
                0, bigLocker.addItem(item, 0));
        assertEquals("zero addition shouldn't change inventory",
                0, bigLocker.getItemCount(item.getType()));
    }

    @Test
    public void zeroAddItemUnsuccessfulCollidingTypes() {
        Item football = ItemFactory.createSingleItem("football");
        Item baseballBat = ItemFactory.createSingleItem("baseball bat");
        int k = randomIdx + 1;

        bigLocker.addItem(football, k);
        assertEquals("baseball bat should not be stored in a locker that contains a football, " +
                "method should return -2", -2, bigLocker.addItem(baseballBat, 0));
        bigLocker.removeItem(football, k);
        bigLocker.addItem(baseballBat, k);
        assertEquals("football should not be stored in a locker that contains a baseball bat, " +
                "method should return -2", -2, bigLocker.addItem(football, 0));
    }

    @Test
    public void zeroAddItemSuccessfulCollidingTypes() {
        Item football = ItemFactory.createSingleItem("football");
        Item baseballBat = ItemFactory.createSingleItem("baseball bat");
        int k = randomIdx + 1;

        bigLocker.addItem(football, 0);
        assertEquals("baseball bat could be stored in a locker that contains zero football, " +
                "method should return 0", 0, bigLocker.addItem(baseballBat, k));
        bigLocker.removeItem(baseballBat, k);
        bigLocker.addItem(baseballBat, 0);
        assertEquals("football could be stored in a locker that contains zero baseball bat, " +
                "method should return 0", 0, bigLocker.addItem(football, k));
    }

    @Test
    public void unsuccessfulAddItemToZeroLocker() {
        Item item = allLegalItems[randomIdx];
        int k = randomIdx + 1; //positive number

        assertEquals("unsuccessful addition should return -1",
                -1, zeroLocker.addItem(item, k));
        assertEquals("item shouldn't be added to a 0 capacity locker",
                zeroLocker.getItemCount(item.getType()), 0);
    }

    @Test
    public void semiSuccessfulAddItem() {
        // n* items can be added, n-n* items are moved to longTerm
        Item item = allLegalItems[randomIdx];
        int volume = item.getVolume();
        int capacity = 10 * volume;
        Locker testLocker = new Locker(capacity);

        // 2 item should be added, and 4 should be moved to longterm
        assertEquals("a semi successful addition should return 1",
                1, testLocker.addItem(item, 6));
        assertEquals("when some items are passed to long term, remainings should take up to 20% of" +
                " locker storage", 2, testLocker.getItemCount(item.getType()));
        assertEquals("when some items are passed to long term, locker should add those to the united" +
                "longterm storage", 4, Locker.longTermStorage.getItemCount(item.getType()));
    }

    @Test
    public void addItemCollidingTypes() {
        Item football = ItemFactory.createSingleItem("football");
        Item baseballBat = ItemFactory.createSingleItem("baseball bat");
        int footballVolume = football.getVolume();
        int baseballBatVolume = baseballBat.getVolume();
        int capacity = (footballVolume + baseballBatVolume) * 100;
        Locker testLocker = new Locker(capacity);
        int k = randomIdx + 1;

        assertEquals("adding football to an empty locker should return 0",
                0, testLocker.addItem(football, k));
        assertEquals("baseball bat should not be stored in a locker that contains a football, " +
                "method should return -2", -2, testLocker.addItem(baseballBat, k));
        testLocker.removeItem(football, k);
        assertEquals("adding baseball bat to an empty locker should return 0",
                0, testLocker.addItem(baseballBat, k));
        assertEquals("football should not be stored in a locker that contains a baseball bat, " +
                "method should return -2", -2, testLocker.addItem(football, k));
    }

}




