package filters.type1error;

import errors.TypeIError;

public class ParamsStringError extends TypeIError {
    private static final long serialVersionUID = 1L;

    public ParamsStringError(){
        super();
    }
    public ParamsStringError(int i){
        super(i);
    }
}
