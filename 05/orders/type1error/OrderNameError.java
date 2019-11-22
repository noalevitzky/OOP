package orders.type1error;

import errors.TypeIError;

public class OrderNameError extends TypeIError {
    private static final long serialVersionUID = 1L;

    public OrderNameError(){
        super();
    }

    public OrderNameError(int i){
        super(i);
    }
}
