package Game;

import Map.MapPosition;
import Map.Region;
import Map.Territory;
import Player.Player;
import Tokenizer.*;
import Parser.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

//rows cols int maxdeposit

public class GameProps extends Game{

    GameProps(){
        randomGen = new Random();
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public HashMap<String, Long> getIdentifiers() {
        HashMap<String, Long> Identifier = currentPlayer.getIdentifier();
        Identifier.putAll(globalIdentifiers);
        MapPosition pos = new MapPosition(Identifier.get("currow").intValue(),Identifier.get("curcol").intValue());
        Region cityCrew = territory.getEachRegion(pos);
        Identifier.put("deposit",cityCrew.getDeposit(currentPlayer));
        Identifier.put("int",cityCrew.getInterest());
        Identifier.put("random",(long) randomGen.nextInt(1000));
        return Identifier;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getCols() {
        return cols;
    }

    @Override
    public void setConfig(int m, int n, int init_plan_min, int init_plan_sec, long init_budget, long init_center_dep, int plan_rev_min, int plan_rev_sec, long rev_cost, long max_dep, int interest_pct) {
        rows = m;
        cols = n;
        initialPlanTime = (init_plan_min * 60) + init_plan_sec;
        initialBudget = init_budget;
        initialCenterDeposit = init_center_dep;
        revisionPlanTime = (plan_rev_min * 60) + plan_rev_sec;
        costToRevision = rev_cost;
        maxDeposit = max_dep;
        interestPercentage = interest_pct;
        globalIdentifiers.put("rows",(long) rows);
        globalIdentifiers.put("cols",(long) cols);
        globalIdentifiers.put("maxdeposit",maxDeposit);
    }

    @Override
    public void Configuration(ArrayList<String> codeLines) {
        Tokenizer tokenizer = new ConfigurationTokenizer(codeLines);
        Parser parser = new ConfigurationParser(tokenizer);
        parser.parse().execute(this);
    }

    @Override
    public void Run() {

    }

    @Override
    public void NewPhase() {

    }

    @Override
    public void StartTurn() {

    }

    @Override
    public void Plan() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void EndTurn() {

    }

    @Override
    public boolean hasWinner() {
        return false;
    }

    @Override
    public void Reset() {

    }

    @Override
    public void assign(String identifier, long value) {
        currentPlayer.putIdentifier(identifier,value);
    }

    @Override
    public void collect(long value) {
        currentPlayer.collect(value);
    }

    @Override
    public void done() {
        EndTurn();
    }

    @Override
    public void invest(long value) {
        currentPlayer.invest(value);
    }

    @Override
    public void relocate() {
        currentPlayer.relocate(territory);
    }

    @Override
    public void move(Direction direction) {
        currentPlayer.move(direction,territory);
    }

    @Override
    public void shoot(Direction direction, long value) {
        currentPlayer.shoot(direction,value);
    }

    @Override
    public int getInterestPercentage() {
        return interestPercentage;
    }

    @Override
    public int getCurrTurn(){
        return currTurn;
    }

    @Override
    public Territory getTerritory() {
        return territory;
    }
}
