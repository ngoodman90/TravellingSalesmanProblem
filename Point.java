/**
 * Created by Noam on 12/1/2016.
 */
public class Point
{
    private int x;
    private int y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX(){return this.x;}

    public int getY(){return this.y;}

    public int distanceToPoint(Point p)
    {
        int deltaX = this.x - p.getX();
        int deltaY = this.y - p.getY();
        return (int)Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    public String toString(){return this.getX() + " " + this.getY();}
}