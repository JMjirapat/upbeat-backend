package AST;

import java.util.Map;

public class Identifier implements ExprNode {
    private String name;
    public Identifier(String name) {
        this.name = name;
    }
    public long eval(
            Map<String, Integer> bindings) throws EvalError {
        if (bindings.containsKey(name))
            return bindings.get(name);
        throw new EvalError("undefined variable: " + name);
    }

}
