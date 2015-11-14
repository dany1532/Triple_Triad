
package tripletriads;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Game implements ActionListener, Serializable {
  //Initial set up
      GamePanel myApp;
      Player player1;
      Player player2;
      Board newBoard;
      MusicPlayer mp;
      Timer tm;
      TripleTriad frame;
      ArrayList<Player> playersList = new ArrayList<Player>();
      
  //For the turns
      Boolean selectMode;
      Boolean locationMode;
      Boolean gameStart;
      
  //For the scores, what turn is it, and the cards selected
      Integer playerTurn;
      Integer cardLocation;
      Integer cardSelected;
      Integer whoWon;
      Integer p1Score;
      Integer p2Score;
      
  //get the images for board, victory and draw, and the selector
      ImageIcon boardImage = new ImageIcon("images/board4.png");
      ImageIcon p1VictoryImage = new ImageIcon("images/p1Image.png");
      ImageIcon p2VictoryImage = new ImageIcon ("images/p2Image.png");
      ImageIcon youWin = new ImageIcon ("images/youWin.png");
      ImageIcon draw = new ImageIcon ("images/drawImage.png");
      ImageIcon selector = new ImageIcon ("images/Selector.png");
      
      
      //Constructor
        public Game(GamePanel ga,TripleTriad tt){
            myApp = ga;
            frame = tt;
            
            gameStart = false;
            
        }
        
    //Loads player's info
        public void loadHistory(){
            try {
                   ObjectInputStream input = new ObjectInputStream(
                                             new FileInputStream ("history.data"));
                try {
                    playersList = (ArrayList<Player>)(input.readObject());
                    
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Could Not Load File");
                }
                    
                    
                } catch (IOException ex) {
                    //JOptionPane.showMessageDialog(null, "");
                }
        }
       
    //Saves Player's info
        public void saveHistory(){
             try{
                FileOutputStream f_out = new FileOutputStream("history.data");
                ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
                obj_out.writeObject ( playersList);
                obj_out.close();

                
            }
            catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Could Not Create File");
                }
        }
     
   //Starting a game, set the players
        public void setPlayers(Player newP1, Player newP2){
            player1 = newP1;
            player2 = newP2;

        }
        
    //Adding a player to the players List
        public void addPlayer(Player newP){
            Player newPlayer = newP;
            playersList.add(newPlayer);
        }
        
     //Initial set up of game
        public void startGame(){
       //Create the players, board, music player, timer for looping
//            player1 = new Player(1,"bla1");
//            player2 = new Player(2,"bla2");
            newBoard = new Board();
            mp = new MusicPlayer("music.wav");
            tm = new Timer(122000,this);
            tm.start();

            gameStart = true;
            
            
       //reset the modes
            playerTurn = 1;
            selectMode = true;
            locationMode = false;
            whoWon = -1;
            cardSelected = -1;
            cardLocation = -1;
            
        //Asks user for a card
            System.out.println("Select a Card");
            
        //updates the score and repaints it
            updateScore();
            myApp.repaint();
            
        //Allows for user input  
            frame.setFocusable(true);
            frame.addKeyListener(new DirectionKeyListener());
            
        }
        
        public void stopGame(){
            mp.stopAudio();
            tm.stop();
            gameStart = false;
            myApp.repaint();
        }
        
  //Used when the game is over, to restart the game
        public void reStartGame(){
       //get new Player hands, clear board, reset the music player
            player1.newHand(1);
            player2.newHand(2);
            newBoard.clearBoard();
            mp = new MusicPlayer("music.wav");
            tm = new Timer(122000,this);
            tm.start();
            
       //reset the modes
            playerTurn = 1;
            selectMode = true;
            locationMode = false;
            cardSelected = -1;
            cardLocation = -1;
            whoWon = -1;
            
       //updates score and repaints
            updateScore();
            myApp.repaint();
        }
        
   //Draw the board, cards and all messages
        public void paintGame(Graphics2D g2){
        //draw the board
            boardImage.paintIcon(myApp, g2, 0, 0);
           
        if(gameStart){
        //Draw the selector, to know who's turn it is
            if(playerTurn == 1){
                selector.paintIcon(myApp,g2,100,40);
            }
            
            else if(playerTurn == 2){
                selector.paintIcon(myApp,g2,700,40);
            }
            
        //Draw each of the player's hand
            for(int i = 0; i < player1.myHand.size();i++){
               player1.myHand.get(i).paintCard(myApp,g2,
                     player1.myHand.get(i).getCardX(),player1.myHand.get(i).getCardY());
             
               player2.myHand.get(i).paintCard(myApp,g2,
                     player2.myHand.get(i).getCardX(),player2.myHand.get(i).getCardY());
            }
            
       //Draw the scores of each player
            newBoard.getImageScore1().paintIcon(myApp, g2, 120, 530);
            newBoard.getImageScore2().paintIcon(myApp,g2, 725, 530);
            
            
        //When vicotry has ensued, draw the according winner message
            if(whoWon == 1){
                p1VictoryImage.paintIcon(myApp,g2,405,280);
                youWin.paintIcon(myApp,g2, 260,320);
            }
            
            else if(whoWon == 2){
                p2VictoryImage.paintIcon(myApp, g2, 405, 280);
                youWin.paintIcon(myApp,g2,260,320);
            }
            
            //in case of a draw
            else if(whoWon == 3){
                draw.paintIcon(myApp, g2, 260, 320);
            }
          }//end of gameStart

        }
        
    //Timer for the looping of background music
        public void actionPerformed(ActionEvent ae){
            mp.stopAudio();
            if(whoWon != 1 || whoWon != 2)
                 mp = new MusicPlayer("music.wav");
        }
        
    //Use of the basic rule. If the according card is higher than opponent than
        //flip card
        public void basicRule(){
         //Actual application of the basic rule
            newBoard.checkBasicRule(cardLocation,playerTurn);
            
         //updates the scores
            updateScore();
            
         //resets values for next turn
            cardSelected = -1;
            cardLocation = -1;
            
         //ReDraw
            myApp.repaint();
            
         //Change of  turns
            if(playerTurn == 1)
                playerTurn = 2;
            else
                playerTurn = 1;
            
        //Can now select a card
            selectMode = true;
            
        //Check if the game is over
            checkGameOver();
        }
        
   //Checks if the game is over
        public void checkGameOver(){
         //to know how many cards are in the board
            int count = 0;
            for(int i = 0; i < newBoard.BOARD_SIZE;i++){
                if(newBoard.getBoardCard(i) != null)
                    count++;
            }
            
         //if the amount of cards is equal to the size of
            //the board then game is over
            if (count == newBoard.BOARD_SIZE)
                gameOver();
        }
       
    //Game is over, who won? or is it a draw?
        public void gameOver(){
            if(p1Score > p2Score){
                whoWon = 1;
                player1.gamesWon++;
                player2.gamesLost++;
            }
            
            else if(p1Score < p2Score){
                whoWon = 2;
                player1.gamesLost++;
                player2.gamesWon++;
            }
            
            else if(p1Score == p2Score)
                whoWon = 3;
            
       //Stop the audio
            mp.stopAudio();
            
       //Message for the user about restarting game
            System.out.println("Press Enter to Restart");
            
       //Redraw with the according victory message
            myApp.repaint();
        }
        
    //is it player1's turn to select a card? is it possible?
        public boolean player1SelectCard(){
            if(playerTurn == 1 && selectMode && !player1.myHand.get(cardSelected).beenPlayed) 
                return true;
            else
                return false;
        }
        
    //is it p2's turn to select a card? is it possible?
        public boolean player2SelectCard(){
            if(playerTurn == 2 && selectMode && !player2.myHand.get(cardSelected).beenPlayed) 
                return true;
            else
                return false;
        }
        
   //is the board's location free?
        public boolean checkFreeSpace(){
            if(newBoard.board.get(cardLocation) == null)
                return true;
            else
                return false;
        }
        
   //prepare to go to the basic rule
        public void goToBasicRule(){
            locationMode = false;
            basicRule();
        }
        
   //prepare to go to the same and plus rule
        public void goToSameorPlusRule(){
            locationMode = false;
            sameRule();
            plusRule();
        }
        
   //Use of the same rule
        public void sameRule(){
        //1 means that it is the same rule
            int ruleofSame = 1;
            
         //Set the position's of the card
            newBoard.setPositionCard(playerTurn, player1, player2, cardLocation, cardSelected);
            
         //add it to the board's array
            newBoard.addCardtoArray(player1,player2,cardLocation,cardSelected,playerTurn);
            
         //Application of the same rule
            newBoard.checkSameorPlusRule(cardLocation, playerTurn,ruleofSame);
        }
        
    //Use of the plus rule
        public void plusRule(){
        //2 means that it is the plus rule
            int ruleofPlus = 2;
            
        //Application of the plus rule
            newBoard.checkSameorPlusRule(cardLocation, playerTurn,ruleofPlus);
            
        //Check for the basic rule
            goToBasicRule();
        }
        
     //updates the score
        public void updateScore(){
            
         //Initial set up
            p1Score = 0;
            p2Score = 0;
            
        //Who does the card belong to? player1? or player2?
            for(int i = 0; i < player1.getHand().size();i++){
                if(player1.gethandPlayerCard(i) == 1)
                    p1Score++;
                else
                    p2Score++;
            
                if(player2.gethandPlayerCard(i) == 1)
                    p1Score++;
                else
                    p2Score++;
            }
            
        //set the score's images
            newBoard.setScore(p1Score, p2Score);
        }
        
    //Moves the card so that the user knows the card they selected
        public void cardSelectAnim(int index, Player p){
         //for the player 1's card
            if(playerTurn == 1) {
                System.out.println("Selected: "+p.handCard(1).getName());
                p.handCard(index).setCardLocation(p.handCard(index).getCardX()-20,
                      p.handCard(index).getCardY());
            }
            
        //for the player 2's card
            else {
                System.out.println("Selected: "+p.handCard(1).getName());
                p.handCard(index).setCardLocation(p.handCard(index).getCardX()+20,
                      p.handCard(index).getCardY());
            }
            
        //repaint
            myApp.repaint();
        }
       
    //choose where to put the card
        public void gotoSelectMode(){
         //can now choose where to put the card
            locationMode = true;
            selectMode = false;
            
        }
        
   //user Input:
        //A,S,D,F,G: for selected the cards
        //1,2,3,4,5,6,7,8,9: where to put the card in the board
        private class DirectionKeyListener implements KeyListener{
          @Override
          public void keyPressed(KeyEvent event){
             // if(gameStart){
              switch(event.getKeyCode()){
           ////////////////////////////////////////////////////////
             ////which card to choose
                  case KeyEvent.VK_A:
                      if(selectMode)
                        cardSelected = 0;
                      if(player1SelectCard()) {
                         cardSelectAnim(cardSelected,player1);
                         gotoSelectMode();
                      }
                    
                    else if(player2SelectCard()) {
                       cardSelectAnim(cardSelected,player2);
                       gotoSelectMode();
                    }  
                    else if(selectMode) {
                     System.out.println("Not in your hand");
                     System.out.println("Please Select a Card");
                    }
                    break; 
                      
                  case KeyEvent.VK_S:
                      if(selectMode)
                        cardSelected = 1;
                    if(player1SelectCard()) {
                         cardSelectAnim(cardSelected,player1);
                         gotoSelectMode();
                      }
                    
                    else if(player2SelectCard()) {
                       cardSelectAnim(cardSelected,player2);
                       gotoSelectMode();
                    }  
                    else if(selectMode) {
                     System.out.println("Not in your hand");
                     System.out.println("Please Select a Card");
                    }
                    break; 
                      
                  case KeyEvent.VK_D:
                      if(selectMode)
                        cardSelected = 2;
                    if(player1SelectCard()) {
                         cardSelectAnim(cardSelected,player1);
                         gotoSelectMode();
                      }
                    
                    else if(player2SelectCard()) {

                       cardSelectAnim(cardSelected,player2);
                       gotoSelectMode();
                    }  
                    else if(selectMode) {
                     System.out.println("Not in your hand");
                     System.out.println("Please Select a Card");
                    }
                    break; 
                      
                  case KeyEvent.VK_F:
                      if(selectMode)
                        cardSelected = 3;
                      if(player1SelectCard()) {
                         cardSelectAnim(cardSelected,player1);
                         gotoSelectMode();
                      }
                    
                    else if(player2SelectCard()) {
                       cardSelectAnim(cardSelected,player2);
                       gotoSelectMode();
                    }  
                    else if(selectMode) {
                     System.out.println("Not in your hand");
                     System.out.println("Please Select a Card");
                    }
                    break; 
                      
                  case KeyEvent.VK_G:
                      if(selectMode)
                        cardSelected = 4;
                    if(player1SelectCard()) {
                         cardSelectAnim(cardSelected,player1);
                         gotoSelectMode();
                      }
                    
                    else if(player2SelectCard()) {
                       cardSelectAnim(cardSelected,player2);
                       gotoSelectMode();
                    }  
                    else if(selectMode) {
                     System.out.println("Not in your hand");
                     System.out.println("Please Select a Card");
                    }
                      break; 
                   
       ///////////////////////////////////////////////////////////////////
                      
         ///Choose the desired card's location
                  case KeyEvent.VK_1:
                      if(locationMode){
                          cardLocation = 0;
                          if(checkFreeSpace())
                            goToSameorPlusRule();
                      }
                      break;
                      
                  case KeyEvent.VK_2:
                      if(locationMode){
                          cardLocation = 1;
                          if(checkFreeSpace())
                            goToSameorPlusRule();
                      }
                      break;
                          
                  case KeyEvent.VK_3:
                      if(locationMode){
                          cardLocation = 2;
                          if(checkFreeSpace())
                            goToSameorPlusRule();
                      }
                      break;
                              
                  case KeyEvent.VK_4:
                      if(locationMode){
                          cardLocation = 3;
                          if(checkFreeSpace())
                            goToSameorPlusRule();
                      }
                      break;
                                  
                  case KeyEvent.VK_5:
                      if(locationMode){
                          cardLocation = 4;
                          if(checkFreeSpace())
                            goToSameorPlusRule();
                      }
                      break;
                      
                  case KeyEvent.VK_6:
                      if(locationMode){
                          cardLocation = 5;
                          if(checkFreeSpace())
                            goToSameorPlusRule();
                      }
                      break;
                      
                 case KeyEvent.VK_7:
                      if(locationMode){
                          cardLocation = 6;
                          if(checkFreeSpace())
                            goToSameorPlusRule();
                      }
                      break;
                     
                case KeyEvent.VK_8:
                      if(locationMode){
                          cardLocation = 7;
                          if(checkFreeSpace())
                            goToSameorPlusRule();
                      }
                      break;
                    
               case KeyEvent.VK_9:
                      if(locationMode){
                          cardLocation = 8;
                          if(checkFreeSpace())
                            goToSameorPlusRule();
                      }
                      break;
                 
           //The game ended, Press Enter to restart
               case KeyEvent.VK_ENTER:
                   System.out.print(player1.playerName);
                   if(whoWon == 1 || whoWon == 2 || whoWon == 3)
                     reStartGame();
                     break;  
                   
               case KeyEvent.VK_0:
                   for(int i = 0; i < player1.HAND_SIZE;i++){
                       System.out.println(player1.myHand.get(i).cardName);
                   }
                   System.out.println("Select Mode: "+ selectMode);
                   System.out.println("Card Selected: "+ cardSelected);
                      
              }
              
              
              
            //}//end of gam
              
          }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
      }
        

}
