package Map;

import Game.Game;

public class Territory {
    private Region[][] regions;

    Territory(int rows,int cols){
        regions = new Region[rows][cols];
    }

    public Region getEachRegion(int row,int col,Game game){
        if(row < 0 || row >= game.getRows())
            return null;
        if(col < 0 || col >= game.getCols())
            return null;
        return regions[row][col];
    }
}
