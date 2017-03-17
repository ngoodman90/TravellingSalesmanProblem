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
        EuclideanGraph euclideanGraph = new EuclideanGraph(s1, true);
        euclideanGraph.buildRandomRoute();
        euclideanGraph.setRouteLength();
        euclideanGraph.printGraph();
    }

    @Test
    public void  greedyTest() throws InterruptedException {
        EuclideanGraph euclideanGraph = new EuclideanGraph(s2, true);
        euclideanGraph.buildGreedyRoute();
        euclideanGraph.setRouteLength();
        euclideanGraph.printGraph();
        Thread.sleep(3000);
    }

    @Test
    public void greedyTest2()throws InterruptedException
    {
        for (int i = 0; i < 10; i++)
        {
            EuclideanGraph euclideanGraph = new EuclideanGraph(200, true);
            euclideanGraph.buildGreedyRoute();
            euclideanGraph.setRouteLength();
            euclideanGraph.printGraph();
            Thread.sleep(3000);
        }
    }


    @Test
    public void  singleSwapTest() throws InterruptedException {
        EuclideanGraph euclideanGraph = new EuclideanGraph(s2, true);
        euclideanGraph.buildSequentialRoute();
        euclideanGraph.setRouteLength();
        euclideanGraph.printGraph();
        Thread.sleep(3000);
        euclideanGraph.buildTwoOptRoute();
        euclideanGraph.setRouteLength();
        euclideanGraph.printGraph();
        Thread.sleep(3000);
    }

    @Test
    public void  twoOptTest() throws InterruptedException {
        EuclideanGraph euclideanGraph = new EuclideanGraph(s1, true);
        euclideanGraph.buildRandomRoute();
        euclideanGraph.setRouteLength();
        euclideanGraph.printGraph();
        euclideanGraph.buildTwoOptRoute();
        euclideanGraph.setRouteLength();
        Thread.sleep(3000);
    }

    @Test
    public void  twoOptRandomGraph() throws InterruptedException {
        EuclideanGraph euclideanGraph = new EuclideanGraph(30, true);
        euclideanGraph.buildRandomRoute();
        euclideanGraph.setRouteLength();
        euclideanGraph.printGraph();
        euclideanGraph.buildTwoOptRoute();
        euclideanGraph.setRouteLength();
        System.out.println("twoOptSwap length: " + euclideanGraph.getRouteLength());
        Thread.sleep(3000);
    }

    @Test
    public void  ThreeAlgorithmTest() throws InterruptedException {
        EuclideanGraph euclideanGraph = new EuclideanGraph(100, true);

        euclideanGraph.buildGreedyRoute();
        euclideanGraph.setRouteLength();
        euclideanGraph.printGraph();
        Thread.sleep(1000);

        euclideanGraph.buildRandomRoute();
        euclideanGraph.setRouteLength();
        euclideanGraph.printGraph();
        Thread.sleep(1000);

        euclideanGraph.buildTwoOptRoute();
        euclideanGraph.setRouteLength();
        euclideanGraph.printGraph();
        Thread.sleep(5000);
    }

    @Test
    public void  ThreeAlgorithmTest2() throws InterruptedException {
        for (int i = 0; i < 10; i++)
        {
            System.out.println( i + ".");
            EuclideanGraph euclideanGraph = new EuclideanGraph((i + 1) * 100, false);

            final long GreedyRouteStartTime = System.nanoTime();
            euclideanGraph.buildGreedyRoute();
            final long GreedyRouteEndTime = System.nanoTime();
            System.out.println("GreedyRoute execution time: " + (GreedyRouteEndTime - GreedyRouteStartTime));
            euclideanGraph.setRouteLength();
            System.out.println("GreedyRoute length: " + euclideanGraph.getRouteLength());

            final long randomStartTime = System.nanoTime();
            euclideanGraph.buildRandomRoute();
            final long randomEndTime = System.nanoTime();
            System.out.println("Random execution time: " + (randomEndTime - randomStartTime));
            euclideanGraph.setRouteLength();
            System.out.println("Random length: " + euclideanGraph.getRouteLength());

            final long twoOptStartTime = System.nanoTime();
            euclideanGraph.buildTwoOptRoute();
            final long twoOptEndTime = System.nanoTime();
            System.out.println("TwoOptSwap execution time: " + (twoOptEndTime - twoOptStartTime));
            euclideanGraph.setRouteLength();
            System.out.println("TwoOptSwap length: " + euclideanGraph.getRouteLength());
            System.out.println("");
        }
    }

    @Test
    public void statistics() throws InterruptedException
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
                EuclideanGraph euclideanGraph = new EuclideanGraph((i + 1) * 100, false);

                euclideanGraph.buildGreedyRoute();
                euclideanGraph.setRouteLength();
                greedyLength[i][j] = euclideanGraph.getRouteLength();

                euclideanGraph.buildTwoOptRoute();
                euclideanGraph.setRouteLength();
                twoOptSwapLength1[i][j] = euclideanGraph.getRouteLength();

                euclideanGraph.buildRandomRoute();
                euclideanGraph.setRouteLength();
                randomLength[i][j] = euclideanGraph.getRouteLength();

                euclideanGraph.buildTwoOptRoute();
                euclideanGraph.setRouteLength();
                twoOptSwapLength2[i][j] = euclideanGraph.getRouteLength();


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

    @Test
    public void  randomDistanceGraphTest() throws InterruptedException {
        DistanceGraph distanceGraph = new DistanceGraph(200);
        distanceGraph.buildRandomRoute();
        distanceGraph.setRouteLength();
        System.out.println("Random Length: " + distanceGraph.getRouteLength());
        distanceGraph.buildTwoOptRoute();
        distanceGraph.setRouteLength();
        System.out.println("Two Opt 1 Length: " + distanceGraph.getRouteLength());
        distanceGraph.resetGraph();
        distanceGraph.buildGreedyRoute();
        distanceGraph.setRouteLength();
        System.out.println("Greedy Length: " + distanceGraph.getRouteLength());
        distanceGraph.buildTwoOptRoute();
        distanceGraph.setRouteLength();
        System.out.println("Two Opt 2 Length: " + distanceGraph.getRouteLength());

    }

    @Test
    public void distanceStatistics() throws InterruptedException
    {
        int intervals = 10;
        int timesTestRuns = 100;
        double[][] randomLength = new double[intervals][timesTestRuns];
        double[][] greedyLength = new double[intervals][timesTestRuns];
        double[][] twoOptSwapLength1 = new double[intervals][timesTestRuns];
        double[][] twoOptSwapLength2 = new double[intervals][timesTestRuns];
        for (int i = 0; i < intervals; i++)
        {
            System.out.println(i);
            for (int j = 0; j < timesTestRuns; j++)
            {
                DistanceGraph distanceGraph = new DistanceGraph((i + 1) * 100);

                distanceGraph.buildGreedyRoute();
                distanceGraph.setRouteLength();
                greedyLength[i][j] = distanceGraph.getRouteLength();

                distanceGraph.buildTwoOptRoute();
                distanceGraph.setRouteLength();
                twoOptSwapLength1[i][j] = distanceGraph.getRouteLength();

                distanceGraph.buildRandomRoute();
                distanceGraph.setRouteLength();
                randomLength[i][j] = distanceGraph.getRouteLength();

                distanceGraph.buildTwoOptRoute();
                distanceGraph.setRouteLength();
                twoOptSwapLength2[i][j] = distanceGraph.getRouteLength();


            }
        }
        System.out.println("Random Length:");
        for (int i = 0; i < intervals; i++)
        {
            System.out.printf("\n");
            for (int j = 0; j < timesTestRuns; j++)
            {
                System.out.println(randomLength[i][j]);
            }
        }

        System.out.printf("\n\n");

        System.out.println("Two opt swap 2 Length:");
        for (int i = 0; i < intervals; i++)
        {
            System.out.printf("\n");
            for (int j = 0; j < timesTestRuns; j++)
            {
                System.out.println(twoOptSwapLength2[i][j]);
            }
        }

        System.out.printf("\n\n");

        System.out.println("Greedy Length:");
        for (int i = 0; i < intervals; i++)
        {
            System.out.printf("\n");
            for (int j = 0; j < timesTestRuns; j++)
            {
                System.out.println(greedyLength[i][j]);
            }
        }

        System.out.printf("\n\n");

        System.out.println("Two opt swap 1 Length:");
        for (int i = 0; i < intervals; i++)
        {
            System.out.printf("\n");
            for (int j = 0; j < timesTestRuns; j++)
            {
                System.out.println(twoOptSwapLength1[i][j]);
            }
        }
    }
}
