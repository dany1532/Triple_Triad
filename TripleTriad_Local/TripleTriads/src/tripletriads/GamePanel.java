
package tripletriads;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class GamePanel extends JPanel{
    TripleTriad app;
    
    
    public GamePanel(TripleTriad tt){
      //reference to the frame
        app = tt;
        setSize(900,680);

    }

    
   //Pass the graphics to the game
    public void paint(Graphics g){
           app.game.paintGame((Graphics2D)g);
    }


}
