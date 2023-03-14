package Player;

import Game.Direction;
import Game.Game;
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

    private Region eachExplore(Direction direction,MapPosition pos,Game game){
        Region currExplore = game.getTerritory().getEachRegion(pos,game);
        while(currExplore.getOwner() == this || currExplore.getOwner() == null){
            Region.getAdjacent(currExplore.ge)
        }
    }

    public long nearby(Direction direction,Game game){
        MapPosition crewPos = new MapPosition(identifier.get("currow").intValue(),identifier.get("curcol").intValue());
        Region crewRegion = game.getTerritory().getEachRegion(crewPos,game);


        switch (direction){
            case UP -> {
                return distance*10+1;
            }
            case DOWN -> {
                return distance*10+4;
            }
            case UPLEFT -> {
                return distance*10+6;
            }
            case UPRIGHT -> {
                return distance*10+2;
            }
            case DOWNLEFT -> {
                return distance*10+5;
            }
            case DOWNRIGHT -> {
                return distance*10+3;
            }
            default -> {
                return 0;
            }
        }
    }

    public long opponent(Game game){
        MapPosition crewPos = new MapPosition(identifier.get("currow").intValue(),identifier.get("curcol").intValue());
        Region crewRegion = game.getTerritory().getEachRegion(crewPos,game);


        switch (direction){
            case UP -> {
                return distance*10+1;
            }
            case DOWN -> {
                return distance*10+4;
            }
            case UPLEFT -> {
                return distance*10+6;
            }
            case UPRIGHT -> {
                return distance*10+2;
            }
            case DOWNLEFT -> {
                return distance*10+5;
            }
            case DOWNRIGHT -> {
                return distance*10+3;
            }
            default -> {
                return 0;
            }
        }
    }
}
