package Map;

import Player.Player;
import Game.Game;

public class Region {

    private MapPosition pos;
    private Player owner;
    private double deposit;

    private double interest;

    Region(MapPosition pos,long initDep){
        this.pos = pos;
        deposit = initDep;
    }

    public void setOwner(Player p){
        owner = p;
    }

    public void update(Game game){
        deposit = deposit*(interest/100);
        interest = game.getInterestPercentage() * Math.log10(deposit) * Math.log(game.getCurrTurn());
    }

    public double getDeposit(Player p){
        if(p.equals(owner))
            return deposit;
        else
            return -(deposit);
    }
}
