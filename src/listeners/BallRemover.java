package listeners;

import core.Game;
import counters.Counter;
import sprites.Ball;
import sprites.Block;
/**
 * BallRemover is a HitListener that removes balls from the game when they hit a designated block
 * (typically a "death region"), and updates the counter tracking the number of remaining balls.
 */
public class BallRemover implements HitListener {
    private final Game game;
    private final Counter remainingBalls;
    /**
     * Constructs a BallRemover with the given game and ball counter.
     * @param game the game instance from which balls should be removed
     * @param remainingBalls a counter tracking the number of remaining balls in the game
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(game);
        remainingBalls.decrease(1);
    }
}

