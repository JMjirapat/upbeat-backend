package AST;

import java.util.Map;

public interface ExprNode extends Node{
    long eval(Game game);

    void prettyPrint(StringBuilder s);
}
