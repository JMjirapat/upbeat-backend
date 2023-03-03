package AST;

public class WhileEvaluator implements PlanNode {
    private ExprNode expression;
    private PlanNode statement;

    private int iteratorCount = 0;

    public WhileEvaluator(ExprNode expr, PlanNode s){
        this.expression = expr;
        this.statement = s;
    }
    @Override
    public void executePlan(Game game) {
        boolean condition = (new BooleanEvaluator(expression)).execute(game);
        while(condition && iteratorCount <10000){
            statement.executePlan(game);
            iteratorCount++;
        }
    }
}
