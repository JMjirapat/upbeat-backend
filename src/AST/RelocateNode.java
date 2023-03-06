package AST;

import Game.Game;
import AST.Node.ExecNode;
public class RelocateNode implements ExecNode {
    @Override
    public void execute(Game game) {
        game.getCurrentPlayer().relocate();
    }
}
