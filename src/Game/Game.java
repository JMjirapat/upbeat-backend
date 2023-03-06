package Game;

import Map.Territory;
import Player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class Game {

    protected Random randomGen;
    protected HashMap<String, Long> globalIdentifier;
    protected long rows;
    protected long cols;
    protected long random;
    public int initialPlanTime;
    public long initialBudget;
    public long initialCenterDeposit;
    public boolean initialPhase;
    public int revisionPlanTime;
    public long costToRevision;
    public long maxDeposit;
    public int interestPercentage;
    protected int currTurn;
    private int PlayerIndex;
    protected Player currentPlayer;
    protected Territory territory;
    private ArrayList<Player> players;
    public abstract Player getCurrentPlayer();

    public abstract HashMap<String, Long> getIdentifier();

    public abstract int getInterestPercentage();
    public abstract int getCurrTurn();
    public abstract void setConfig(int m, int n, int init_plan_min, int init_plan_sec, long init_budget, long init_center_dep,
                                   int plan_rev_min, int plan_rev_sec, long rev_cost, long max_dep, int interest_pct);
    public abstract void Configuration(ArrayList<String> codeLines);
    public abstract void Run();
    public abstract void StartTurn();
    public abstract void Plan();
    public abstract void Update();
    public abstract void EndTurn();
    public abstract boolean hasWinner();
    public abstract void Reset();
}
