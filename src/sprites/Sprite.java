package sprites;
import biuoop.DrawSurface;

/**
 * The Sprite interface represents a game object that can be drawn on the screen
 * and can be notified when time has passed. Classes implementing this interface
 * must define how they are drawn and updated over time.
 */
public interface Sprite {

    /**
     * Draws the sprite on the given DrawSurface.
     *
     * @param d the surface to draw the sprite on
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the sprite that a unit of time has passed,
     * so it can update its state accordingly.
     */
    void timePassed();
}

