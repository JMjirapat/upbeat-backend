package Map;

public class Territory {
    private Region[][] regions;

    Territory(int rows,int cols){
        regions = new Region[rows][cols];
    }

    public Region getEachRegion(int row,int col){
        return regions[row][col];
    }
}
