package Parser;

import AST.ConfigurationNode;
import AST.ExecNode;
import Tokenizer.Tokenizer;
import Parser.ParserException.*;

public class ConfigurationParser implements Parser {
    private final Tokenizer tkz;
    private int m;
    private int n;
    private int init_plan_min;
    private int init_plan_sec;
    private int init_budget;
    private int init_center_dep;
    private int plan_rev_min;
    private int plan_rev_sec;
    private int rev_cost;
    private int max_dep;
    private int interest_pct;

    ConfigurationParser(Tokenizer tkz){
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
        int value = 0;
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
            case "m":
                m = value;
                break;
            case "n":
                n = value;
                break;
            case "init_plan_min":
                init_plan_min = value;
                break;
            case "init_plan_sec":
                init_plan_sec = value;
                break;
            case "init_budget":
                init_budget = value;
                break;
            case "init_center_dep":
                init_center_dep = value;
                break;
            case "plan_rev_min":
                plan_rev_min = value;
                break;
            case "plan_rev_sec":
                plan_rev_sec = value;
                break;
            case "rev_cost":
                rev_cost = value;
                break;
            case "max_dep":
                max_dep = value;
                break;
            case "interest_pct":
                interest_pct = value;
                break;
            default:
                throw new NoConfigMatch(tkz.peek(), tkz.getLine());
        }
    }

    private static boolean isNumber(String str){
        return str.matches("\\d+");
    }
}
