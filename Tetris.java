/**
 * The class is where the main method of the game is at.
 * @version 09 February 2023
 * @author Wesley Lukman
 */
public class Tetris {
    /**
     * It is the main method of the game.
     * @param args It is an array of strings from the operating system.
     */
    public static void main(String[] args) {
        TetrisFrame frame = new TetrisFrame();
        frame.start();
    }
}