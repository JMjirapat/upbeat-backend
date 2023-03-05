package AST;

public class ASTException extends RuntimeException{
    protected ASTException(String msg){
        super(msg);
    }

    public static class UnknownOperator extends ASTException {
        protected UnknownOperator(String msg) {
            super(msg);
        }
    }
}
