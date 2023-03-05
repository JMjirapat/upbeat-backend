package AST;

public class RelocateNode implements ExecNode {

    @Override
    public void executePlan(Game game) {
        game.getCurrentPlayer().relocate();
    }
}
