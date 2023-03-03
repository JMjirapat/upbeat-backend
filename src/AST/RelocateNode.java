package AST;

public class RelocateNode implements PlanNode {

    @Override
    public void executePlan(Game game) {
        game.getCurrentPlayer().relocate();
    }
}
