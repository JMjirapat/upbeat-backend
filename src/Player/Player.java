package Player;

import Game.Direction;
import Map.MapPosition;
import Map.Region;
import Map.Territory;
import Game.Game;

import java.util.HashMap;

public class Player {
    private final HashMap<String, Long> identifiers;  //currow curcol budget // region : deposit
    private MapPosition centerPos;
    private MapPosition crewPos;
    private long budget;

    Player(long initBudget, MapPosition centerPos){
        this.centerPos = centerPos;
        identifiers = new HashMap<>();
        setBudget(initBudget);
    }

    private void setBudget(long value){
        budget = value;
        identifiers.put("budget",value);
    }

    private void setCrewPos(MapPosition pos){
        crewPos = pos;
        identifiers.put("currow",(long) pos.getRow());
        identifiers.put("curcol",(long) pos.getColumn());
    }

    public void initTurn(){
        setCrewPos(centerPos);
    }

    public HashMap<String, Long> getIdentifier(){
        return new HashMap<>(identifiers);
    }
    public void putIdentifier(String key,long value){
        identifiers.put(key,value);
    }
    public boolean relocate(Territory territory){
        int distance = Territory.shortestPath(centerPos,crewPos);
        long cost = (long) 5 * distance + 10L;
        if(budget < cost || territory.getEachRegion(crewPos).getOwner() != this)
            return false;
        setBudget(budget - cost);
        centerPos = crewPos;
        return true;
    }

    public boolean move(Direction direction,Territory territory){
        if(budget < 1)
            return false;
        setBudget(budget - 1L);
        MapPosition destPos = Region.getAdjacentPos(crewPos,direction);
        Region destRegion = territory.getEachRegion(destPos);
        if(destRegion != null && (destRegion.getOwner() == null || destRegion.getOwner() == this)){
            setCrewPos(destPos);
        }
        return true;
    }

    public boolean invest(long value,Territory territory){
        if(budget < 1)
            return false;
        long cost = value + 1L;
        if(budget < cost || !territory.hasOccupiedAdjacent(crewPos,this) || value < 0){
            setBudget(budget - 1L);
            return true;
        }
        setBudget(budget-cost);
        Region crewRegion = territory.getEachRegion(crewPos);
        crewRegion.invest(value,this);
        return true;
    }

    public boolean collect(long value,Territory territory){
        if(budget < 1)
            return false;
        setBudget(budget - 1L);
        if(value < 0)
            return true;
        Region crewRegion = territory.getEachRegion(crewPos);
        crewRegion.collect(value,this);
        return true;
    }

    public void receive(long value){
        setBudget(budget+value);
    }

    public boolean shoot(Direction direction,long value,Game game){
        if(budget < 1)
            return false;
        Territory territory = game.getTerritory();
        Region crewRegion = territory.getEachRegion(crewPos);
        Region targetRegion = crewRegion.getAdjacentRegion(direction,territory);
        long cost = value + 1L;
        if(budget < cost || value < 0 || targetRegion == null){
            setBudget(budget - 1L);
            return true;
        }
        setBudget(budget-cost);
        targetRegion.attacked(value,game);
        return true;
    }

    public void lost(Game game){
        game.playerDefeated(this);
    }

    private Region eachExplore(Direction direction, Territory territory){
        Region currRegion = territory.getEachRegion(crewPos);
        while(currRegion != null && (currRegion.getOwner() == this || currRegion.getOwner() == null)){
            currRegion = currRegion.getAdjacentRegion(direction,territory);
        }
        return currRegion;
    }

    public int nearby(Direction direction,Territory territory){
        Region crewRegion = territory.getEachRegion(crewPos);
        Region oopRegion = eachExplore(direction,territory);
        if(oopRegion == null)
            return 0;
        int distance = Territory.shortestPath(crewRegion.getPos(),oopRegion.getPos());
        int digitDeposit = (int) Math.log10(Math.abs(oopRegion.getDeposit(this))) + 1;
        return 100 * distance + digitDeposit;
    }

    public int opponent(Territory territory){
        Region crewRegion = territory.getEachRegion(crewPos);
        Direction direction = null;
        int distance = 0;
        for(Direction d:Direction.values()){
            Region oopRegion = eachExplore(d,territory);
            if(oopRegion == null)
                continue;
            int currDistance = Territory.shortestPath(crewRegion.getPos(),oopRegion.getPos());
            if(direction == null){
                direction = d;
                distance = currDistance;
            } else if(currDistance < distance){
                direction = d;
                distance = currDistance;
            }
        }

        if(direction == null)
            return 0;

        switch (direction){
            case UP -> {
                return distance*10+1;
            }
            case UPRIGHT -> {
                return distance*10+2;
            }
            case DOWNRIGHT -> {
                return distance*10+3;
            }
            case DOWN -> {
                return distance*10+4;
            }
            case DOWNLEFT -> {
                return distance*10+5;
            }
            case UPLEFT -> {
                return distance*10+6;
            }
            default -> {
                return 0;
            }
        }
    }
}
