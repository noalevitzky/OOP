package filters;

import java.io.File;
import java.util.ArrayList;

public class SmallerThanFilter implements Filter {

    private Double value;

    SmallerThanFilter(Double d) {
        this.value = d;
    }

    @Override
    public File[] filter(File[] files) {
        ArrayList<File> filtered = new ArrayList<>();
        for (File f : files) {
            if (f.length() < this.value) {
                filtered.add(f);
            }
        }
        return filtered.toArray(new File[]{});
    }
}

