/**
 * Created by Noam on 12/10/2016.
 */

import org.junit.Test;

public class AlgorithmTests {

    @Test
    public void  test1(){
        String s = String.join("\n"
            , "5"
            , "Jerusalem"
            , "1 2"
            , "Haifa"
            , "70 80"
            , "Beer Sheva"
            , "1000 4000"
            , "Ra'anana"
            , "33333 4444"
            , "Naharia"
            , "4000 1000"
            , ""
        );
        System.out.println(s);
        Graph graph = new Graph(s);
        //System.out.println("Initial graph\n" + graph.toString());
        RandomRoute randomRoute = new RandomRoute(graph);
        System.out.println("Random Route\n" + graph.toString());
        graph.resetNodes();
        //System.out.println("Graph after reset\n" + graph.toString());
        NearestNeighbourRoute nearestNeighbourRoute = new NearestNeighbourRoute(graph);
        System.out.println("Nearest neigbour\n" + graph.toString());
        System.out.println("randomRoute length: " + randomRoute.getRouteLength());
        System.out.println("nearestNeighbourRoute length: " + nearestNeighbourRoute.getRouteLength());
    }
}
