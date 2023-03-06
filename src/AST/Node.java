package AST;

import Game.Game;

public interface Node {
    interface ExecNode extends Node{
        void execute(Game game);
    }

    interface ExprNode extends Node{
        long eval(Game game);

        void prettyPrint(StringBuilder s);
    }
}

