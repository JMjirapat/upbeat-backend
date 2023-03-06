package Map;

import Player.Player;
import Game.Game;
import Game.Direction;

public class Region {

    private MapPosition pos;
    private Player owner;
    private long deposit;
    private double interest;

    Region(MapPosition pos,long initDep){
        this.pos = pos;
        deposit = initDep;
    }

    public void setOwner(Player p){
        owner = p;
    }

    public Player getOwner(){
        return owner;
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

    public long getDeposit(Player p){
        if(p.equals(owner))
            return deposit;
        else
            return -(deposit);
    }

    public long getInterest(){
        return (long) interest;
    }

    public Region getAdjacent(Game game,Direction direction){
        boolean isOddColumn = pos.getColumn()%2 == 1;
        switch (direction){
            case UP -> {
                return game.getTerritory().getEachRegion(pos.getRow()-1,pos.getColumn(),game);
            }
            case DOWN -> {
                return game.getTerritory().getEachRegion(pos.getRow()+1,pos.getColumn(),game);
            }
            case UPLEFT -> {
                if(isOddColumn)
                    return game.getTerritory().getEachRegion(pos.getRow()-1,pos.getColumn()-1,game);
                else
                    return game.getTerritory().getEachRegion(pos.getRow(),pos.getColumn()-1,game);
            }
            case UPRIGHT -> {
                if(isOddColumn)
                    return game.getTerritory().getEachRegion(pos.getRow()-1,pos.getColumn()+1,game);
                else
                    return game.getTerritory().getEachRegion(pos.getRow(),pos.getColumn()+1,game);
            }
            case DOWNLEFT -> {
                if(isOddColumn)
                    return game.getTerritory().getEachRegion(pos.getRow(),pos.getColumn()-1,game);
                else
                    return game.getTerritory().getEachRegion(pos.getRow()+1,pos.getColumn()-1,game);
            }
            case DOWNRIGHT -> {
                if(isOddColumn)
                    return game.getTerritory().getEachRegion(pos.getRow(),pos.getColumn()+1,game);
                else
                    return game.getTerritory().getEachRegion(pos.getRow()+1,pos.getColumn()+1,game);
            }
            default -> {
                return null;
            }
        }
    }
}
