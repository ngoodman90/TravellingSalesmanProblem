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
        TspGraph tspGraph = new TspGraph(numOfNodes, true);

        tspGraph.buildGreedyRoute();
        tspGraph.setRouteLength();
        System.out.println("nearestNeighbourRoute length: " + tspGraph.getRouteLength());
        tspGraph.printGraph();
        Thread.sleep(5000);

        tspGraph.buildRandomRoute();
        tspGraph.setRouteLength();
        System.out.println("Random length: " + tspGraph.getRouteLength());
        tspGraph.printGraph();
        Thread.sleep(5000);

        tspGraph.buildTwoOptRoute();
        tspGraph.setRouteLength();
        System.out.println("twoOptSwap length: " + tspGraph.getRouteLength());
        Thread.sleep(3000);
    }
}

