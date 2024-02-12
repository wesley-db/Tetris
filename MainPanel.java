import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import javax.swing.JPanel;

/**
 * The class is a JPanel representing the title screens.
 * @version 09 February 2023
 * @author Wesley Lukman
 */
public class MainPanel extends JPanel {
    final static String TITLE = "title";
    final static String GAME = "game";
    final static String GAME_OVER = "game over";
    private String text;
    
    /**
     * It is the constructor of the MainPanel.
     * @param str It is the title of the MainPanel.
     */
    public MainPanel(String str) {
        text = str;
        /**************************************/
        setPreferredSize(TetrisFrame.GAME_DIMENSION);
        setMinimumSize(TetrisFrame.GAME_DIMENSION);
        setBackground(TetrisFrame.NIGHT_BLUE);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        drawGrid(g,TetrisFrame.GAME_DIMENSION.width,TetrisFrame.GAME_DIMENSION.height);
        switch(text) {
            case TITLE:
                drawTitle(g,TetrisFrame.GAME_DIMENSION.width,TetrisFrame.GAME_DIMENSION.height);
                break;
            case GAME_OVER:
                drawGameOver(g,TetrisFrame.GAME_DIMENSION.width,TetrisFrame.GAME_DIMENSION.height);
                break;
        }
    }

    /**
     * The method draws the grid of the screen.
     * @param g The Graphics context to draw in.
     * @param width The screen's width.
     * @param height The screen's height.
     */
    public static void drawGrid(Graphics g, int width, int height) {
        g.setColor(TetrisFrame.DARK_GREY);
        for(int i=0; i<=height; i+=Block.UNIT_SIZE) {
            /*******Horizontal*******/
            g.drawLine(0, i, width, i);
            /*******Vertical***********/
            if (i<=width)
                g.drawLine(i, 0, i, height);
        }
    }

    /**
     * The method draws the word Tetris.
     * @param g The Graphics context to draw in.
     * @param width The screen's width.
     * @param height The screen's height.
     */
    private void drawTitle(Graphics g, int width, int height) {
        Font f = new Font("cambria",Font.BOLD,100);
        g.setFont(f);
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics();
        int x = (width/2)-(fm.stringWidth("Tetris")/2);
        g.drawString("Tetris",x,height/2);
    }

    /**
     * The method draws the word Game Over.
     * @param g The Graphics context to draw in.
     * @param width The screen's width.
     * @param height The screen's height.
     */
    private void drawGameOver(Graphics g, int width, int height) {
        Font f = new Font("cambria",Font.BOLD,100);
        g.setFont(f);
        g.setColor(Color.RED);
        FontMetrics fm = g.getFontMetrics();
        int x = (width/2)-(fm.stringWidth("game")/2)+10;
        int y = (height/2)-(fm.getHeight()/2);
        g.drawString("game",x,y);
        /******************************************/
        x = (width/2)-(fm.stringWidth("Over")/2)+15;
        y = (height/2)+(fm.getHeight()/2)-10;
        g.drawString("over", x, y);
    }
}