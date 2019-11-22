import org.junit.*;
import oop.ex3.searchengine.*;

import static org.junit.Assert.*;

import java.util.Random;

/**
 * test class for boopingSite methods
 */
public class BoopingSiteTest {
    private Random Random = new Random();
    private static String HOTELS_PARTIAL = "hotels_tst1.txt";
    private static String EMPTYFILE = "hotels_tst2.txt";
    private static String HOTELS_ALL = "hotels_dataset.txt";
    private static Hotel[] hotelsAll;
    private static Hotel[] hotelsPartial;
    private int randomIdx;
    private BoopingSite boopingSitePartial;
    private BoopingSite boopingSiteEmpty;
    private BoopingSite boopingSiteAll;
    private static String MANALI, randomCity;
    private static String HIGH_STAR_UID = "72968b25d39d6fbc47f342fc6ca80cee";
    private static String LOW_STAR_FIRST_UID = "c5cfd0295572c7f718bf405fff4b3cd4";
    private static String LOW_STAR_SECOND_UID = "a0a707bcd33d6168bf39638bf9adf16b";
    private static double GEO_LOCATION_LATITUDE = 28.55616;
    private static double GEO_LOCATION_LONGITUDE = 77.100281;
    private static double ILLEGAL_COORDINATE = 400.00;
    private static String CLOSE_HOTEL_UID = "6c858c24e8eea56b71f2343c7b936382";
    private static String FAR_HOTEL_FIRST_UID = "1b42d47ffa7a51885ab8529fef7e8131";
    private static String FAR_HOTEL_SECOND_UID = "df0971f9c5501af112485ee28b468ce5";

    @BeforeClass
    public static void createTestObjects() {
        hotelsAll = HotelDataset.getHotels(HOTELS_ALL);
        hotelsPartial = HotelDataset.getHotels(HOTELS_PARTIAL);
        MANALI = hotelsPartial[0].getCity();
    }

    @Before
    public void resetTestObjects() {
        randomIdx = Random.nextInt(hotelsAll.length);
        boopingSitePartial = new BoopingSite(HOTELS_PARTIAL);
        boopingSiteEmpty = new BoopingSite(EMPTYFILE);
        boopingSiteAll = new BoopingSite(HOTELS_ALL);
        randomCity = hotelsAll[randomIdx].getCity();
    }

    @Test
    public void successfulSortHotelsInCityByRating() {
        Hotel[] sorted = boopingSitePartial.getHotelsInCityByRating(MANALI);

        int sortedHighStarId = getIdxByUniqueId(HIGH_STAR_UID, sorted);
        int sortedLowStarFirstId = getIdxByUniqueId(LOW_STAR_FIRST_UID, sorted);
        int sortedLowStarSecondId = getIdxByUniqueId(LOW_STAR_SECOND_UID, sorted);
        assertTrue("Higher star-rated hotel should appear before lower star-rated",
                sortedHighStarId < sortedLowStarFirstId);
        assertTrue("Hotels that have the same star-rating should be alphabetically organized",
                sortedLowStarFirstId < sortedLowStarSecondId);
    }

    @Test
    public void inCity_SortHotelsInCityByRating() {
        Hotel[] sorted = boopingSiteAll.getHotelsInCityByRating(MANALI);
        for (Hotel hotel : sorted) {
            assertEquals("all hotels in sorted array should be in the given city",
                    MANALI, hotel.getCity());
        }
    }

    @Test
    public void emptySortHotelsInCityByRating() {
        // no hotels in city
        String testCity = "TestTest";
        Hotel[] sorted = boopingSitePartial.getHotelsInCityByRating(testCity);
        assertEquals("If there are no hotels in a given city, method should return an empty array",
                0, sorted.length);

        //empty booping site
        Hotel[] emptyArray = boopingSiteEmpty.getHotelsInCityByRating(randomCity);
        assertEquals("If there are no hotels in the original file, method should return an empty" +
                " array", 0, emptyArray.length);
    }

    private int getIdxByUniqueId(String uniqueId, Hotel[] sorted) {
        for (int i = 0; i < sorted.length; i++) {
            if (sorted[i].getUniqueId().equals(uniqueId))
                return i;
        }
        return -1;
    }

    @Test
    public void successfulSortHotelsByProximity() {
        Hotel[] sorted = boopingSitePartial.getHotelsByProximity(GEO_LOCATION_LATITUDE, GEO_LOCATION_LONGITUDE);

        int sortedCloseHotelId = getIdxByUniqueId(CLOSE_HOTEL_UID, sorted);
        int sortedFarFirstHotelId = getIdxByUniqueId(FAR_HOTEL_FIRST_UID, sorted);
        int sortedFarSecondHotelId = getIdxByUniqueId(FAR_HOTEL_SECOND_UID, sorted);

        assertTrue("closer hotel should appear before far hotel",
                sortedCloseHotelId < sortedFarFirstHotelId);
        assertTrue("Hotels that have the same distance from given geo location should be organized " +
                        "according to the number of POI for which they are close to",
                sortedFarFirstHotelId < sortedFarSecondHotelId);
    }


    @Test
    public void negativeInput_SuccessfulSortHotelsByProximity() {
        Hotel[] negSorted = boopingSitePartial.getHotelsByProximity(-GEO_LOCATION_LATITUDE,
                -GEO_LOCATION_LONGITUDE);

        int sortedCloseHotelId = getIdxByUniqueId(CLOSE_HOTEL_UID, negSorted);
        int sortedFarFirstHotelId = getIdxByUniqueId(FAR_HOTEL_FIRST_UID, negSorted);
        int sortedFarSecondHotelId = getIdxByUniqueId(FAR_HOTEL_SECOND_UID, negSorted);

        assertTrue("closer hotel should appear before far hotel",
                sortedCloseHotelId < sortedFarFirstHotelId);
        assertTrue("Hotels that have the same distance from given geo location should be organized " +
                        "according to the number of POI for which they are close to",
                sortedFarFirstHotelId < sortedFarSecondHotelId);
    }


    @Test
    public void illegalSortHotelByProximity() {
        //illegal coordinates
        Hotel[] sorted1 = boopingSitePartial.getHotelsByProximity(ILLEGAL_COORDINATE, GEO_LOCATION_LONGITUDE);
        Hotel[] sorted2 = boopingSitePartial.getHotelsByProximity(GEO_LOCATION_LATITUDE, ILLEGAL_COORDINATE);

        assertEquals("in case of illegal latitude, method should return an empty array",
                0, sorted1.length);
        assertEquals("in case of illegal longitude, method should return an empty array",
                0, sorted2.length);

        //empty boopingSite
        Hotel[] emptyArray = boopingSiteEmpty.getHotelsByProximity(GEO_LOCATION_LATITUDE, GEO_LOCATION_LONGITUDE);
        assertEquals("If there are no hotels in the original file, method should return an empty " +
                "array", 0, emptyArray.length);
    }

    @Test
    public void successfulHotelsInCityByProximity() {
        Hotel[] sorted = boopingSitePartial.getHotelsInCityByProximity(MANALI, GEO_LOCATION_LATITUDE,
                GEO_LOCATION_LONGITUDE);

        int sortedCloseHotelId = getIdxByUniqueId(CLOSE_HOTEL_UID, sorted);
        int sortedFarFirstHotelId = getIdxByUniqueId(FAR_HOTEL_FIRST_UID, sorted);
        int sortedFarSecondHotelId = getIdxByUniqueId(FAR_HOTEL_SECOND_UID, sorted);

        assertTrue("closer hotel should appear before far hotel",
                sortedCloseHotelId < sortedFarFirstHotelId);
        assertTrue("Hotels that have the same distance from given geo location should be organized " +
                        "according to the number of POI for which they are close to",
                sortedFarFirstHotelId < sortedFarSecondHotelId);
    }

    @Test
    public void negativeInput_SuccessfulHotelsInCityByProximity() {
        Hotel[] negSorted = boopingSitePartial.getHotelsInCityByProximity(MANALI, -GEO_LOCATION_LATITUDE,
                -GEO_LOCATION_LONGITUDE);

        int sortedCloseHotelId = getIdxByUniqueId(CLOSE_HOTEL_UID, negSorted);
        int sortedFarFirstHotelId = getIdxByUniqueId(FAR_HOTEL_FIRST_UID, negSorted);
        int sortedFarSecondHotelId = getIdxByUniqueId(FAR_HOTEL_SECOND_UID, negSorted);

        assertTrue("closer hotel should appear before far hotel",
                sortedCloseHotelId < sortedFarFirstHotelId);
        assertTrue("Hotels that have the same distance from given geo location should be organized " +
                        "according to the number of POI for which they are close to",
                sortedFarFirstHotelId < sortedFarSecondHotelId);
    }

    @Test
    public void illegalSortHotelInCityByProximity() {
        //illegal coordinates
        Hotel[] sorted1 = boopingSitePartial.getHotelsInCityByProximity(MANALI, ILLEGAL_COORDINATE,
                GEO_LOCATION_LONGITUDE);
        Hotel[] sorted2 = boopingSitePartial.getHotelsInCityByProximity(MANALI, GEO_LOCATION_LATITUDE,
                ILLEGAL_COORDINATE);

        assertEquals("in case of illegal latitude, method should return an empty array",
                0, sorted1.length);
        assertEquals("in case of illegal longitude, method should return an empty array",
                0, sorted2.length);

        //empty booping site
        Hotel[] emptyArray = boopingSitePartial.getHotelsInCityByProximity(randomCity, GEO_LOCATION_LATITUDE,
                GEO_LOCATION_LONGITUDE);
        assertEquals("If there are no hotels in the original file, method should return an empty " +
                "array", 0, emptyArray.length);
    }

    @Test
    public void inCity_sortHotelInCityByProximity() {
        Hotel[] sorted = boopingSiteAll.getHotelsInCityByProximity(randomCity, GEO_LOCATION_LATITUDE,
                GEO_LOCATION_LONGITUDE);
        for (Hotel hotel : sorted) {
            assertEquals("all hotels in sorted array should be in the given city",
                    randomCity, hotel.getCity());
        }
    }
}
