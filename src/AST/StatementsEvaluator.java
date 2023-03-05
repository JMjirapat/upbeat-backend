package AST;

import java.util.ArrayList;

public class StatementsEvaluator implements PlanNode{
    ArrayList<PlanNode> statements;
    public StatementsEvaluator(ArrayList<PlanNode> statements){
        this.statements = statements;
    }

    public void executePlan(Game game){
        statements.stream().forEach(stm -> stm.executePlan(game));
    }
}
