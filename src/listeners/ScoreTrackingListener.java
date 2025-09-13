package listeners;

import counters.Counter;
import sprites.Ball;
import sprites.Block;
/**
 * ScoreTrackingListener is a HitListener that updates the game score.
 * whenever a block is hit and removed
 */
public class ScoreTrackingListener implements HitListener {
    private final Counter currentScore;
    /**
     * Constructs a ScoreTrackingListener with the given score counter.
     * @param scoreCounter the counter used to track the current score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (!beingHit.ballColorMatch(hitter)) {
            currentScore.increase(5);
        }
    }
}
