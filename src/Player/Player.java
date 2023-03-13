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
        Region cityCrewRegion = game.getTerritory().getEachRegion(identifier.get("currow").intValue(),identifier.get("curcol").intValue(),game);
        Region nearbyRegion;
        int distance = 1;
        nearbyRegion = cityCrewRegion.getAdjacent(game,direction);
        while(nearbyRegion != null && (nearbyRegion.getOwner() == this || nearbyRegion.getOwner() == null)){
            nearbyRegion = cityCrewRegion.getAdjacent(game,direction);
            distance++;
        }

        if(nearbyRegion == null){
            return 0;
        }

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

    public long opponent(){

    }
}
