package filters;

import java.io.File;
import java.util.ArrayList;

public class ExecutableFilter implements Filter {

    private Boolean value;

    ExecutableFilter(String s) {
        this.value = s.equals("YES");
    }

    @Override
    public File[] filter(File[] files) {
        ArrayList<File> filtered = new ArrayList<>();
        for (File f : files) {
            if (f.canExecute() == value) {
                filtered.add(f);
            }
        }
        return filtered.toArray(new File[]{});
    }
}
