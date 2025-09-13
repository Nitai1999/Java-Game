import core.Game;
// Name: Nitai Weiss
// ID: 208302984
/**
 * The Ass5Game class serves as the entry point for running the Arkanoid game.
 * It initializes and starts the game using the Game class.
 */
public class Ass5Game {
    /**
     * The main method creates and runs the game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
