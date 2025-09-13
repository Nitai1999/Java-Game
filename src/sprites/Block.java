package sprites;
import biuoop.DrawSurface;
import collidables.Collidable;
import core.Game;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import listeners.HitListener;
import listeners.HitNotifier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The Block class represents a rectangular block in the game.
 * Blocks can be collided with and drawn to the screen.
 */
public class Block implements Sprite, Collidable, HitNotifier {
    private final Rectangle rectangle;
    private final Color color;
    private final List<HitListener> hitListeners;


    /**
     * Constructs a block with a given rectangle and color.
     * @param rect  the rectangle defining the block's shape and position
     * @param color the color of the block
     */
    public Block(Rectangle rect, Color color) {
        this.hitListeners = new ArrayList<>();
        this.rectangle = rect;
        this.color = color;
    }
    /**
     * Returns the color of the block.
     *
     * @return the block's color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Returns the "collision shape" of the object.
     * @return the rectangle representing the block's shape
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return rectangle;
    }

    /**
     * Notifies the block that it was hit and returns the new velocity
     * after the collision based on which edge was hit.
     * @param collisionPoint  the point where the collision occurred
     * @param currentVelocity the velocity before the hit
     * @return the new velocity after the hit
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double x = currentVelocity.getDx();
        double y = currentVelocity.getDy();

        // Check which edge was hit and change velocity accordingly
        Rectangle r = this.rectangle;
        Line top = r.top();
        Line bottom = r.bottom();
        Line left = r.left();
        Line right = r.right();
        //Corner hits
        if ((top.isPointOnLine(collisionPoint) && left.isPointOnLine(collisionPoint))
        || left.isPointOnLine(collisionPoint) && bottom.isPointOnLine(collisionPoint)
        || bottom.isPointOnLine(collisionPoint) && right.isPointOnLine(collisionPoint)
        || right.isPointOnLine(collisionPoint) && top.isPointOnLine(collisionPoint)) {
            x = -x;
            y = -y;
        } else if (top.isPointOnLine(collisionPoint) || bottom.isPointOnLine(collisionPoint)) {
            y = -y;
        } else if (left.isPointOnLine(collisionPoint) || right.isPointOnLine(collisionPoint)) {
            x = -x;
        }
        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }
        return new Velocity(x, y);
    }

    /**
     * Notifies the block that time has passed.
     */
    @Override
    public void timePassed() { }

    /**
     * Draws the block on the given DrawSurface.
     * @param d the drawing surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        int x = (int) rectangle.getUpperLeft().getX();
        int y = (int) rectangle.getUpperLeft().getY();
        int width = (int) rectangle.getWidth();
        int height = (int) rectangle.getHeight();

        // Fill block
        d.setColor(this.color);
        d.fillRectangle(x, y, width, height);

        // Draw border
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, width, height);
    }

    /**
     * Adds this block to the game as both a Sprite and a Collide.
     * @param game the game to add this block to
     */
    public void addToGame(Game game) {
        game.addSprite(this);
        game.addCollidable(this);
    }
    @Override
    public String toString() {
        return "Block at " + rectangle.getUpperLeft();
    }
    /**
     * Checks whether the color of the given ball matches the color of this block.
     *
     * @param ball the ball whose color is to be compared
     * @return true if the ball's color matches the block's color; false otherwise
     */
    public boolean ballColorMatch(Ball ball) {
        return this.color.equals(ball.getColor());
    }
    /**
     * Removes this block from the game by removing it from both the collidables
     * and the sprites collections.
     *
     * @param game the game instance from which the block should be removed
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
    /**
     * Adds a HitListener to the block.
     * The listener will be notified whenever the block is hit.
     *
     * @param hl the HitListener to be added
     */
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }
    /**
     * Removes a HitListener from the block.
     * The listener will no longer be notified of hit events.
     *
     * @param hl the HitListener to be removed
     */
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }
}
