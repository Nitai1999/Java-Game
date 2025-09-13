package collidables;

import geometry.Point;

/**
 * The CollisionInfo class holds information about a collision:
 * the point at which the collision occurs and the collidable object involved.
 */
public class CollisionInfo {
    private final Point point;
    private final Collidable object;

    /**
     * Constructs a CollisionInfo object with the given collision point and collidable object.
     *
     * @param point  the point where the collision occurs
     * @param object the collidable object that is involved in the collision
     */
    public CollisionInfo(Point point, Collidable object) {
        this.point = point;
        this.object = object;
    }

    /**
     * Returns the point at which the collision occurs.
     * @return the collision point
     */
    public Point collisionPoint() {
        return point;
    }

    /**
     * Returns the collidable object involved in the collision.
     * @return the collidable object
     */
    public Collidable collisionObject() {
        return object;
    }
}
