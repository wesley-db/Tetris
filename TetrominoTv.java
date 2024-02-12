import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import javax.swing.JPanel;

/**
 * The class is a JPanel representing the TV screens, excluding the TV's border and title.
 * @version 09 February 2023
 * @author Wesley Lukman
 */
public class TetrominoTv extends JPanel {
    private Tetromino.Shape minoShape;
    final private int V_EDGE = 5;
    final private int H_EDGE = 10;
    final Dimension TV_DIMENSION = new Dimension(Block.UNIT_SIZE*4+(H_EDGE*2),Block.UNIT_SIZE*2+(V_EDGE*2));

    /**
     * It is the constructor of TetrominoTv.
     */
    public TetrominoTv() {
        setPreferredSize(TV_DIMENSION);
        setMinimumSize(TV_DIMENSION);
        setBackground(TetrisFrame.NIGHT_BLUE);
    }

    /**
     * The method is to report the Tetromino needed to be shown.
     * @param mino The Tetromino in question.
     */
    public void updateTetromino(Tetromino.Shape mino) {
        minoShape = mino;
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        if (minoShape != null)
            drawTetromino(g);
        drawGrid(g);
        drawEdge(g);
    }

    /**
     * The method paints the game's grid on the TV panel.
     * @param g The Graphics context to draw in.
     */
    private void drawGrid(Graphics g) {
        g.setColor(TetrisFrame.DARK_GREY);
        int w = TV_DIMENSION.width - H_EDGE;
        int h = TV_DIMENSION.height - V_EDGE;
        for (int i=0; i<TV_DIMENSION.width; i+=Block.UNIT_SIZE) {
            if (i < h)
                g.drawLine(H_EDGE, V_EDGE+i, w, V_EDGE+i);//horizontal
            g.drawLine(H_EDGE+i, V_EDGE, H_EDGE+i, h);//vertical
        }
    }

    /**
     * The method draws the edges of the TV.
     * @param g The Graphics context to draw in.
     */
    private void drawEdge(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.LIGHT_GRAY);
        /******Vertical*********/
        int halfH = H_EDGE/2;
        int halfV = V_EDGE/2; 
        int dummyX = TV_DIMENSION.width-halfH;
        int dummyY = TV_DIMENSION.height-halfV;
        g2d.setStroke(new BasicStroke(H_EDGE+1));
        g2d.drawLine(halfH, halfV, halfH, dummyY);
        g2d.drawLine(dummyX,halfV,dummyX,dummyY);
        /******Horizontal*********/
        g2d.setStroke(new BasicStroke(V_EDGE+1));
        g2d.drawLine(halfH, halfV, dummyX, halfV);
        g2d.drawLine(halfH,dummyY,dummyX,dummyY);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(0,0,TV_DIMENSION.width,0);
    }

    /**
     * The method draws the Tetromino of interest.
     * @param g The Graphics context to draw in.
     */
    private void drawTetromino(Graphics g) {
        int secRowY = V_EDGE+Block.UNIT_SIZE;
        switch (minoShape) {
            case T:
                g.setColor(Tetromino.T_GREEN);
                Block.drawBlock(g, H_EDGE+Block.UNIT_SIZE, V_EDGE);
                Block.drawBlock(g, H_EDGE, secRowY);
                Block.drawBlock(g, H_EDGE+Block.UNIT_SIZE, secRowY);
                Block.drawBlock(g, H_EDGE+(2*Block.UNIT_SIZE), secRowY);
                break;
            case S:
                g.setColor(Tetromino.S_RED);
                Block.drawBlock(g, H_EDGE+Block.UNIT_SIZE, V_EDGE);
                Block.drawBlock(g, H_EDGE+(2*Block.UNIT_SIZE), V_EDGE);
                Block.drawBlock(g, H_EDGE, secRowY);
                Block.drawBlock(g, H_EDGE+Block.UNIT_SIZE, secRowY);
                break;
            case Z:
                g.setColor(Tetromino.Z_ORANGE);
                Block.drawBlock(g, H_EDGE, V_EDGE);
                Block.drawBlock(g, H_EDGE+Block.UNIT_SIZE, V_EDGE);
                Block.drawBlock(g, H_EDGE+Block.UNIT_SIZE, secRowY);
                Block.drawBlock(g, H_EDGE+(2*Block.UNIT_SIZE), secRowY);
                break;
            case O:
                g.setColor(Tetromino.O_BLUE);
                int firstCol = H_EDGE+Block.UNIT_SIZE;
                int secCol = H_EDGE+(2*Block.UNIT_SIZE);
                Block.drawBlock(g, firstCol, V_EDGE);
                Block.drawBlock(g, secCol, V_EDGE);
                Block.drawBlock(g, firstCol, secRowY);
                Block.drawBlock(g, secCol, secRowY);
                break;
            case I:
                g.setColor(Tetromino.I_BROWN);
                Block.drawBlock(g, H_EDGE, secRowY);
                Block.drawBlock(g, H_EDGE+Block.UNIT_SIZE, secRowY);
                Block.drawBlock(g, H_EDGE+(2*Block.UNIT_SIZE), secRowY);
                Block.drawBlock(g, H_EDGE+(3*Block.UNIT_SIZE), secRowY);
                break;
            case L:  
                g.setColor(Tetromino.L_PURPLE);
                Block.drawBlock(g, H_EDGE, secRowY);
                Block.drawBlock(g, H_EDGE+Block.UNIT_SIZE, secRowY);
                Block.drawBlock(g, H_EDGE+(2*Block.UNIT_SIZE), secRowY);
                Block.drawBlock(g, H_EDGE+(2*Block.UNIT_SIZE), V_EDGE);
                break;
            case J:
                g.setColor(Tetromino.J_BRAY);
                Block.drawBlock(g, H_EDGE, V_EDGE);
                Block.drawBlock(g, H_EDGE, secRowY);
                Block.drawBlock(g, H_EDGE+Block.UNIT_SIZE, secRowY);
                Block.drawBlock(g, H_EDGE+(2*Block.UNIT_SIZE), secRowY);
                break;
        }
    }
}