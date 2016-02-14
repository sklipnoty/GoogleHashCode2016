package domain;

public class Coordinate
{
    private int x;
    private int y;

    public Coordinate(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    @Override
    public String toString()
    {
        return "Coordinate{" + "x=" + x + ", y=" + y + '}';
    }

    public int distance(Coordinate that)
    {
        int deltaX = this.getX() - that.getX();
        int deltaY = this.getY() - that.getY();

        int deltasSquaredSum = deltaX * deltaX + deltaY * deltaY;

        return (int) Math.ceil(Math.sqrt(deltasSquaredSum));
    }

}
