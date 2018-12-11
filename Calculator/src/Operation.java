public class Operation implements Token {

    OperationType myType;
    int priority;

    Operation(OperationType t) {
        myType = t;
        switch (myType) {
            case MULTIPLICATION:
                priority = 1;
                break;
            case DIVISION:
                priority = 1;
                break;
            case ADDITION:
                priority = 2;
                break;
            case SUBTRACTION:
                priority = 2;
                break;
            default:
                priority = 10;
        }
    }

    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
