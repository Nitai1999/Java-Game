package collidables;
import geometry.Line;
import geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameEnvironment class holds a collection of collidable objects.
 * It is responsible for managing collision detection between objects and the game environment.
 */
public class GameEnvironment {
    private final List<Collidable> collidables = new ArrayList<>();

    /**
     * Adds the given collidable to the environment.
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * Returns the list of all collidable objects in the game environment.
     * @return a list of Collidable objects.
     */
    public List<Collidable> getCollidables() {
        return collidables;
    }

    /**
     * Checks for the closest collision along a given trajectory.
     * If no collision occurs with any collidable in the environment, returns null.
     * Otherwise, returns information about the closest collision.
     * @param trajectory the path to check for collisions
     * @return a CollisionInfo object representing the closest collision, or null
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo closest = null;
        double minDistance = Double.MAX_VALUE;

        for (Collidable c : collidables) {
            Point p = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (p != null) {
                double dist = trajectory.start().distance(p);
                if (dist < minDistance) {
                    minDistance = dist;
                    closest = new CollisionInfo(p, c);
                }
            }
        }
        return closest;
    }
    /**
     * Removes the given collidable object from the environment.
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }

}

