import java.util.Scanner;

/**
 * Created by Noam on 12/1/2016.
 */
public class Main
{
    public static void main(String args[]) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Number of Nodes:");
        int numOfNodes = scanner.nextInt();

        int numberOfRuns = 100;

        double[] twoOptSwapLength1 = new double[numberOfRuns];
        double[] twoOptSwapLength2 = new double[numberOfRuns];
        double[] lengthDelta = new double[numberOfRuns];


        long[] twoOptTime1 = new long[numberOfRuns];
        long[] twoOptTime2 = new long[numberOfRuns];
        long[] timeDelta = new long[numberOfRuns];

        for (int i = 0; i < numberOfRuns; i++)
        {
            System.out.println(i);
            TspGraph tspGraph = new TspGraph(numOfNodes, false);

            final long twoOptStartTime1 = System.currentTimeMillis();
            tspGraph.buildGreedyRoute();
            tspGraph.setRouteLength();

            tspGraph.buildTwoOptRoute();
            tspGraph.setRouteLength();
            twoOptSwapLength1[i] = tspGraph.getRouteLength();
            final long twoOptEndTime1 = System.currentTimeMillis();
            twoOptTime1[i] = twoOptEndTime1 - twoOptStartTime1;

            final long twoOptStartTime2 = System.currentTimeMillis();
            tspGraph.buildRandomRoute();
            tspGraph.setRouteLength();

            tspGraph.buildTwoOptRoute();
            tspGraph.setRouteLength();
            twoOptSwapLength2[i] = tspGraph.getRouteLength();
            final long twoOptEndTime2 = System.currentTimeMillis();
            twoOptTime2[i] = twoOptEndTime2 - twoOptStartTime2;

            lengthDelta[i] = twoOptSwapLength2[i] - twoOptSwapLength1[i];
            timeDelta[i] = twoOptTime2[i] - twoOptTime1[i];
        }

        System.out.println("twoOptLength1\n");

        for (int i = 0; i < numberOfRuns; i++)
            System.out.println(twoOptSwapLength1[i]);

        System.out.println("\ntwoOptLength2\n");

        for (int i = 0; i < numberOfRuns; i++)
            System.out.println(twoOptSwapLength2[i]);

        System.out.println("\ntwoOptTime1\n");

        for (int i = 0; i < numberOfRuns; i++)
            System.out.println(twoOptTime1[i]);

        System.out.println("\ntwoOptTime2\n");

        for (int i = 0; i < numberOfRuns; i++)
            System.out.println(twoOptTime2[i]);

        System.out.println("\nDelta length: twoOpt2 - twoOpt1\n");

        for (int i = 0; i < numberOfRuns; i++)
            System.out.println(lengthDelta[i]);

        System.out.println("\nDelta time: twoOpt2 - twoOpt1\n");

        for (int i = 0; i < numberOfRuns; i++)
            System.out.println(timeDelta[i]);
    }
}

