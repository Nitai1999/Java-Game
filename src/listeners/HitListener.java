package listeners;

import sprites.Ball;
import sprites.Block;
/**
 * A HitListener is notified of hit events: whenever a Block is hit by a Ball.
 * Implementing classes can define custom behavior such as removing game elements
 * or updating counters when a collision occurs.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit the Block that was hit
     * @param hitter the Ball that hit the block
     */
    void hitEvent(Block beingHit, Ball hitter);
}

