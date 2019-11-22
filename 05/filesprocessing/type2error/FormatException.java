package filesprocessing.type2error;

import errors.TypeIIError;

public class FormatException extends TypeIIError {
    private static final long serialVersionUID = 1L;

    public FormatException(){
        super();
    }

    public FormatException(String s){
        super(s);
    }
}
