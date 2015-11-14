/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclient;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;


public class Game {
 //List of players
    ArrayList<Player> playersList;
    Board myBoard;
    Integer numberCards1 = 5;
    Integer numberCards2 = 5;
    Integer Score1;
    Integer Score2;
    
 //Creates the list and board
    public Game(){
        playersList = new ArrayList<Player>();
        myBoard = new Board();
    }
    
 //Plays the game: Using Same, Plus and basic rule
    public void playGame(Integer loc, Integer whatPlayer){
        int cardLocationBoard = loc;
        int whoPlayer = whatPlayer;
        int sameRule = 1;
        int plusRule = 2;
        
        myBoard.checkSameorPlusRule(cardLocationBoard, whoPlayer,sameRule);
        myBoard.checkSameorPlusRule(cardLocationBoard, whoPlayer,plusRule);
        myBoard.checkBasicRule(cardLocationBoard, whoPlayer);
    }
    
 //Checks if the board is full
    public boolean isGameOver(){
        int count = 0;
        for (int i = 0;i < boardSize();i++){
            if(returnCardBoard(i)!=null){
                count++;
            }
        }
        if(boardSize().equals(count))
            return true;
        else
            return false;
        
    }
    
 //Returns the score of player 1
    public Integer scorePlayer1(){
        Integer count = numberCards1;
        for(int i = 0; i < myBoard.BOARD_SIZE; i++){
            if(returnCardBoard(i)!=null){
                if(sendCardPlayer(i).equals(1))
                    count++;
            }
        }
        return count;
    }
    
 //Returns the score of player 2
    public Integer scorePlayer2(){
        Integer count = numberCards2;
        for(int i = 0; i < myBoard.BOARD_SIZE; i++){
            if(returnCardBoard(i)!=null){
                if(sendCardPlayer(i).equals(2))
                    count++;
            }
        }
        return count;
    }
    
 //Returns the size of the board
    public Integer boardSize(){
        return myBoard.BOARD_SIZE;
    }
    
 //Adds the player to the list
    public void addPlayer(Player p1){
        playersList.add(p1);
    }
    
 //Checks if a player is in the list(loading purposes)
    public Player playerFound(String namePlayer){
        for(int i = 0; i < playersList.size(); i++){
            if(playersList.get(i).playerName.equals(namePlayer))
                return playersList.get(i);                 
        }
        return null;
    }
    
 //Adds the card to the board and updates part of the score
    public void sendBoard(Integer index, Integer player, Card newCard, Integer cardsHand){
        newCard.setPlayerCard(player);
        myBoard.boardList.set(index, newCard);
        if(player.equals(1))
            numberCards1 = cardsHand;
        else
            numberCards2 = cardsHand;
    }
    
 //Returns who does the card belong to...
    public boolean isCardPlayer(Integer whatPlayer,int index){
        if(sendCardPlayer(index).equals(whatPlayer))
            return true;
        else
            return false;             
    }
    
 //Returns the amount of cards there is on the board of a certain player
    public Integer amountPlayerBoardCard(Integer whatPlayer){
        Integer count = 0;
        
        for(int i = 0; i < myBoard.BOARD_SIZE; i++){
            
            if(returnCardBoard(i) != null){            
                if(sendCardPlayer(i).equals(whatPlayer)){
                    count++;
                }
            }
        }
        return count;
    }
    
 //Loads the history of the players to the list
    public void loadHistory(){
            try {
                   ObjectInputStream input = new ObjectInputStream(
                                             new FileInputStream ("history.data"));
                try {
                    playersList = (ArrayList<Player>)(input.readObject());
                    System.out.println("History Loaded");
                    
                }
                
                catch (ClassNotFoundException ex) {
                    System.out.println("Could Not Load File");
                }
                    
                    
            } 
            catch (IOException ex) {
                    System.out.println("");
                }
        }
       
    //Saves Player's info
        public void saveHistory(){
             try{
                FileOutputStream f_out = new FileOutputStream("history.data");
                ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
                obj_out.writeObject ( playersList);
                obj_out.close();
                System.out.println("History Saved");

                
            }
            catch (IOException ex) {
                    System.out.println("Could Not Create File");
                }
        }
    
  //Returns a card from the board
    public Card returnCardBoard(int index){
        return myBoard.boardList.get(index);
    }
    
 //Returns the name of a card in the board(Debugging purposes)
    public String sendCardName(int i){
        return myBoard.boardList.get(i).cardName;
    }
    
 //Returns who does the card in the board belong to...
    public Integer sendCardPlayer(int i){
        return myBoard.boardList.get(i).cardPlayer;
    }
    
 //Returns the Y position of the card (Debuggin purposes)
    public Integer sendCardY(int i){
        return myBoard.boardList.get(i).cardY;
    }
    
 //Returns the size of the list
    public Integer returnPlayerListSize(){
        Integer size = (Integer)playersList.size();
        return size;
    }
    
 //Returns the name of a player in the list
    public String returnPlayerListName(int index){
        return playersList.get(index).playerName;
    }
    
 //Returns how many games have been won from a player
    public Integer returnPlayerListWon(int index){
        return playersList.get(index).gamesWon;
    }
    
 //Returns how many games have been lost from a player
    public Integer returnPlayerListLost(int index){
        return playersList.get(index).gamesLost;
    }
    
 //Returns the current Board(Not used, because it didn't work...)
    public Board returnBoard(){
        return myBoard;
    }
    
    
}
