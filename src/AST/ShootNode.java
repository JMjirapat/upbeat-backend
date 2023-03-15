package AST;

import Game.Direction;
import Game.Game;
import AST.Node.ExecNode;

public class ShootNode implements ExecNode {

    private final Direction direction;
    private final ExprNode expression;

    public ShootNode(Direction direction, ExprNode expression){
        this.direction = direction;
        this.expression = expression;
    }
    @Override
    public void execute(Game game) {
        game.shoot(direction,expression.eval(game));
    }
}
