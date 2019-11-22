package errors;

/**
 * warnings due to a bad section structure (i.e bad subsection, illegal params).
 * should not stop the program, and print message before program's output.
 */
public class TypeIError extends Exception {
    private static final long serialVersionUID = 1L;

    public TypeIError() {
        super("Warning: general type I error.");
    }

    public TypeIError(int i) {
        super("Warning in line " + i);
    }
}