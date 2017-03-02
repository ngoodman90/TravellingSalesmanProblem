/**
 * Created by Noam on 3/2/2017.
 */
public class DoublePoint
{
    private double x;
    private double y;

    public DoublePoint(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX(){return this.x;}

    public double getY(){return this.y;}

    public double distanceToPoint(DoublePoint p)
    {
        double deltaX = this.x - p.getX();
        double deltaY = this.y - p.getY();
        double delta = Math.pow(deltaX, 2) + Math.pow(deltaY, 2);
        return Math.sqrt(delta);
    }

    public String toString(){return this.getX() + " " + this.getY();}
}

