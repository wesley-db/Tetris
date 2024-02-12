import java.awt.Graphics;

/**
 * The class encapsulates all information related to a single block of the Tetromino.
 * It includes helper methods to process info related to the block.
 * @version 09 February 2023
 * @author Wesley Lukman
 */
public class Block {
    final static int UNIT_SIZE = 40;
    int x;
    int y;

    /**
     * It is the constructor of the Block class.
     */
    public Block() {
        x = 0;
        y = 0;
    }

    /**
     * It is the constructor of the Block class.
     * @param x The desired starting x-coordinate of the Block.
     * @param y The desired starting x-coordinate of the Block.
     */
    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The method sets the Block's coordinate as desire.
     * @param x The desired x-coordinate of the Block.
     * @param y The desired x-coordinate of the Block.
     */
    public void setCoordinates (int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The method calculates the Block's new coordinate when moved in a certain way.
     * @param x An integer indicating how much the Block moved horizontally; negative for left and positive for right.
     * @param y An integer indicating how much the Block moved vertically; negative for up and positive for down.
     * @return It returns a new Block with the desired coordinates.
     */
    public Block translate(int x, int y) {
        int newX = x!=0 ? this.x+(UNIT_SIZE*x) : this.x;
        int newY = y!=0 ? this.y+(UNIT_SIZE*y) : this.y;
        return new Block(newX, newY);
    }

    /**
     * The method calculates the new point as a consequence of a 
     * clockwise 90 degrees turn. 
     * @param centerY The y coordinate of the center of rotation.
     * @return It returns a new Block with the desired coordinates.
     */
   public Block rotateRight(int centerX, int centerY) {
        int answX = -this.y+centerY+centerX;
        int answY = this.x-centerX+centerY;
        return new Block(answX,answY);
    }

    /**
     * The method format the Block's coordinate into a String.
     */
    public String toString() {
        return "{" + x + "," + y + "}\n";
    }

    /**
     * The method draws a Block into the screen.
     * @param g The graphics context to draw the Block.
     * @param x The x-coordinate of the Block.
     * @param y The y-coordinate of the Block.
     */
    public static void drawBlock(Graphics g, int x, int y) {
        g.drawRect(x,y,Block.UNIT_SIZE,Block.UNIT_SIZE);
        g.fillRect(x,y,Block.UNIT_SIZE,Block.UNIT_SIZE);
    }
}