package filesprocessing.type2error;

import errors.TypeIIError;

public class IOFileException extends TypeIIError {
    private static final long serialVersionUID = 1L;

    public IOFileException(){
        super();
    }

    public IOFileException(String s){
        super(s);
    }
}
