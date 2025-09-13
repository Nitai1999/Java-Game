package collidables;

import geometry.Point;
import geometry.Rectangle;
import sprites.Ball;
import sprites.Velocity;

/**
 * The Collidable interface should be implemented by any object that can be collided with.
 * It defines methods to get the object's collision shape and to handle collision behavior.
 */
public interface Collidable {

    /**
     * Returns the collision shape of the object, represented by a rectangle.
     * @return the Rectangle that defines the object's collision boundaries
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that a collision occurred at a given point with a given velocity.
     * Returns the new velocity expected after the hit, based on the object's response to the collision.
     * @param collisionPoint  the point where the collision occurred
     * @param currentVelocity the velocity of the object before the collision
     * @param hitter the ball that hits.
     * @return the new velocity after the collision
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}

