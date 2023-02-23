import java.util.NoSuchElementException;

public class ConstructorTokenizer implements Tokenizer{

    private String src, next;  private int pos;

    ConstructorTokenizer(String src) throws LexicalError {
        this.src = src;  pos = 0;
        computeNext();
    }
    @Override
    public boolean hasNextToken() {
        return next != null;
    }

    @Override
    public String peek() {
        if (!hasNextToken()) throw new NoSuchElementException("no more tokens");
        return next;
    }

    @Override
    public boolean peek(String s) {
        if (!hasNextToken()) return false;
        return peek().equals(s);
    }

    @Override
    public String consume() throws LexicalError {
        if (!hasNextToken()) throw new NoSuchElementException("no more tokens");
        String result = next;
        computeNext();
        return result;
    }

    @Override
    public void consume(String s) throws SyntaxError, LexicalError {
        if (peek(s)){
            consume();
        } else
            throw new SyntaxError(s + " expected");
    }

    private void computeNext() throws LexicalError {
        StringBuilder s = new StringBuilder();
        while (pos < src.length() && isSpace(src.charAt(pos)))
            pos++;  // ignore whitespace
        if (pos == src.length())
        { next = null;  return; }  // no more tokens
        char c = src.charAt(pos);
        if (isDigit(c)) {  // start of number
            s.append(c);
            for (pos++; pos < src.length() &&
                    isDigit(src.charAt(pos)); pos++)
                s.append(src.charAt(pos));
        } else if(isChar(c) || isUnderscore(c)){
            s.append(c);
            for (pos++; pos < src.length() &&
                    (isChar(src.charAt(pos)) || isUnderscore(src.charAt(pos))); pos++)
                s.append(src.charAt(pos));
        }else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '(' || c == ')' || c == '{' || c == '}' || c == '^' || c == '=') {
            s.append(c);  pos++;
        }else throw new LexicalError("unknown character: " + c);
        next = s.toString();
    }

    public static boolean isSpace(char c){
        return c == ' ';
    }

    public static boolean isUnderscore(char c){
        return c == '_';
    }

    public static boolean isDigit(char c){
        return (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9');
    }

    public static boolean isChar(char c){
        return (c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k' || c == 'l' || c == 'm'
                || c == 'n' || c == 'o' || c == 'p' || c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v' || c == 'w' || c == 'x' || c == 'y' || c == 'z' ||
                c == 'A' || c == 'B' || c == 'C' || c == 'D' || c == 'E' || c == 'F' || c == 'G' || c == 'H' || c == 'I' || c == 'J' || c == 'K' || c == 'L' || c == 'M'
                || c == 'N' || c == 'O' || c == 'P' || c == 'Q' || c == 'R' || c == 'S' || c == 'T' || c == 'U' || c == 'V' || c == 'W' || c == 'X' || c == 'Y' || c == 'Z');
    }
}
