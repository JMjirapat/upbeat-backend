package AST;

import Game.Game;
import AST.Node.ExprNode;

public class OpponentNode implements ExprNode {
    @Override
    public long eval(Game game) {
        return game.getCurrentPlayer().opponent();
    }

    @Override
    public void prettyPrint(StringBuilder s) {

    }
}
