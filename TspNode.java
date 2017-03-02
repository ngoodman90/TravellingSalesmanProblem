/**
 * Created by Noam on 12/1/2016.
 */
public class TspNode
{
    private String name;
    private int id;
    private DoublePoint location;
    private TspNode next;
    private double distanceToNext;

    TspNode(String name, int id, DoublePoint location)
    {
        this.name = name;
        this.id = id;
        this.location = location;
        this.next = null;
        this.distanceToNext = 0.0;
    }

    TspNode(String name, int id, DoublePoint location, TspNode next, double distanceToNext)
    {
        this.name = name;
        this.id = id;
        this.location = location;
        this.next = next;
        this.distanceToNext = distanceToNext;
    }

    public String getName() {return name;}

    public int getId() {return id;}

    public DoublePoint getLocation() {
        return location;
    }

    public TspNode getNext() {
        return next;
    }

    public void setNext(TspNode next) {
        this.next = next;
        distanceToNext = distanceToNode(next);
    }

    public double getDistanceToNext() {
        return distanceToNext;
    }

    public double distanceToNode(TspNode otherTspNode){ return this.location.distanceToPoint(otherTspNode.getLocation());}

    public void resetNode()
    {
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
    public TspNode clone() {return new TspNode(name, id, location, next, distanceToNext);}

    public boolean equals(TspNode otherTspNode){ return id == otherTspNode.getId();}
}