package geometry;
import java.util.List;
/**
 * Represents a 2D line by a start and end point.
 */
public class Line {
    private final Point start;
    private final Point end;

    /**
     * Constructors.
     * @param start the starting point
     * @param end the ending point
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructors.
     * @param x1 x start point
     * @param y1 y start point
     * @param x2 x end point
     * @param y2 y end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * @return the start point of the line.
     */
    public Point getStart() {
        return start;
    }

    /**
     * @return a copy of the start point
     */
    public Point start() {
        return new Point(start.getX(), start.getY());
    }

    /**
     * checks if two lines are parallel.
     *
     * @param other another line
     * @return true if the lines are parallel.
     */
    public boolean areParallel(Line other) {
        double dx1 = this.end.getX() - this.start.getX();
        double dy1 = this.end.getY() - this.start.getY();
        double dx2 = other.end.getX() - other.start.getX();
        double dy2 = other.end.getY() - other.start.getY();
        return MathUtils.doubleEquals(dy1 * dx2, dy2 * dx1);
    }

    /**
     * Checks if point q lies on the segment p to r.
     *
     * @param p start of the segment
     * @param q the point to check
     * @param r end of the segment
     * @return true if q lies on segment p to r.
     */
    public boolean onSegment(Point p, Point q, Point r) {
        return q.getX() <= Math.max(p.getX(), r.getX())
                && q.getX() >= Math.min(p.getX(), r.getX())
                && q.getY() <= Math.max(p.getY(), r.getY())
                && q.getY() >= Math.min(p.getY(), r.getY());
    }

    /**
     * determines the orientation of an ordered 3 points.
     *
     * @param p first point
     * @param q second point
     * @param r third point
     * @return 0 if on the same, 1 if on the right, 2 if on the left
     */
    public int orientation(Point p, Point q, Point r) {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX())
                - (q.getX() - p.getX()) * (r.getY() - q.getY());
        if (MathUtils.doubleEquals(val, 0)) {
            return 0;
        }
        return (val > 0) ? 1 : 2;
    }

    /**
     * checks if this line and another overlap.
     *
     * @param other the other line
     * @return true if the lines overlap
     */
    public boolean doOverlap(Line other) {
        if (areParallel(other)) {
            if (orientation(this.start, this.end, other.start) == 0
                    && orientation(this.start, this.end, other.end) == 0) {
                return onSegment(this.start, other.start, this.end)
                        || onSegment(this.start, other.end, this.end)
                        || onSegment(other.start, this.start, other.end)
                        || onSegment(other.start, this.end, other.end);
            }
        }
        return false;
    }

    /**
     * checks if this line intersects with another line.
     *
     * @param other the other line
     * @return true if the lines intersect.
     */
    public boolean isIntersecting(Line other) {
        Point p1 = this.start;
        Point q1 = this.end;
        Point p2 = other.start;
        Point q2 = other.end;

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4) {
            return true;
        }

        if (o1 == 0 && onSegment(p1, p2, q1)) {
            return true;
        }
        if (o2 == 0 && onSegment(p1, q2, q1)) {
            return true;
        }
        if (o3 == 0 && onSegment(p2, p1, q2)) {
            return true;
        }
        return o4 == 0 && onSegment(p2, q1, q2);
    }

    /**
     * Calculates the intersection point with another line.
     *
     * @param other the other line to check intersection with.
     * @return the intersection point, or null if the lines don't intersect.
     */
    public Point intersectionWith(Line other) {
        if (!isIntersecting(other) || doOverlap(other)) {
            return null;
        }

        double x1 = this.start.getX();
        double y1 = this.start.getY();
        double x2 = this.end.getX();
        double y2 = this.end.getY();

        double x3 = other.start.getX();
        double y3 = other.start.getY();
        double x4 = other.end.getX();
        double y4 = other.end.getY();

        // Both lines are vertical
        if (MathUtils.doubleEquals(x1, x2) && MathUtils.doubleEquals(x3, x4)) {
            return null;
        }

        // This line is vertical
        if (MathUtils.doubleEquals(x1, x2)) {
            if (MathUtils.doubleEquals(x3, x4)) {
                return null;
            }
            double slope2 = (y4 - y3) / (x4 - x3);
            double py = slope2 * (x1 - x3) + y3;
            Point intersection = new Point(x1, py);
            if (onSegment(this.start, intersection, this.end) && onSegment(other.start, intersection, other.end)) {
                return intersection;
            }
            return null;
        }

        // Other line is vertical
        if (MathUtils.doubleEquals(x3, x4)) {
            double slope1 = (y2 - y1) / (x2 - x1);
            double py = slope1 * (x3 - x1) + y1;
            Point intersection = new Point(x3, py);
            if (onSegment(this.start, intersection, this.end) && onSegment(other.start, intersection, other.end)) {
                return intersection;
            }
            return null;
        }

        // General case using Cramer's Rule
        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (MathUtils.doubleEquals(denominator, 0)) {
            return null;
        }

        double numeratorX = ((x1 * y2 - y1 * x2) * (x3 - x4)) - ((x1 - x2) * (x3 * y4 - y3 * x4));
        double numeratorY = ((x1 * y2 - y1 * x2) * (y3 - y4)) - ((y1 - y2) * (x3 * y4 - y3 * x4));

        double px = numeratorX / denominator;
        double py = numeratorY / denominator;
        Point intersection = new Point(px, py);

        if (onSegment(this.start, intersection, this.end) && onSegment(other.start, intersection, other.end)) {
            return intersection;
        }

        return null;
    }

    /**
     * Returns the closest intersection point between this line and the given rectangle,
     * measured from the start point of the line.
     *
     *<p>If there are no intersection points between the line and the rectangle,
     * this method returns {@code null}.</p>
     * @param rect the rectangle to check for intersections with this line
     * @return the closest intersection point to the start of the line,
     *         or {@code null} if there is no intersection
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> points = rect.intersectionPoints(this);
        if (points.isEmpty()) {
            return null;
        }

        Point closestPoint = null;
        double smallestDistance = Double.MAX_VALUE;
        Point startOfLine = getStart();

        for (Point p : points) {
            double distance = startOfLine.distance(p);
            if (distance < smallestDistance) {
                smallestDistance = distance;
                closestPoint = p;
            }
        }

        return closestPoint;
        }
    /**
     * Checks if the given point lies on this line segment.
     * @param point the point to check
     * @return true if the point lies on this line segment, false otherwise
     */
    public boolean isPointOnLine(Point point) {
        double epsilon = 0.15;

        // Check that point lies between start and end
        boolean inRangeX = (point.getX() >= Math.min(start.getX(), end.getX()) - epsilon)
                && (point.getX() <= Math.max(start.getX(), end.getX()) + epsilon);
        boolean inRangeY = (point.getY() >= Math.min(start.getY(), end.getY()) - epsilon)
                && (point.getY() <= Math.max(start.getY(), end.getY()) + epsilon);

        // Check collinearity
        double area = Math.abs(
                (end.getX() - start.getX()) * (point.getY() - start.getY())
                        - (point.getX() - start.getX()) * (end.getY() - start.getY())
        );

        return area < epsilon && inRangeX && inRangeY;
    }





}