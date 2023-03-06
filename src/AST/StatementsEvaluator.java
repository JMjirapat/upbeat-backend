package AST;

import AST.Node.ExecNode;
import Game.*;

import java.util.ArrayList;

public class StatementsEvaluator implements ExecNode {
    ArrayList<ExecNode> statements;
    public StatementsEvaluator(ArrayList<ExecNode> statements){
        this.statements = statements;
    }

    public void execute(Game game){
        statements.stream().forEach(stm -> stm.execute(game));
    }
}
