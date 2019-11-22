package filters;

import filesprocessing.Toolbox;

import java.io.File;
import java.util.ArrayList;

public class PrefixFilter implements Filter {

    private String value;

    PrefixFilter(String s) {
        this.value = s;
    }

    @Override
    public File[] filter(File[] files) {
        ArrayList<File> filtered = new ArrayList<>();
        for (File f : files) {
            String name = Toolbox.getRelativePath(f);
            if (name.startsWith(this.value)) {
                filtered.add(f);
            }
        }
        return filtered.toArray(new File[]{});
    }
}



