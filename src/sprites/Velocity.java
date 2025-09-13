package sprites;

import geometry.Point;

/**
 * The Velocity class represents the change in position.
 */
public class Velocity {
    private final double dx;
    private final double dy;

    /**
     * Constructs a velocity object.
     * @param dx the change in x
     * @param dy the change in y
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Applies the velocity to a given point and returns a new point.
     * @param p the point to apply the velocity to
     * @return a new Point after applying
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * Returns the horizontal component of the velocity.
     * @return dx value
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Returns the vertical component of the velocity.
     * @return dy value
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Creates a Velocity based on angle and speed.
     * @param angle the direction of movement in degrees
     * @param speed the magnitude
     * @return a new Velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radians = Math.toRadians(angle);
        double dx = speed * Math.sin(radians);
        double dy = -speed * Math.cos(radians);

        return new Velocity(dx, dy);
    }
}