package Map;

public class MapPosition {
    private long row;
    private long column;

    public MapPosition(long r, long c){
        this.row = r;
        this.column = c;
    }

    public long getRow(){
        return row;
    }

    public long getColumn(){
        return column;
    }
}
