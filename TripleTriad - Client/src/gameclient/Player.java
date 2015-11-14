
package gameclient;

import java.io.*;
import java.util.ArrayList;

public class Player implements Serializable{
  //Initial set up
     ArrayList<Card> myHand;
     Deck myDeck;
     Integer HAND_SIZE = 5;
     String playerName;
     Integer gamesWon = 0;
     Integer gamesLost = 0;
        
    //Creates player and sets up card according to who player it is
       public Player(int whatPlayer, String playerString){
           playerName = playerString;
           
         // Initial Y coordinate of the cards
            int pHandY = 80;
            
        //Creates a deck and hand
            myDeck = new Deck();
            myHand = new ArrayList<Card>();
            
        //shuffle the deck
            myDeck.shuffle();
            
         //Depending on who is the player the cards will be located 
            //differently
            for(int i = 0; i < HAND_SIZE; i++){
                if(whatPlayer == 1) {
                    myDeck.getCard(i).setPlayerCard(1);
                    myDeck.getCard(i).setCardLocation(100,pHandY);
                    pHandY += 80;
                }
                else if(whatPlayer == 2) {
                    myDeck.getCard(i).setPlayerCard(2);
                    myDeck.getCard(i).setCardLocation(700,pHandY);
                    pHandY += 80;
                }
                
           //Add the cards to the players' hands
                myHand.add(i,myDeck.getCard(i)); 
            }
        }
       
  //For restarting the game purposes
       //Shuffles the deck and sets them into the hand
       public void newHand(int whatPlayer){
           int pHandY = 80;
           myDeck.shuffle();
           
            for(int i = 0; i < HAND_SIZE; i++){
                if(whatPlayer == 1) {
                    myDeck.getCard(i).setPlayerCard(1);
                    myDeck.getCard(i).setToNotPlayed();
                    myDeck.getCard(i).setCardLocation(100,pHandY);
                    pHandY += 80;
                }
                else if(whatPlayer == 2) {
                    myDeck.getCard(i).setPlayerCard(2);
                    myDeck.getCard(i).setToNotPlayed();
                    myDeck.getCard(i).setCardLocation(700,pHandY);
                    pHandY += 80;
                }
                

                myHand.set(i,myDeck.getCard(i)); 
            }
       }
       
  //Gets the card from the hand
       public Card handCard(int index){
           return myHand.get(index);
       }
       
  //gets the array of cards from the hand
       public ArrayList getHand(){
           return myHand;
       }
       
  //To know who the card belongs to...
       public Integer gethandPlayerCard(int index){
           return myHand.get(index).cardPlayer;
       }
        
}
