package AST;

public class BooleanEvaluator {

    private ExprNode expression;

    BooleanEvaluator(ExprNode expression){
        this.expression = expression;
    }

    public boolean execute(Game game) {
        if(expression.eval(game.getIdentifier()) > 0){
            return true;
        }else{
            return false;
        }
    }
}
