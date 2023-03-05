package AST;

public class InvestNode implements ExecNode {
    private ExprNode expression;
    public InvestNode(ExprNode expression){
        this.expression = expression;
    }
    @Override
    public void executePlan(Game game) {
        game.getCurrentPlayer().invest(expression.eval(game.getIdentifier()));
    }
}
