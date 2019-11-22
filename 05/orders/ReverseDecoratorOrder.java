package orders;

import java.io.File;
import java.util.Comparator;

public class ReverseDecoratorOrder implements Comparator<File> {

    private Comparator<File> order;

    public ReverseDecoratorOrder(Comparator<File> o) {
        this.order = o;
    }

    @Override
    public int compare(File f1, File f2) {
        return -1 * this.order.compare(f1, f2);

    }
}
