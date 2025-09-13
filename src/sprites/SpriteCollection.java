package sprites;
import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

/**
 * The SpriteCollection class manages a collection of Sprite objects.
 * It is responsible for updating and drawing all the sprites in the collection.
 */
public class SpriteCollection {
    private final List<Sprite> sprites = new ArrayList<>();

    /**
     * Adds a sprite to the collection.
     * @param s the sprite to be added
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Calls timePassed on all sprites in the collection.
     * This updates each sprite according to the passage of time.
     */
    public void notifyAllTimePassed() {
        List<Sprite> copy = new ArrayList<>(this.sprites);
        for (Sprite s : copy) {
            s.timePassed();
        }
    }

    /**
     * Calls drawOn on all sprites in the collection.
     * This draws each sprite onto the given DrawSurface.
     * @param d the drawing surface
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
    /**
     * Removes the given sprite from the sprite collection.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
}
