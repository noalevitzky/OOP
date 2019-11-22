package filters;

import java.io.File;

public interface Filter {

    File[] filter(File[] files);
}
