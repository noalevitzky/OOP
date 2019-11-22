package filesprocessing;

import filesprocessing.type2error.IOFileException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Toolbox {

    private static final double BYTE_FACTOR = 1024;

    /**
     * reads the file and adds each line as a string to the string array.
     *
     * @param fileName text file to read
     * @return string array of file's content
     * @throws IOFileException if command file accessing caused IO errors
     */
    static String[] file2StringArray(String fileName) throws IOFileException {
        List<String> fileContent = new ArrayList<>();

        // reader obj to read the file
        BufferedReader reader = null;

        try {
            // Open a reader and read first
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            while (line != null) {
                // Add the line and read next
                fileContent.add(line);
                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            throw new IOFileException("ERROR: File " + fileName + " is not found\n");
        } catch (IOException e) {
            throw new IOFileException("ERROR: An IO error occurred\n");
        } finally {
            // Try to close the file
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                throw new IOFileException("ERROR: Could not close file " + fileName + "\n");
            }
        }

        // Convert the list to an array and return the array
        String[] result = new String[fileContent.size()];
        fileContent.toArray(result);
        return result;
    }

    /**
     * @param dir given directory
     * @return File array of files in directory
     */
    static File[] getFileArray(File dir) {
        File[] directoryContent = dir.listFiles();
        if (directoryContent == null)
            return null;

        //will hols files only from directoryContent
        ArrayList<File> files = new ArrayList<>();

        // get files only
        for (File f : directoryContent) {
            if (f != null && f.isFile())
                files.add(f);
        }
        return files.toArray(new File[]{});
    }

    /**
     * @param f requested file
     * @return relative path of the file
     */
    public static String getRelativePath(File f) {
        final int dLength = DirectoryProcessor.directory.getAbsolutePath().length();
        return f.getAbsolutePath().substring(dLength + 1);
    }

    /**
     * @param s requested string
     * @return split string by given regex
     */
    public static String[] splitString(String s) {
        return s.split("#");
    }

    /**
     * @param f requested file
     * @return extension of file
     */
    public static String getExtension(File f) {
        String name = Toolbox.getRelativePath(f);
        String ext;

        int idx = name.lastIndexOf(".");
        if (idx > 0)
            ext = name.substring(idx);
        else //idx == 0 (hidden), idx==-1 (no extension)
            ext = "";
        return ext;
    }

    /**
     * @param kb to be converted
     * @return convert kb to bytes
     */
    public static double convert2byte(double kb) {
        return kb * BYTE_FACTOR;
    }
}


