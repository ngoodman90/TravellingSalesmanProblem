/**
 * Created by Noam on 12/1/2016.
 */
public class EuclideanNode
{
    private String name;
    private int id;
    private DoublePoint location;
    private EuclideanNode next;
    private double distanceToNext;

    EuclideanNode(String name, int id, DoublePoint location)
    {
        this.name = name;
        this.id = id;
        this.location = location;
        this.next = null;
        this.distanceToNext = 0.0;
    }

    EuclideanNode(String name, int id, DoublePoint location, EuclideanNode next, double distanceToNext)
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

    public EuclideanNode getNext() {
        return next;
    }

    public void setNext(EuclideanNode next) {
        this.next = next;
        distanceToNext = distanceToNode(next);
    }

    public double getDistanceToNext() {
        return distanceToNext;
    }

    public double distanceToNode(EuclideanNode otherEuclideanNode){ return this.location.distanceToPoint(otherEuclideanNode.getLocation());}

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
    public EuclideanNode clone() {return new EuclideanNode(name, id, location, next, distanceToNext);}

    public boolean equals(EuclideanNode otherEuclideanNode){ return id == otherEuclideanNode.getId();}
}