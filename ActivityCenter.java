import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.Color;
/****************************************/
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Timer;
/****************************************/
import java.util.LinkedList;

/**
 * The class is the sole class responsible for listening to events.
 * It will process the info based on the events listened.
 * @version 09 February 2023
 * @author Wesley Lukman
 */
public class ActivityCenter implements ActionListener{
    final static int SPEED = 750;
    /*********************************/
    final static String PLAY_BUTTON = "play";
    final static String EXIT_BUTTON = "exit";
    final static String PREVIEW_TV = "preview";
    final static String HOLD_TV = "hold";
    /*********************************/
    private JPanel mainScreen;
    private GamePanel theGame;
    private TetrominoTv preview;
    private TetrominoTv hold;
    private JButton play;
    private JButton exit;
    /*********************************/
    private Timer time;
    /*********************************/
    private GameMaster gm;

    /**
     *It is the constructor of the ActivityCenter. 
     */
    public ActivityCenter(){
        gm = new GameMaster();
        time = new Timer(SPEED, this);
        /*********Initiate buttons**************/
        play = new JButton("Play");
        play.addActionListener(this);
        exit = new JButton("Exit");
        exit.addActionListener(this);
        /************Initiate tvs***************/
        preview = new TetrominoTv();
        hold = new TetrominoTv();
        /********Initiate mainScreen************/
        initiateMainScreen();
    }
 
    /**
     * The method gets the main screen ready to be used.
     */
    private void initiateMainScreen() {
        mainScreen = new JPanel();
        mainScreen.setLayout(new CardLayout());
        /*****************************************************************/
        mainScreen.add(MainPanel.TITLE, new MainPanel(MainPanel.TITLE));
        theGame = new GamePanel();
        theGame.addKeyListener( new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_LEFT)
                    gm.play(GameMaster.LEFT);
                if(e.getKeyCode()==KeyEvent.VK_RIGHT)
                    gm.play(GameMaster.RIGHT);
                if(e.getKeyCode()==KeyEvent.VK_DOWN)
                    gm.play(GameMaster.DOWN);
                if(e.getKeyCode()==KeyEvent.VK_UP)
                    gm.play(GameMaster.ROTATE_RIGHT);
                if(e.getKeyCode()==KeyEvent.VK_NUMPAD0)
                    System.exit(0);
                if(e.getKeyCode()==KeyEvent.VK_NUMPAD1) {
                    theGame.updateTetromino(gm.currMino);
                    theGame.repaint();
                }
                if(e.getKeyCode()==KeyEvent.VK_SPACE)
                    gm.play(GameMaster.HOLD);
            }
        });
        mainScreen.add(MainPanel.GAME, theGame);
        mainScreen.add(MainPanel.GAME_OVER, new MainPanel(MainPanel.GAME_OVER));
    }

    /**
     * It is a getter method for the main screen of the game console.
     * @return It returns the main panel, which is connected to ActivityCenter.
     */
    public JPanel getMainScreen() {
        return mainScreen;
    }

    /**
     * It is a getter method for the TV panel of the game console.
     * @param tvName It is either the string PREVIEW_TV or HOLD_TV.
     * @return It returns the desired TV panel, which is connected to ActivityCenter.
     */
    public TetrominoTv getTetrominoTv(String tvName) {
        if (tvName.equals(PREVIEW_TV))
            return preview;
        else if (tvName.equals(HOLD_TV))
            return hold;
        else
            return null;
    }

    /**
     * It is a getter method for buttons of the game console.
     * @param tvName It is either the string PLAY_BUTTON or EXIT_BUTTON.
     * @return It returns the desired buttons, which is connected to ActivityCenter.
     */
    public JButton getButton(String buttonName) {
        if (buttonName.equals(PLAY_BUTTON))
            return play;
        else if (buttonName.equals(EXIT_BUTTON))
            return exit;
        else
            return null;
    }

    /**
     * The method is invoked when an event occured, and process info accordingly.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) mainScreen.getLayout();
        if (e.getSource() == exit) 
            System.exit(0);
        if (e.getSource() == play) {
            cl.show(mainScreen, MainPanel.GAME);
            theGame.requestFocus();
            time.start();
        }
        /*************************/
        //System.out.println(gm.currMino.toString());
        if (!gm.gameOver) {
            gm.play(GameMaster.DOWN);
            /**********/
            theGame.updateBlocks(gm.coor);
            theGame.updateTetromino(gm.currMino);
            theGame.repaint();
            /**********/
            preview.updateTetromino(gm.nextMino.currShape);
            preview.repaint();
            if (gm.holdMino != null)
                hold.updateTetromino(gm.holdMino.currShape);
            hold.repaint();
        }
        else {
            time.stop();
            cl.show(mainScreen, MainPanel.GAME_OVER);
            gm.restart();
        }
    }

    /**
     * The class is helps the ActionCenter in processing the info related to the game itself and only the game.
     * For example, it is the one that keep tracks of the Tetromino's location.
     */
    private static class GameMaster {
        private boolean hold;
        private boolean gameOver;
        private LinkedList<Color[]> coor;
        private Tetromino currMino;
        private Tetromino holdMino;
        private Tetromino nextMino;
        /*************************/
        final static int DOWN = 0;
        final static int LEFT = 1;
        final static int RIGHT = 2;
        final static int ROTATE_RIGHT = 3;
        final static int HOLD = 4;

        private GameMaster() {
            coor = new LinkedList<Color[]>();
            currMino = new Tetromino();
            nextMino = new Tetromino();
            hold = true;
        }

        /**
         * The method restarts the components of the game to its default.
         */
        public void restart() {
            coor = new LinkedList<Color[]>();
            currMino = new Tetromino();
            nextMino = new Tetromino();
            hold = true;
            gameOver = false;           
        }

        /**
         * The method process the info needed to be processed when an action occured.
         * @param action It is the type of action wished to be done.
         */
        public void play(int action) {
            switch (action) {
                case DOWN: 
                    currMino.down(1);
                    break;
                case LEFT:
                    currMino.left(1);
                    if (sideWallCrashCheck()<0 || blockCrashCheck())
                        currMino.reverseTranslation();
                    break;
                case RIGHT:
                    currMino.right(1);
                    if (sideWallCrashCheck()>0 || blockCrashCheck())
                        currMino.reverseTranslation();
                    break;
                case ROTATE_RIGHT:
                    currMino.rotateRight();
                    int swc = sideWallCrashCheck();
                    int twc = topWallCrashCheck();
                    if (swc < 0)
                        currMino.right(swc);
                    else if (swc > 0)
                        currMino.left(swc);
                    else if (twc > 0)
                        currMino.down(twc);
                    else if (blockCrashCheck())
                        currMino.reverseTranslation();
                    break;
                case HOLD:
                    if (hold) {
                        if (holdMino == null) {
                            holdMino = currMino;
                            currMino = nextMino;
                            currMino.resetCoor();
                            nextMino = new Tetromino();
                            hold = false;
                        }
                        else {
                            Tetromino temp = currMino;
                            holdMino.resetCoor();
                            currMino = holdMino;
                            holdMino = temp;
                        }
                    }
                    break;
            }
            if (setCheck()) {
                setTetromino();
                currMino = nextMino;
                gameOver = blockCrashCheck();
                if (!gameOver) {
                    nextMino = new Tetromino();
                    hold = true;
                }
            }
            clearRow();   
        }

        /**
         * The method tells if currMino is out of bounds to the left or right.
         * @return It tells by how many blocks the currMino is out of the screen.
         * Negative indicates left and positive indicates right.
         */
        private int sideWallCrashCheck() {
            int answ = 0;
            int width = (GamePanel.TOTAL_COLUMN-1)*Block.UNIT_SIZE;
            for (Block bl : currMino.b) {
                if (bl.x < 0) {
                    int temp = bl.x/Block.UNIT_SIZE;
                    answ = temp<answ ? temp : answ;
                }
                else if (bl.x > width) {
                    int temp = (bl.x-width)/Block.UNIT_SIZE;
                    answ = temp>answ ? temp : answ;
                }
            }
            return answ;
        }

        /**
         *The method tells if currMino is out of bound to the top of the screen. 
         *@return It returns a positive number indicating by how many blocks currMino is out of the screen.
         */
        private int topWallCrashCheck() {
            int answ = 0;
            for (Block bl : currMino.b) {
                if (bl.y < 0) {
                    int temp = (0-bl.y)/Block.UNIT_SIZE;
                    answ = temp<answ ? temp : answ;
                }
            }
            return answ;
        }

        /**
         * The method tells if currMino's location is in conflict with an existing block.
         * @return It returns true when the currMino's location overlaps with an existing block, and false otherwise.
         */
        private boolean blockCrashCheck() {
            boolean answ = false;
            for (Block bl : currMino.b) {
                int yInd = retionalizeY(bl.y);
                answ = yInd<coor.size() && coor.get(yInd)[bl.x/Block.UNIT_SIZE]!=null;
                if (answ)
                    break;
            }
            return answ;
        }

        /**
         * The method tells if it times to start a new round.
         * @return It returns true when a new round should start, and false otherwise.
         */
        private boolean setCheck() {
            //System.out.println(this.toString());
            boolean cond1 = false;
            boolean cond2 = false;
            int height = GamePanel.TOTAL_ROW*Block.UNIT_SIZE;
            for (Block bl : currMino.b) {
                int yInd = retionalizeY(bl.y);
                cond1 = bl.y>=height;
                cond2 = !cond1 && yInd-1<coor.size() && coor.get(yInd-1)[bl.x/Block.UNIT_SIZE]!=null;
                if (cond1 || cond2)
                    break;
            }
            return cond1 || cond2;
        }

        /**
         * The method sets currMino into place.
         */
        private void setTetromino() {
            for (Block bl : currMino.b) {   
                int indY = retionalizeY(bl.y);
                int indX = bl.x/Block.UNIT_SIZE;
                while (indY >= coor.size())
                    coor.add(new Color[GamePanel.TOTAL_COLUMN]);
                coor.get(indY)[indX] = currMino.currCol;
            }
        }

        /**
         * The method clears any row that is full.
         */
        private void clearRow(){
            boolean clear = false;
            for (int i=0; i<coor.size(); i++) {
                for (Color col : coor.get(i)) {
                    clear = (col != null);
                    if (!clear)
                        break;
                }
                if (clear) 
                    coor.remove(i);
                clear = false;
            }
        }

        /**
         * It changes the y-coordinate in the JPanel format
         * to the format of the coor's data structure.
         * @param yCoor An integer such that 0 >= yCoor < game screen's height.
         * @return It returns the corresponding index of yCoor.
         */
        private int retionalizeY(int yCoor) {
            /*JPanel's 0,0 is at the top left. However, the data structured used treats 0,0 to be at bottom left.
             * Thus, adjustment to the y-coordinates needs to be done.
             */
            int height = GamePanel.TOTAL_ROW*Block.UNIT_SIZE;
            return (height-yCoor)/Block.UNIT_SIZE;
        }

        /*public String toString() {
            String answ = "";
            for (int i=0; i<coor.size(); i++) {
                for (Color col : coor.get(i)) {
                    answ += col != null ? col.toString() + "|" : "null" + "|";
                }
                answ += "\n";
            }
            return answ;
        }*/
    }
}