package filters;

import java.io.File;
import java.util.ArrayList;

public class WritableFilter implements Filter {

    private boolean value;

    WritableFilter(String s) {
        this.value = s.equals("YES");
    }

    @Override
    public File[] filter(File[] files) {
        ArrayList<File> filtered = new ArrayList<>();
        for (File f : files) {
            if (f.canWrite() == this.value) {
                filtered.add(f);
            }
        }
        return filtered.toArray(new File[]{});
    }
}
