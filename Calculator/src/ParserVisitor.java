import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParserVisitor implements TokenVisitor {
    private Stack<Token> acc;
    private List<Token> result;

    ParserVisitor() {
        acc = new Stack<>();
        result = new ArrayList<>();
    }

    List<Token> getResult() {
        while (!acc.empty()) {
            result.add(acc.pop());
        }

        return result;
    }

    public void visit(NumberToken token) {
        result.add(token);
    }

    public void visit(Brace token) {
        if (token.myType == BraceType.LEFT) {
            acc.push(token);
        }
        if (token.myType == BraceType.RIGHT) {
            while (!(acc.empty() || (acc.peek() instanceof Brace && ((Brace) acc.peek()).myType == BraceType.LEFT))) {
                result.add(acc.pop());
            }
        }
    }

    public void visit(Operation token) {
        while (!acc.empty() && (acc.peek() instanceof Operation && ((Operation) acc.peek()).priority <= token.priority)) {
            result.add(acc.pop());
        }
        acc.push(token);
    }
}
