
public class Main {
    public static void main(String[] args) {
        System.out.println("src: 3,4");
        System.out.println(" Distance:" + Map.Territory.shortestPath(3,4,3,0));
        System.out.println(" Distance:" + Map.Territory.shortestPath(3,4,2,1));
        System.out.println(" Distance:" + Map.Territory.shortestPath(3,4,1,4));
        System.out.println(" Distance:" + Map.Territory.shortestPath(3,4,2,5));
        System.out.println(" Distance:" + Map.Territory.shortestPath(3,4,1,7));
        System.out.println(" Distance:" + Map.Territory.shortestPath(3,4,3,7));
        System.out.println(" Distance:" + Map.Territory.shortestPath(3,4,4,7));
        System.out.println(" Distance:" + Map.Territory.shortestPath(3,4,5,7));
        System.out.println(" Distance:" + Map.Territory.shortestPath(3,4,5,2));
        System.out.println(" Distance:" + Map.Territory.shortestPath(3,4,5,5));
        System.out.println(" Distance:" + Map.Territory.shortestPath(3,4,7,4));
        System.out.println("src: 4,3");
        System.out.println(" Distance:" + Map.Territory.shortestPath(4,3,0,0));
        System.out.println(" Distance:" + Map.Territory.shortestPath(4,3,4,0));
        System.out.println(" Distance:" + Map.Territory.shortestPath(4,3,2,2));
        System.out.println(" Distance:" + Map.Territory.shortestPath(4,3,5,2));
        System.out.println(" Distance:" + Map.Territory.shortestPath(4,3,1,4));
        System.out.println(" Distance:" + Map.Territory.shortestPath(4,3,1,6));
        System.out.println(" Distance:" + Map.Territory.shortestPath(4,3,6,5));
        System.out.println(" Distance:" + Map.Territory.shortestPath(4,3,3,6));
        System.out.println(" Distance:" + Map.Territory.shortestPath(4,3,4,6));
        System.out.println("src: random");
        System.out.println(" Distance:" + Map.Territory.shortestPath(2,1,4,3));
        System.out.println(" Distance:" + Map.Territory.shortestPath(3,4,0,2));
        System.out.println(" Distance:" + Map.Territory.shortestPath(0,5,4,1));
        System.out.println(" Distance:" + Map.Territory.shortestPath(4,5,5,6));
        System.out.println(" Distance:" + Map.Territory.shortestPath(0,8,7,0));
        System.out.println(" Distance:" + Map.Territory.shortestPath(7,6,0,0));
    }
}