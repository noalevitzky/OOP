package orders;

import java.io.File;
import java.util.Comparator;

public class AbsOrder implements Comparator<File> {

    @Override
    public int compare(File f1, File f2) {
        return f1.getAbsolutePath().compareTo(f2.getAbsolutePath());
    }

}
