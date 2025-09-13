package sprites;
import biuoop.DrawSurface;
import collidables.Collidable;
import collidables.CollisionInfo;
import collidables.GameEnvironment;
import core.Game;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**
 * The Ball class represents a ball with a position, radius, color, and velocity.
 * It can be drawn on the screen, and reacts to collisions based on a game environment.
 */
public class Ball implements Sprite {
    private Point center;
    private final int r;
    private Color color;
    private Velocity velocity;
    private GameEnvironment environment;
    private Paddle paddle;

    /**
     * Constructs a new Ball at a given Point with a radius and color.
     * @param center the center point
     * @param r      the radius
     * @param color  the ball's color
     */
    public Ball(Point center, int r, Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
    }

    /**
     * Constructs a new Ball using coordinates, radius and color.
     * @param x      x coordinate
     * @param y      y coordinate
     * @param r      radius
     * @param color  color
     */
    public Ball(double x, double y, int r, Color color) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
    }

    /**
     * Returns the x-coordinate of the center of the ball.
     * @return the x position as an integer
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Returns the y-coordinate of the center of the ball.
     *
     * @return the y position as an integer
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Returns the radius of the ball.
     *
     * @return the radius of the ball
     */
    public int getSize() {
        return this.r;
    }

    /**
     * Returns the color of the ball.
     *
     * @return the color of the ball
     */
    public Color getColor() {
        return this.color;
    }
    /**
     * Sets the color of the ball.
     *
     * @param color the new color to assign to the ball
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the current velocity of the ball.
     *
     * @return the velocity of the ball
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Sets the velocity of the ball using a Velocity object.
     *
     * @param v the new velocity to assign
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets the velocity of the ball using dx and dy components.
     *
     * @param dx the change in x
     * @param dy the change in y
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }


    /**
     * Moves the ball one step while checking for collisions.
     */
    public void moveOneStep() {
        boolean insideBlock = false;
        Collidable insideCollidable = null;

        for (Collidable c : environment.getCollidables()) {
            if (isInsideRectangle(c.getCollisionRectangle())) {
                insideBlock = true;
                insideCollidable = c;
                break;
            }
        }

        if (insideBlock) {
            emergencyExtraction(insideCollidable.getCollisionRectangle());
            return;
        }

        Point nextPoint = velocity.applyToPoint(center);
        Line trajectory = new Line(center, nextPoint);
        CollisionInfo collisionInfo = environment.getClosestCollision(trajectory);

        if (collisionInfo == null) {
            this.center = nextPoint;
        } else {
            Point collisionPoint = collisionInfo.collisionPoint();
            double safeDistance = center.distance(collisionPoint) - (r + 1.0);

            if (safeDistance > 0) {
                double move = safeDistance / center.distance(nextPoint);
                double newX = center.getX() + (nextPoint.getX() - center.getX()) * move;
                double newY = center.getY() + (nextPoint.getY() - center.getY()) * move;
                this.center = new Point(newX, newY);
            }

            this.velocity = collisionInfo.collisionObject().hit(this, collisionPoint, velocity);
        }
    }

    private boolean isInsideRectangle(Rectangle rect) {
        double left = rect.getUpperLeft().getX();
        double top = rect.getUpperLeft().getY();
        double right = left + rect.getWidth();
        double bottom = top + rect.getHeight();

        return center.getX() >= left && center.getX() <= right
                && center.getY() >= top && center.getY() <= bottom;
    }

    private void emergencyExtraction(Rectangle rect) {
        double left = rect.getUpperLeft().getX();
        double top = rect.getUpperLeft().getY();
        double right = left + rect.getWidth();
        double bottom = top + rect.getHeight();

        double distToLeft = center.getX() - left;
        double distToRight = right - center.getX();
        double distToTop = center.getY() - top;
        double distToBottom = bottom - center.getY();

        double minDist = Math.min(Math.min(distToLeft, distToRight),
                Math.min(distToTop, distToBottom));

        if (minDist == distToLeft) {
            center = new Point(left - r - 1, center.getY());
            velocity = new Velocity(-Math.abs(velocity.getDx()), velocity.getDy());
        } else if (minDist == distToRight) {
            center = new Point(right + r + 1, center.getY());
            velocity = new Velocity(Math.abs(velocity.getDx()), velocity.getDy());
        } else if (minDist == distToTop) {
            center = new Point(center.getX(), top - r - 1);
            velocity = new Velocity(velocity.getDx(), -Math.abs(velocity.getDy()));
        } else {
            center = new Point(center.getX(), bottom + r + 1);
            velocity = new Velocity(velocity.getDx(), Math.abs(velocity.getDy()));
        }
    }

    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) center.getX(), (int) center.getY(), r);
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Sets the game environment for the ball.
     * @param environment the GameEnvironment object
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.environment = environment;
    }

    /**
     * Adds the ball to the game as a sprite.
     * @param game the game to add the ball to
     */
    public void addToGame(Game game) {
        game.addSprite(this);
    }

    /**
     * Sets the paddle that the ball can interact with for manual collision logic.
     * @param paddle the paddle object
     */
    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }
    /**
     * Removes this ball from the game by removing it from the game's sprite collection.
     *
     * @param g the game from which the ball should be removed
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }

}

