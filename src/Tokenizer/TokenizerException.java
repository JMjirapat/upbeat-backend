package Tokenizer;

public class TokenizerException extends RuntimeException{
    protected TokenizerException(String msg){
        super(msg);
    }

    public static class LexicalError extends TokenizerException{
        public LexicalError(char c, int line){
            super("Character '" + c + "' is unknown at line " + line);
        }
    }

    public static class NotExpectedCharacter extends TokenizerException{

        public NotExpectedCharacter(String expect, int line){
            super("Expected '" + expect + "' but no more tokens at line" + line);
        }
        public NotExpectedCharacter(String expect, String got, int line){
            super("Expected '" + expect + "' but got '" + got + "' at line" + line);
        }
    }

    public static class NotHaveToken extends TokenizerException {
        public NotHaveToken() {
            super("Not have token to proceed");
        }
    }
}
