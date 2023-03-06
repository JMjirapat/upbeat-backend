package AST;

import Game.Direction;
import Game.Game;
import AST.Node.ExecNode;

public class AttackNode implements ExecNode {

    private final Direction direction;
    private final ExprNode expression;

    public AttackNode(Direction direction, ExprNode expression){
        this.direction = direction;
        this.expression = expression;
    }
    @Override
    public void execute(Game game) {
        long value = expression.eval(game);
        game.getCurrentPlayer().attack(direction,value);
    }
}
