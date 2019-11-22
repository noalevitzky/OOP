package filters;

import filesprocessing.Toolbox;

import java.io.File;
import java.util.ArrayList;

public class FileFilter implements Filter {

    private String value;

    FileFilter(String s) {
        this.value = s;
    }

    @Override
    public File[] filter(File[] files) {
        ArrayList<File> filtered = new ArrayList<>();
        for (File f : files) {
            String name = Toolbox.getRelativePath(f);
            if (name.equals(this.value)) {
                filtered.add(f);
            }
        }
        return filtered.toArray(new File[]{});
    }
}