
package tripletriadhw3;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class Card implements Serializable{
 //Initial set up
    int upValue, rightValue, leftValue, downValue;
    int cardX, cardY;
    String cardName;
    int cardPlayer = 1;
    boolean beenPlayed = false;
    ImageIcon cardblueImage;
    ImageIcon cardredImage;
    
 //Create the card
   public Card(int topValue, int eastValue, int bottomValue, int westValue, 
            String newName, ImageIcon imageblueName, ImageIcon imageredName ){
       
    //Set the Cards values
        upValue = topValue;
        rightValue = eastValue;
        downValue = bottomValue;
        leftValue = westValue;
        
     //Set the cards names and Images
        cardName = newName;
        cardblueImage = imageblueName;
        cardredImage = imageredName;
    }
   
////The card takes care of drawing itself
//   public void paintCard(GamePanel j, Graphics2D g, int x, int y){
//       if(cardPlayer == 1)
//           cardblueImage.paintIcon(j,g,x,y);
//       
//       else if(cardPlayer == 2)
//           cardredImage.paintIcon(j,g,x,y);
//   }
   
   public ImageIcon getCardImage(){
       if(cardPlayer == 1)
           return cardblueImage;
       else
           return cardredImage;
   }
   
//Set who the card belongs to...
   public void setPlayerCard(int i){
       cardPlayer = i;
   }
   
//The card now has a new location
   public void setCardLocation(int newX, int newY){
       cardX = newX;
       cardY = newY;
   }
   
//Get the X coordinate of the card
   public int getCardX(){
       return cardX;
   }
   
//get the y coordinate
   public int getCardY(){
       return cardY;
   }
 
//get the top value of the card
   public int getUpValue(){
       return upValue;
   }
   

   public int getRightValue(){
       return rightValue;
   }
   
   public int getDownValue(){
       return downValue;
   }
   
   public int getLeftValue(){
       return leftValue;
   }
   
//The card is now on the board and out of the hand
   public void setToPlayed(){
       beenPlayed = true;
   }
   
   public void setToNotPlayed(){
       beenPlayed = false;
   }
   
//who does the card belong to?
   public int getPlayerCard(){
       return cardPlayer;
   }
   
//what is the card's name?
   public String getName(){
       return cardName;
   }
  
}
    

