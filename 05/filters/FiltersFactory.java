package filters;

import filesprocessing.Toolbox;
import errors.TypeIError;
import filters.type1error.*;

import java.util.Arrays;

public class FiltersFactory {

    //legal string values for writable/executable/hidden filters.
    private String[] legalValues = {"YES", "NO"};

    //legal suffix
    private static final String NOT = "NOT";

    private Filter filter;

    public Filter createFilter(String s, int line) throws TypeIError {
        //verify params & create filter
        verifyFilter(s, line);
        return this.filter;
    }

    /**
     * creates a filter based on given command,
     *
     * @param command contains requested filter and needed values
     * @param line    current command line
     * @throws FilterNameError    filter name is illegal
     * @throws ParamsStringError  String values are illegal (!= YES/NO)
     * @throws ParamsDoubleError  negative doubles
     * @throws ParamsBetweenError second param is smaller then first
     */
    private void verifyFilter(String command, int line)
            throws FilterNameError, ParamsStringError, ParamsDoubleError, ParamsBetweenError {
        //split command into a filter and it's params (if exist)
        String[] params = Toolbox.splitString(command);
        String filterName = params[0];
        String v1 = (params.length >= 2) ? params[1] : null;
        String v2 = (params.length >= 3) ? params[2] : null;

        if (filterName == null)
            return;

        //create filter, or throws error
        switch (filterName) {
            case "greater_than":
                double g1 = Double.parseDouble(v1);
                verifyDouble(g1, v2, line);
                filter = new GreaterThanFilter(Toolbox.convert2byte(g1));
                break;
            case "between":
                double b1 = Double.parseDouble(v1);
                double b2 = Double.parseDouble(v2);
                verify2doubles(b1, b2, line);
                filter = new BetweenFilter(Toolbox.convert2byte(b1), Toolbox.convert2byte(b2));
                break;
            case "smaller_than":
                double s1 = Double.parseDouble(v1);
                verifyDouble(s1, v2, line);
                filter = new SmallerThanFilter(Toolbox.convert2byte(s1));
                break;
            case "file":
                filter = new FileFilter(v1);
                break;
            case "contains":
                filter = new ContainsFilter(v1);
                break;
            case "prefix":
                filter = new PrefixFilter(v1);
                break;
            case "suffix":
                filter = new SuffixFilter(v1);
                break;
            case "writable":
                verifyString(v1, v2, line);
                filter = new WritableFilter(v1);
                break;
            case "executable":
                verifyString(v1, v2, line);
                filter = new ExecutableFilter(v1);
                break;
            case "hidden":
                verifyString(v1, v2, line);
                filter = new HiddenFilter(v1);
                break;
            case "all":
                filter = new AllFilter();
                break;
            default:
                throw new FilterNameError(line);
        }
        //apply NOT decorator if needed
        if (params[params.length - 1].equals(NOT))
            filter = new NotDecoratorFilter(filter);
    }

    /**
     * @return default filter
     */
    public Filter createDefault() {
        return new AllFilter();
    }

    /**
     * relevant to filters with 1 string parameter
     *
     * @param v1   should be a legal string
     * @param v2   should be null/NOT
     * @param line line where error might occur
     * @throws ParamsStringError if params are illegal
     */
    private void verifyString(String v1, String v2, int line) throws ParamsStringError {
        if (!Arrays.asList(legalValues).contains(v1) || (v2 != null && !v2.equals(NOT)))
            throw new ParamsStringError(line);
    }

    /**
     * relevant to filter with only 1 double parameter
     *
     * @param v1   should be non-negative
     * @param v2   should be null/NOT
     * @param line line where error might occur
     * @throws ParamsDoubleError if v1 is illegal
     * @throws ParamsStringError if v2 is illegal
     */
    private void verifyDouble(double v1, String v2, int line) throws ParamsDoubleError, ParamsStringError {
        if (v1 < 0)
            throw new ParamsDoubleError(line);
        else if (v2 != null && !v2.equals(NOT))
            throw new ParamsStringError(line);
    }

    /**
     * relevant to filter with 2 double parameter (between filter only)
     *
     * @param v1   should be non-negative
     * @param v2   should be non-negative and greater then v1
     * @param line line where error might occur
     * @throws ParamsDoubleError  if params are illegal
     * @throws ParamsBetweenError if v2 smaller than v1
     */
    private void verify2doubles(double v1, double v2, int line) throws ParamsDoubleError, ParamsBetweenError {
        if (v1 > v2)
            throw new ParamsBetweenError(line);
        if (v1 < 0 || v2 < 0)
            throw new ParamsDoubleError(line);
    }
}


