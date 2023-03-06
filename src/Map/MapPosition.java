package Map;

public class MapPosition {
    public final int row;
    public final int column;

    public MapPosition(int r, int c){
        this.row = r;
        this.column = c;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }
}
