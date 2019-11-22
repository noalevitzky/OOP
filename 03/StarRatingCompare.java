import oop.ex3.searchengine.Hotel;
import java.util.Comparator;

public class StarRatingCompare implements Comparator<Hotel> {

    /**
     * comparing hotels' star rating (ascending)
     *
     * @param h1 first hotel to compare
     * @param h2 second hotel to compare
     * @return 0 if ratings are equal, -1 if h1 should appear first, 1 else.
     */
    @Override
    public int compare(Hotel h1, Hotel h2) {
        if (h1.getStarRating() == h2.getStarRating())
            return 0;
        return Integer.compare(h1.getStarRating(), h2.getStarRating());
    }
}
