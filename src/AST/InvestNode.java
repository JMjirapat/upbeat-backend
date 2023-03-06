package AST;
import AST.Node.*;
import Game.Game;

public class InvestNode implements ExecNode {
    private ExprNode expression;
    public InvestNode(ExprNode expression){
        this.expression = expression;
    }
    @Override
    public void execute(Game game) {
        game.getCurrentPlayer().invest(expression.eval(game));
    }
}
