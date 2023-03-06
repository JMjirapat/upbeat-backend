package Player;

import Game.Direction;
import Map.MapPosition;
import Map.Region;

import java.util.HashMap;

public class Player {

    private boolean isInitialPlan;
    private HashMap<String, Long> identifier;  //currow curcol budget // region : deposit
    private MapPosition centerPos;
    //private Region[] ownedRegion;

    Player(long initBudget){
        centerPos = new MapPosition(2,4);
        identifier.put("budget",initBudget);
    }

    public void initTurn(){
        identifier.put("currow",centerPos.getRow());
        identifier.put("curcol",centerPos.getColumn());
    }

    public HashMap<String, Long> getIdentifier(){
        return new HashMap<>(identifier);
    }
    public HashMap<String, Long> setIdentifier(){

    }
    public void relocate(){

    }

    public void move(Direction direction){

    }

    public void invest(long value){

    }

    public void collect(long value){

    }

    public void shoot(Direction direction,long value){

    }
}
