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

//Practically the same code as GameClient, changing only the order of some things
    //Because of the speed, the AI will always be player 2.
public class AI implements ActionListener {
    
    Socket mySocket = null;
    PrintWriter pw;
    BufferedReader br;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    
    Board currentBoard;
    Player computer;
    javax.swing.Timer connectTimer;
    javax.swing.Timer startDelayTimer;
    javax.swing.Timer turnDelayTimer;
    Integer whatPlayer;
    final Integer NUMBER_PLAYER1 = 1;
    final Integer NUMBER_PLAYER2 = 2;
    Integer cardSelected;
    Integer cardLocation;
    Integer score1;
    Integer score2;
    
    public AI(){
        connectTimer = new javax.swing.Timer(50,this);
        startDelayTimer = new javax.swing.Timer(20,this);
        turnDelayTimer = new javax.swing.Timer(50,this);
        
        startDelayTimer.setRepeats(false);
        turnDelayTimer.setRepeats(false);
        connectTimer.setRepeats(false);
        
        connectTimer.start();
    }
    
    public void connectServer(){
     //Connect to serer and create Streams
        try {
	    mySocket = new Socket( "localhost", 1532 );
	    System.out.println( "AI: got a connection" );
	    
	    pw = new PrintWriter( mySocket.getOutputStream(), true );
	    br = new BufferedReader( new InputStreamReader( mySocket.getInputStream() ) );
            oos = new ObjectOutputStream(mySocket.getOutputStream());
            ois = new ObjectInputStream(mySocket.getInputStream());
	    System.out.println("AI: got streams");
            createPlayer();
            
          //recursive function
            startGame();

	} catch (Exception e) {
	    System.out.println("got an exception" + e.getMessage() );
	}
    }
    
 //Creates Player and sets what player it is...
    public void createPlayer(){
        currentBoard = new Board();
        computer = new Player(1,"Computer");
        sendRequest("clearBoard");
        sendRequest("setPlayer");
        setWhatPlayer();
        printHand();
    }
    
 //Clear board and get new hand
    public void restartGame(){
        currentBoard.clearBoard();
        sendRequest("clearBoard");
        sendRequest("setPlayer");
        setWhatPlayer();
        printHand();
        startGame();
    }
    
 //Returns a card from the hand
    public Card getHandCard(int i){
        return computer.myHand.get(i);
    }
    
 //Clear board and restart the timer for starting game
    public void startGame(){
        currentBoard.clearBoard();
        startDelayTimer.restart();
    }
    
 //Play a card from hand and locate an empty space on board
    public void playCard(){
        cardSelected = 0;
        
    //Select a card from Hand
        while(true){
            if(canPlayCard(cardSelected))
                break;
            else
                cardSelected++;
        }
        
        cardLocation = 0;
    //Where in the board to place it
        while(true){
            if(checkFreeSpace())
                break;
            else
                cardLocation++;
        }
        
        getHandCard(cardSelected).setToPlayed();  
    }
    
 // Check if the space on the board is free
    public boolean checkFreeSpace(){
            if(currentBoard.boardList.get(cardLocation) == null)
                return true;
            else{
                return false;
            }
        }
    
    //is it player1's turn to select a card? is it possible?
    public boolean canPlayCard(int wantedCard){
            if(!computer.myHand.get(wantedCard).beenPlayed) 
                return true;
            else
                return false;
        }
    
 //Wait for server to tell the AI if the game is over
    public boolean checkGameOver(){
        String message;
        try{
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
    
 //what player am I (Because of the speed the AI will be player 2 always
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
            computer.newHand(whatPlayer);
        }
        catch (Exception e){
            System.out.println("got an exception" + e.getMessage());
        }
    }
    
 //wait for the server to answer...
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
    
 //Print the current hand
    public void printHand(){
        for(int i = 0; i < computer.HAND_SIZE;i++){
            if(!computer.myHand.get(i).beenPlayed)
                System.out.println("Computer Hand "+i+": "+computer.myHand.get(i).cardName);
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
            //oos.reset();
        } 
        catch (Exception e) {
            System.out.println("got an exception" + e.getMessage());
        }
    }
    
 //Obtain an object from server
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
    
 //How many playable cards are in my hand
    public Integer cardsHand(){
            Integer count = 0;
            for(int i = 0; i < computer.HAND_SIZE;i++){
                if(!computer.myHand.get(i).beenPlayed)
                    count++;
            }
            return count;
        }
    
 //Send to the server the wanted location in board, card, what player am I and
    //how many cards in my hand I have
    public void sendCurrentBoard(){
            currentBoard.addCardtoArray(computer,cardLocation,cardSelected);
            currentBoard.setPositionCard();
            currentBoard.getBoardCard(cardLocation).setPlayerCard(whatPlayer);
            sendRequest("sendBoard");
            waitServer();
            sendObject(cardLocation);
            sendObject(whatPlayer);
            sendObject(currentBoard.getBoardCard(cardLocation));
            sendObject(cardsHand());
            waitServer();
            turnDelayTimer.restart();
            
        }
    
  //Obtain info from the server to update my board
    public void updateCurrentBoard(){
            sendRequest("returnBoard");
            waitServer();
            sendObject(NUMBER_PLAYER1);
        //Obtain info from player 1
            Integer p1Cards = (Integer) returnedObject();
            if(p1Cards > 0){
                for(int i = 0; i < p1Cards;i++){
                    Integer newLocation = (Integer)returnedObject();
                    Card newCard = (Card) returnedObject();
                    newCard.setPlayerCard(NUMBER_PLAYER1);
                    currentBoard.addCardtoArray(newLocation, newCard);
                }
            }
            
         //Obtain info from player 2
            waitServer();
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
            
         //Update the score (Not so much for the AI, but necessary for human players)
            score1 = (Integer)returnedObject();
            score2 = (Integer)returnedObject();
        }
    
    @Override
        public void actionPerformed(ActionEvent ae){
      //Delay Timer so that the server get ready to connect to a client
            if(connectTimer == ae.getSource())
                connectServer();
            
       //Delaytimer for starting the game...
            else if(startDelayTimer == ae.getSource()){
                sendRequest("startgame");
                waitServer();
                
             //For the second player (computer), since he will start here...
                updateCurrentBoard();
                playCard();
                printHand();
                sendCurrentBoard(); 
            }
            
        //Delaytimer for turn over
            else if(turnDelayTimer == ae.getSource()){
                sendRequest("turn over");
                
                //waits for the response of server if it is game over
                if(checkGameOver()){
                    updateCurrentBoard();
                    sendRequest("Beginning");
                    restartGame();
                    
                }
            //It is not game over, continue playing
                else{
                    updateCurrentBoard();
                    playCard();
                    printHand();
                    sendCurrentBoard();
                }
            }
                
            
        }
}
