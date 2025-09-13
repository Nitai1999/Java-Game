package sprites;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collidables.Collidable;
import core.Game;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**
 * The Paddle class represents the player's paddle in the game.
 * It is controlled using the keyboard and can bounce balls.
 * The paddle is both a Sprite and a Collidable.
 */
public class Paddle implements Sprite, Collidable {
    private Rectangle rectangle;
    private final Color color;
    private final KeyboardSensor keyboard;
    private final double speed;
    private final int screenWidth;

    /**
     * Constructs a Paddle object.
     * @param rectangle    the shape and position of the paddle
     * @param color        the color of the paddle
     * @param keyboard     the keyboard sensor used for user input
     * @param speed        the movement speed of the paddle
     * @param screenWidth  the width of the screen
     */
    public Paddle(Rectangle rectangle, Color color, KeyboardSensor keyboard, int speed, int screenWidth) {
        this.rectangle = rectangle;
        this.color = color;
        this.keyboard = keyboard;
        this.speed = speed;
        this.screenWidth = screenWidth;
    }

    /**
     * Moves the paddle to the left. Wraps around the screen if needed.
     */
    public void moveLeft() {
        double newX = rectangle.getUpperLeft().getX() - speed;
        if (newX + rectangle.getWidth() < 0) {
            newX = screenWidth;
        }
        this.rectangle = new Rectangle(new Point(newX, rectangle.getUpperLeft().getY()),
                rectangle.getWidth(), rectangle.getHeight());
    }

    /**
     * Moves the paddle to the right. Wraps around the screen if needed.
     */
    public void moveRight() {
        double newX = rectangle.getUpperLeft().getX() + speed;
        if (newX > screenWidth) {
            newX = -rectangle.getWidth();
        }
        this.rectangle = new Rectangle(new Point(newX, rectangle.getUpperLeft().getY()),
                rectangle.getWidth(), rectangle.getHeight());
    }

    /**
     * Notifies the paddle that time has passed.
     * Moves the paddle based on keyboard input.
     */
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Draws the paddle on the given DrawSurface.
     *
     * @param d the surface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        int x = (int) rectangle.getUpperLeft().getX();
        int y = (int) rectangle.getUpperLeft().getY();
        int width = (int) rectangle.getWidth();
        int height = (int) rectangle.getHeight();
        d.setColor(color);
        d.fillRectangle(x, y, width, height);
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, width, height);
    }

    /**
     * Returns the rectangle representing the paddle's collision shape.
     *
     * @return the collision rectangle
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Handles the logic when the ball hits the paddle.
     * Splits the paddle into 5 regions to determine the new direction.
     *
     * @param collisionPoint  the point where the ball hit
     * @param currentVelocity the velocity before the hit
     * @return the new velocity after the hit
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double x = collisionPoint.getX();
        double y = collisionPoint.getY();
        double top = rectangle.getUpperLeft().getY();
        double left = rectangle.getUpperLeft().getX();
        double width = rectangle.getWidth();
        double right = left + width;
        double bottom = top + rectangle.getHeight();
        double epsilon = 0.5;

        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        double speed = Math.hypot(dx, dy);

        // Corner hits: top-left or top-right
        boolean topLeftCorner = Math.abs(x - left) < epsilon && Math.abs(y - top) < epsilon;
        boolean topRightCorner = Math.abs(x - right) < epsilon && Math.abs(y - top) < epsilon;

        if (topLeftCorner || topRightCorner) {
            return Velocity.fromAngleAndSpeed(topLeftCorner ? 300 : 60, speed);
        }

        //  Side hits: left or right edges
        if ((Math.abs(x - left) < epsilon || Math.abs(x - right) < epsilon)
                && y > top && y < bottom) {
            return new Velocity(-dx, dy);
        }

        // Top surface hit: region-based reflection
        if (Math.abs(y - top) < epsilon && x > left && x < right) {
            double regionWidth = width / 5;
            int region = (int) ((x - left) / regionWidth) + 1;
            region = Math.max(1, Math.min(5, region));

            return switch (region) {
                case 1 -> Velocity.fromAngleAndSpeed(300, speed);
                case 2 -> Velocity.fromAngleAndSpeed(330, speed);
                case 3 -> new Velocity(dx, -Math.abs(dy));
                case 4 -> Velocity.fromAngleAndSpeed(30, speed);
                case 5 -> Velocity.fromAngleAndSpeed(60, speed);
                default -> new Velocity(dx, -Math.abs(dy)); // fallback
            };
        }

        //Fallback: vertical bounce
        return new Velocity(dx, -dy);
    }







    /**
     * Adds the paddle to the game as both a Sprite and a Collidable.
     *
     * @param g the game to add the paddle to
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
    @Override
    public String toString() {
        return "Paddle at " + rectangle.getUpperLeft();
    }
}
