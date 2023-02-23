import java.util.Map;

interface Expr extends Node {
    int eval(Map<String, Integer> bindings) throws EvalError;
}

