package tripletriads;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;


//Start of Application
public class TripleTriad extends JFrame {
    Rectangle background;
    int maxX;
    int maxY;
    GamePanel gamePanel;
    StartPanel startPanel;
    MenuBar menuBar;
    Game game;
    JPanel panels;
    CardLayout cl;
    //CreatePlayer createPanel;
    ScorePanel scorePanel;
    
 //Creates the JFrame
    public TripleTriad(){
      //Size
        maxX = 900;
        maxY = 680;
        background = new Rectangle(0,0,maxX,maxY);
        setSize(maxX,maxY);
        
     //Title and location of the Frame
        setTitle("Triple Triads");
        setLocation(200,50);
        
        
      //Create graphics Panel, instance of Game and load History
        gamePanel = new GamePanel(this);
        game = new Game(gamePanel,this);
        game.loadHistory();
        
     //Create Panels for scores and new game
        startPanel = new StartPanel(this);
        //createPanel = new CreatePlayer(this);
        scorePanel = new ScorePanel(this);
        
     //Create Panel with CardLayout and store the panels
        cl = new CardLayout();
        panels = new JPanel();
        panels.setLayout(cl);
        panels.add(gamePanel,"GamePanel");
        panels.add(startPanel,"StartPanel");
        //panels.add(createPanel,"CreatePanel");
        panels.add(scorePanel, "ScorePanel");
        cl.show(panels,"GamePanel");
        
    //Set Cardlayout for the frame and place the panel
        setLayout(new CardLayout());
        add(panels,"Panel");
        
        
    //Create a Menu Bar
        menuBar = new MenuBar(getContentPane(),this);
        setJMenuBar(menuBar);
        
        
    //Asks user if they want to exit the application
        addWindowListener(new WindowAdapter() {
            @Override
         public void windowClosing(WindowEvent e) {
             int result = JOptionPane.showConfirmDialog(
            getContentPane(),
            "Are you sure you want to exit the application?",
            "Exit Application",
            JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION){
             setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             game.saveHistory();
             }
         }
         });
        
    }

    public static void main(String[] args) {
        TripleTriad trTriads = new TripleTriad();
        trTriads.setVisible(true);

        trTriads.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
    }
    


}

        
    



