package sprites;
import biuoop.DrawSurface;
import counters.Counter;

import java.awt.Color;
/**
 * ScoreIndicator is a Sprite that displays the current score at the top of the screen.
 */
public class ScoreIndicator implements Sprite {
    private final Counter score;
    /**
     * Constructs a ScoreIndicator with the given score counter.
     * @param score the counter tracking the current score
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(350, 18, "Score: " + score.getValue(), 16);
    }

    @Override
    public void timePassed() {
    }
}
