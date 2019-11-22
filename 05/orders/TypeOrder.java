package orders;

import filesprocessing.Toolbox;

import java.io.File;
import java.util.Comparator;

public class TypeOrder implements Comparator<File> {

    private static Comparator<File> absCompare = new AbsOrder();

    @Override
    public int compare(File f1, File f2) {
        String ext1 = Toolbox.getExtension(f1);
        String ext2 = Toolbox.getExtension(f2);
        int n = ext1.compareTo(ext2);
        if (n == 0)
            return absCompare.compare(f1, f2);
        return n;
    }


}