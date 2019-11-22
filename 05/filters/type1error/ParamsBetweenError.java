package filters.type1error;

import errors.TypeIError;

public class ParamsBetweenError extends TypeIError {
    private static final long serialVersionUID = 1L;

    public ParamsBetweenError(){
        super();
    }

    public ParamsBetweenError(int i){
        super(i);
    }
}
