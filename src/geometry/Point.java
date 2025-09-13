package geometry;
/**
 * A class that represents a 2D point and provides basic operations such as
 * distance calculation and equality check.
 */
public class Point {
    private final double x;
    private final double y;

    /**
     * Constructors.
     * @param x x coordinate
     * @param y y coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x.
     * @return the x value
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y.
     * @return the y value
     */
    public double getY() {
        return y;
    }

    /**
     * Calculates the distance from this point to another point.
     * @param other the other point
     * @return the distance
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.getX(), 2)
                + Math.pow(this.y - other.getY(), 2));
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}