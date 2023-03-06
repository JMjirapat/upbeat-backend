package AST;

public class ASTException extends RuntimeException{
    protected ASTException(String msg){
        super(msg);
    }

    public static class UnknownOperator extends ASTException {
        protected UnknownOperator(String op) {
            super("Operator '" + op + "' is unknown operator");
        }
    }

    public static class UnknownVariable extends ASTException {
        protected UnknownVariable(String var) {
            super("Variable '" + var + "' is unknown variable");
        }
    }
}
