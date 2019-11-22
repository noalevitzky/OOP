import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

public class SimpleSetPerformanceAnalyzer {

    private static SimpleSet[] dataSets1, dataSets2;
    private static SimpleSet linkedListSet1, treeSet1, hashSet1, openHashSet1, closedHashSet1,
            linkedListSet2, treeSet2, hashSet2, openHashSet2, closedHashSet2;

    private static String[] data1 = Ex4Utils.file2array("data1.txt");
    private static String[] data2 = Ex4Utils.file2array("data2.txt");

    private static final int NANO_TO_MILL_FACTOR = 1000000;
    private static final int WARMUP_TIME = 70000;

    private static void initiation() {
        //init collections for dataset1
        linkedListSet1 = new CollectionFacadeSet(new LinkedList<>());
        treeSet1 = new CollectionFacadeSet(new TreeSet<>());
        hashSet1 = new CollectionFacadeSet(new HashSet<>());
        openHashSet1 = new OpenHashSet();
        closedHashSet1 = new ClosedHashSet();

        //init collections for dataset2
        linkedListSet2 = new CollectionFacadeSet(new LinkedList<>());
        treeSet2 = new CollectionFacadeSet(new TreeSet<>());
        hashSet2 = new CollectionFacadeSet(new HashSet<>());
        openHashSet2 = new OpenHashSet();
        closedHashSet2 = new ClosedHashSet();

        //apply collections to a specific array for testings
        dataSets1 = new SimpleSet[]{treeSet1, hashSet1, linkedListSet1, openHashSet1, closedHashSet1};
        dataSets2 = new SimpleSet[]{treeSet2, hashSet2, linkedListSet2, openHashSet2, closedHashSet2};
    }

    /**
     * add all strings from strings' array
     * @param data array of strings to add
     * @param collection strings will be added to this object
     */
    private static void add(String[] data, SimpleSet collection) {
        for (String string : data)
            collection.add(string);
    }

    /**
     * calculates the time that the addition operation took for given collection
     * @param data array of strings to add
     * @param collection strings will be added to this object
     */
    private static void countAddition(String[] data, SimpleSet collection) {
        long timeBefore = System.nanoTime();
        add(data, collection);
        long difference = (System.nanoTime() - timeBefore) / NANO_TO_MILL_FACTOR;
        System.out.println("Collection: " + collection + " Time: " + difference);

    }

    /**
     * calculates the time that the contains operation took for given collection
     * @param string string to search
     * @param collection string will be searched in this object
     */
    private static void countContains(String string, SimpleSet collection) {
        if (collection != linkedListSet1 || collection !=linkedListSet2) {
            //warmup
            for (int i = 0; i < WARMUP_TIME; i++)
                collection.contains(string);
        }
        long timeBefore = System.nanoTime();
        for (int i = 0; i < WARMUP_TIME; i++)
            collection.contains(string);
        long difference = (System.nanoTime() - timeBefore) / WARMUP_TIME;
        System.out.println("Collection: " + collection + " Time: " + difference);
    }

    public static void main(String[] args) {
        initiation();
        System.out.println("===== STARTING TESTS =====\n");

        //test1
        System.out.println("===== 1: ADD DATA 1 (millisecond) =====");
        for (SimpleSet collection : dataSets1) {
            countAddition(data1, collection);
            System.out.println("size: actual "+ collection.size()+", expected "+data1.length);
        }

        //test2
        System.out.println("===== 2: ADD DATA 2 (millisecond) =====");
        for (SimpleSet collection : dataSets2) {
            countAddition(data2, collection);
            System.out.println("size: actual "+ collection.size()+", expected "+data2.length);

        }

        //test3
        System.out.println("===== 3: CONTAINS \"hi\" (nanosecond) =====");
        for (SimpleSet collection : dataSets1) {
            countContains("hi", collection);
            System.out.println("Contains: actual "+ collection.contains("hi")+", expected false");

        }

        //test4
        System.out.println("===== 4: CONTAINS \"-1317089015\" (nanosecond) =====");
        for (SimpleSet collection : dataSets1) {
            countContains("-1317089015", collection);
            System.out.println("Contains: actual "+ collection.contains("-1317089015")+", expected false");

        }

        //test5
        System.out.println("===== 5: CONTAINS \"23\" (nanosecond) =====");
        for (SimpleSet collection : dataSets2) {
            countContains("23", collection);
            System.out.println("Contains: actual "+ collection.contains("23")+", expected true");

        }

        //test6
        System.out.println("===== 6: CONTAINS \"hi\" (nanosecond) =====");
        for (SimpleSet collection : dataSets2) {
            countContains("hi", collection);
            System.out.println("Contains: actual "+ collection.contains("hi")+", expected false");
        }

        System.out.println("\n===== END OF TESTS =====");
    }

}

