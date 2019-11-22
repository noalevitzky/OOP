package filesprocessing.type2error;

import errors.TypeIIError;

public class UsageException extends TypeIIError {
    private static final long serialVersionUID = 1L;

    public UsageException(){
        super();
    }

    public UsageException(String s){
        super(s);
    }
}
