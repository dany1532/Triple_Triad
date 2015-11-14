/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclient;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameClient extends JFrame implements ActionListener{
  //for the frame
    Rectangle background;
    int maxX;
    int maxY;
    
 //Panels and Menus to show
    GamePanel gamePanel;
    StartPanel startPanel;
    MenuBar menuBar;
    JPanel panels;
    CardLayout cl;
    ScorePanel scorePanel;
    
 //For communication to server
    Socket mySocket = null;
    PrintWriter pw;
    BufferedReader br;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    
 //Client specific data
    Player player;
    int whatPlayer;
    boolean startGame;
    boolean selectMode = true;
    boolean locationMode = false;
    boolean canPlay = false; 
    Board currentBoard;
    javax.swing.Timer startDelayTimer;
    javax.swing.Timer turnDelayTimer;
    final Integer NUMBER_PLAYER1 = 1;
    final Integer NUMBER_PLAYER2 = 2;
    Integer cardSelected;
    Integer cardLocation;
    Integer score1 = 5; 
    Integer score2 = 5;
    
    public GameClient(){
        try {
         //Connect with Server
	    mySocket = new Socket( "localhost", 1532 );
	    System.out.println( "got a connection" );
	    
         //Create streams
	    pw = new PrintWriter( mySocket.getOutputStream(), true );
	    br = new BufferedReader( new InputStreamReader( mySocket.getInputStream() ) );
            oos = new ObjectOutputStream(mySocket.getOutputStream());
            ois = new ObjectInputStream(mySocket.getInputStream());
	    System.out.println("got streams");
            
        //GUI Creation
            createPanels_Layout();
            setFocusable(true);
            addKeyListener(new DirectionKeyListener());
            
        //Game related data
            currentBoard = new Board();
            startDelayTimer = new javax.swing.Timer(50,this);
            turnDelayTimer = new javax.swing.Timer(50,this);
            startDelayTimer.setRepeats(false);
            turnDelayTimer.setRepeats(false);
            
        //For painting the initial score since it always starts 5 - 5
            currentBoard.setScore(score1, score2);

	} catch (Exception e) {
	    System.out.println("got an exception" + e.getMessage() );
	}
        
    }
    
 //Send a request to the server
    public void sendRequest(String command){
        try{
            pw.println(command);
        }
        catch(Exception e){
            System.out.println("got an exception" + e.getMessage());
        }  
    }
    
 //Send an object to the server
    public void sendObject(Object obj){
        try {
            oos.writeObject(obj);
        } 
        catch (Exception e) {
            System.out.println("got an exception" + e.getMessage());
        }
    }
    
//Wait for the server to return an object
    public Object returnedObject(){
        Object obj = null;
        try{
            obj = ois.readObject();
        }
        
        catch (Exception e) {
            System.out.println("got an exception" + e.getMessage());
        }
        
        return obj;
    }
    
 //When the other panels need to send player to server
    public Object returnPlayer(){
        return player;
    }
    
 //Ask the server if you can do a certain action
    public boolean permissionGranted(){
        String message;
        try{
            message = br.readLine();
            System.out.println(message);
        }
        catch (Exception e){
            System.out.println("got an exception" + e.getMessage());
            return false;
        }
        
        if(message.equals("ok"))
            return true;
        else
            return false;      
    }
    
 //Wait for the server for a reply, the question: Is the game over?
    public boolean checkGameOver(){
        String message;
        try{
            //System.out.println("here?");
            message = br.readLine();
            System.out.println(message);
        }
        catch (Exception e){
            System.out.println("got an exception" + e.getMessage());
            return false;
        }
        
        if(message.equals("Server: GameOver"))
            return true;
        else
            return false;      
    }
    
 //Wait for the server for any kind of reply. Used for making people take turns
    public void waitServer(){
        String message;
        try{
            message = br.readLine();
            System.out.println(message);
        }
        catch (Exception e){
            System.out.println("got an exception" + e.getMessage());
        }  
        
    }
    
//This client is player 1 or 2? The server will decide...
    public void setWhatPlayer(){
        String message;
        try{
            message = br.readLine();
            System.out.println(message);
            if(message.equals("1"))
                whatPlayer = 2;
            else
                whatPlayer = 1;
            
            System.out.println(""+whatPlayer);
            player.newHand(whatPlayer);
        }
        catch (Exception e){
            System.out.println("got an exception" + e.getMessage());
        }
    }
    
 //Creates a player with the desired name
    public void createPlayer(String newPlayer){
        player = new Player(1,newPlayer);
    }
    
 //Loads the info of the desired player
    public void loadPlayer(Player loadedPlayer){
        player = loadedPlayer;
    }
    
//Returns the player's name
    public String returnPlayerName(){
        return player.playerName;
    }
    
 //Returns a card from the hand
    public Card getHandCard(int i){
        return player.myHand.get(i);
    }
    
 //returns the size of the hand
    public int sizeHand(){
        return player.HAND_SIZE;
    }
    
//Creates the GUI Layout
    private void createPanels_Layout(){
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
        
     //Create Panels for scores and new game
        startPanel = new StartPanel(this);
        scorePanel = new ScorePanel(this);
        
     //Create Panel with CardLayout and store the panels
        cl = new CardLayout();
        panels = new JPanel();
        panels.setLayout(cl);
        panels.add(gamePanel,"GamePanel");
        panels.add(startPanel,"StartPanel");
        panels.add(scorePanel, "ScorePanel");
        cl.show(panels,"GamePanel");
        
    //Set Cardlayout for the frame and place the panel
        setLayout(new CardLayout());
        add(panels,"Panel");
        
        
    //Create a Menu Bar
        JMenuBar bar = new JMenuBar();
        menuBar = new MenuBar(getContentPane(),this);
        setJMenuBar(menuBar);
    }
    
   //is it player1's turn to select a card? is it possible?
    public boolean playerSelectCard(int wantedCard){
         if(!player.myHand.get(wantedCard).beenPlayed) 
                return true;
         else
                return false;
    }
        
 //Little animation of the card selected. Depends on what player you are...    
    public void cardSelectAnim(int index){
         //for the player 1's card
        if(whatPlayer == 1){
            System.out.println("Selected: "+player.handCard(index).getName());
            player.handCard(index).setCardLocation(player.handCard(index).getCardX()-20,
                   player.handCard(index).getCardY());
        }
                
        //for the player 2's card
            else {
                System.out.println("Selected: "+player.handCard(index).getName());
                player.handCard(index).setCardLocation(player.handCard(index).getCardX()+20,
                      player.handCard(index).getCardY());
            }
            
            
        //repaint
            gamePanel.repaint();
        }
        
 //Can you place the card on the board? Is the space empty?    
    public boolean checkFreeSpace(){
         if(currentBoard.boardList.get(cardLocation) == null)
             return true;
         else{
             System.out.println("Space occupied");
             return false;
         }
    }
        
 //Send to the server: The desired location in the board, am I player 1 or 2, 
    //the card that I want to play and how many card I have in my hand(scoring
        //purposes)
    public void sendCurrentBoard(){
      //add card to the board, set the position of the card for painting purposes
        //set the card to what player it is..
         currentBoard.addCardtoArray(player,cardLocation,cardSelected);
         currentBoard.setPositionCard();
         currentBoard.getBoardCard(cardLocation).setPlayerCard(whatPlayer);
         gamePanel.repaint();
         
      //For the next turn purposes
         locationMode = false;
         canPlay = false;
         
      //Send request and then send the objects...
         sendRequest("sendBoard");
         waitServer();
         sendObject(cardLocation);
         sendObject(whatPlayer);
         sendObject(currentBoard.getBoardCard(cardLocation));
         sendObject(cardsHand());
            
         waitServer();
         
      //Wait a little bit before sending the request of turn over (painting 
         //purposes)
         turnDelayTimer.restart();
            
        }
        
    //Update my board for painting purposes
        public void updateCurrentBoard(){
            sendRequest("returnBoard");
            waitServer();
         
         //Get the cards of the player 1
            sendObject(NUMBER_PLAYER1);
            Integer p1Cards = (Integer) returnedObject();
            if(p1Cards > 0){
                for(int i = 0; i < p1Cards;i++){
                    Integer newLocation = (Integer)returnedObject();
                    Card newCard = (Card) returnedObject();
                    newCard.setPlayerCard(NUMBER_PLAYER1);
                    currentBoard.addCardtoArray(newLocation, newCard);
                }
            }
            
            waitServer();
            
         //Get the cards of the player 2
            sendObject(NUMBER_PLAYER2);
            Integer p2Cards = (Integer) returnedObject();
            if(p2Cards > 0){
                for(int i = 0; i < p2Cards;i++){
                    Integer newLocation = (Integer)returnedObject();
                    Card newCard = (Card) returnedObject();
                    newCard.setPlayerCard(NUMBER_PLAYER2);
                    currentBoard.addCardtoArray(newLocation, newCard);
                }
            }
            
         //Update Score
            score1 = (Integer)returnedObject();
            score2 = (Integer)returnedObject();
            
         //Set the score for the paint
            currentBoard.setScore(score1, score2);
            
            gamePanel.repaint();
            selectMode = true;
            canPlay = true;
        }
        
     //How many cards are on my hand
        public Integer cardsHand(){
            Integer count = 0;
            for(int i = 0; i < player.HAND_SIZE;i++){
                if(!player.myHand.get(i).beenPlayed)
                    count++;
            }
            return count;
        }
        
   //Delay Timers so that the paint function can do its magic
    @Override
        public void actionPerformed(ActionEvent ae){
         //Start Game and wait for the server to contact you
            if(startDelayTimer == ae.getSource()){
                sendRequest("startgame");
                waitServer();
                
             //For the second player, since he will start here...
                updateCurrentBoard();
            }
            
        //Wait for the server to reply if it is game over
            else{
                sendRequest("turn over");
                if(checkGameOver()){
                    updateCurrentBoard();
                    sendRequest("Beginning");
                    selectMode = false;
                    canPlay = false;
                    menuBar.startGameItem.setEnabled(true);
                    menuBar.viewScoresItem.setEnabled(true);
                    
                }
                
            //its not game over, go ahead and continue playing
                else
                    updateCurrentBoard();
            }
        }
        
  //Return a card from the board
        public Card returnBoardCard(int index){
            return currentBoard.getBoardCard(index);
        }
    
    //user Input:
        //A,S,D,F,G: for selected the cards
        //1,2,3,4,5,6,7,8,9: where to put the card in the board
        private class DirectionKeyListener implements KeyListener{
          @Override
          public void keyPressed(KeyEvent event){
              
              
            if(canPlay){
              switch(event.getKeyCode()){
           ////////////////////////////////////////////////////////
             ////which card to choose
                  case KeyEvent.VK_A: 
                      if(selectMode){
                        cardSelected = 0;
                        if(playerSelectCard(cardSelected)){
                            cardSelectAnim(cardSelected);
                            selectMode = false;
                            locationMode = true;
                        }
                        else
                            System.out.println("Card Not in Hand");
                      }
                      break;
//                      
                      
                      
                      
                  case KeyEvent.VK_S: if(selectMode){
                        cardSelected = 1;
                        if(playerSelectCard(cardSelected)){
                            cardSelectAnim(cardSelected);
                            selectMode = false;
                            locationMode = true;
                        }
                        else
                            System.out.println("Card Not in Hand");
                      }
                      break;
                      
                  case KeyEvent.VK_D:
                      if(selectMode){
                        cardSelected = 2;
                        if(playerSelectCard(cardSelected)){
                            cardSelectAnim(cardSelected);
                            selectMode = false;
                            locationMode = true;
                        }
                        else
                            System.out.println("Card Not in Hand");
                      }
                      break;
                      
                  case KeyEvent.VK_F:
                      if(selectMode){
                        cardSelected = 3;
                        if(playerSelectCard(cardSelected)){
                            cardSelectAnim(cardSelected);
                            selectMode = false;
                            locationMode = true;
                        }
                        else
                            System.out.println("Card Not in Hand");
                      }
                      break;
                      
                  case KeyEvent.VK_G:
                      if(selectMode){
                        cardSelected = 4;
                        if(playerSelectCard(cardSelected)){
                            cardSelectAnim(cardSelected);
                            selectMode = false;
                            locationMode = true;
                        }
                        else
                            System.out.println("Card Not in Hand");
                      }
                      break;
                   
       ///////////////////////////////////////////////////////////////////
                      
         ///Choose the desired card's location
                  case KeyEvent.VK_1:
                      if(locationMode){
                          cardLocation = 0;
                          if(checkFreeSpace()){
                            System.out.println("cardPlayer1: "+player.handCard(0).cardPlayer);
                            getHandCard(cardSelected).setToPlayed();
                            System.out.println("cardPlayer2: "+player.handCard(0).cardPlayer);
                            sendCurrentBoard(); 
                          }
                      }
                      break;

                      
                      
                  case KeyEvent.VK_2:
                      if(locationMode){
                          cardLocation = 1;
                          if(checkFreeSpace()){
                            getHandCard(cardSelected).setToPlayed();
                            sendCurrentBoard(); 
                          }
                      }
                      break;

                          
                  case KeyEvent.VK_3:
                      if(locationMode){
                          cardLocation = 2;
                          if(checkFreeSpace()){ 
                            getHandCard(cardSelected).setToPlayed();
                            sendCurrentBoard(); 
                          }
                      }
                      break;

                              
                  case KeyEvent.VK_4:
                      if(locationMode){
                          cardLocation = 3;
                          if(checkFreeSpace()){
                            getHandCard(cardSelected).setToPlayed();
                            sendCurrentBoard(); 
                          }
                      }
                      break;

                                  
                  case KeyEvent.VK_5:
                      if(locationMode){
                          cardLocation = 4;
                          if(checkFreeSpace()){
                            getHandCard(cardSelected).setToPlayed();
                            sendCurrentBoard(); 
                          }
                      }
                      break;

                      
                  case KeyEvent.VK_6:
                      if(locationMode){
                          cardLocation = 5;
                          if(checkFreeSpace()){
                            getHandCard(cardSelected).setToPlayed();
                            sendCurrentBoard(); 
                          }
                      }
                      break;

                      
                  case KeyEvent.VK_7:
                      if(locationMode){
                          cardLocation = 6;
                          if(checkFreeSpace()){
                            getHandCard(cardSelected).setToPlayed();
                            sendCurrentBoard(); 
                          }
                      }
                      break;

                     
                  case KeyEvent.VK_8:
                      if(locationMode){
                          cardLocation = 7;
                          if(checkFreeSpace()){
                            getHandCard(cardSelected).setToPlayed();
                            sendCurrentBoard(); 
                          }
                      }
                      break;

                    
                  case KeyEvent.VK_9:
                      if(locationMode){
                          cardLocation = 8;
                          if(checkFreeSpace()){
                            getHandCard(cardSelected).setToPlayed();
                            sendCurrentBoard(); 
                          }
                      }
                      break;

                 
           //The game ended, Press Enter to restart
               case KeyEvent.VK_ENTER:
                   for(int i = 0; i < currentBoard.BOARD_SIZE;i++){
                    if (currentBoard.getBoardCard(i) != null)
                        System.out.println("CardPlayer "+i+": "+ currentBoard.getBoardCard(i).cardPlayer);
                    }
                   break;
                   
                      
              }
            }
              
          }

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
      }
    
    
    public static void main( String[] args ) {
        GameClient gc = new GameClient();
        gc.setVisible(true);
        gc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
