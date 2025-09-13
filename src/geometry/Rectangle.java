package geometry;
import java.util.ArrayList;
import java.util.List;

/**
 * The Rectangle class represents a rectangle defined by its upper-left corner,
 * width, and height. It is used for defining collision boundaries of objects
 * such as blocks and paddles in the game. It also provides methods for
 * computing intersection points with lines.
 */
public class Rectangle {
    private final double width;
    private final double height;
    private final Point upperLeft;

    /**
     * Constructs a new rectangle given its upper-left point, width, and height.
     * @param upperLeft the upper-left corner of the rectangle
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }
    /**
     * Returns a list of intersection points between the given line and this rectangle.
     *
     * <p>The method checks all four edges of the rectangle and collects the points
     * where the given line intersects the rectangle's sides. If the line does not
     * intersect the rectangle at all, an empty list is returned.</p>
     * @param line the line to check for intersection with this rectangle
     * @return a list of intersection points (possibly empty) with the rectangle
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        List<Point> points = new ArrayList<>();

        // Define the four edges:
        Line top = new Line(upperLeft.getX(), upperLeft.getY(),
                upperLeft.getX() + width, upperLeft.getY());
        Line bottom = new Line(upperLeft.getX(), upperLeft.getY() + height,
                upperLeft.getX() + width, upperLeft.getY() + height);
        Line left = new Line(upperLeft.getX(), upperLeft.getY(),
                upperLeft.getX(), upperLeft.getY() + height);
        Line right = new Line(upperLeft.getX() + width, upperLeft.getY(),
                upperLeft.getX() + width, upperLeft.getY() + height);

        for (Line side : new Line[]{top, bottom, left, right}) {
            Point ip = line.intersectionWith(side);
            if (ip != null) {
                points.add(ip);
            }
        }
        return points;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return the width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return the height of the rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the upper left corner point of the rectangle.
     * @return the upper left of the rectangle
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
    /**
     * Returns the upper-right point of the rectangle.
     *
     * @return the upper-right corner as a Point.
     */
    public Point getUpperRight() {
        double x = this.upperLeft.getX() + this.width;
        double y = this.upperLeft.getY();
        return new Point(x, y);
    }

    /**
     * Returns the bottom-right point of the rectangle.
     *
     * @return the bottom-right corner as a Point.
     */
    public Point getBottomRight() {
        double x = this.upperLeft.getX() + this.width;
        double y = this.upperLeft.getY() + this.height;
        return new Point(x, y);
    }

    /**
     * Returns the bottom-left point of the rectangle.
     *
     * @return the bottom-left corner as a Point.
     */
    public Point getBottomLeft() {
        double x = this.upperLeft.getX();
        double y = this.upperLeft.getY() + this.height;
        return new Point(x, y);
    }

    /**
     * Returns the top edge of the rectangle as a Line.
     *
     * @return a Line representing the top side of the rectangle.
     */
    public Line top() {
        Point start = this.upperLeft;
        Point end = this.getUpperRight();
        return new Line(start, end);
    }

    /**
     * Returns the right edge of the rectangle as a Line.
     *
     * @return a Line representing the right side of the rectangle.
     */
    public Line right() {
        Point start = this.getUpperRight();
        Point end = this.getBottomRight();
        return new Line(start, end);
    }

    /**
     * Returns the left edge of the rectangle as a Line.
     *
     * @return a Line representing the left side of the rectangle.
     */
    public Line left() {
        Point start = this.upperLeft;
        Point end = this.getBottomLeft();
        return new Line(start, end);
    }

    /**
     * Returns the bottom edge of the rectangle as a Line.
     *
     * @return a Line representing the bottom side of the rectangle.
     */
    public Line bottom() {
        Point start = this.getBottomLeft();
        Point end = this.getBottomRight();
        return new Line(start, end);
    }
}
