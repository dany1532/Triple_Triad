
package gameclient;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class GamePanel extends JPanel{
    GameClient app;
    ImageIcon boardImage = new ImageIcon("images/board4.png");
    ImageIcon p1VictoryImage = new ImageIcon("images/p1Image.png");
      ImageIcon p2VictoryImage = new ImageIcon ("images/p2Image.png");
      ImageIcon youWin = new ImageIcon ("images/youWin.png");
      ImageIcon draw = new ImageIcon ("images/drawImage.png");
    
    
    public GamePanel(GameClient gc){
      //reference to the frame
        app = gc;
        setSize(900,680);

    }

    
   //Pass the graphics to the game
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        //draw the board
            boardImage.paintIcon(app, g2, 0, 0);
            
            if(app.startGame){
                //Draw each of the player's hand
                for(int i = 0; i < app.sizeHand();i++){
                    if(!app.getHandCard(i).beenPlayed){
                        app.getHandCard(i).getCardImage().paintIcon(this, g2, 
                          app.getHandCard(i).getCardX(), app.getHandCard(i).getCardY());
                    }
                }
                
                for(int i = 0; i < app.currentBoard.BOARD_SIZE;i++){
                    if(app.returnBoardCard(i) != null){
                        
                        app.returnBoardCard(i).getCardImage().paintIcon(
                            this, g2, app.returnBoardCard(i).getCardX(), 
                            app.returnBoardCard(i).getCardY());
                    }
                }
                
                //Draw the scores of each player
            app.currentBoard.getImageScore1().paintIcon(this, g2, 120, 530);
            app.currentBoard.getImageScore2().paintIcon(this,g2, 725, 530);
            }
    }


}
