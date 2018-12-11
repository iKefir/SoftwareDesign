public abstract class State {
    Tokenizer tokenizer;

    State(Tokenizer newTokenizer) {
        tokenizer = newTokenizer;
    }

    abstract void process(int curSymbol);
}
