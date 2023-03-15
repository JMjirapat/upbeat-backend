package Game;

import Map.Territory;
import Player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class Game {

    protected Random randomGen;
    protected HashMap<String, Long> globalIdentifiers;
    protected int rows;
    protected int cols;
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

    public abstract HashMap<String, Long> getIdentifiers();
    public abstract int getRows();
    public abstract int getCols();
    public abstract int getInterestPercentage();
    public abstract int getCurrTurn();
    public abstract Territory getTerritory();
    public abstract void setConfig(int m, int n, int init_plan_min, int init_plan_sec, long init_budget, long init_center_dep,
                                   int plan_rev_min, int plan_rev_sec, long rev_cost, long max_dep, int interest_pct);
    public abstract void Configuration(ArrayList<String> codeLines);
    public abstract void Run();
    public abstract void NewPhase();
    public abstract void StartTurn();
    public abstract void Plan();
    public abstract void Update();
    public abstract void EndTurn();
    public abstract boolean hasWinner();
    public abstract void Reset();

    public abstract void assign(String identifier,long value);
    public abstract void shoot(Direction direction,long value);
    public abstract void collect(long value);
    public abstract void done();
    public abstract void invest(long value);
    public abstract void move(Direction direction);
    public abstract void relocate();
}
