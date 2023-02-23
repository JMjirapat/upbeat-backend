import java.util.Map;

interface PlanNode extends Node {
    int eval(Map<String, Integer> bindings) throws EvalError;
}

