package core;
import java.awt.Color;
import biuoop.DrawSurface;
import biuoop.GUI;
import collidables.Collidable;
import collidables.GameEnvironment;
import counters.Counter;
import geometry.Point;
import geometry.Rectangle;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.HitListener;
import listeners.ScoreTrackingListener;
import sprites.ScoreIndicator;
import sprites.Block;
import sprites.Ball;
import sprites.SpriteCollection;
import sprites.Sprite;
import sprites.Paddle;
import sprites.Velocity;


/**
 * The Game class is responsible for setting up and running the game.
 * It handles the creation of sprites, collidables, and the animation loop.
 */
public class Game {
    private final SpriteCollection sprites;
    private final GameEnvironment environment;
    private final GUI gui;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;


    /**
     * Constructs a new Game object.
     * Initializes the SpriteCollection, GameEnvironment, and GUI.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("Arkanoid", 800, 600);
    }

    /**
     * Adds a collidable object to the game environment.
     * @param c the Collidable to add
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * Adds a sprite object to the sprite collection.
     * @param s the Sprite to add
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Initializes the game by creating and adding borders, blocks, paddle, and balls.
     * Sets up the full game state before the animation loop starts.
     */
    public void initialize() {
        int borderThickness = 20;
        Color borderColor = Color.GRAY;

        // Initialize counters
        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter();
        this.score = new Counter();

        // Create listeners
        BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);
        BallRemover ballRemover = new BallRemover(this, remainingBalls);
        ScoreTrackingListener scoreListener = new ScoreTrackingListener(score);

        // Create and add border blocks (top, left, right)
        Block top = new Block(new Rectangle(new Point(0, 0), 800, borderThickness), borderColor);
        Block left = new Block(new Rectangle(new Point(0, 0), borderThickness, 600), borderColor);
        Block right = new Block(new Rectangle(new Point(800 - borderThickness, 0), borderThickness, 600), borderColor);

        top.addToGame(this);
        left.addToGame(this);
        right.addToGame(this);

        // Create and add death-region block
        Block deathRegion = new Block(new Rectangle(new Point(0, 580), 800, 20), Color.DARK_GRAY);
        deathRegion.addToGame(this);
        deathRegion.addHitListener(ballRemover);

        // Add level blocks and attach both the block remover and score listener
        addLevelBlocks(this, blockRemover, scoreListener);

        // Paddle
        Paddle paddle = new Paddle(new Rectangle(new Point(340, 575), 120, 5), Color.ORANGE,
                gui.getKeyboardSensor(), 6, 800);
        paddle.addToGame(this);

        // Ball 1
        Ball ball1 = new Ball(new Point(300, 400), 5, Color.RED);
        ball1.setVelocity(new Velocity(2, -2));
        ball1.setGameEnvironment(this.environment);
        ball1.setPaddle(paddle);
        ball1.addToGame(this);
        remainingBalls.increase(1);

        // Ball 2
        Ball ball2 = new Ball(new Point(400, 500), 5, Color.PINK);
        ball2.setVelocity(new Velocity(-2, 2));
        ball2.setGameEnvironment(this.environment);
        ball2.setPaddle(paddle);
        ball2.addToGame(this);
        remainingBalls.increase(1);

        // Ball 3
        Ball ball3 = new Ball(new Point(350, 450), 5, Color.BLUE);
        ball3.setVelocity(new Velocity(1, -3));
        ball3.setGameEnvironment(this.environment);
        ball3.setPaddle(paddle);
        ball3.addToGame(this);
        remainingBalls.increase(1);

        // Score indicator at top of screen
        ScoreIndicator scoreIndicator = new ScoreIndicator(score);
        this.addSprite(scoreIndicator);
    }






    /**
     * Starts the animation loop that continuously draws and updates the game.
     * The game runs at 60 frames per second using a Sleeper.
     */
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        biuoop.Sleeper sleeper = new biuoop.Sleeper();

        while (this.remainingBlocks.getValue() > 0 && this.remainingBalls.getValue() > 0) {
            long startTime = System.currentTimeMillis();

            DrawSurface d = gui.getDrawSurface();

            // Draw all sprites
            this.sprites.drawAllOn(d);
            gui.show(d);

            // Notify all sprites that time passed
            this.sprites.notifyAllTimePassed();

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }

        // Award +100 score bonus for clearing all blocks
        if (this.remainingBlocks.getValue() == 0) {
            this.score.increase(100);
            System.out.println("You Win!\nYour score is: " + this.score.getValue());
        } else {
            System.out.println("Game Over.\nYour score is: " + this.score.getValue());
        }

        // Close GUI
        gui.close();
    }






    /**
     * Adds multiple rows of blocks to the game in a staggered pattern.
     * @param game the game instance to which the blocks are added
     * @param remover will remove it
     * @param scorer will add it to the score
     */
    public void addLevelBlocks(Game game, HitListener remover, HitListener scorer) {
        int startY = 100;
        int rows = 6;
        int blocksPerRow = 12;
        int blockWidth = 50;
        int blockHeight = 25;
        Color[] rowColors = {
                Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE, Color.PINK, Color.GREEN
        };

        for (int i = 0; i < rows; i++) {
            Color color = rowColors[i];
            int y = startY + i * blockHeight;
            int screenWidth = 800;
            int borderThickness = 20;

            for (int j = 0; j < blocksPerRow - i; j++) {
                int x = screenWidth - borderThickness - (j + 1) * blockWidth;
                Rectangle rect = new Rectangle(new Point(x, y), blockWidth, blockHeight);
                Block block = new Block(rect, color);
                block.addToGame(game);
                block.addHitListener(scorer);
                block.addHitListener(remover);
                remainingBlocks.increase(1);
            }
        }
    }
    /**
     * Removes the given collidable from the game environment.
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removes the given sprite from the game's sprite collection.
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }


}

