package AST;

import AST.Node.ExprNode;
import Game.Game;

public class BooleanEvaluator {

    private ExprNode expression;

    BooleanEvaluator(ExprNode expression){
        this.expression = expression;
    }

    public boolean execute(Game game) {
        if(expression.eval(game) > 0){
            return true;
        }else{
            return false;
        }
    }
}
