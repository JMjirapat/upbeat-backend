package AST;

import Game.Game;
import AST.Node.ExprNode;

public class OpponentNode implements ExprNode {
    @Override
    public long eval(Game game) {
        return (long) game.getCurrentPlayer().opponent(game.getTerritory());
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append("opponent");
    }
}
