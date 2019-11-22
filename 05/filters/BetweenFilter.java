package filters;

import filesprocessing.Toolbox;

import java.io.File;
import java.util.ArrayList;

public class BetweenFilter implements Filter {

    private Double value1, value2;

    BetweenFilter(Double d1, Double d2) {
        this.value1 = d1;
        this.value2 = d2;
    }

    @Override
    public File[] filter(File[] files) {
        ArrayList<File> filtered = new ArrayList<>();
        for (File file : files) {
            if (this.value1 <= file.length() && file.length() <= this.value2) {
                filtered.add(file);
            }
        }
        return filtered.toArray(new File[]{});
    }

}