import java.util.Map;

interface ExprNode extends Node{
    int eval(Map<String, Integer> bindings) throws EvalError;
}
