package Map;

import Player.Player;
import Game.Game;
import Game.Direction;

public class Region {

    private final MapPosition position;
    private Player owner;
    private long deposit;
    private double interest;

    Region(MapPosition pos,long initDep){
        position = pos;
        deposit = initDep;
    }

    public void setOwner(Player p){
        owner = p;
    }

    public void update(Game game){
        if (deposit == 0) {
            interest = 0;
        } else {
            interest = game.getInterestPercentage() * Math.log10(deposit) * Math.log(game.getCurrTurn());
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
