package AST;

import java.util.Map;
import AST.ASTException.*;

public class ArithmeticEvaluator implements ExprNode {
    private ExprNode left, right;
    private String op;
    public ArithmeticEvaluator(
            ExprNode left, String op, ExprNode right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }
    public long eval(Game game) {
        long lv = left.eval(game.getIdentifier());
        long rv = right.eval(game.getIdentifier());
        if (op.equals("+")) return lv + rv;
        if (op.equals("*")) return lv * rv;
        if (op.equals("-")) return lv - rv;
        if (op.equals("/")) return lv / rv;
        if (op.equals("%")) return lv % rv;
        if (op.equals("^")) return lv % rv;
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
