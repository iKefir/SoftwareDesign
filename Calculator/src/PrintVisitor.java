public class PrintVisitor implements TokenVisitor {
    StringBuffer result;

    PrintVisitor() {
        result = new StringBuffer();
    }

    String getResult() {
        return result.toString();
    }

    public void visit(NumberToken token) {
        result.append(token.value);
        result.append(" ");
    }

    public void visit(Brace token) {
        String toWrite = (token.myType == BraceType.LEFT ? "(" : ")");
        result.append(toWrite);
        result.append(" ");
    }

    public void visit(Operation token) {
        String toWrite = "";
        switch (token.myType) {
            case ADDITION:
                toWrite = "+";
                break;
            case SUBTRACTION:
                toWrite = "-";
                break;
            case MULTIPLICATION:
                toWrite = "*";
                break;
            case DIVISION:
                toWrite = "/";
                break;
        }
        result.append(toWrite);
        result.append(" ");
    }
}
