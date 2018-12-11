public class NumberState extends State {

    Integer acc;

    NumberState(Tokenizer tokenizer) {
        super(tokenizer);
        acc = 0;
    }

    void process(int curSymbol) {
        if (Character.isDigit((char) curSymbol)) {
            acc *= 10;
            acc += curSymbol - '0';
            tokenizer.next();
        } else {
            tokenizer.addToken(new NumberToken(acc));
            tokenizer.changeState(new StartState(tokenizer));
        }
    }
}
