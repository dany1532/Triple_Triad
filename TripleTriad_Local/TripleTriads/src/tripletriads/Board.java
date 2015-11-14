
package tripletriads;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Board implements Serializable{
    ArrayList<Card> board;
    int BOARD_SIZE = 9;
    
 //What Rule will be used: 1 for Same, 2 for Plus
    int whatRule;
    
 //Score Images
    ImageIcon player1Score;
    ImageIcon player2Score;
    ImageIcon number0;
    ImageIcon number1;
    ImageIcon number2;
    ImageIcon number3;
    ImageIcon number4;
    ImageIcon number5;
    ImageIcon number6;
    ImageIcon number7;
    ImageIcon number8;
    ImageIcon number9;
    
 //Create an empty board and obtain the images for the score
    public Board(){
        board = new ArrayList<Card>(BOARD_SIZE);
        
     //Obtain the images for scoring purposes
        loadImages();
        
        for(int i = 0; i < BOARD_SIZE;i++){
            board.add(null);
        } 
    }
    
//Empty the board
    public void clearBoard(){
        board.clear();
        for(int i = 0; i < BOARD_SIZE;i++)
            board.add(null);
    }
    
//Store the images to the variables(scoring purposes)
    public void loadImages(){
        number0 = new ImageIcon("images/zeroImage.png");
        number1 = new ImageIcon("images/oneImage.png");
        number2 = new ImageIcon("images/twoImage.png");
        number3 = new ImageIcon("images/threeImage.png");
        number4 = new ImageIcon("images/fourImage.png");
        number5 = new ImageIcon("images/fiveImage.png");
        number6 = new ImageIcon("images/sixImage.png");
        number7 = new ImageIcon("images/sevenImage.png");
        number8 = new ImageIcon("images/eightImage.png");
        number9 = new ImageIcon("images/nineImage.png");
    }
    
//Update the image score
    public void setScore(int score1,int score2){
   //Player 1 Score
        switch(score1){
            case 0: player1Score = number0;
                    break;
                
            case 1: player1Score = number1;
                    break;
                
            case 2: player1Score = number2;
                    break;
                    
            case 3: player1Score = number3;
                    break;
                
            case 4: player1Score = number4;
                    break;
                
            case 5: player1Score = number5;
                    break;
                
            case 6: player1Score = number6;
                    break;
                
            case 7: player1Score = number7;
                    break;
                
            case 8: player1Score = number8;
                    break;
                
            case 9: player1Score = number9;
                    break;
        }
  ////////////////////////////////////////////////////////////////
        //Player 2 Score
        switch(score2){
            case 0: player2Score = number0;
                    break;
                
            case 1: player2Score = number1;
                    break;
                
            case 2: player2Score = number2;
                    break;
                    
            case 3: player2Score = number3;
                    break;
                
            case 4: player2Score = number4;
                    break;
                
            case 5: player2Score = number5;
                    break;
                
            case 6: player2Score = number6;
                    break;
                
            case 7: player2Score = number7;
                    break;
                
            case 8: player2Score = number8;
                    break;
                
            case 9: player2Score = number9;
                    break;
        }
    }
    
//Get the image of player1's score
    public ImageIcon getImageScore1(){
        return player1Score;
    }
    
//get the image of player2's score
    public ImageIcon getImageScore2(){
        return player2Score;
    }
    
//Get the card from the wanted location
    public Card getBoardCard(int index){
        return board.get(index);
    }
    
//Add a card to the board' array and make the card unselectable
    public void addCardtoArray(Player p1, Player p2, int loc, int index, int turn){
      if(turn == 1){
          board.set(loc,p1.myHand.get(index));
          p1.myHand.get(index).setToPlayed();
        }
        
      else if (turn == 2){
          board.set(loc,p2.myHand.get(index));
          p2.myHand.get(index).setToPlayed();
        }
    }
    
    
 //Application of the basic rule
    //If the value of the card is higher than the cards around it, flip them
    public void checkBasicRule(int cardLocation, int playerTurn){
        
    //Upper left corner, check if win any cards
        if(cardLocation == 0){
          //check right Card
            if(board.get(1) != null && checkRightSide(cardLocation,1) &&
               board.get(1).getPlayerCard() != playerTurn ){
                System.out.println("Won: "+board.get(1).getName());
                board.get(1).setPlayerCard(playerTurn);
            }
            
          //check down Card
            if(board.get(3) != null && checkDownSide(cardLocation,3)) {
                System.out.println("Won: "+board.get(3).getName());
                board.get(3).setPlayerCard(playerTurn);
            }
        }
        
     //Upper middle corner, check if win any cards
        if(cardLocation == 1){
            
          //check right Card
            if(board.get(2) != null && checkRightSide(cardLocation,2)&&
               board.get(2).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(2).getName());
                board.get(2).setPlayerCard(playerTurn);
            }
        
         //check down Card
            if(board.get(4) != null && checkDownSide(cardLocation,4)&&
               board.get(4).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(4).getName());
                board.get(4).setPlayerCard(playerTurn);
            }
            
         //check left Card
            if(board.get(0) != null && checkLeftSide(cardLocation,0)&&
               board.get(0).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(0).getName());
                board.get(0).setPlayerCard(playerTurn);
            }
        }
        
      //Upper right corner, check if win any cards
        if(cardLocation == 2){
        //check down Card
            if(board.get(5) != null && checkDownSide(cardLocation,5)&&
               board.get(5).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(5).getName());
                board.get(5).setPlayerCard(playerTurn);
            }
            
        //check left Card
            if(board.get(1) != null && checkLeftSide(cardLocation,1)&&
               board.get(1).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(1).getName());
                board.get(1).setPlayerCard(playerTurn);
            }
        }
        
     //middle left, check if win any cards
        if(cardLocation == 3){
          //check right Card
            if(board.get(4) != null && checkRightSide(cardLocation,4)&&
               board.get(4).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(4).getName());
                board.get(4).setPlayerCard(playerTurn);
            }
        
         //check down Card
            if(board.get(6) != null && checkDownSide(cardLocation,6)&&
               board.get(6).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(6).getName());
                board.get(6).setPlayerCard(playerTurn);
            }
            
         //check up Card
            if(board.get(0) != null && checkUpSide(cardLocation,0)&&
               board.get(0).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(0).getName());
                board.get(0).setPlayerCard(playerTurn);
            }
        }
        
        //middle, check if win any cards
        if(cardLocation == 4){
          //check right Card
            if(board.get(5) != null && checkRightSide(cardLocation,5)&&
               board.get(5).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(5).getName());
                board.get(5).setPlayerCard(playerTurn);
            }
        
         //check down Card
            if(board.get(7) != null && checkDownSide(cardLocation,7)&&
               board.get(7).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(7).getName());
                board.get(7).setPlayerCard(playerTurn);
            }
            
         //check up Card
            if(board.get(1) != null && checkUpSide(cardLocation,1)&&
               board.get(1).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(1).getName());
                board.get(1).setPlayerCard(playerTurn);
            }
            
            //check left Card
            if(board.get(3) != null && checkLeftSide(cardLocation,3)&&
               board.get(3).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(3).getName());
                board.get(3).setPlayerCard(playerTurn);
            }
        }
        
     //middle right, check if win any cards
        if(cardLocation == 5){
         //check down Card
            if(board.get(8) != null && checkDownSide(cardLocation,8)&&
               board.get(8).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(8).getName());
                board.get(8).setPlayerCard(playerTurn);
            }
            
         //check up Card
            if(board.get(2) != null && checkUpSide(cardLocation,2)&&
               board.get(2).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(2).getName());
                board.get(2).setPlayerCard(playerTurn);
            }
            
            //check left Card
            if(board.get(4) != null && checkLeftSide(cardLocation,4)&&
               board.get(4).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(4).getName());
                board.get(4).setPlayerCard(playerTurn);
            }
        }
        
    //Lower left, check if win any cards
        if(cardLocation == 6){
          //check right Card
            if(board.get(7) != null && checkRightSide(cardLocation,7)&&
               board.get(7).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(7).getName());
                board.get(7).setPlayerCard(playerTurn);
            }
            
         //check up Card
            if(board.get(3) != null && checkUpSide(cardLocation,3)&&
               board.get(3).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(3).getName());
                board.get(3).setPlayerCard(playerTurn);
            }
        }
        
    //Lower middle, check if win any cards
        if(cardLocation == 7){
          //check right Card
            if(board.get(8) != null && checkRightSide(cardLocation,8)&&
               board.get(8).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(8).getName());
                board.get(8).setPlayerCard(playerTurn);
            }
            
         //check up Card
            if(board.get(4) != null && checkUpSide(cardLocation,4)&&
               board.get(4).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(4).getName());
                board.get(4).setPlayerCard(playerTurn);
            }
            
            //check left Card
            if(board.get(6) != null && checkLeftSide(cardLocation,6)&&
               board.get(6).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(6).getName());
                board.get(6).setPlayerCard(playerTurn);
            }
        }
        
    //Lower right, check if win any cards
        if(cardLocation == 8){           
         //check up Card
            if(board.get(5) != null && checkUpSide(cardLocation,5)&&
               board.get(5).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(5).getName());
                board.get(5).setPlayerCard(playerTurn);
            }
            
            //check left Card
            if(board.get(7) != null && checkLeftSide(cardLocation,7)&&
               board.get(7).getPlayerCard() != playerTurn) {
                System.out.println("Won: "+board.get(7).getName());
                board.get(7).setPlayerCard(playerTurn);
            }
        }
        
    }
    
 //Check the left and down cards, to see if you can win them over with the plus or same rules
    public boolean checkESCards(int referenceCard, int eastCard, int southCard,int newRule ){
       //Obtain the addition of the left Card and down Cards
            int eastPlus = board.get(referenceCard).getRightValue() + board.get(eastCard).getLeftValue();
            int southPlus = board.get(referenceCard).getDownValue() + board.get(southCard).getUpValue();
        
        //Can apply plus rule
            if(eastPlus == southPlus && newRule == 2) {
                System.out.println("PLUS!");
                return true;
             }
         
       //can apply same rule
            else if(board.get(referenceCard).getRightValue() == board.get(eastCard).getLeftValue() && 
                    board.get(referenceCard).getDownValue() == board.get(southCard).getUpValue()&& newRule == 1) {
                 System.out.println("SAME!");
                 return true;
        }
            
        // none apply
            else {
            return false;
        }
    }
    
 //check the down and right cards for the plus and same rules
    public boolean checkSWCards(int referenceCard, int southCard, int westCard,int newRule ){
        int westPlus = board.get(referenceCard).getLeftValue() + board.get(westCard).getRightValue();
        int southPlus = board.get(referenceCard).getDownValue() + board.get(southCard).getUpValue();
        
    //Plus rule worthy
        if(westPlus == southPlus && newRule == 2) {
            System.out.println("PLUS!");
            return true;
        }
        
     //Same rule applies
        else if(board.get(referenceCard).getLeftValue()== board.get(westCard).getRightValue() &&
               board.get(referenceCard).getDownValue() == board.get(southCard).getUpValue() && newRule == 1 ) {
            
            System.out.println("SAME!");
            return true;
        }
        
    //none apply
        else {
            return false;
        }
    }
    
//check the left and up cards for the same and plus rules
    public boolean checkWNCards(int referenceCard, int westCard, int northCard, int newRule ){
        int westPlus = board.get(referenceCard).getLeftValue() + board.get(westCard).getRightValue();
        int northPlus = board.get(referenceCard).getUpValue() + board.get(northCard).getDownValue();
        
     //can apply plus rule
        if(westPlus == northPlus && newRule == 2) {
            System.out.println("PLUS!");
            return true;
        }
        
    //can apply same rule
        else if(board.get(referenceCard).getLeftValue() == board.get(westCard).getRightValue() &&
                board.get(referenceCard).getUpValue() == board.get(northCard).getDownValue() && newRule == 1) {
            
            System.out.println("SAME!");
            return true;
        }
        
        else {
            return false;
        }
    }
    
//Check the up and down cards for same and plus rules
    public boolean checkNSCards(int referenceCard, int northCard, int southCard, int newRule ){
        int northPlus = board.get(referenceCard).getUpValue() + board.get(northCard).getDownValue();
        int southPlus = board.get(referenceCard).getDownValue() + board.get(southCard).getUpValue();
        
        if(northPlus == southPlus && newRule == 2) {
            System.out.println("PLUS!");
            return true;
        }
        
        else if(board.get(referenceCard).getUpValue() == board.get(northCard).getDownValue() &&
                board.get(referenceCard).getDownValue() == board.get(southCard).getUpValue() && newRule == 1) {
            
             System.out.println("SAME!");
             return true;
        }
        
        else {
            return false;
        }
    }
    
 //Check the right and left cards for same and plus rules
    public boolean checkEWCards(int referenceCard, int eastCard, int westCard, int newRule ){
        int eastPlus = board.get(referenceCard).getRightValue() + board.get(eastCard).getLeftValue();
        int westPlus = board.get(referenceCard).getLeftValue() + board.get(westCard).getRightValue();
        
        if(eastPlus == westPlus && newRule == 2) {
            System.out.println("PLUS!");
            return true;
        }
        
        else if(board.get(referenceCard).getRightValue() == board.get(eastCard).getLeftValue() &&
               board.get(referenceCard).getLeftValue() == board.get(westCard).getRightValue() && newRule == 1 ) {
            
            System.out.println("SAME!");
            return true; 
        } 
        
        else {
            return false;
        }
    }
    
//check the up and right cards for the plus or same rule
    public boolean checkNECards(int referenceCard, int northCard, int eastCard, int newRule ){
        int eastPlus = board.get(referenceCard).getRightValue() + board.get(eastCard).getLeftValue();
        int northPlus = board.get(referenceCard).getUpValue() + board.get(northCard).getDownValue();
        
        
        if(eastPlus == northPlus && newRule == 2) {
            System.out.println("PLUS!");
            return true;
        }
        
        else if(board.get(referenceCard).getRightValue() == board.get(eastCard).getLeftValue() &&
                 board.get(referenceCard).getUpValue() == board.get(northCard).getDownValue() && newRule == 1) {
            
            System.out.println("SAME!");
            return true;
        }
        else {
            return false;
        }
    }
    
//Are both of the cards of the same player, then don't need to apply any rules...
    public boolean checkSamePlayerCards(int x, int y, int whatPlayer){
        if(board.get(x).getPlayerCard() != whatPlayer || board.get(y).getPlayerCard() != whatPlayer) {
            return true;
        }
        
        else {
            return false;
        }
    }
    
//Application of the same and Plus rules:
    //Same: if adjacent cards have the same value to the corresponding values of the card, then flip them
    //Plus: if the addition of the adjacent values of the cards are the same, then flip them
    public void checkSameorPlusRule(int cardLocation, int playerTurn, int theRule){
     //what rule is it? 1 for Same, 2 for Plus
        whatRule = theRule;
        
    //Upper left corner, check if win any cards
        if(cardLocation == 0){
          //check right and down Cards
            if(board.get(1) != null && board.get(3) != null &&
               checkSamePlayerCards(1,3,playerTurn) && checkESCards(cardLocation,1,3,whatRule)){
                board.get(1).setPlayerCard(playerTurn);
                board.get(3).setPlayerCard(playerTurn);
            }
        }
        
     //Upper middle corner, check if win any cards
        if(cardLocation == 1){
          //check right and down Cards
            if(board.get(2) != null && board.get(4) != null &&
               (board.get(2).getPlayerCard() != playerTurn || board.get(4).getPlayerCard() != playerTurn) &&
                 checkSamePlayerCards(2,4,playerTurn) && checkESCards(cardLocation,2,4,whatRule)) {
                board.get(2).setPlayerCard(playerTurn);
                board.get(4).setPlayerCard(playerTurn);
            }
        
         //check down and left Cards
            if(board.get(4) != null && board.get(0) != null &&
              checkSamePlayerCards(4,0,playerTurn) && checkSWCards(cardLocation,4, 0,whatRule)) {
                board.get(4).setPlayerCard(playerTurn);
                board.get(0).setPlayerCard(playerTurn);
            }
            
         //check right and left Card
            if(board.get(0) != null && board.get(2) != null &&
               checkSamePlayerCards(0,2,playerTurn) && checkEWCards(cardLocation,2,0,whatRule)) {
                board.get(0).setPlayerCard(playerTurn);
                board.get(2).setPlayerCard(playerTurn);
            }
        }
        
      //Upper right corner, check if win any cards
        if(cardLocation == 2){
        //check down and left Cards
            if(board.get(5) != null && board.get(1) != null &&
              checkSamePlayerCards(5,1,playerTurn) && checkSWCards(cardLocation,5,1,whatRule)) {
                board.get(5).setPlayerCard(playerTurn);
                board.get(1).setPlayerCard(playerTurn);
            }
        }
        
     //middle left, check if win any cards
        if(cardLocation == 3){
          //check right and down Cards
            if(board.get(4) != null && board.get(6) != null &&
              checkSamePlayerCards(4,6,playerTurn) && checkESCards(cardLocation,4,6,whatRule)) {
                
                board.get(4).setPlayerCard(playerTurn);
                board.get(6).setPlayerCard(playerTurn);
            }
        
         //check up and down Cards
            if(board.get(6) != null && board.get(0) != null &&
               checkSamePlayerCards(6,0,playerTurn) && checkNSCards(cardLocation,0,6,whatRule)) {
                board.get(6).setPlayerCard(playerTurn);
                board.get(0).setPlayerCard(playerTurn);
            }
            
         //check up and right Card
            if(board.get(0) != null && board.get(4) != null &&
               checkSamePlayerCards(0,4,playerTurn) && checkNECards(cardLocation,0,4,whatRule)) {
                System.out.println("here?");
                board.get(0).setPlayerCard(playerTurn);
                board.get(4).setPlayerCard(playerTurn);
            }
        }
        
        //middle, check if win any cards
        if(cardLocation == 4){
          //check right and down Cards
            if(board.get(5) != null && board.get(7) != null &&
               checkSamePlayerCards(5,7,playerTurn) && checkESCards(cardLocation,5,7,whatRule)) {
                board.get(5).setPlayerCard(playerTurn);
                board.get(7).setPlayerCard(playerTurn);
            }
        
         //check down and left Cards
            if(board.get(7) != null && board.get(3) != null &&
               checkSamePlayerCards(7,3,playerTurn) && checkSWCards(cardLocation,7, 3,whatRule)) {
                board.get(7).setPlayerCard(playerTurn);
                board.get(3).setPlayerCard(playerTurn);
            }
            
         //check left and north Cards
            if(board.get(3) != null && board.get(1) != null &&
               checkSamePlayerCards(3,1,playerTurn) && checkWNCards(cardLocation,3, 1,whatRule)) {
                board.get(3).setPlayerCard(playerTurn);
                board.get(1).setPlayerCard(playerTurn);
            }
            
          //check up and right Card
            if(board.get(1) != null && board.get(5) != null &&
               checkSamePlayerCards(1,5,playerTurn) && checkNECards(cardLocation,1,5,whatRule)) {
                board.get(1).setPlayerCard(playerTurn);
                board.get(5).setPlayerCard(playerTurn);
            }
            
         //check right and left Card
            if(board.get(5) != null && board.get(3) != null &&
               checkSamePlayerCards(5,3,playerTurn) && checkEWCards(cardLocation,5,3,whatRule)) {
                board.get(5).setPlayerCard(playerTurn);
                board.get(3).setPlayerCard(playerTurn);
            }
            
        //check up and down Cards
            if(board.get(1) != null && board.get(7) != null &&
               checkSamePlayerCards(1,7,playerTurn) && checkNSCards(cardLocation,1,7,whatRule)) {
                board.get(1).setPlayerCard(playerTurn);
                board.get(7).setPlayerCard(playerTurn);
            }
        }
        
     //middle right, check if win any cards
        if(cardLocation == 5){
         //check down and left Cards
            if(board.get(8) != null && board.get(4) != null &&
               checkSamePlayerCards(8,4,playerTurn) && checkSWCards(cardLocation,8, 4,whatRule)) {
                board.get(8).setPlayerCard(playerTurn);
                board.get(4).setPlayerCard(playerTurn);
            }
            
         //check left and north Cards
            if(board.get(4) != null && board.get(2) != null &&
                checkSamePlayerCards(4,2,playerTurn) && checkWNCards(cardLocation,4, 2,whatRule)) {
                board.get(4).setPlayerCard(playerTurn);
                board.get(2).setPlayerCard(playerTurn);
            }
            
         //check up and down Cards
            if(board.get(2) != null && board.get(8) != null &&
                checkSamePlayerCards(2,8,playerTurn) && checkNSCards(cardLocation,2,8,whatRule)) {
                board.get(2).setPlayerCard(playerTurn);
                board.get(8).setPlayerCard(playerTurn);
            }
        }
        
    //Lower left, check if win any cards
        if(cardLocation == 6){
          //check up and right Card
            if(board.get(3) != null && board.get(7) != null &&
               checkSamePlayerCards(3,7,playerTurn) && checkNECards(cardLocation,3,7,whatRule)) {
                board.get(3).setPlayerCard(playerTurn);
                board.get(7).setPlayerCard(playerTurn);
            }
            
        }
        
    //Lower middle, check if win any cards
        if(cardLocation == 7){
          //check left and north Cards
            if(board.get(6) != null && board.get(4) != null &&
                checkSamePlayerCards(6,4,playerTurn) && checkWNCards(cardLocation,4, 2,whatRule)) {
                board.get(6).setPlayerCard(playerTurn);
                board.get(4).setPlayerCard(playerTurn);
            }
            
         //check up and right Card
            if(board.get(4) != null && board.get(8) != null &&
               checkSamePlayerCards(4,8,playerTurn) && checkNECards(cardLocation,4,8,whatRule)) {
                board.get(4).setPlayerCard(playerTurn);
                board.get(8).setPlayerCard(playerTurn);
            }
            
         //check right and left Card
            if(board.get(8) != null && board.get(6) != null &&
               checkSamePlayerCards(8,6,playerTurn) && checkEWCards(cardLocation,8,6,whatRule)) {
                board.get(8).setPlayerCard(playerTurn);
                board.get(6).setPlayerCard(playerTurn);
            }
        }
        
    //Lower right, check if win any cards
        if(cardLocation == 8){           
         //check left and north Cards
            if(board.get(7) != null && board.get(5) != null &&
               checkSamePlayerCards(7,5,playerTurn) && checkWNCards(cardLocation,7, 5,whatRule)) {
                board.get(7).setPlayerCard(playerTurn);
                board.get(5).setPlayerCard(playerTurn);
            }
        }
        
    }
    
 //Check the up card for the basic rule
    public boolean checkUpSide(int downCard, int upCard){
        if(board.get(downCard).getUpValue() > board.get(upCard).getDownValue())
            return true;
        else
            return false;
    }
    
//check the left card for the basic rule
    public boolean checkLeftSide(int rightCard, int leftCard){
        if(board.get(rightCard).getLeftValue() > board.get(leftCard).getRightValue())
            return true;
        else
            return false;
    }
    
 //check the down card of the basic rule
    public boolean checkDownSide(int upCard, int downCard){
        if(board.get(upCard).getDownValue() > board.get(downCard).getUpValue())
            return true;
        else
            return false;
    }
    
 //check the right card for the basic rule
    public boolean checkRightSide(int leftCard, int rightCard){
        if(board.get(leftCard).getRightValue() > board.get(rightCard).getLeftValue()) {
            return true;
        }
        else {
            return false;
        }
    }
    
//Set the position of the cards to the wanted location in the board
    public void setPositionCard(int playerTurn,Player player1, Player player2, 
            int location, int cardSelected){

        
        switch(location){
                case 0:if(playerTurn == 1)
                          player1.myHand.get(cardSelected).setCardLocation(260, 130);
                
                       else
                          player2.myHand.get(cardSelected).setCardLocation(260, 130);
                       break;
                    
                case 1:if(playerTurn == 1)
                          player1.myHand.get(cardSelected).setCardLocation(405, 130);
                
                       else
                          player2.myHand.get(cardSelected).setCardLocation(405, 130);
                       break;
                          
                case 2:if(playerTurn == 1)
                          player1.myHand.get(cardSelected).setCardLocation(550, 130);
                
                       else
                          player2.myHand.get(cardSelected).setCardLocation(550, 130);
                       break;
                        
                case 3:if(playerTurn == 1)
                          player1.myHand.get(cardSelected).setCardLocation(260, 280);
                
                       else
                          player2.myHand.get(cardSelected).setCardLocation(260, 280);
                       break;
                        
                case 4:if(playerTurn == 1)
                          player1.myHand.get(cardSelected).setCardLocation(405, 280);
                
                       else
                          player2.myHand.get(cardSelected).setCardLocation(405, 280);
                       break;
                        
                case 5:if(playerTurn == 1)
                          player1.myHand.get(cardSelected).setCardLocation(550, 280);
                
                       else
                          player2.myHand.get(cardSelected).setCardLocation(550, 280);
                       break;
                        
                case 6:if(playerTurn == 1)
                          player1.myHand.get(cardSelected).setCardLocation(260, 430);
                
                       else
                          player2.myHand.get(cardSelected).setCardLocation(260, 430);
                       break;
                        
                case 7:if(playerTurn == 1)
                          player1.myHand.get(cardSelected).setCardLocation(405, 430);
                
                       else
                          player2.myHand.get(cardSelected).setCardLocation(405, 430);
                       break;
                        
                case 8:if(playerTurn == 1)
                          player1.myHand.get(cardSelected).setCardLocation(550, 430);
                
                       else
                          player2.myHand.get(cardSelected).setCardLocation(550, 430);
                       break;
                      
                }
        
    }
}
