package Map;

import Game.Direction;

public class Territory {
    private Region[][] regions;
    private final int rows;
    private final int cols;


    Territory(int rows,int cols){
        this.rows = rows;
        this.cols = cols;
        regions = new Region[rows][cols];
    }

    public Region getEachRegion(MapPosition pos){
        int row = pos.getRow();
        int col = pos.getColumn();
        if(row < 0 || row >= this.rows)
            return null;
        if(col < 0 || col >= this.cols)
            return null;
        return regions[row][col];
    }

    public static int shortestPath(MapPosition srcPos, MapPosition destPos){
        int srcRow = srcPos.getRow();
        int srcCol = srcPos.getColumn();
        int destRow = destPos.getRow();
        int destCol = destPos.getColumn();
        int distance = 0;
        if(srcRow == destRow){
            distance = Math.abs(destCol-srcCol);
        }else if(srcCol == destCol){
            distance = Math.abs(destRow-srcRow);
        }else if(destCol < srcCol && destRow < srcRow){
            MapPosition adjacentPos = Region.getAdjacentPos(srcPos, Direction.UPLEFT);
            distance = 1 + shortestPath(adjacentPos,destPos);
        }else if(destCol > srcCol && destRow < srcRow){
            MapPosition adjacentPos = Region.getAdjacentPos(srcPos, Direction.UPRIGHT);
            distance = 1 + shortestPath(adjacentPos,destPos);
        }else if(destCol > srcCol && destRow > srcRow){
            MapPosition adjacentPos = Region.getAdjacentPos(srcPos, Direction.DOWNRIGHT);
            distance = 1 + shortestPath(adjacentPos,destPos);
        }else if(destCol < srcCol && destRow > srcRow){
            MapPosition adjacentPos = Region.getAdjacentPos(srcPos, Direction.DOWNLEFT);
            distance = 1 + shortestPath(adjacentPos,destPos);
        }
        return distance;
    }
}
