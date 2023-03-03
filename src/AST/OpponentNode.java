package AST;

import Game.Game;

import java.util.Map;

public class OpponentNode implements ExprNode {
    @Override
    public long eval(Game gmae) throws EvalError {
        return game.getCurrentPlayer().opponent();
    }
}
