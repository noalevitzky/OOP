package filesprocessing;

import errors.TypeIIError;
import filesprocessing.type2error.FormatException;
import filesprocessing.type2error.SubSectionNameException;

import java.util.ArrayList;

/**
 * create sections out of command file
 */
class Parser {

    private static final String FILTER = "FILTER", ORDER = "ORDER", DEFAULT_ORDER_NAME = "abs";

    /**
     * @return Section list based on command fie instructions
     * @throws SubSectionNameException if FILTER/ORDER subsections have illegal names
     * @throws FormatException         i.e if there's no ORDER subsection
     */
    static Section[] parseFile() throws TypeIIError {
        ArrayList<Section> sections = new ArrayList<>();

        //validate first line exists
        if (DirectoryProcessor.commands.length == 0)
            return null;

        int fLine, oLine;
        int line = 0;

        //build section by section
        try {
            while (line < DirectoryProcessor.commands.length) {
                String filterName, orderName;

                //validate first line
                firstLine(line);
                line++;

                //set second line as filter name
                lineExists(line);
                filterName = DirectoryProcessor.commands[line];
                line++;
                fLine = line;

                //check third line
                lineExists(line);
                thirdLine(line);
                line++;

                //check forth line
                if (orderExists(line)) {
                    orderName = DirectoryProcessor.commands[line];
                    line++;
                    oLine = line;
                } else { //start of a new section
                    orderName = DEFAULT_ORDER_NAME;
                    oLine = -1;
                }

                //add section to section list
                Section s = new Section(filterName, fLine, orderName, oLine);
                sections.add(s);
            }
            return sections.toArray(new Section[]{});
        } catch (TypeIIError e) {
            throw new TypeIIError(e.getMessage());
        }
    }

    /**
     * validate first line, FILTER subsection
     *
     * @param line first line of section
     * @throws SubSectionNameException if != FILTER
     */
    private static void firstLine(int line) throws SubSectionNameException {
        String command = DirectoryProcessor.commands[line];
        if (!command.equals(FILTER)) {
            throw new SubSectionNameException("ERROR: bad sub-section name, problem with FILTER\n");
        }
    }

    /**
     * validate third line, ORDER subsection
     *
     * @param line third line of section
     * @throws SubSectionNameException if != ORDER
     */
    private static void thirdLine(int line) throws SubSectionNameException {
        String command = DirectoryProcessor.commands[line];
        if (!command.equals(ORDER)) {
            throw new SubSectionNameException("ERROR: bad sub-section name, problem with ORDER\n");
        }
    }

    /**
     * @param line requested line
     * @throws FormatException if line dont exist
     */
    private static void lineExists(int line) throws FormatException {
        if (DirectoryProcessor.commands.length <= line)
            throw new FormatException("ERROR: missing command");
    }

    /**
     *
     * @param line requested line
     * @return true if order name is specified, false otherwise
     */
    private static boolean orderExists(int line) {
        return DirectoryProcessor.commands.length > line && !DirectoryProcessor.commands[line].equals(FILTER);
    }
}