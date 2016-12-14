import java.util.ArrayList;

/**
 * Created by Noam on 12/10/2016.
 */
public class NearestNeighbourRoute extends AbstractTspAlgorithm {

    public NearestNeighbourRoute(Graph graph)
    {
        super(graph);
        super.getGraph().buildNearestNeighbourGraph();
        this.calculateRouteLength();
    }

    @Override
    public void calculateRouteLength() {
        ArrayList<Node> nodes = super.getGraph().getNodes();
        Node node = nodes.get(0);
        int routeLength = 0;
        while (node.getNext() != null)
        {
            routeLength += node.getDistanceToNext();
            node = node.getNext();
        }
        super.setRouteLength(routeLength);
    }
}
