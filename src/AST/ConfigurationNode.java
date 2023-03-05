package AST;

public class ConfigurationNode implements ExecNode {
    private final int m;
    private final int n;
    private final int init_plan_min;
    private final int init_plan_sec;
    private final int init_budget;
    private final int init_center_dep;
    private final int plan_rev_min;
    private final int plan_rev_sec;
    private final int rev_cost;
    private final int max_dep;
    private final int interest_pct;

    public ConfigurationNode(int m, int n, int init_plan_min, int init_plan_sec, int init_budget, int init_center_dep,
                             int plan_rev_min, int plan_rev_sec, int rev_cost, int max_dep, int interest_pct) {
        this.m = m;
        this.n = n;
        this.init_plan_min = init_plan_min;
        this.init_plan_sec = init_plan_sec;
        this.init_budget = init_budget;
        this.init_center_dep = init_center_dep;
        this.plan_rev_min = plan_rev_min;
        this.plan_rev_sec = plan_rev_sec;
        this.rev_cost = rev_cost;
        this.max_dep = max_dep;
        this.interest_pct = interest_pct;
    }

    @Override
    public void execute(Game game) {

    }
}