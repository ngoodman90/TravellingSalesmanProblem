/**
 * Created by Noam on 12/10/2016.
 */

import org.junit.Test;

public class AlgorithmTests  {

    private static String s1 = String.join("\n"
                                    , "16"
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
                                    , "i"
                                    , "666 777"
                                    , "j"
                                    , "423 900"
                                    , "k"
                                    , "135 794"
                                    , "l"
                                    , "873 199"
                                    , "m"
                                    , "357 275"
                                    , "n"
                                    , "920 239"
                                    , "o"
                                    , "158 543"
                                    , "p"
                                    , "582 258"
                                    , ""
                            );

    private static String s2 = String.join("\n"
                                    , "8"
                                    , "a"
                                    , "0 100"
                                    , "b"
                                    , "100 0"
                                    , "c"
                                    , "200 0"
                                    , "d"
                                    , "300 100"
                                    , "e"
                                    , "0 300"
                                    , "f"
                                    , "100 400"
                                    , "g"
                                    , "200 400"
                                    , "h"
                                    , "300 300"
                                    , ""
                            );

    @Test
    public void  randomGraphTest() throws InterruptedException {
        TspGraph tspGraph = new TspGraph(s1);
        tspGraph.buildRandomRoute();
        tspGraph.setRouteLength();
        //System.out.println("Random Route:\n" + tspGraph.toString());
        //System.out.println("randomRoute length: " + tspGraph.getRouteLength());
        tspGraph.printGraph();
    }

    @Test
    public void  nearestNeighbourTest() throws InterruptedException {
        TspGraph tspGraph = new TspGraph(s1);
        tspGraph.buildNearestNeighbourGraph();
        //System.out.println("nearestNeighbourRoute length: " + tspGraph.getRouteLength());
        tspGraph.printGraph();
    }

    @Test
    public void  greedyTest() throws InterruptedException {
        TspGraph tspGraph = new TspGraph(s1);
        tspGraph.buildGreedyRoute();
        tspGraph.setRouteLength();
        //System.out.println("Greedy length: " + tspGraph.getRouteLength());
        tspGraph.printGraph();
    }

    @Test
    public void  singleSwapTest() throws InterruptedException {
        TspGraph tspGraph = new TspGraph(s2);
        tspGraph.buildSequentialRoute();
        tspGraph.setRouteLength();
        tspGraph.printGraph();
        tspGraph.buildTwoOptRoute();
        tspGraph.setRouteLength();
        tspGraph.printGraph();
    }

    @Test
    public void  twoOptTest() throws InterruptedException {
        TspGraph tspGraph = new TspGraph(s1);
        tspGraph.buildRandomRoute();
        tspGraph.setRouteLength();
        //System.out.println("pre swap TspGraph:\n" + tspGraph.toString());
        //System.out.println("pre swap length: " + tspGraph.getRouteLength());
        tspGraph.printGraph();
        tspGraph.buildTwoOptRoute();
        tspGraph.setRouteLength();
        //System.out.println("Two Opt TspGraph:\n" + tspGraph.toString());
        //System.out.println("twoOptSwap length: " + tspGraph.getRouteLength());
        Thread.sleep(3000);
    }
}
