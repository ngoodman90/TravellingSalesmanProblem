import java.util.ArrayList;

/**
 * Created by Noam on 12/10/2016.
 */
public abstract class AbstractTspAlgorithm {

    private ArrayList<Node> nodes;
    private int routeLength;

    public AbstractTspAlgorithm(ArrayList<Node> nodes)
    {
        this.nodes = nodes;
        this.routeLength = 0;
    }

    public abstract void buildRoute();


    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public int getRouteLength() {return routeLength;}

    public void setRouteLength()
    {
        int routeLength = 0;
        for (Node node : this.nodes)
        {
            routeLength += node.getDistanceToNext();
        }
        this.routeLength = routeLength;
    }




}
