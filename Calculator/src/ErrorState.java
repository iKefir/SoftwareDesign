public class ErrorState extends State {
    int value;

    ErrorState(Tokenizer newTokenizer, int symbol) {
        super(newTokenizer);
        value = symbol;
    }

    void process(int curSymbol) {

    }
}
