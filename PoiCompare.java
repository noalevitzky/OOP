import oop.ex3.searchengine.Hotel;
import java.util.Comparator;

public class PoiCompare implements Comparator<Hotel> {

    /**
     * comparing hotels' num of POI (ascending)
     *
     * @param h1 first hotel to compare
     * @param h2 second hotel to compare
     * @return 0 if num of POI are equal, -1 if h1 should appear first, 1 else.
     */
    @Override
    public int compare(Hotel h1, Hotel h2) {
        if(h1.getNumPOI()==h2.getNumPOI())
            return 0;
        return Integer.compare(h1.getNumPOI(), h2.getNumPOI());
    }
}
