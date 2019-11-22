package orders;

import java.io.File;
import java.util.Comparator;

public class SizeOrder implements Comparator<File> {

    private static Comparator<File> absCompare = new AbsOrder();

    @Override
    public int compare(File f1, File f2) {
        int n = Long.compare(f1.length(), f2.length());
        if (n == 0)
            return absCompare.compare(f1, f2);
        return n;
    }
}