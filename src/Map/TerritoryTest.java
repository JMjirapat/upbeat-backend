package Map;

import org.junit.Test;
import static Map.Territory.shortestPath;
import static org.junit.Assert.*;
public class TerritoryTest {
    @Test
    public void testMyMethod() {
        // Write your test code here
        assertEquals(shortestPath(4,3,19,7),17);
    }
}
