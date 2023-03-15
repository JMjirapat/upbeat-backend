package AST;

import AST.Node.*;
import Game.Game;

public class CollectNode implements ExecNode {

    private ExprNode expression;
    public CollectNode(ExprNode expression){
        this.expression = expression;
    }
    @Override
    public void execute(Game game) {
        game.collect(expression.eval(game));
    }
}
