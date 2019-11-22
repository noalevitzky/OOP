package orders;

import filesprocessing.Toolbox;
import orders.type1error.OrderNameError;

import java.io.File;
import java.util.Comparator;

public class OrdersFactory {

    private static final String REVERSE = "REVERSE";
    private Comparator<File> order;

    /**
     * @param s    order name
     * @param line line of command in command file
     * @return file Comparator based on command
     */
    public Comparator<File> createOrder(String s, int line) throws OrderNameError{
        //verify params & create order
        verifyOrder(s, line);
        return this.order;
    }

    /**
     * creates a filter based on given command,
     *
     * @param command contains requested filter and needed values
     * @param line    current command line
     * @throws OrderNameError order name is illegal
     */
    private void verifyOrder(String command, int line) throws OrderNameError {
        //split command into an order and it's param (if exist)
        String[] params = Toolbox.splitString(command);
        String orderName = params[0];
        boolean reverse = (params.length == 2) && params[1].equals(REVERSE);

        if (orderName == null)
            return;

        //create order and verify params
        switch (orderName) {
            case "abs":
                order = new AbsOrder();
                break;
            case "type":
                order = new TypeOrder();
                break;
            case "size":
                order = new SizeOrder();
                break;
            default:
                throw new OrderNameError(line);
        }
        //apply decorator if needed
        if (reverse)
            order = new ReverseDecoratorOrder(order);
    }

    /**
     * @return default order
     */
    public Comparator<File> createDefault() {
        return new AbsOrder();
    }
}
