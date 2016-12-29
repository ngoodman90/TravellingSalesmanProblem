/**
 * Created by Noam on 12/10/2016.
 */

import org.junit.Test;

import java.util.ArrayList;

public class AlgorithmTests  {

    private static String s1 = String.join("\n"
                                    , "8"
                                    , "a"
                                    , "1 2"
                                    , "b"
                                    , "70 80"
                                    , "c"
                                    , "1000 400"
                                    , "d"
                                    , "333 444"
                                    , "e"
                                    , "400 1000"
                                    , "f"
                                    , "0 500"
                                    , "g"
                                    , "800 700"
                                    , "h"
                                    , "90 1000"
                                    , ""
                            );

    @Test
    public void  randomGraphTest() throws InterruptedException {
        TspGraph tspGraph = new TspGraph(s1);
        tspGraph.buildRandomRoute();
        tspGraph.setRouteLength();
        System.out.println("Random Route:\n" + tspGraph.toString());
        System.out.println("randomRoute length: " + tspGraph.getRouteLength());
        tspGraph.printGraph();
        Thread.sleep(5000);
    }

    @Test
    public void  nearestNeighbourTest() throws InterruptedException {
        TspGraph tspGraph = new TspGraph(s1);
        tspGraph.buildNearestNeighbourGraph();
        tspGraph.setRouteLength();
        System.out.println("Nearest neigbour:\n" + tspGraph.toString());
        System.out.println("nearestNeighbourRoute length: " + tspGraph.getRouteLength());
        tspGraph.printGraph();
        Thread.sleep(5000);
    }

    @Test
    public void  singleSwapTest() throws InterruptedException {
        ArrayList<TspNode> newRoute;
        TspGraph newGraph;
        TspNode n1, n2;

        TspGraph tspGraph = new TspGraph(s1);
        tspGraph.buildRandomRoute();
        tspGraph.setRouteLength();
        System.out.println("pre swap graph:\n" + tspGraph.toString());
        System.out.println("pre swap length: " + tspGraph.getRouteLength());

        n1 = tspGraph.getNodes().get(2);
        n2 = tspGraph.getNodes().get(5);
        newRoute = tspGraph.swap(n1, n2);
        newGraph = new TspGraph(newRoute);
        newGraph.setRouteLength();
        System.out.println("post swap graph:\n" + newGraph.toString());
        System.out.println("post swap length: " + newGraph.getRouteLength());

    }

    @Test
    public void  twoOptTest() throws InterruptedException {
        TspGraph tspGraph = new TspGraph(s1);
        tspGraph.buildRandomRoute();
        tspGraph.setRouteLength();
        System.out.println("pre swap TspGraph:\n" + tspGraph.toString());
        System.out.println("pre swap length: " + tspGraph.getRouteLength());
        /*tspGraph.printGraph();
        Thread.sleep(5000);*/
        tspGraph.twoOptSwap();
        tspGraph.setRouteLength();
        System.out.println("Two Opt TspGraph:\n" + tspGraph.toString());
        System.out.println("twoOptSwap length: " + tspGraph.getRouteLength());
        tspGraph.printGraph();
        Thread.sleep(2000);
    }
}
