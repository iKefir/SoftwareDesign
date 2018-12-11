public class StartState extends State {

    StartState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    void process(int curSymbol) {
        if (Character.isDigit((char) curSymbol)) {
            tokenizer.changeState(new NumberState(tokenizer));
        } else {
            switch (curSymbol) {
                case '+':
                    tokenizer.addToken(new Operation(OperationType.ADDITION));
                    break;
                case '-':
                    tokenizer.addToken(new Operation(OperationType.SUBTRACTION));
                    break;
                case '*':
                    tokenizer.addToken(new Operation(OperationType.MULTIPLICATION));
                    break;
                case '/':
                    tokenizer.addToken(new Operation(OperationType.DIVISION));
                    break;
                case '(':
                    tokenizer.addToken(new Brace(BraceType.LEFT));
                    break;
                case ')':
                    tokenizer.addToken(new Brace(BraceType.RIGHT));
                    break;
                case -1:
                    tokenizer.changeState(new EndState(tokenizer));
                default:
                    tokenizer.changeState(new ErrorState(tokenizer, curSymbol));
            }
        }
    }
}
