import oop.ex3.searchengine.*;

import java.util.*;

/**
 * this class represents a booking site
 */
public class BoopingSite implements Comparator<Hotel> {
    private String hotelFile;
    private Hotel[] hotels;
    private double poiLatitude, poiLongitude;
    private int LAT_BOTTOM_BAR = -90;
    private int LAT_TOP_BAR= 90;
    private int LONG_BOTTOM_BAR = -180;
    private int LONG_TOP_BAR = 180;
    private StarRatingCompare starRatingCompare = new StarRatingCompare();
    private NameCompare nameCompare = new NameCompare();
    private PoiCompare poiCompare = new PoiCompare();

    public BoopingSite(String name) {
        this.hotelFile = name;
        this.hotels = HotelDataset.getHotels(this.hotelFile);
    }

    /**
     * proximity compare, based on euclidean distance from given geo location
     * @param h1 first hotel to compare
     * @param h2 second hotel to compare
     * @return 0 if items are equal, 1 if h1>h2, -1 otherwise
     */
    @Override
    public int compare(Hotel h1, Hotel h2){
        if (distance(h1)==distance(h2))
            return 0;
        return Double.compare(distance(h1), distance(h2));
    }

    /**
     * this method sorts hotels in city from highest star rating to the lowest.
     * hotels with the same rating will be alphabetically organized. if there are no hotels in city, an empty
     * array is returned.
     * @param city requested city
     * @return a sorted and filtered Hotel array
     */
    public Hotel[] getHotelsInCityByRating(String city) {
        List<Hotel> list = new ArrayList<>(Arrays.asList(this.hotels));
        List<Hotel> filtered = filterCity(city, list);
        if (filtered.size() == 0) //no hotels in city
            return new Hotel[0];
        filtered.sort(this.nameCompare);
        filtered.sort(Collections.reverseOrder(this.starRatingCompare));
        return filtered.toArray(new Hotel[filtered.size()]);
    }

    /**
     * this method sorts hotels according to their euclidean distance from a given geo location, in ascending
     * order. hotels with the same distance will be organized according to the num of POI they are close to
     * (descending). in case of illegal input, an empty array will be returned.
     * @param latitude of geo location
     * @param longitude of geo location
     * @return a sorted Hotel array
     */
    public Hotel[] getHotelsByProximity(double latitude, double longitude) {
        if(isInputIllegal(latitude, longitude))
            return new Hotel[0];
        List<Hotel> list = new ArrayList<>(Arrays.asList(this.hotels));
        return sortProximity(list, latitude, longitude);
    }

    /**
     * this method sorts hotels in the given city, according to their euclidean distance from a given geo
     * location, in ascending order. hotels with the same distance will be organized according to the num of
     * POI they are close to (descending). in case of illegal input, an empty array will be returned.
     * @param city requested city
     * @param latitude of geo location
     * @param longitude of geo location
     * @return a sorted and filtered Hotel array
     */
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude){
        List<Hotel> list = new ArrayList<>(Arrays.asList(this.hotels));
        List<Hotel> filtered = filterCity(city, list);
        if (filtered.size() == 0 || isInputIllegal(latitude, longitude))
            return new Hotel[0];
        return sortProximity(filtered, latitude, longitude);
    }

    /**
     * this is a helper method for sorting by proximity and then by poi num.
     * @param list list to sort
     * @param latitude of geo location
     * @param longitude of geo location
     * @return sorted array by proximity
     */
    private Hotel[] sortProximity(List<Hotel> list ,double latitude, double longitude){
        setPoiCoordinates(latitude, longitude);
        list.sort(Collections.reverseOrder(this.poiCompare));
        list.sort(this::compare);
        return list.toArray(new Hotel[list.size()]);
    }

    /**
     * @param latitude of geo location
     * @param longitude of geo location
     * @return true upon legal input, false otherwise
     */
    private boolean isInputIllegal(double latitude, double longitude){
        return (latitude<LAT_BOTTOM_BAR || latitude>LAT_TOP_BAR ||
                longitude<LONG_BOTTOM_BAR || longitude>LONG_TOP_BAR);
    }

    /**
     * this is a helper method that filters a given list by a specific city
     * @param city requested city to filter by
     * @param list list to sort
     * @return filtered list
     */
    private List<Hotel> filterCity(String city, List<Hotel> list){
        List<Hotel> filtered = new ArrayList<>();
        for(Hotel hotel: list){
            if(hotel.getCity().equals(city))
                filtered.add(hotel);
        } return filtered;
    }

    /**
     * @param hotel requested hotel
     * @return euclidean distance from given geo location (coordinates are set as data members)
     */
    private double distance(Hotel hotel){
        double x = Math.abs(hotel.getLatitude()-this.poiLatitude);
        double y = Math.abs(hotel.getLongitude()-this.poiLongitude);
        return Math.sqrt((x*x)+(y*y));
    }

    /**
     * set geo location coordinates as data members, for the later distance calculation.
     * @param latitude of geo location
     * @param longitude of geo location
     */
    private void setPoiCoordinates(double latitude, double longitude){
        this.poiLatitude = latitude;
        this.poiLongitude = longitude;
    }
}
