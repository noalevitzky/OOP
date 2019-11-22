package errors;

/**
 * exceptions due to illegal usage (i.e when section instructions are illegal, invalid arguments).
 * should stop the program, and print message without program's output.
 */
public class TypeIIError extends Exception {
    private static final long serialVersionUID = 1L;

    public TypeIIError() {
        super("ERROR: general type II error.");
    }

    public TypeIIError(String s) {
        super(s);
    }
}
