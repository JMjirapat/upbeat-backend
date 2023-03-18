package AST;

import AST.Node.ExprNode;
import Game.Game;

public class IntLit implements ExprNode {
    private final int val;
    public IntLit(int val) {
        this.val = val;
    }
    public long eval(Game game) {
        return val;
    }

    @Override
    public void prettyPrint(StringBuilder s) {
        s.append(val);
    }
}
