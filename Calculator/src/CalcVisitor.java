import java.util.Stack;

public class CalcVisitor implements TokenVisitor {
    Stack<Integer> acc;

    CalcVisitor() {
        acc = new Stack<>();
    }

    Integer getResult() {
        return acc.peek();
    }

    public void visit(NumberToken token) {
        acc.push(token.value);
    }

    public void visit(Brace token) {

    }

    public void visit(Operation token) {
        Integer first, second;
        second = acc.pop();
        first = acc.pop();
        switch (token.myType) {
            case ADDITION:
                acc.push(first + second);
                break;
            case SUBTRACTION:
                acc.push(first - second);
                break;
            case MULTIPLICATION:
                acc.push(first * second);
                break;
            case DIVISION:
                acc.push(first / second);
                break;
        }
    }
}
