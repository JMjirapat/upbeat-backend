package AST;

import java.util.Arrays;
import java.util.List;
import Game.Game;
import AST.Node.ExecNode;

public class AssignmentNode implements ExecNode {
    private final String identifier;
    private final ExprNode expression;

    private final List<String> special = Arrays.asList("rows", "cols", "currow", "curcol", "budget", "deposit", "int", "maxdeposit", "random");
    public AssignmentNode(String identifier, ExprNode expression){
        this.identifier = identifier;
        this.expression = expression;
    }

    @Override
    public void execute(Game game) {
        if(special.contains(identifier)){
            return;
        }
        game.assign(identifier,expression.eval(game));
    }
}
