package AST;

public class IfElseEvaluator implements PlanNode {
    private ExprNode expression;
    private PlanNode TrueStatement;
    private PlanNode FalseStatement;

    public IfElseEvaluator(ExprNode expr, PlanNode ts, PlanNode fs){
        this.expression = expr;
        this.TrueStatement = ts;
        this.FalseStatement = fs;
    }
    @Override
    public void executePlan(Game game) {
        boolean condition = (new BooleanEvaluator(expression)).execute(game);
        if(condition){
            TrueStatement.executePlan(game);
        }else{
            FalseStatement.executePlan(game);
        }
    }
}
