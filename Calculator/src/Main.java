import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Tokenizer tokenizer = new Tokenizer();

        String exampleString = "1+2/3*14-4";
        InputStream stream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));
        List<Token> result = tokenizer.tokenize(stream);

        ParserVisitor visitor = new ParserVisitor();

        for (Token t : result) {
            t.accept(visitor);
        }

        List<Token> RPN = visitor.getResult();

        PrintVisitor printVisitor = new PrintVisitor();

        CalcVisitor calcVisitor = new CalcVisitor();
        for (Token t : RPN) {
            t.accept(printVisitor);
            t.accept(calcVisitor);
        }

        String printed = printVisitor.getResult();
        Integer calculated = calcVisitor.getResult();

        System.out.println(printed);
        System.out.println(calculated);
    }
}
