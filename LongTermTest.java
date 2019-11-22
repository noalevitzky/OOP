import oop.ex3.spaceship.*;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.Random;

/**
 * Tests the different long-term storage actions.
 */
public class LongTermTest {
    private java.util.Random Random = new Random();
    private int randomIdx;
    private static Item[] allLegalItems;
    private static LongTermStorage longTermStorage;
    private static final int LONG_TERM_CAPACITY = 1000;

    @BeforeClass
    public static void createTestObjects() {
        allLegalItems = ItemFactory.createAllLegalItems();
        longTermStorage = new LongTermStorage();
    }

    @Before
    public void resetTestObjects() {
        longTermStorage.resetInventory();
        randomIdx = Random.nextInt(allLegalItems.length);
    }

    @Test
    public void capacityTest() {
        assertEquals("get capacity returned unexpected value",
                LONG_TERM_CAPACITY, longTermStorage.getCapacity());
    }

    @Test
    public void initAvailableCapacityTest() {
        assertEquals("when a long term storage is initialized, available capacity should be equal to" +
                " locker capacity", longTermStorage.getAvailableCapacity(), longTermStorage.getCapacity());
    }

    @Test
    public void availableCapacityUpdateAfterAddItem() {
        Item item = allLegalItems[randomIdx];
        int k = randomIdx + 1;

        if (canAddItem(item, k)) {
            longTermStorage.addItem(item, k);
            int actualAvailableCapacity = LONG_TERM_CAPACITY - (item.getVolume() * k);
            assertEquals("calculation of available capacity is incorrect",
                    actualAvailableCapacity, longTermStorage.getAvailableCapacity());
        }
    }

    @Test
    public void getItemCountAfterAddItem() {
        Item item = allLegalItems[randomIdx];
        String addedType = item.getType();
        int k = randomIdx;

        if (canAddItem(item, k)) {
            longTermStorage.addItem(item, k);
            assertEquals("getItemCount method didn't return the number of items of a specific type",
                    longTermStorage.getItemCount(addedType), k);
        }
    }

    @Test
    public void illegalTypeGetItemCount() {
        String randomType = allLegalItems[randomIdx].getType();
        assertEquals("getItemCount should return 0 if it has no items of give type",
                0, longTermStorage.getItemCount(randomType));
    }

    @Test
    public void initGetInventory() {
        assertTrue("inventory should be empty at initiation",
                longTermStorage.getInventory().isEmpty());
    }

    @Test
    public void getInventoryUpdateAfterAddItem() {
        Item item1 = allLegalItems[randomIdx];
        int k = randomIdx + 1;
        longTermStorage.addItem(item1, k);

        if (canAddItem(item1, k)) {
            assertTrue("inventory should hold item's type as key after it's been added to locker",
                    longTermStorage.getInventory().containsKey(item1.getType()));
            assertEquals("inventory's values should be the Integer number of items of a specific item" +
                    " type", k, longTermStorage.getItemCount(item1.getType()));
        }
    }

    @Test
    public void successfulAddItem() {
        Item item = allLegalItems[randomIdx];
        int k = randomIdx + 1; //positive number

        if (canAddItem(item, k)) {
            assertEquals("successful addition should return 0",
                    longTermStorage.addItem(item, k), 0);
            assertEquals("inventory should be updated after a successful adding of an item",
                    longTermStorage.getItemCount(item.getType()), k);
        }
    }

    @Test
    public void zeroAddItemSuccessful() {
        Item item = allLegalItems[randomIdx];

        assertEquals("successful addition should return 0",
                0, longTermStorage.addItem(item, 0));
        assertEquals("inventory should not be updated after adding 0 items",
                0, longTermStorage.getItemCount(item.getType()));
    }


    @Test
    public void unsuccessfulAddItem() {
        Item item = allLegalItems[randomIdx];
        int k = longTermStorage.getAvailableCapacity(); //enough to exceed available capacity, since itemVolume>=1

        if (!canAddItem(item, k)) {
            assertEquals("unsuccessful addition should return -1",
                    -1, longTermStorage.addItem(item, k));
            assertEquals("item shouldn't be added to long term storage, volume is grater " +
                    "then capacity", 0, longTermStorage.getItemCount(item.getType()));
        }
    }

    private boolean canAddItem(Item item, int n) {
        return (item.getVolume() * n <= longTermStorage.getAvailableCapacity());
    }
}



