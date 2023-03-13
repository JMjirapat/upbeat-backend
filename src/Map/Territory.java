package Map;

import Game.Game;
import Player.Player;
import Game.Direction;

import java.util.ArrayList;

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

    public int getShortOpponent(Direction direction, Player p,Game game){
        Region cityCrewRegion = getEachRegion(p.getIdentifier().get("currow").intValue(),p.getIdentifier().get("curcol").intValue(),game);
        Region shortRegion;
        int distance = 1;
        shortRegion = cityCrewRegion.getAdjacent(game,direction);
        while(shortRegion != null && (shortRegion.getOwner() == p || shortRegion.getOwner() == null)){
            shortRegion = cityCrewRegion.getAdjacent(game,direction);
            distance++;
        }
        if(shortRegion == null)
            return 0;
        return distance;
    }

    public static int shortestPath(int srcRow, int srcCol, int destRow, int destCol){
        int distance = 0;
        if(srcRow == destRow){
            if(srcCol > destCol){
                for(int i=srcCol;i>=destCol;i--) {
                    System.out.print("( " + srcRow + "," + i + " )");
                    System.out.print(" ---> ");
                }
            }else if(srcCol < destCol){
                for(int i=srcRow;i<=destRow;i++) {
                    System.out.print("( " + srcRow + "," + i + " )");
                    System.out.print(" ---> ");
                }
            }
            return Math.abs(destCol-srcCol);
        }else if(srcCol == destCol){
            if(srcRow > destRow){
                for(int i=srcRow;i>=destRow;i--) {
                    System.out.print("( " + i + "," + srcCol + " )");
                    System.out.print(" ---> ");
                }
            }else if(srcRow < destRow){
                for(int i=srcRow;i<=destRow;i++) {
                    System.out.print("( " + i + "," + srcCol + " )");
                    System.out.print(" ---> ");
                }
            }
            return Math.abs(destRow-srcRow);
        }else if(srcRow > destRow && srcCol > destCol){
            if(srcCol%2 == 0){
                srcCol -= 1;
            }else{
                srcCol -= 1;
                srcRow -= 1;
            }
            System.out.print("( " + srcRow + "," + srcCol + " )");
            System.out.print(" ---> ");
            return 1 + shortestPath(srcRow,srcCol,destRow,destCol);
        }else if(srcRow > destRow && srcCol < destCol){
            if(srcCol%2 == 0){
                srcCol += 1;
            }else{
                srcCol += 1;
                srcRow -= 1;
            }
            System.out.print("( " + srcRow + "," + srcCol + " )");
            System.out.print(" ---> ");
            return 1 + shortestPath(srcRow,srcCol,destRow,destCol);
        }else if(srcRow < destRow && srcCol > destCol){
            if(srcCol%2 == 0){
                srcCol -= 1;
                srcRow += 1;
            }else{
                srcCol -= 1;
            }
            System.out.print("( " + srcRow + "," + srcCol + " )");
            System.out.print(" ---> ");
            return 1 + shortestPath(srcRow,srcCol,destRow,destCol);
        }else if(srcRow < destRow && srcCol < destCol){
            if(srcCol%2 == 0){
                srcCol += 1;
                srcRow += 1;
            }else{
                srcCol += 1;
            }
            System.out.print("( " + srcRow + "," + srcCol + " )");
            System.out.print(" ---> ");
            return 1 + shortestPath(srcRow,srcCol,destRow,destCol);
        }else{
            return distance;
        }
    }
}
