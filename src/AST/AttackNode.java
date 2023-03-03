package AST;

import Game.Direction;

public class AttackNode implements PlanNode {

    private final Direction direction;
    private final ExprNode expression;

    public AttackNode(Direction direction, ExprNode expression){
        this.direction = direction;
        this.expression = expression;
    }
    @Override
    public void executePlan(Game game) {
        game.getCurrentPlayer().attack(direction,expression);
    }
}
