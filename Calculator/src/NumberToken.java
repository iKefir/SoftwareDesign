public class NumberToken implements Token {

    Integer value;

    NumberToken(Integer other) {
        value = other;
    }

    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
