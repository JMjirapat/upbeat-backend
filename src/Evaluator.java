import java.util.Map;

public interface Evaluator {
    int eval(Map<String, Integer> bindings) throws EvalError;
}
