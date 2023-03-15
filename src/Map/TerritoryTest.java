package Map;

import org.junit.Test;
import static Map.Territory.shortestPath;
import static org.junit.Assert.*;
public class TerritoryTest {
    @Test
    public void shortestPathTest() {
        assertEquals(shortestPath(new MapPosition(3,4),new MapPosition(3,0)),4);
        assertEquals(shortestPath(new MapPosition(3,4),new MapPosition(2,1)),3);
        assertEquals(shortestPath(new MapPosition(3,4),new MapPosition(1,4)),2);
        assertEquals(shortestPath(new MapPosition(3,4),new MapPosition(2,5)),2);
        assertEquals(shortestPath(new MapPosition(3,4),new MapPosition(1,7)),4);
        assertEquals(shortestPath(new MapPosition(3,4),new MapPosition(3,7)),3);
        assertEquals(shortestPath(new MapPosition(3,4),new MapPosition(4,7)),3);
        assertEquals(shortestPath(new MapPosition(3,4),new MapPosition(5,7)),3);
        assertEquals(shortestPath(new MapPosition(3,4),new MapPosition(5,2)),3);
        assertEquals(shortestPath(new MapPosition(3,4),new MapPosition(5,5)),2);
        assertEquals(shortestPath(new MapPosition(3,4),new MapPosition(7,4)),4);
        assertEquals(shortestPath(new MapPosition(4,3),new MapPosition(0,0)),5);
        assertEquals(shortestPath(new MapPosition(4,3),new MapPosition(4,0)),3);
        assertEquals(shortestPath(new MapPosition(4,3),new MapPosition(2,2)),2);
        assertEquals(shortestPath(new MapPosition(4,3),new MapPosition(5,2)),2);
        assertEquals(shortestPath(new MapPosition(4,3),new MapPosition(1,4)),3);
        assertEquals(shortestPath(new MapPosition(4,3),new MapPosition(1,6)),4);
        assertEquals(shortestPath(new MapPosition(4,3),new MapPosition(6,5)),3);
        assertEquals(shortestPath(new MapPosition(4,3),new MapPosition(3,6)),3);
        assertEquals(shortestPath(new MapPosition(4,3),new MapPosition(4,6)),3);
        assertEquals(shortestPath(new MapPosition(2,1),new MapPosition(4,3)),3);
        assertEquals(shortestPath(new MapPosition(3,4),new MapPosition(0,2)),4);
        assertEquals(shortestPath(new MapPosition(0,5),new MapPosition(4,1)),6);
        assertEquals(shortestPath(new MapPosition(4,5),new MapPosition(5,6)),2);
        assertEquals(shortestPath(new MapPosition(0,8),new MapPosition(7,0)),11);
        assertEquals(shortestPath(new MapPosition(7,6),new MapPosition(0,0)),10);
        assertEquals(shortestPath(new MapPosition(7,6),new MapPosition(7,6)),0);
        assertEquals(shortestPath(new MapPosition(7,6),new MapPosition(7,5)),1);
        assertEquals(shortestPath(new MapPosition(7,6),new MapPosition(7,7)),1);
        assertEquals(shortestPath(new MapPosition(7,6),new MapPosition(6,6)),1);
        assertEquals(shortestPath(new MapPosition(7,6),new MapPosition(8,6)),1);
        assertEquals(shortestPath(new MapPosition(7,6),new MapPosition(8,7)),1);
        assertEquals(shortestPath(new MapPosition(7,6),new MapPosition(6,5)),2);
        assertEquals(shortestPath(new MapPosition(7,6),new MapPosition(8,5)),1);
        assertEquals(shortestPath(new MapPosition(7,6),new MapPosition(6,7)),2);
    }
}
