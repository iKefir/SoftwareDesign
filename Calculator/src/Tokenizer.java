import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private State curState;
    private Boolean readNext;

    List<Token> tokens;

    Tokenizer() {
        curState = new StartState(this);
        tokens = new ArrayList<>();
        readNext = true;
    }

    void changeState(State other) {
        curState = other;
        readNext = false;
    }

    void addToken(Token t) {
        tokens.add(t);
        next();
    }

    void next() {
        readNext = true;
    }

    List<Token> tokenize(InputStream is) throws IOException {
        int ch = 0;
        while (!(curState instanceof EndState || curState instanceof ErrorState)) {
            if (readNext) {
                ch = is.read();
            }
            curState.process(ch);
        }

        if (curState instanceof ErrorState) {
            throw new IOException("Unexpected token " + (char)((ErrorState) curState).value);
        }

        return tokens;
    }
}
