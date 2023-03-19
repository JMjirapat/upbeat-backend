package Map;

import Player.Player;
import Game.Game;
import Game.Direction;

public class Region {

    private final MapPosition position;
    private Player owner;
    private long deposit;
    private double interest;
    private final long maxDeposit;
    private boolean isCityCenter = false;

    Region(MapPosition pos,long initDep, long maxDeposit){
        position = pos;
        deposit = initDep;
        this.maxDeposit = maxDeposit;
    }

    private void setDeposit(long value){
        deposit = Math.max(0,value);
        deposit = Math.min(deposit,maxDeposit);
        if(deposit == 0){
            setOwner(null);
        }
    }
    public void setOwner(Player p){
        owner = p;
    }

    public void update(int baseInterest,int turn){
        if (deposit == 0 || owner == null) {
            interest = 0;
        } else {
            interest = baseInterest * Math.log10(deposit) * Math.log(turn);
        }
        double updatedDeposit = deposit * (interest/100);
        deposit += (long) updatedDeposit;
    }

    public MapPosition getPos(){
        return new MapPosition(position.getRow(), position.getColumn());
    }

    public long getDeposit(Player p){
        if(p.equals(owner))
            return deposit;
        else
            return -(deposit);
    }

    public void invest(long value, Player p){
        if(owner == null)
            setOwner(p);
        setDeposit(deposit+value);
    }

    public void collect(long value, Player p){
        if(owner != p || deposit < value)
            return;
        setDeposit(deposit-value);
        p.receive(value);
    }

    public void attacked(long value, Game game){
        Player tmpOwner = owner;
        setDeposit(deposit-value);
        if(deposit == 0 && isCityCenter){
            tmpOwner.lost(game);
            isCityCenter = false;
        }
    }

    public long getInterest(){
        return (long) interest;
    }

    private static MapPosition AdjacentEven(MapPosition pos,Direction direction){
        switch (direction) {
            case UP -> {
                return new MapPosition(pos.getRow() - 1, pos.getColumn());
            }
            case DOWN -> {
                return new MapPosition(pos.getRow() + 1, pos.getColumn());
            }
            case UPLEFT -> {
                return new MapPosition(pos.getRow(), pos.getColumn() - 1);
            }
            case UPRIGHT -> {
                return new MapPosition(pos.getRow(), pos.getColumn() + 1);
            }
            case DOWNLEFT -> {
                return new MapPosition(pos.getRow() + 1, pos.getColumn() - 1);
            }
            case DOWNRIGHT -> {
                return new MapPosition(pos.getRow() + 1, pos.getColumn() + 1);
            }
            default -> {
                return null;
            }
        }
    }

    private static MapPosition AdjacentOdd(MapPosition pos,Direction direction){
        switch (direction) {
            case UP -> {
                return new MapPosition(pos.getRow() - 1, pos.getColumn());
            }
            case DOWN -> {
                return new MapPosition(pos.getRow() + 1, pos.getColumn());
            }
            case UPLEFT -> {
                return new MapPosition(pos.getRow() - 1, pos.getColumn() - 1);
            }
            case UPRIGHT -> {
                return new MapPosition(pos.getRow() - 1, pos.getColumn() + 1);
            }
            case DOWNLEFT -> {
                return new MapPosition(pos.getRow(), pos.getColumn() - 1);
            }
            case DOWNRIGHT -> {
                return new MapPosition(pos.getRow(), pos.getColumn() + 1);
            }
            default -> {
                return null;
            }
        }
    }

    public static MapPosition getAdjacentPos(MapPosition pos,Direction direction){
        boolean isEvenColumn = pos.getColumn()%2 == 0;
        if(isEvenColumn)
            return AdjacentEven(pos,direction);
        else
            return AdjacentOdd(pos,direction);
    }

    public Region getAdjacentRegion(Direction direction,Territory territory){
        boolean isEvenColumn = position.getColumn()%2 == 0;
        if(isEvenColumn)
            return territory.getEachRegion(AdjacentEven(position,direction));
        else
            return territory.getEachRegion(AdjacentOdd(position,direction));
    }

    public Player getOwner() {
        return owner;
    }
}
