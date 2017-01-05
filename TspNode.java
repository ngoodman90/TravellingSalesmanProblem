/**
 * Created by Noam on 12/1/2016.
 */
public class TspNode
{
    private String name;
    private int id;
    private Point location;
    private TspNode prev;
    private TspNode next;
    private double distanceToNext;

    TspNode(String name, int id, Point location)
    {
        this.name = name;
        this.id = id;
        this.location = location;
        this.prev = null;
        this.next = null;
        this.distanceToNext = 0.0;
    }

    TspNode(String name, int id, Point location, TspNode prev, TspNode next, double distanceToNext)
    {
        this.name = name;
        this.id = id;
        this.location = location;
        this.prev = prev;
        this.next = next;
        this.distanceToNext = distanceToNext;
    }

    public String getName() {return name;}

    public int getId() {return id;}

    public Point getLocation() {
        return location;
    }

    public TspNode getPrev() {return prev;}

    public void setPrev(TspNode prev) {this.prev = prev;}

    public TspNode getNext() {
        return next;
    }

    public void setNext(TspNode next) {
        this.next = next;
        next.setPrev(this);
        distanceToNext = distanceToNode(next);
    }

    public void setNextNull(TspNode next)
    {
        this.next = null;
        next.setPrev(null);
        distanceToNext = 0.0;
    }

    public double getDistanceToNext() {
        return distanceToNext;
    }

    public void setDistanceToNext(TspNode nextTspNode) {
        if (nextTspNode == null)
            this.distanceToNext = 0.0;
        else
            this.distanceToNext = this.location.distanceToPoint(nextTspNode.getLocation());
    }

    public double distanceToNode(TspNode otherTspNode){ return this.location.distanceToPoint(otherTspNode.getLocation());}

    public void resetNode()
    {
        this.prev = null;
        this.next = null;
        this.distanceToNext = 0.0;
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
    public TspNode clone() {return new TspNode(name, id, location, prev, next, distanceToNext);}

    public boolean equals(TspNode otherTspNode){ return id == otherTspNode.getId();}
}