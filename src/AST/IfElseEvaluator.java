package AST;

import AST.Node.*;
import Game.Game;

public class IfElseEvaluator implements ExecNode {
    private ExprNode expression;
    private ExecNode TrueStatement;
    private ExecNode FalseStatement;

    public IfElseEvaluator(ExprNode expr, ExecNode ts, ExecNode fs){
        this.expression = expr;
        this.TrueStatement = ts;
        this.FalseStatement = fs;
    }
    @Override
    public void execute(Game game) {
        boolean condition = (new BooleanEvaluator(expression)).execute(game);
        if(condition){
            TrueStatement.execute(game);
        }else{
            FalseStatement.execute(game);
        }
    }
}
