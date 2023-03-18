package AST;

import Game.Direction;
import Game.Game;
import AST.Node.ExecNode;

public class MoveNode implements ExecNode {
    private final Direction direction;
    public MoveNode(Direction direction){
        this.direction = direction;
    }

    @Override
    public void execute(Game game) {
        game.move(direction);
    }
}
