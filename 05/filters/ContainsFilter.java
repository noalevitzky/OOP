package filters;

import filesprocessing.Toolbox;

import java.io.File;
import java.util.ArrayList;

public class ContainsFilter implements Filter {

    private String value;

    ContainsFilter(String s) {
        this.value = s;
    }

    @Override
    public File[] filter(File[] files) {
        ArrayList<File> filtered = new ArrayList<>();
        for (File file : files) {
            String name = Toolbox.getRelativePath(file);
            if (name.contains(this.value)) {
                filtered.add(file);
            }
        }
        return filtered.toArray(new File[]{});
    }

}
