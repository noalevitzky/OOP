package filters.type1error;

import errors.TypeIError;

public class ParamsDoubleError extends TypeIError {
    private static final long serialVersionUID = 1L;

    public ParamsDoubleError(){
        super();
    }

    public ParamsDoubleError(int i){
        super(i);
    }
}
