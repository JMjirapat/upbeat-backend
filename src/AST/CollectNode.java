package AST;

public class CollectNode implements PlanNode {

    private ExprNode expression;
    public CollectNode(ExprNode expression){
        this.expression = expression;
    }
    @Override
    public void executePlan(Game game) {
        game.getCurrentPlayer().collect(expression.eval(game.getIdentifier()));
    }
}
