package filesprocessing;

import filesprocessing.type2error.UsageException;
import errors.TypeIIError;

import java.io.File;

/**
 * the program' runner, handles all operations on files
 */
public class DirectoryProcessor {

    static private String commandFileName, sourceDirName;
    static String[] commands;
    static File directory;
    private static File[] files;

    public static void main(String[] args) {
        try {
            validateUsage(args);

            //process command file and provided directory
            commands = Toolbox.file2StringArray(commandFileName);
            directory = new File(sourceDirName);
            files = Toolbox.getFileArray(directory);

            //parse files to sections, and print output
            Section[] sections = Parser.parseFile();
            processSections(sections);
        } catch (TypeIIError e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * validate that user input is legal
     *
     * @param args user input, should be 2 strings representing a sourcedir and a commandfile
     */
    private static void validateUsage(String[] args) throws UsageException {
        //checks that there are only 2 args
        if (args.length != 2)
            throw new UsageException("ERROR: invalid usage, 2 arguments should be received.\n");
        sourceDirName = args[0];
        commandFileName = args[1];
        //validation of args order: first file is a directory, second is a file.
        if (!(new File(sourceDirName)).isDirectory() || !(new File(commandFileName)).isFile())
            throw new UsageException("ERROR: invalid usage, first argument should be " +
                    "SourceDirectory, second should be CommandsFile.\n");
    }

    /**
     * for each section, filter and sort files by order, and print output (file names)
     *
     * @param sections to be processed
     */
    private static void processSections(Section[] sections) {
        if (sections == null)
            return;

        for (Section section : sections) {
            //print type1errors
            String[] warnings = section.getWarnings();
            if (warnings!= null) {
                for (String s: warnings) {
                    System.err.println(s);
                }
            }

            //filter & sort section
            File[] output = section.orderFiles(section.filterFiles(files));

            //print files names
            if (output != null) {
                for (File f : output)
                    System.out.println(f.getName());
            }
        }

    }
}
