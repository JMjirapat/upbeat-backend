package Player;

import Game.Direction;
import Map.MapPosition;
import Map.Region;

import java.util.HashMap;
import Game.Game;

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
        identifier.put("currow",(long) centerPos.getRow());
        identifier.put("curcol",(long) centerPos.getColumn());
    }

    public HashMap<String, Long> getIdentifier(){
        return new HashMap<>(identifier);
    }
    public void setIdentifier(String key,long value){
        identifier.put(key,value);
    }
    public void relocate(){

    }

    public void move(Direction direction){

    }

    public void invest(long value){

    }

    public void collect(long value){

    }

    public void attack(Direction direction,long value){

    }

    public long nearby(Direction direction,Game game){
        int distance = game.getTerritory().getShortOpponent(direction,this,game);
        //return distance*100+(int) (Math.log10(Math.abs(nearbyRegion.getDeposit(this))) + 1);
        return -1;
    }

    public long opponent(){
        return -1;
    }
}
