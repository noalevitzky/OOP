package filters;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class NotDecoratorFilter implements Filter {

    private Filter decoratedFilter;
    private File[] decFiltered;

    NotDecoratorFilter(Filter f) {
        this.decoratedFilter = f;
    }

    @Override
    public File[] filter(File[] files) {
        decFiltered = decoratedFilter.filter(files);
        ArrayList<File> filtered = new ArrayList<>();

        //add files to filter iff they are not in the decoratorFilter output
        for (File f : files) {
            if (fileNotInDecFiltered(f)) {
                filtered.add(f);
            }
        }
        return filtered.toArray(new File[]{});
    }


    private boolean fileNotInDecFiltered(File f) {
        return !Arrays.asList(decFiltered).contains(f);
    }
}
