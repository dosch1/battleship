package battleship.view;

import javax.swing.*;
import java.awt.*;

/**
 * Graphical interface for placing ships and playing the game
 */
public class GamePlayWindow extends JFrame {

    private static final Color CARRIER_COLOR = new Color(43,97,16);
    private static final Color SUBMARINE_COLOR = new Color(220,165,35);
    private static final Color DESTROYER_COLOR = new Color(91,0,91);
    private static final Color BATTLESHIP_COLOR = new Color(162,94,32);
    private static final Color CRUISER_COLOR = new Color(164,60,49);

    //variables used for sizing the frames and panels based on the screen size
    private final int frameSize;
    private final int boardPanelHeight;
    private final int buttonSize;

    private Board userBoard;
    private Board enemyBoard;
    private JTextArea log;
    private JScrollPane scrollPane;
    private ButtonGroup shipButtonGroup;
    private JPanel logPanel;
    private JPanel shipPanel;
    private JPanel boardPanel;
    private JPanel optionButtons;
    private JButton resetButton;
    private JButton randomButton;
    private JButton playGameButton;
    private JMenuItem rulesMenu;
    private JMenuItem howToPlay;
    private JCheckBoxMenuItem soundsItem;

    public GamePlayWindow(int frameSize){
        this.frameSize = frameSize;
        boardPanelHeight = (int) (frameSize*.85);
        buttonSize = (boardPanelHeight/2)/11;

        setupBoardPanel();
        setupLogPanel();
        setupShipPlacementOptionsPanel();

        setTitle("Battleship: Game Board");
        setSize(frameSize,frameSize);
        setMinimumSize(new Dimension(frameSize,frameSize));
        setResizable(true);
        setVisible(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(setupGamePlayBar());

        add(logPanel, BorderLayout.CENTER);
        add(boardPanel, BorderLayout.WEST);
        add(optionButtons, BorderLayout.SOUTH);
    }

    /*
     * Set up the user and game boards
     */
    private void setupBoardPanel(){
        boardPanel = new JPanel(new BorderLayout());

        boardPanel.setSize(new Dimension(boardPanelHeight/2, boardPanelHeight));

        userBoard = new Board(buttonSize);
        enemyBoard = new Board(buttonSize);

        boardPanel.add(enemyBoard, BorderLayout.NORTH);
        boardPanel.add(userBoard, BorderLayout.SOUTH);

        boardPanel.setBorder(BorderFactory.createEmptyBorder(0, 0,5,0));
    }

    /*
     * Set up the log
     */
    private void setupLogPanel(){
        log = new JTextArea(20,20);
        log.setText("Place your ships on the lower grid \n");
        log.setEditable(false);

        scrollPane = new JScrollPane(log);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setSize(frameSize/2, frameSize/2);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Messages"));

        logPanel = new JPanel(new BorderLayout());
        logPanel.add(scrollPane, BorderLayout.NORTH);

        logPanel.setBorder(BorderFactory.createEmptyBorder(5, (int) (frameSize *.1),5,5));

        setupShipPanel();
    }

    /*
     * Set up all the ship buttons for ship placement
     */
    private void setupShipPanel(){
        JRadioButton carrierButton = new JRadioButton("Carrier: 5 Tiles");
        JRadioButton battleshipButton = new JRadioButton("Battleship: 4 Tiles");
        JRadioButton cruiserButton = new JRadioButton("Cruiser: 3 Tiles");
        JRadioButton submarineButton = new JRadioButton("Submarine: 3 Tiles");
        JRadioButton destroyerButton = new JRadioButton("Destroyer: 2 Tiles");

        carrierButton.setActionCommand("Carrier");
        battleshipButton.setActionCommand("Battleship");
        cruiserButton.setActionCommand("Cruiser");
        submarineButton.setActionCommand("Submarine");
        destroyerButton.setActionCommand("Destroyer");

        shipButtonGroup = new ButtonGroup();
        shipButtonGroup.add(carrierButton);
        shipButtonGroup.add(battleshipButton);
        shipButtonGroup.add(cruiserButton);
        shipButtonGroup.add(submarineButton);
        shipButtonGroup.add(destroyerButton);

        shipButtonGroup.setSelected(carrierButton.getModel(), true);

        JPanel carrierPanel = new JPanel(new BorderLayout());
        JPanel battleshipPanel = new JPanel(new BorderLayout());
        JPanel cruiserPanel = new JPanel(new BorderLayout());
        JPanel submarinePanel = new JPanel(new BorderLayout());
        JPanel destroyerPanel = new JPanel(new BorderLayout());

        JLabel carrierColor = new JLabel("    ");
        JLabel battleShipColor = new JLabel("    ");
        JLabel cruiserColor = new JLabel("    ");
        JLabel submarineColor = new JLabel("    ");
        JLabel destroyerColor = new JLabel("    ");

        carrierColor.setBackground(CARRIER_COLOR);
        battleShipColor.setBackground(BATTLESHIP_COLOR);
        cruiserColor.setBackground(CRUISER_COLOR);
        submarineColor.setBackground(SUBMARINE_COLOR);
        destroyerColor.setBackground(DESTROYER_COLOR);

        carrierColor.setOpaque(true);
        battleShipColor.setOpaque(true);
        cruiserColor.setOpaque(true);
        submarineColor.setOpaque(true);
        destroyerColor.setOpaque(true);

        carrierPanel.add(carrierColor, BorderLayout.WEST);
        battleshipPanel.add(battleShipColor, BorderLayout.WEST);
        cruiserPanel.add(cruiserColor, BorderLayout.WEST);
        submarinePanel.add(submarineColor, BorderLayout.WEST);
        destroyerPanel.add(destroyerColor, BorderLayout.WEST);

        carrierPanel.add(carrierButton, BorderLayout.CENTER);
        battleshipPanel.add(battleshipButton, BorderLayout.CENTER);
        cruiserPanel.add(cruiserButton, BorderLayout.CENTER);
        submarinePanel.add(submarineButton, BorderLayout.CENTER);
        destroyerPanel.add(destroyerButton, BorderLayout.CENTER);

        shipPanel = new JPanel(new GridLayout(5, 1, 0, (int) (frameSize *.05)));

        shipPanel.add(carrierPanel);
        shipPanel.add(battleshipPanel);
        shipPanel.add(cruiserPanel);
        shipPanel.add(submarinePanel);
        shipPanel.add(destroyerPanel);

        shipPanel.setBorder(BorderFactory.createEmptyBorder(5, 0,0,0));

        logPanel.add(shipPanel, BorderLayout.CENTER);
    }

    /*
     * Set up the options that are available only during the ship placement phase
     */
    private void setupShipPlacementOptionsPanel(){
        optionButtons = new JPanel(new GridLayout(1, 3, 10, 0));
        optionButtons.setBorder(BorderFactory.createEmptyBorder(0, 5,5,5));

        resetButton = new JButton("Reset");
        randomButton = new JButton("Random");
        playGameButton = new JButton("Play Game");
        playGameButton.setEnabled(false);

        optionButtons.add(resetButton);
        optionButtons.add(randomButton);
        optionButtons.add(playGameButton);
    }

    /*
     * Setup the menu bar for this frame
     */
    private JMenuBar setupGamePlayBar(){
        JMenuBar menuBar = new JMenuBar();

        JMenu playMenu = new JMenu("Play Game");
        playMenu.setEnabled(false);
        menuBar.add(playMenu);

        JMenu helpMenu = new JMenu("Help");
        rulesMenu = new JMenuItem("Rules");
        howToPlay = new JMenuItem("How to Play");
        helpMenu.add(rulesMenu);
        helpMenu.add(howToPlay);
        menuBar.add(helpMenu);

        JMenu settingsMenu = new JMenu("Settings");

        JMenu computerDifficultyMenu = new JMenu("Computer Difficulty");
        computerDifficultyMenu.setEnabled(false);
        settingsMenu.add(computerDifficultyMenu);

        soundsItem = new JCheckBoxMenuItem("Sound Effects");
        settingsMenu.add(soundsItem);
        soundsItem.setSelected(true);
        menuBar.add(settingsMenu);

        return menuBar;
    }

    /**
     * Property for determining to play the sound effects or not
     * @return if the sounds option is selected
     */
    public boolean soundsSelected(){
        return soundsItem.isSelected();
    }

    public JMenuItem getRulesItem() {
        return rulesMenu;
    }

    public JMenuItem getHowToPlayItem(){
        return howToPlay;
    }

    public Board getUserBoard() {
        return userBoard;
    }

    public Board getEnemyBoard() {
        return enemyBoard;
    }

    public JButton getResetButton(){
        return resetButton;
    }

    public JButton getRandomButton(){
        return randomButton;
    }

    public JButton getPlayGameButton(){
        return playGameButton;
    }

    public ButtonGroup getShipButtonGroup(){
        return shipButtonGroup;
    }

    public JPanel getOptionButtons(){
        return optionButtons;
    }

    public JTextArea getLog() {return log;}

    public int getButtonSize(){
        return buttonSize;
    }
}
