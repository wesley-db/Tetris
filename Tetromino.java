import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * The class encapsulates all information related to a Tetromino.
 * It includes helper methods to process info related to the Tetromino.
 * @version 09 February 2023
 * @author Wesley Lukman
 */
public class Tetromino {
    final static int BLOCK_NUM = 4;
    private final int centerBlock = 1;
    public static Color T_GREEN = new Color(111,195,152);   //T grass green
    public static Color S_RED = new Color(240,17,89);       //S lipstick red
    public static Color Z_ORANGE = new Color(253,206,104);  //Z pastel orange
    public static Color O_BLUE = new Color(223,248,254);    //O cloud blue
    public static Color I_BROWN = new Color(210,108,20);    //I light bark brown
    public static Color L_PURPLE = new Color(218,203,230);  //L light-ish purple
    public static Color J_BRAY = new Color(153,204,255);    //J light gray-blue
    public static enum Shape {
        T,S,Z,O,I,L,J;

        private static Random rand = new Random();
        private static Shape[] minoType = values();

        /*It generates new shape randomly*/
        private static Shape newMino() {
            return minoType[rand.nextInt(minoType.length)];
        }
    }
    Color currCol;
    Shape currShape;
    Block[] b;
    private Block[] tempB;

    /**
     * It is the constructor of the Tetromino.
     */
    public Tetromino() {
        b = new Block[BLOCK_NUM];
        tempB = new Block[]{new Block(),new Block(),new Block(),new Block()};
        currShape = Shape.newMino();
        base(currShape);
    }

    /**
     * The method sets the Tetromino's coordinates to its default.
     */
    public void resetCoor() {
        base(currShape);
    }

    /**
     * The method change the shape of the Tetromino.
     */
    public void changeShape() {
        currShape = Shape.newMino();
        base(currShape);
    }

    /**
     * The metod moves the Tetromino down by a specific amount of times.
     * @param y A positive integer indicating how much the Tetromino should go down.
     */
    public void down(int y){
        for(int i=0; i<BLOCK_NUM; i++) {
            tempB[i].setCoordinates(b[i].x,b[i].y);
            b[i] = b[i].translate(0,y);
        }
    }

    /**
     * The metod moves the Tetromino up by a specific amount of times.
     * @param y A positive integer indicating how much the Tetromino should go up.
     */
    public void up(int y){
        for(int i=0; i<BLOCK_NUM; i++) {
            tempB[i].setCoordinates(b[i].x,b[i].y);
            b[i] = b[i].translate(0,-y);
        }
    }

    /**
     * The metod moves the Tetromino to the left by a specific amount of times.
     * @param y A positive integer indicating how much the Tetromino should go left.
     */
    public void left(int x){
        for(int i=0; i<BLOCK_NUM; i++) {
            tempB[i].setCoordinates(b[i].x,b[i].y);
            b[i] = b[i].translate(-x,0);
        }
    }

    /**
     * The metod moves the Tetromino to the right by a specific amount of times.
     * @param y A positive integer indicating how much the Tetromino should go right.
     */
    public void right(int x){
        for(int i=0; i<BLOCK_NUM; i++) {
            tempB[i].setCoordinates(b[i].x,b[i].y);
            b[i] = b[i].translate(x,0);
        }
    }

    /**
     * The method rotates the Tetromino clockwise.
     */
    public void rotateRight() {
        int centerX = b[centerBlock].x;
        int centerY = b[centerBlock].y;
        for(int i=0; i<BLOCK_NUM;i++) {
            tempB[i].setCoordinates(b[i].x,b[i].y);
            b[i] = b[i].rotateRight(centerX, centerY);
        }
    }

    /**
     * The method revoke the last changes to the Tetromino's coordinates.
     */
    public void reverseTranslation() {
        for(int i=0; i<BLOCK_NUM;i++) 
            b[i].setCoordinates(tempB[i].x,tempB[i].y);
    }

    /**
     * The method defines the default coordinates of all Tetromino shapes.
     * @param s The desired shape of the Tetromino.
     */
    private void base(Shape s){
        int middleW = TetrisFrame.GAME_DIMENSION.width/2;
        int dummy = Block.UNIT_SIZE;
        switch (s) {
            case T:
                currCol = T_GREEN;
                b[0] = new Block(middleW,0);
                b[1] = new Block(middleW,dummy);
                b[2] = new Block(middleW-dummy,dummy);
                b[3] = new Block(middleW+dummy,dummy);
                break;
            case S:
                currCol = S_RED;
                b[0] = new Block(middleW,0);
                b[1] = new Block(middleW,dummy);
                b[2] = new Block(middleW+dummy,0);
                b[3] = new Block(middleW-dummy,dummy);
                break;
            case Z:
                currCol = Z_ORANGE;
                b[0] = new Block(middleW,0);
                b[1] = new Block(middleW,dummy);
                b[2] = new Block(middleW-dummy,0);
                b[3] = new Block(middleW+dummy,dummy);
                break;
            case O:
                currCol = O_BLUE;
                b[0] = new Block(middleW,0);
                b[1] = new Block(middleW-dummy,0);
                b[2] = new Block(middleW,dummy);
                b[3] = new Block(middleW-dummy,dummy);
                break;
            case I:
                currCol = I_BROWN;
                b[0] = new Block(middleW-dummy,0);
                b[1] = new Block(middleW-(dummy*2),0);
                b[2] = new Block(middleW,0);
                b[3] = new Block(middleW+dummy,0);
                break;
            case L:  
                currCol = L_PURPLE;
                b[0] = new Block(middleW,0);
                b[1] = new Block(middleW,dummy);
                b[2] = new Block(middleW,(dummy*2));
                b[3] = new Block(middleW+dummy,(dummy*2));
                break;
            case J:
                currCol = J_BRAY;
                b[0] = new Block(middleW,0);
                b[1] = new Block(middleW,dummy);
                b[2] = new Block(middleW,(dummy*2));
                b[3] = new Block(middleW-dummy,(dummy*2));
                break;
        }
    }

    /*public String toString() {
        String answ = "";
        for (Block bl : b) {
            answ += bl.toString();
        }
        answ += "\n";
        return answ;
    }*/
}
