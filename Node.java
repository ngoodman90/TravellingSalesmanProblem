/**
 * Created by Noam on 12/1/2016.
 */
public class Node
{
    private String name;
    private Point location;
    private Node prev;
    private Node next;
    private int distanceToNext;

    Node(String name, int x, int y)
    {
        this.name = name;
        this.location = new Point(x, y);
        this.prev = null;
        this.next = null;
        this.distanceToNext = Integer.MAX_VALUE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
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
        this.distanceToNext = this.location.distanceToPoint(nextNode.getLocation());
    }

    public void resetNode()
    {
        this.prev = null;
        this.next = null;
        this.distanceToNext = Integer.MAX_VALUE;
    }
}