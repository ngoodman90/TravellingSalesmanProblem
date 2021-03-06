/**
 * Created by Noam on 12/1/2016.
 */
public class IntPoint
{
    private int x;
    private int y;

    public IntPoint(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX(){return this.x;}

    public int getY(){return this.y;}

    public double distanceToPoint(IntPoint p)
    {
        int deltaX = this.x - p.getX();
        int deltaY = this.y - p.getY();
        double delta = Math.pow(deltaX, 2) + Math.pow(deltaY, 2);
        return Math.sqrt(delta);
    }

    public String toString(){return this.getX() + " " + this.getY();}
}