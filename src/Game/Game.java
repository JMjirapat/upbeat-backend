package Game;

import Map.Territory;
import Player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Game {
    public int row;
    public int col;
    public int initialPlanTime;
    public long initialBudget;
    public long initialCenterDeposit;
    public boolean initialPhase;
    public int revisionPlanTime;
    public long costToRevision;
    public long maxDeposit;
    public long interestPercentage;
    private Player currentPlayer;
    private Territory territory;
    private ArrayList<Player> players;
    public abstract Player getCurrentPlayer();

    public abstract HashMap<String, Long> getIdentifier();
    public abstract HashMap<String, Long> setIdentifier();
    public abstract void Configuration();
    public abstract void Run();
    public abstract void StartTurn();
    public abstract void Plan();
    public abstract void Update();
    public abstract void EndTurn();
    public abstract boolean hasWinner();
    public abstract void Reset();
}
