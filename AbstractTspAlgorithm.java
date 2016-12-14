import java.util.ArrayList;

/**
 * Created by Noam on 12/10/2016.
 */
public abstract class AbstractTspAlgorithm {


    private Graph graph;
    private int routeLength;

    public AbstractTspAlgorithm(Graph graph)
    {
        this.graph = graph;
        this.routeLength = 0;
    }

    public Graph getGraph() {return graph;}

    public int getRouteLength() {return routeLength;}

    public void setRouteLength(int routeLength){this.routeLength = routeLength;}

    public abstract void calculateRouteLength();
}
