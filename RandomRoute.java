import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Noam on 12/1/2016.
 */
public class RandomRoute extends AbstractTspAlgorithm
{
    public RandomRoute(ArrayList<Node> nodes) {super(nodes);}

    public void buildRoute()
    {
        for (Node node : super.getNodes())
        {
            int randInt;
            Random rand = new Random();
            do {randInt = rand.nextInt(super.getNodes().size());}
            while (super.getNodes().get(randInt).getPrev() != null);
            Node next = super.getNodes().get(randInt);
            node.setNext(next);
            next.setPrev(node);
            next.setDistanceToNext(node);
        }
    }
}
