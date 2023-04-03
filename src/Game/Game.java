package Game;

import Map.Territory;
import Player.Player;
import Map.Region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class Game {

    private static Game instance;

    public static Game instance() {
        if (instance == null || instance.isGameEnd) {
            // lazy instantiation
            instance = new GameProps();
        }
        return instance;
    }

    // Config field
    protected int rows;
    protected int cols;
    protected int initialPlanTime;
    protected long initialBudget;
    protected long initialCenterDeposit;
    protected int revisionPlanTime;
    protected long costToRevision;
    protected long maxDeposit;
    protected int interestPercentage;

    // Instance field
    protected Random randomGen;
    protected Player currentPlayer; // need player
    protected Territory territory; // need config
    protected ArrayList<Player> players; // need player
    protected ArrayList<Player> alivePlayers; // need player
    protected HashMap<String, Long> globalIdentifiers; // need config
    protected boolean initialTurn;
    protected int currTurn;
    protected int PlayerIndex;
    protected boolean isGameEnd;

    // Instance Methods
    public abstract Player getCurrentPlayer();
    public abstract HashMap<String, Long> getIdentifiers();
    public abstract int getRows();
    public abstract int getCols();
    public abstract int getInterestPercentage();
    public abstract int getCurrTurn();
    public abstract Territory getTerritory();
    public abstract Region[] getAllRegionsOccupy(Player p);

    // Game State
    public abstract void Run();
    public abstract void Configuration(ArrayList<String> codeLines);
    public abstract void setConfig(int m, int n, int init_plan_min, int init_plan_sec, long init_budget, long init_center_dep,
                                   int plan_rev_min, int plan_rev_sec, long rev_cost, long max_dep, int interest_pct);
    public abstract void NewPhase();
    public abstract void StartTurn();
    public abstract void Plan();
    public abstract void Update();
    public abstract void EndTurn();
    public abstract boolean hasWinner();
    public abstract void Reset();

    // Game Logic (Action Commands)
    public abstract void assign(String identifier,long value);
    public abstract void shoot(Direction direction,long value);
    public abstract void playerDefeated(Player p);
    public abstract void collect(long value);
    public abstract void done();
    public abstract void invest(long value);
    public abstract void move(Direction direction);
    public abstract void relocate();
}
