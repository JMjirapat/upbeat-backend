package AST;

import Game.Direction;

public class MoveNode implements PlanNode {
    private Direction direction;
    public MoveNode(Direction direction){
        this.direction = direction;
    }

    @Override
    public void executePlan(Game game) {
        game.getCurrentPlayer().move(direction);
    }
}
