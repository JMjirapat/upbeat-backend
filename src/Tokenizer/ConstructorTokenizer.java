package Tokenizer;
import java.util.ArrayList;
import java.util.List;

import Tokenizer.TokenizerException.*;

public class ConstructorTokenizer implements Tokenizer{

    private String src;
    private String next;
    private int pos;
    private int line;
    private List<String> lines;

    public ConstructorTokenizer(List<String> lines) {
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
        if (!hasNextToken()) throw new NotHaveToken();
        return next;
    }

    @Override
    public boolean peek(String s) {
        if (!hasNextToken()) return false;
        return next.equals(s);
    }

    @Override
    public String consume() {
        if (!hasNextToken()) throw new NotHaveToken();
        String result = next;
        computeNext();
        return result;
    }

    @Override
    public void consume(String s) {
        if (peek(s)){
            consume();
        } else if(hasNextToken())
            throw new NotExpectedCharacter(s,peek(),getLine());
        else
            throw new NotExpectedCharacter(s,getLine());
    }

    private void computeNext() {
        StringBuilder s = new StringBuilder();
        if (pos == src.length()) {
            if((lines.size()-1) == line){
                next = null;
                return;
            }else {
                pos = 0;
                line++;
                this.src = lines.get(line);
                //computeNext();
            }
        }
        while (pos < src.length() && (isSpace(src.charAt(pos))))
            pos++;  // ignore whitespace
        if (pos == src.length()) {
            if((lines.size()-1) == line){
                next = null;
                return;
            }else {
                pos = 0;
                line++;
                this.src = lines.get(line);
                //computeNext();
            }
        }  // no more tokens
        char c = src.charAt(pos);
        if (isDigit(c)) {  // start of number
            s.append(c);
            for (pos++; pos < src.length() &&
                    isDigit(src.charAt(pos)); pos++)
                s.append(src.charAt(pos));
        } else if(isLetter(c) || isUnderscore(c)){ // start with ( letter or _ )
            s.append(c);
            for (pos++; pos < src.length() &&
                    (canContainInVariable(src.charAt(pos))); pos++)
                s.append(src.charAt(pos));
        }else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '(' || c == ')' || c == '{' || c == '}' || c == '^' || c == '=') {
            s.append(c);  pos++;
        }else throw new LexicalError(c,getLine());
        next = s.toString();
    }

    @Override
    public int getLine(){
        int indexOffset = 1;
        return line + indexOffset;
    }
    public static boolean isSpace(char c){
        return (c == ' ');
    }

    public static boolean isUnderscore(char c){
        return c == '_';
    }

    public static boolean isDigit(char c){
        return Character.isDigit(c);
    }

    public static boolean isLetter(char c){
        return Character.isLetter(c);
    }

    public static boolean canContainInVariable(char c){
        return (isDigit(c) || isLetter(c) || isUnderscore(c));
    }
}
