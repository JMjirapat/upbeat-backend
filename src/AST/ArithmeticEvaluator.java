package AST;

import java.util.Map;

public class ArithmeticEvaluator implements ExprNode {
    private ExprNode left, right;
    private String op;
    public ArithmeticEvaluator(
            ExprNode left, String op, ExprNode right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }
    public long eval(Map<String, Integer> bindings) throws EvalError {
        long lv = left.eval(bindings);
        long rv = right.eval(bindings);
        if (op.equals("+")) return lv + rv;
        if (op.equals("*")) return lv * rv;
        if (op.equals("-")) return lv - rv;
        if (op.equals("/")) return lv / rv;
        if (op.equals("%")) return lv % rv;
        throw new EvalError("unknown op: " + op);
    }
    public void prettyPrint(StringBuilder s) {
        s.append("(");
        left.prettyPrint(s);
        s.append(op);
        right.prettyPrint(s);
        s.append(")");
    }
}
