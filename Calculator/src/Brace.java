public class Brace implements Token {

    BraceType myType;

    Brace(BraceType t) {
        myType = t;
    }

    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
