package AST;

public class DoneNode implements ExecNode {

    @Override
    public void executePlan(Game game) {
        game.EndTurn();
    }
}
