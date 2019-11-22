package filesprocessing;

import java.util.Comparator;

class QuickSort<E> {

    private Comparator<E> order;

    /**
     * sort files by merge-sort algorithm, using provided section comparator
     *
     * @param e     to be sorted
     * @param order comparator to be used
     * @return sorted file list
     */
    E[] sort(E[] e, Comparator<E> order) {
        this.order = order;

        //run sort algorithm
        return quickSort(e, 0, e.length - 1);
    }

    private E[] quickSort(E[] e, int low, int high) {
        if (e == null || e.length == 0) {
            return null;
        }

        //if not a singletons
        if (low < high) {
            int part = partition(e, low, high);

            //sort sub-arrays recursively
            quickSort(e, low, part - 1);
            quickSort(e, part + 1, high);
        }
        //return sorted
        return e;
    }

    private int partition(E[] e, int low, int high) {
        //choose last obj as a pivot
        E pivot = e[high];

        //reorder obj using comparator, around the pivot (smaller->pivot->greater)
        for (int j = low; j < high; j++) {
            if (order.compare(e[j], pivot) <= 0) {
                //swap objects
                E swap = e[low];
                e[low] = e[j];
                e[j] = swap;
                low++;
            }
        }

        //place pivot in it's correct placement
        E swap = e[low];
        e[low] = e[high];
        e[high] = swap;

        return low;
    }
}
