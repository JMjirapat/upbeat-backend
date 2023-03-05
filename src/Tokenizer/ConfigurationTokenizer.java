package Tokenizer;

public class ConfigurationTokenizer implements Tokenizer{
    @Override
    public boolean hasNextToken() {
        return false;
    }

    @Override
    public String peek() {
        return null;
    }

    @Override
    public boolean peek(String s) {
        return false;
    }

    @Override
    public String consume() {
        return null;
    }

    @Override
    public void consume(String s) {

    }

    @Override
    public int getLine() {
        return 0;
    }
}
