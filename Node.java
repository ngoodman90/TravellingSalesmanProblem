import java.util.Comparator;

/**
 * Created by Noam on 12/1/2016.
 */
public class Node
{
    private String name;
    private int id;
    private Point location;
    private Node prev;
    private Node next;
    private int distanceToNext;

    Node(String name, int id, Point location)
    {
        this.name = name;
        this.id = id;
        this.location = location;
        this.prev = null;
        this.next = null;
        this.distanceToNext = 0;
    }

    public String getName() {return name;}

    public int getId() {return id;}

    public Point getLocation() {
        return location;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public int getDistanceToNext() {
        return distanceToNext;
    }

    public void setDistanceToNext(Node nextNode) {
        if (nextNode == null)
            this.distanceToNext = 0;
        else
            this.distanceToNext = this.location.distanceToPoint(nextNode.getLocation());
    }

    public int distanceToNode(Node otherNode){ return this.location.distanceToPoint(otherNode.getLocation());}

    public void resetNode()
    {
        this.prev = null;
        this.next = null;
        this.distanceToNext = 0;
    }

    @Override
    public String toString()
    {
        return String.join("\n"
                , "Name :" + this.name
                , "Location: " + this.location.toString()
                , (next!= null ? "Next: " + next.getName() : "")
                , "Distance: " + this.distanceToNext
                , "\n");
    }

    @Override
    public Node clone() {return new Node(this.name, this.id, this.location);}
}