/**
 * Created by Noam on 12/10/2016.
 */

import org.junit.Test;

public class AlgorithmTests{

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
        TspGraph tspGraph = new TspGraph(s1, true);
        tspGraph.buildRandomRoute();
        tspGraph.setRouteLength();
        tspGraph.printGraph();
    }

    @Test
    public void  greedyTest() throws InterruptedException {
        TspGraph tspGraph = new TspGraph(s2, true);
        tspGraph.buildGreedyRoute();
        tspGraph.setRouteLength();
        tspGraph.printGraph();
        Thread.sleep(3000);
    }

    @Test
    public void greedyTest2()throws InterruptedException
    {
        for (int i = 0; i < 10; i++)
        {
            TspGraph tspGraph = new TspGraph(200, true);
            tspGraph.buildGreedyRoute();
            tspGraph.setRouteLength();
            tspGraph.printGraph();
            Thread.sleep(3000);
        }
    }


    @Test
    public void  singleSwapTest() throws InterruptedException {
        TspGraph tspGraph = new TspGraph(s2, true);
        tspGraph.buildSequentialRoute();
        tspGraph.setRouteLength();
        tspGraph.printGraph();
        Thread.sleep(3000);
        tspGraph.buildTwoOptRoute();
        tspGraph.setRouteLength();
        tspGraph.printGraph();
        Thread.sleep(3000);
    }

    @Test
    public void  twoOptTest() throws InterruptedException {
        TspGraph tspGraph = new TspGraph(s1, true);
        tspGraph.buildRandomRoute();
        tspGraph.setRouteLength();
        tspGraph.printGraph();
        tspGraph.buildTwoOptRoute();
        tspGraph.setRouteLength();
        Thread.sleep(3000);
    }

    @Test
    public void  twoOptRandomGraph() throws InterruptedException {
        TspGraph tspGraph = new TspGraph(30, true);
        tspGraph.buildRandomRoute();
        tspGraph.setRouteLength();
        tspGraph.printGraph();
        tspGraph.buildTwoOptRoute();
        tspGraph.setRouteLength();
        System.out.println("twoOptSwap length: " + tspGraph.getRouteLength());
        Thread.sleep(3000);
    }

    @Test
    public void  ThreeAlgorithmTest() throws InterruptedException {
        TspGraph tspGraph = new TspGraph(100, true);

        tspGraph.buildGreedyRoute();
        tspGraph.setRouteLength();
        tspGraph.printGraph();
        Thread.sleep(1000);

        tspGraph.buildRandomRoute();
        tspGraph.setRouteLength();
        tspGraph.printGraph();
        Thread.sleep(1000);

        tspGraph.buildTwoOptRoute();
        tspGraph.setRouteLength();
        tspGraph.printGraph();
        Thread.sleep(5000);
    }

    @Test
    public void  ThreeAlgorithmTest2() throws InterruptedException {
        for (int i = 0; i < 10; i++)
        {
            System.out.println( i + ".");
            TspGraph tspGraph = new TspGraph((i + 1) * 100, false);

            final long GreedyRouteStartTime = System.nanoTime();
            tspGraph.buildGreedyRoute();
            final long GreedyRouteEndTime = System.nanoTime();
            System.out.println("GreedyRoute execution time: " + (GreedyRouteEndTime - GreedyRouteStartTime));
            tspGraph.setRouteLength();
            System.out.println("GreedyRoute length: " + tspGraph.getRouteLength());

            final long randomStartTime = System.nanoTime();
            tspGraph.buildRandomRoute();
            final long randomEndTime = System.nanoTime();
            System.out.println("Random execution time: " + (randomEndTime - randomStartTime));
            tspGraph.setRouteLength();
            System.out.println("Random length: " + tspGraph.getRouteLength());

            final long twoOptStartTime = System.nanoTime();
            tspGraph.buildTwoOptRoute();
            final long twoOptEndTime = System.nanoTime();
            System.out.println("TwoOptSwap execution time: " + (twoOptEndTime - twoOptStartTime));
            tspGraph.setRouteLength();
            System.out.println("TwoOptSwap length: " + tspGraph.getRouteLength());
            System.out.println("");
        }
    }

    @Test
    public void  statistics() throws InterruptedException
    {
        int intervals = 10;
        int timesTestRuns = 100;
        double[][] randomLength = new double[intervals][timesTestRuns];
        double[][] greedyLength = new double[intervals][timesTestRuns];
        double[][] twoOptSwapLength1 = new double[intervals][timesTestRuns];
        double[][] twoOptSwapLength2 = new double[intervals][timesTestRuns];
        for (int i = 0; i < intervals; i++)
        {
            for (int j = 0; j < timesTestRuns; j++)
            {
                TspGraph tspGraph = new TspGraph((i + 1) * 100, false);

                tspGraph.buildGreedyRoute();
                tspGraph.setRouteLength();
                greedyLength[i][j] = tspGraph.getRouteLength();

                tspGraph.buildTwoOptRoute();
                tspGraph.setRouteLength();
                twoOptSwapLength1[i][j] = tspGraph.getRouteLength();

                tspGraph.buildRandomRoute();
                tspGraph.setRouteLength();
                randomLength[i][j] = tspGraph.getRouteLength();

                tspGraph.buildTwoOptRoute();
                tspGraph.setRouteLength();
                twoOptSwapLength2[i][j] = tspGraph.getRouteLength();


            }
        }
        System.out.println("Random Length:");
        for (int i = 0; i < intervals; i++)
        {
            System.out.println(i + ":\n");
            for (int j = 0; j < timesTestRuns; j++)
            {
                System.out.println(randomLength[i][j]);
            }
        }

        System.out.printf("\n\n\n\n\n\n");

        System.out.println("Two opt swap 2 Length:");
        for (int i = 0; i < intervals; i++)
        {
            System.out.println(i + ":\n");
            for (int j = 0; j < timesTestRuns; j++)
            {
                System.out.println(twoOptSwapLength2[i][j]);
            }
        }

        System.out.printf("\n\n\n\n\n\n");


        System.out.println("Greedy Length:");
        for (int i = 0; i < intervals; i++)
        {
            System.out.println(i + ":\n");
            for (int j = 0; j < timesTestRuns; j++)
            {
                System.out.println(greedyLength[i][j]);
            }
        }

        System.out.printf("\n\n\n\n\n\n");

        System.out.println("Two opt swap 1 Length:");
        for (int i = 0; i < intervals; i++)
        {
            System.out.println(i + ":\n");
            for (int j = 0; j < timesTestRuns; j++)
            {
                System.out.println(twoOptSwapLength1[i][j]);
            }
        }
    }
}
