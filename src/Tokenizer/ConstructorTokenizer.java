package Tokenizer;

import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class ConstructorTokenizer implements Tokenizer{

    private String src, next;  private int pos,line;
    private ArrayList<String> lines;

    public static Predicate<Character> isSpace = c -> c == ' ';
    public static Predicate<Character> isUnderscore = c -> c == '_';
    public static Predicate<Character> isDigit = c -> Character.isDigit(c);
    public static Predicate<Character> isChar = c -> Character.isLetter(c);

    ConstructorTokenizer(ArrayList<String> lines) throws LexicalError {
        this.lines = new ArrayList<>(lines);
        pos = 0;
        line = 0;
        if(this.lines.isEmpty()){
            next = null;
        }else {
            this.src = this.lines.get(line);
            computeNext();
        }
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
        while (pos < src.length() && isSpace.test(src.charAt(pos)))
            pos++;  // ignore whitespace
        if (pos == src.length()) {
            if((lines.size()-1) == line){
                next = null;
                return;
            }else {
                pos = 0;
                line++;
                this.src = lines.get(line);
                computeNext();
            }
        }  // no more tokens
        char c = src.charAt(pos);
        if (isDigit.test(c)) {  // start of number
            s.append(c);
            for (pos++; pos < src.length() &&
                    isDigit.test(src.charAt(pos)); pos++)
                s.append(src.charAt(pos));
        } else if(isChar.test(c) || isUnderscore.test(c)){
            s.append(c);
            for (pos++; pos < src.length() &&
                    (isChar.test(src.charAt(pos)) || isUnderscore.test(src.charAt(pos))); pos++)
                s.append(src.charAt(pos));
        }else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '(' || c == ')' || c == '{' || c == '}' || c == '^' || c == '=') {
            s.append(c);  pos++;
        }else throw new LexicalError("unknown character: " + c);
        next = s.toString();
    }

}
