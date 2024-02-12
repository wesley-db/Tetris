import java.awt.Graphics;
import java.util.LinkedList;
import java.awt.Color;
import javax.swing.JPanel;

/**
 * The class is a JPanel representing the game itself.
 * @version 09 February 2023
 * @author Wesley Lukman
 */
public class GamePanel extends JPanel{
    private Tetromino mino;
    private LinkedList<Color[]> coor;
    /***********************/
    final static int TOTAL_ROW = 15;
    final static int TOTAL_COLUMN = 12;

    /**
     * It is the constructor of the GamePanel.
     */
    public GamePanel() {
        setPreferredSize(TetrisFrame.GAME_DIMENSION);
        setMinimumSize(TetrisFrame.GAME_DIMENSION);
        setBackground(TetrisFrame.NIGHT_BLUE);
    }

    /**
     * The method is to report the Tetromino that is currently active.
     * @param mino The Tetromino currently active.
     */
    public void updateTetromino(Tetromino mino) {
        this.mino = mino;
    }

    /**
     * The method is to report the location of all existing Blocks.
     * @param coor The coordiantes of all existing Blocks along with its color info.
     */
    public void updateBlocks(LinkedList<Color[]> coor) {
        this.coor = coor;
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        drawTetromino(g);
        drawBlocks(g);
        MainPanel.drawGrid(g,TetrisFrame.GAME_DIMENSION.width,TetrisFrame.GAME_DIMENSION.height);
    }

    /**
     * The method helps to draw a Tetromino.
     * @param g The Graphics context to draw in.
     */
    private void drawTetromino(Graphics g) {
        if (mino != null) {
            g.setColor(mino.currCol);
            for (Block bl : mino.b) {
                Block.drawBlock(g, bl.x, bl.y);
            }
        }
    }

    /**
     * The method helps to draw all existing Blocks.
     * @param g The Graphics context to draw in.
     */    
    private void drawBlocks(Graphics g) {
        for (int i=0; i<coor.size();i++) {
            int yCoor = rationalizeY(i);
            for (int j=0; j<TOTAL_COLUMN; j++) {
                if (coor.get(i)[j] != null) {
                    g.setColor(coor.get(i)[j]);
                    Block.drawBlock(g, j*Block.UNIT_SIZE, yCoor);
                }
            }
        }
    }

    /**
     * The method calculates the y-coordinate given its index.
     * @param yInd The index in question.
     * @return The desired integer.
     */
    private int rationalizeY(int yInd) {
        /*JPanel's 0,0 is at the top left. However, the data structured used treats 0,0 to be at bottom left.
        * Thus, adjustment to the y-coordinates needs to be done to get its index.
        */
        return (TOTAL_ROW-yInd-1)*Block.UNIT_SIZE;
    }

}