package Tokenizer;

public interface Tokenizer {
    /** Returns true if there is
     *  more token */
    boolean hasNextToken();

    /** Returns the next token
     *  in the input stream. */
    String peek();
    boolean peek(String s);

    /** Consumes the next token
     *  from the input stream
     *  and returns it.
     *  effects: removes the next token
     *           from the input stream */
    String consume() throws LexicalError;
    void consume(String s) throws SyntaxError, LexicalError;
}

