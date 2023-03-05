package AST;

import java.util.ArrayList;

public class StatementsEvaluator implements ExecNode {
    ArrayList<ExecNode> statements;
    public StatementsEvaluator(ArrayList<ExecNode> statements){
        this.statements = statements;
    }

    public void executePlan(Game game){
        statements.stream().forEach(stm -> stm.executePlan(game));
    }
}
