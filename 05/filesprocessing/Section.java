package filesprocessing;

import errors.TypeIError;
import filters.*;
import orders.*;
import orders.type1error.OrderNameError;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * represent a single filter and order request in command file
 */
class Section {

    private static FiltersFactory filtersFactory = new FiltersFactory();
    private static OrdersFactory ordersFactory = new OrdersFactory();
    private Filter filter;
    private Comparator<File> order;
    private String[] warnings;

    Section(String filter, int fLine, String order, int oLine) {
        ArrayList<String> warnings = new ArrayList<>();

        //add filter
        try {
            this.filter = filtersFactory.createFilter(filter, fLine);
        } catch (TypeIError e) {
            warnings.add(e.getMessage());
            this.filter = filtersFactory.createDefault();
        }

        //add order
        try {
            this.order = ordersFactory.createOrder(order, oLine);
        } catch (OrderNameError e) {
            warnings.add(e.getMessage());
            this.order = ordersFactory.createDefault();
        }

        this.warnings = warnings.toArray(new String[]{});
    }

    /**
     * @param files requested file list
     * @return filter files
     */
    File[] filterFiles(File[] files) {
        if (files == null)
            return null;
        return filter.filter(files);
    }

    /**
     * @param filtered requested file list
     * @return order files
     */
    File[] orderFiles(File[] filtered) {
        if (filtered == null)
            return null;
        QuickSort<File> mySort = new QuickSort<>();
        return mySort.sort(filtered, this.order);
    }

    String[] getWarnings(){
        return (warnings.length>0)? warnings:null;
    }


}
