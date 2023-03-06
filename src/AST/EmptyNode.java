package AST;

import AST.Node.ExecNode;
import Game.Game;
public class EmptyNode implements ExecNode {
    @Override
    public void execute(Game game) {
    }
}
