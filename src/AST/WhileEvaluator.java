package AST;

import AST.Node.ExecNode;
import Game.Game;

public class WhileEvaluator implements ExecNode {
    private ExprNode expression;
    private ExecNode statement;

    private int iteratorCount = 0;

    public WhileEvaluator(ExprNode expr, ExecNode s){
        this.expression = expr;
        this.statement = s;
    }
    @Override
    public void execute(Game game) {
        boolean condition = (new BooleanEvaluator(expression)).execute(game);
        while(condition && iteratorCount <10000){
            statement.execute(game);
            iteratorCount++;
        }
    }
}
