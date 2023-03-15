package AST;

import AST.Node.ExecNode;
import Game.Game;

public class DoneNode implements ExecNode {

    @Override
    public void execute(Game game) {
        game.done();
    }
}
