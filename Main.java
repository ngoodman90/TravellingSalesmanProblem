import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Noam on 12/1/2016.
 */
public class Main {

    public static void main(String args[])
    {
        ArrayList<Node> nodes = createNodes(new Scanner(System.in));
        RandomRoute randomRoute = new RandomRoute(nodes);
        randomRoute.buildRoute();
        randomRoute.setRouteLength();
        resetNodes(nodes);
    }

    public static ArrayList<Node> createNodes(Scanner scanner)
    {
        int N = scanner.nextInt(); // the total number of nodes
        scanner.nextLine();
        ArrayList<Node> nodes = new ArrayList<>(N);
        for (int i = 0; i < N; i++)
        {
            String name = scanner.nextLine();
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            scanner.nextLine();//read newline char
            nodes.add(new Node(name, x, y));
        }
        return nodes;
    }

    public static void resetNodes(ArrayList<Node> nodes) {nodes.forEach(node -> node.resetNode());}
}
