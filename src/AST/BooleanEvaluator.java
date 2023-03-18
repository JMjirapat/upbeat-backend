package AST;

import AST.Node.ExprNode;
import Game.Game;

public class BooleanEvaluator {

    private final ExprNode expression;

    BooleanEvaluator(ExprNode expression){
        this.expression = expression;
    }

    public boolean execute(Game game) {
        return expression.eval(game) > 0;
    }
}
