package AST;

import AST.ASTException.*;
import AST.Node.ExprNode;
import Game.Game;

import java.util.HashMap;

public class Identifier implements ExprNode {
    private final String name;
    public Identifier(String name) {
        this.name = name;
    }
    public long eval(Game game) {
        HashMap<String, Long> identifiers = game.getIdentifiers();
        if (identifiers.containsKey(name))
            return identifiers.get(name);
        throw new UnknownVariable(name);
    }
    @Override
    public void prettyPrint(StringBuilder s) {
        s.append(name);
    }
}
