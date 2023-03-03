package AST;

import Game.Direction;

public class NearByNode implements ExprNode {
    private Direction direction;
    public NearByNode(Direction direction) {
        this.direction = direction;
    }

    @Override
    public long eval(Game game) throws EvalError {
        return game.getCurrentPlayer().nearby(direction);
    }
}
