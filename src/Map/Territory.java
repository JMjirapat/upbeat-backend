package Map;

import Game.Game;

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
