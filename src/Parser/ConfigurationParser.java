package Parser;

import AST.ConfigurationNode;
import AST.Node.*;
import Tokenizer.Tokenizer;
import Parser.ParserException.*;

public class ConfigurationParser implements Parser {
    private final Tokenizer tkz;
    private int m;
    private int n;
    private int init_plan_min;
    private int init_plan_sec;
    private long init_budget;
    private long init_center_dep;
    private int plan_rev_min;
    private int plan_rev_sec;
    private long rev_cost;
    private long max_dep;
    private int interest_pct;

    public ConfigurationParser(Tokenizer tkz){
        if (!tkz.hasNextToken())
            throw new ParserException.TokenRequired();
        this.tkz = tkz;
    }

    @Override
    public ExecNode parse() {
        while (tkz.hasNextToken()) {
            parseConfig();
        }
        return new ConfigurationNode(m, n, init_plan_min, init_plan_sec, init_budget, init_center_dep,
                plan_rev_min, plan_rev_sec, rev_cost, max_dep, interest_pct);
    }

    private void parseConfig(){
        String tkzVariable = tkz.peek();
        int value;
        if(tkz.peek("=")){
            tkz.consume();
        }else{
            throw new IllegalAssignment(tkz.getLine());
        }
        if(isNumber(tkz.peek())){
            value = Integer.parseInt(tkz.consume());
        }else{
            throw new IllegalAssignment(tkz.getLine());
        }
        switch (tkzVariable) {
            case "m" -> m = value;
            case "n" -> n = value;
            case "init_plan_min" -> init_plan_min = value;
            case "init_plan_sec" -> init_plan_sec = value;
            case "init_budget" -> init_budget = value;
            case "init_center_dep" -> init_center_dep = value;
            case "plan_rev_min" -> plan_rev_min = value;
            case "plan_rev_sec" -> plan_rev_sec = value;
            case "rev_cost" -> rev_cost = value;
            case "max_dep" -> max_dep = value;
            case "interest_pct" -> interest_pct = value;
            default -> throw new NoConfigMatch(tkz.peek(), tkz.getLine());
        }
    }

    private static boolean isNumber(String str){
        return str.matches("\\d+");
    }
}
