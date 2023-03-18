package AST;

import Game.Game;
import AST.ASTException.*;
import AST.Node.ExprNode;

public class ArithmeticEvaluator implements ExprNode {
    private final ExprNode left, right;
    private final String op;
    public ArithmeticEvaluator(
            ExprNode left, String op, ExprNode right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }
    public long eval(Game game) {
        long lv = left.eval(game);
        long rv = right.eval(game);
        if (op.equals("+")) return lv + rv;
        if (op.equals("*")) return lv * rv;
        if (op.equals("-")) return lv - rv;
        if (op.equals("/")) return lv / rv;
        if (op.equals("%")) return lv % rv;
        if (op.equals("^")) return lv ^ rv;
        throw new UnknownOperator(op);
    }
    public void prettyPrint(StringBuilder s) {
        s.append("(");
        left.prettyPrint(s);
        s.append(op);
        right.prettyPrint(s);
        s.append(")");
    }
}
