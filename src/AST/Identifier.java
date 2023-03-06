package AST;

import AST.ASTException.*;
import AST.Node.ExprNode;
import Game.Game;

public class Identifier implements ExprNode {
    private String name;
    public Identifier(String name) {
        this.name = name;
    }
    public long eval(Game game) {
        if (game.getIdentifier().containsKey(name))
            return game.getIdentifier().get(name);
        throw new UnknownVariable(name);
    }

    @Override
    public void prettyPrint(StringBuilder s) {

    }

}
