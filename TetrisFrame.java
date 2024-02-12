import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

/**
 * The class defines the looks of the game console.
 * Thus, it is where all comes together.
 * @version 09 February 2023
 * @author Wesley Lukman
 */
public class TetrisFrame extends JFrame {
    private final Dimension FRAME_DIMENION = new Dimension(550,820);
    private final Dimension ANCESTOR_DIMENSION = new Dimension(500,790);
    private final Dimension BUTTONS_DIMENSION = new Dimension(100,120);
    private final Dimension MENU_DIMENSION = new Dimension(480,140);
    final static Dimension GAME_DIMENSION = new Dimension(480,600);
    private final int TV_LABEL_HEIGHT = 29;
    /********************************/
    final static Color NIGHT_BLUE = new Color(21,25,47);//Dark night sky blue
    final static Color DARK_GREY = new Color(97,97,107);
    final static Color MUSHROOM = new Color(203,189,173);
    /********************************/
    private JPanel ancestor;
    private JPanel menu;
    private JPanel mainScreen;
    /*********************************/
    private ActivityCenter ac;

    /**
     * It is the constructor of TetrisFrame.
     */
    public TetrisFrame() {
        ac = new ActivityCenter();
        /****Miscellaneous settings****/
        setSize(FRAME_DIMENION);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setName("Tetris");
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        /****Setting panels****/
        setMenu();
        setGame();
        setAncestor();
    }

    /**
     * The method shows the game console to the screen.
     */
    public void start() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 0.5;
        gbc.insets = new Insets(10,30,10,30);
        add(ancestor, gbc);
        setVisible(true);
    }

    /**
     * The method defines the look of the game console as a whole.
     */
    private void setAncestor() {
        ancestor = new JPanel();
        ancestor.setPreferredSize(ANCESTOR_DIMENSION);
        ancestor.setMinimumSize(ANCESTOR_DIMENSION);
        ancestor.setBackground(MUSHROOM);
        ancestor.setBorder(BorderFactory.createMatteBorder(1,1,3,3,DARK_GREY));
        ancestor.setLayout(new GridBagLayout());
        /***********************************************/
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5,0,0,0);
        ancestor.add(mainScreen,gbc);
        /**********************************************/
        gbc.gridy = 1;
        gbc.insets = new Insets(0,0,0,0);
        gbc.anchor = GridBagConstraints.CENTER;
        ancestor.add(menu,gbc);
    }

    /**
     * The method sets the look of the buttons.
     * @param loc It tells the position of the button.
     */
    private void setButtons(GridBagConstraints loc) {
        JPanel result = new JPanel();
        /****************************************/
        Font f = new Font("cambria",Font.BOLD,30);
        JButton play = ac.getButton(ActivityCenter.PLAY_BUTTON);
        play.setFont(f);
        JButton exit = ac.getButton(ActivityCenter.EXIT_BUTTON);
        exit.setFont(f);
        /****************************************/
        GridLayout layout = new GridLayout(2,1);
        layout.setVgap(20);
        /****************************************/
        result = new JPanel();
        result.setLayout(layout);
        result.setPreferredSize(BUTTONS_DIMENSION);
        result.setMinimumSize(BUTTONS_DIMENSION);
        result.setBackground(MUSHROOM);
        result.add(play);
        result.add(exit);
        /*****************************************/
        menu.add(result, loc);
    }

    /**
     * The method sets the look of the TV panel.
     * @param str It is the name of the TV panel.
     * @param loc It is the location of the TV panel.
     * @param screen It is the TV panel itself.
     */
    private void setTv(String str, GridBagConstraints loc, TetrominoTv screen) {
        JPanel result = new JPanel();
        JLabel title = new JLabel(str, SwingConstants.CENTER);
        /*********************************/
        int screenW = screen.TV_DIMENSION.width;
        int screenH = screen.TV_DIMENSION.height;
        Font f = new Font("cambria",Font.BOLD,24);
        Dimension titleDimension = new Dimension(screenW,TV_LABEL_HEIGHT);
        title.setPreferredSize(titleDimension);
        title.setMinimumSize(titleDimension);
        title.setFont(f);
        /*********************************/
        Dimension tvDimension = new Dimension(screenW,screenH+TV_LABEL_HEIGHT);
        result.setPreferredSize(tvDimension);
        result.setMinimumSize(tvDimension);
        result.setLayout(new BorderLayout());
        result.add(title, BorderLayout.NORTH);
        result.add(screen, BorderLayout.CENTER);
        result.setBorder(BorderFactory.createMatteBorder(2,1,1,2,Color.DARK_GRAY));
        /********************************/
        menu.add(result,loc);
    }

    /**
     * The method sets the looks of the game's menu, which consist of the TV panels and buttons.
     */
    private void setMenu() {
        menu = new JPanel();
        menu.setBackground(MUSHROOM);
        menu.setPreferredSize(MENU_DIMENSION);
        menu.setMinimumSize(MENU_DIMENSION);
        menu.setLayout(new GridBagLayout());
        /*****************************************/
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 0.5;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        setTv("Hold",gbc,ac.getTetrominoTv(ActivityCenter.HOLD_TV));
        /*****************************************/
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        setButtons(gbc);
        /*****************************************/
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 2;
        setTv("Preview",gbc,ac.getTetrominoTv(ActivityCenter.PREVIEW_TV));
    }

    /**
     * It sets the look of the game screen.
     */
    private void setGame() {
        mainScreen = ac.getMainScreen();
        mainScreen.setPreferredSize(GAME_DIMENSION);
        mainScreen.setMinimumSize(GAME_DIMENSION);
        mainScreen.setBorder(BorderFactory.createMatteBorder(1,1,2,2,Color.DARK_GRAY));
    }
}