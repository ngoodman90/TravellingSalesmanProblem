/**
 * Created by Noam on 12/1/2016.
 */
public class RandomRoute extends AbstractTspAlgorithm
{
    public RandomRoute(Graph graph)
    {
        super(graph);
        super.getGraph().buildRandomRoute();
//      super.getGraph().removeLongestEdge();
        this.calculateRouteLength();
    }

    @Override
    public void calculateRouteLength()
    {
        int routeLength = 0;
        for (Node node : super.getGraph().getNodes())
            routeLength += node.getDistanceToNext();
        super.setRouteLength(routeLength);
    }
}
