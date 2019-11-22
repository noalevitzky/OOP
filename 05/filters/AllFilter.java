package filters;

import java.io.File;

public class AllFilter implements Filter {

    @Override
    public File[] filter(File[] files) {
        return files;
    }

}
