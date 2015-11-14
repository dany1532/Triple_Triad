
package tripletriads;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

public class Deck implements Serializable{
 //Initial Setup
    ArrayList <Card> cardDeck;
    
 
    private String abaddonName,
            behemothName,
            chimeraName,
            elastoidName,
            elnoyleName,
            gerogeroName,
            gim47nName, 
            iguionName,
            krystaName,
            oilboyleName,
            biggsName,
            elvoretName,
            fujinName,
            granaldoName,
            ironName,
            marlboroName,
            norgName,
            rubyName,
            tonberryKName,
            traumaName,
            xatmName;
    
   private ImageIcon abaddonblueImage, abaddonredImage,
              behemothblueImage, behemothredImage,
              chimerablueImage, chimeraredImage,
              elastoidblueImage, elastoidredImage,
              elnoyleblueImage, elnoyleredImage,
              gerogeroblueImage, gerogeroredImage,
              gim47nblueImage, gim47nredImage,
              iguionblueImage, iguionredImage,
              krystablueImage, krystaredImage,
              oilboyleblueImage, oilboyleredImage,
              biggsblueImage, biggsredImage,
              elvoretblueImage, elvoretredImage,
              fujinblueImage, fujinredImage,
              granaldoblueImage, granaldoredImage,
              ironblueImage, ironredImage,
              marlboroblueImage, marlbororedImage,
              norgblueImage, norgredImage,
              rubyblueImage, rubyredImage,
              tonberryKblueImage, tonberryKredImage,
              traumablueImage, traumaredImage,
              xatmblueImage, xatmredImage;

   //Creates Cards and adds them to the deck array 
   public Deck(){
     //Create the deck
        cardDeck = new ArrayList <Card>();
        
     //Set the name of the cards
        setCardNames();
        
    //set the images of the cards
        setCardImages();
        
    //Creates the Cards
        Card abaddonCard = new Card(6,8,4,5,abaddonName,abaddonblueImage, abaddonredImage);
        Card behemothCard = new Card(3,6,5,7,behemothName,behemothblueImage, behemothredImage);
        Card chimeraCard = new Card(7,6,5,3,chimeraName,chimerablueImage, chimeraredImage);
        Card elastoidCard = new Card(6,2,6,7,elastoidName,elastoidblueImage, elastoidredImage);
        Card elnoyleCard = new Card(5,3,7,6, elnoyleName,elnoyleblueImage, elnoyleredImage);
        Card gerogeroCard = new Card(1,8,8,3,gerogeroName,gerogeroblueImage, gerogeroredImage);
        Card gim47nCard = new Card(5,5,7,4,gim47nName,gim47nblueImage, gim47nredImage);
        Card iguionCard = new Card(8,2,8,2,iguionName,iguionblueImage, iguionredImage);
        Card krystaCard = new Card(7,5,8,1,krystaName,krystablueImage, krystaredImage);
        Card oilboyleCard = new Card(1,8,4,8,oilboyleName,oilboyleblueImage, oilboyleredImage);
        Card biggsCard = new Card(6,6,2,7,biggsName,biggsblueImage, biggsredImage);
        Card elvoretCard = new Card(7,8,3,4,elvoretName,elvoretblueImage, elvoretredImage);
        Card fujinCard = new Card(2,8,8,4,fujinName,fujinblueImage, fujinredImage);
        Card granaldoCard = new Card(7,2,8,5,granaldoName,granaldoblueImage, granaldoredImage);
        Card ironCard = new Card(6,5,6,5,ironName,ironblueImage, ironredImage);
        Card marlboroCard = new Card(7,7,4,2,marlboroName,marlboroblueImage, marlbororedImage);
        Card norgCard = new Card(6,5,8,4,norgName,norgblueImage, norgredImage);
        Card rubyCard = new Card(7,2,7,4,rubyName,rubyblueImage, rubyredImage);
        Card tonberryKCard = new Card(4,6,7,4,tonberryKName,tonberryKblueImage, tonberryKredImage);
        Card traumaCard = new Card(4,8,5,6,traumaName,traumablueImage, traumaredImage);
        Card xatmCard = new Card(4,8,7,3,xatmName,xatmblueImage, xatmredImage);
        
   //Add the cards to the deck
        cardDeck.add(abaddonCard);
        cardDeck.add(behemothCard);
        cardDeck.add(chimeraCard);
        cardDeck.add(elastoidCard);
        cardDeck.add(elnoyleCard);
        cardDeck.add(gerogeroCard);
        cardDeck.add(gim47nCard);
        cardDeck.add(iguionCard);
        cardDeck.add(biggsCard);
        cardDeck.add(elvoretCard);
        cardDeck.add(fujinCard);
        cardDeck.add(granaldoCard);
        cardDeck.add(ironCard);
        cardDeck.add(marlboroCard);
        cardDeck.add(norgCard);
        cardDeck.add(rubyCard);
        cardDeck.add(tonberryKCard);
        cardDeck.add(traumaCard);
        cardDeck.add(xatmCard);
        cardDeck.add(oilboyleCard);
        cardDeck.add(krystaCard);

        
    }
    
//Shuffle the cards, 
    public void shuffle(){
        
        ArrayList <Card> shuffledCards = new ArrayList<Card>();
        int DECK_SIZE = cardDeck.size();
        Random r = new Random();
        
        for(int i = 0; i < DECK_SIZE ;i++){
            int index = r.nextInt(DECK_SIZE-i);
            shuffledCards.add(cardDeck.get(index));
            cardDeck.remove(index);
        }
        
        cardDeck = shuffledCards;
  
    }
   
//Sets the name of the cards
    void setCardNames(){
        abaddonName = "Abaddon";
        behemothName = "Behemoth";
        biggsName = "Biggs and Wedge";
        chimeraName = "Chimera";
        elastoidName = "Elastoid";
        elnoyleName = "Elnoyle";
        elvoretName = "Elvoret";
        fujinName = "Fujin and Rajin";
        gerogeroName = "Gerogero";
        gim47nName = "GIM47N";
        granaldoName = "Granaldo";
        iguionName = "Iguion";
        ironName = "Iron Giant";
        krystaName = "Krysta";
        marlboroName = "Marlboro";
        norgName = "NORG";
        oilboyleName = "Oilboyle";
        rubyName = "Ruby Dragon";
        tonberryKName = "Tonberry King";
        traumaName = "Trauma";
        xatmName = "X-ATM098";
        
    }
    
 //sets the Images of the cards
    void setCardImages(){
        abaddonblueImage = new ImageIcon("images/Abaddon_Blue.png");
        abaddonredImage = new ImageIcon("images/Abaddon_Red.png");

        behemothblueImage = new ImageIcon("images/Behemoth_Blue.png");
        behemothredImage = new ImageIcon("images/Behemoth_Red.png");
        
        biggsblueImage = new ImageIcon("images/BW_Blue.png");
        biggsredImage = new ImageIcon("images/BW_Red.png");
        
        chimerablueImage = new ImageIcon ("images/Chimera_Blue.png");
        chimeraredImage = new ImageIcon ("images/Chimera_Red.png");
        
        elastoidblueImage = new ImageIcon ("images/Elastoid_Blue.png");
        elastoidredImage = new ImageIcon ("images/Elastoid_Red.png");
        
        elnoyleblueImage = new ImageIcon("images/Elnoyle_Blue.png");
        elnoyleredImage = new ImageIcon("images/Elnoyle_Red.png");
        
        elvoretblueImage = new ImageIcon("images/Elvoret_Blue.png");
        elvoretredImage = new ImageIcon("images/Elvoret_Red.png");
        
        fujinblueImage= new ImageIcon("images/FR_Blue.png");
        fujinredImage = new ImageIcon("images/FR_Red.png");
        
        gerogeroblueImage = new ImageIcon("images/Gerogero_Blue.png");
        gerogeroredImage = new ImageIcon("images/Gerogero_Red.png");
        
        gim47nblueImage = new ImageIcon("images/GIM47N_Blue.png");
        gim47nredImage = new ImageIcon("images/GIM47N_Red.png");
        
        granaldoblueImage = new ImageIcon("images/Granaldo_Blue.png");
        granaldoredImage = new ImageIcon("images/Granaldo_Red.png");
        
        iguionblueImage = new ImageIcon("images/Iguion_Blue.png");
        iguionredImage = new ImageIcon("images/Iguion_Red.png");
        
        ironblueImage = new ImageIcon("images/IronGiant_Blue.png");
        ironredImage = new ImageIcon("images/IronGiant_Red.png");
        
        krystablueImage = new ImageIcon("images/Krysta_Blue.png");
        krystaredImage = new ImageIcon("images/Krysta_Red.png");
        
        marlboroblueImage = new ImageIcon("images/Marlboro_Blue.png");
        marlbororedImage = new ImageIcon("images/Marlboro_Red.png");
        
        norgblueImage = new ImageIcon("images/Norg_Blue.png");
        norgredImage = new ImageIcon("images/Norg_Red.png");
        
        oilboyleblueImage = new ImageIcon("images/Oilboyle_Blue.png");
        oilboyleredImage = new ImageIcon("images/Oilboyle_Red.png");
        
        rubyblueImage = new ImageIcon("images/Rubydragon_Blue.png");
        rubyredImage = new ImageIcon("images/Rubydragon_Red.png");
        
        tonberryKblueImage = new ImageIcon("images/Tonberryking_Blue.png");
        tonberryKredImage = new ImageIcon("images/Tonberryking_Red.png");
        
        traumablueImage = new ImageIcon("images/Trauma_Blue.png");
        traumaredImage = new ImageIcon("images/Trauma_Red.png");
        
        xatmblueImage = new ImageIcon("images/XATM_Blue.png");
        xatmredImage = new ImageIcon("images/XATM_Red.png");
    }
    
 //Get the card from the deck index
    public Card getCard(int index){
        return cardDeck.get(index);
    }
}
