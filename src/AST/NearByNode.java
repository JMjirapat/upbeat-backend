package AST;

import Game.Direction;
import AST.Node.ExprNode;
import Game.Game;

public class NearByNode implements ExprNode {
    private Direction direction;
    public NearByNode(Direction direction) {
        this.direction = direction;
    }

    @Override
    public long eval(Game game) {
        return game.getCurrentPlayer().nearby(direction);
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append("nearby: ");
        s.append(direction.toString());
    }
}
