/**
 * Created by Noam on 3/15/2017.
 */
public class DistanceNode {

    private int id;
    private DistanceNode next;

    public DistanceNode(int id) {this.id = id; next = null;}

    public DistanceNode getNext(){return next;}

    public void setNext(DistanceNode next) {this.next = next;}

    public  int getId(){return id;}

    public void resetNode(){next = null;}
}
