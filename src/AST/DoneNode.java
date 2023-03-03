package AST;

public class DoneNode implements PlanNode {

    @Override
    public void executePlan(Game game) {
        game.EndTurn();
    }
}
