package AST;

import java.util.Map;

public class IntLit implements ExprNode {
    private int val;
    public IntLit(int val) {
        this.val = val;
    }
    public long eval(Map<String, Integer> bindings) {
        return val;
    }
}
