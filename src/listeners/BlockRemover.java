package listeners;

import core.Game;
import counters.Counter;
import sprites.Ball;
import sprites.Block;

/**
 * BlockRemover is a HitListener that is responsible for removing blocks from the game
 * when they are hit by a ball of a different color, and updating the counter that tracks
 * the number of remaining blocks.
 */
public class BlockRemover implements HitListener {
    private final Game game;
    private final Counter remainingBlocks;
    /**
     * Constructs a BlockRemover with the given game and block counter.
     * @param game the game instance from which blocks will be removed
     * @param remainingBlocks a counter tracking the number of remaining blocks in the game
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (!beingHit.ballColorMatch(hitter)) {
            hitter.setColor(beingHit.getColor());
            beingHit.removeFromGame(game);
            beingHit.removeHitListener(this);
            remainingBlocks.decrease(1);
        }
    }


}