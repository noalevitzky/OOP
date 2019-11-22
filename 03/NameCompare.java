import oop.ex3.searchengine.Hotel;

import java.util.Comparator;

public class NameCompare implements Comparator<Hotel> {

    /**
     * comparing hotels' names (ascending)
     *
     * @param h1 first hotel to compare
     * @param h2 second hotel to compare
     * @return 0 if names are equal, -1 if h1 should appear first, 1 else.
     */
    @Override
    public int compare(Hotel h1, Hotel h2) {
        if (h1.getPropertyName().equals(h2.getPropertyName()))
            return 0;
        return h1.getPropertyName().compareTo(h2.getPropertyName());
    }
}
