package filesprocessing.type2error;

import errors.TypeIIError;

public class SubSectionNameException extends TypeIIError {
    private static final long serialVersionUID = 1L;

    public SubSectionNameException(){super();}

    public SubSectionNameException(String s){
        super(s);
    }
}
