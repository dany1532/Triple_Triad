
package tripletriads;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartPanel extends JPanel {
    CreatePlayer1 createPlayer1Panel;
    CreatePlayer2 createPlayer2Panel;
    JButton startGameButton;
    Player p1 = null;
    Player p2 = null;
    TripleTriad app;

    
    public StartPanel(TripleTriad frame){
        setSize(900,50);
        app = frame;
        
     //Creates panels for creating or loading the players
        createPlayer1Panel = new CreatePlayer1();
        createPlayer2Panel = new CreatePlayer2();
        
     //Creates a button for starting the game once the players are set
        startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener(){
            @Override
               public void actionPerformed(ActionEvent e){
                       app.game.setPlayers(p1, p2);
                       if(app.game.gameStart)
                          app.game.stopGame();
                       app.game.startGame();
                       app.menuBar.continueGameItem.setEnabled(true);
                       app.menuBar.stopGameItem.setEnabled(true);
                       app.menuBar.viewScoresItem.setEnabled(true);
                       app.menuBar.startGameItem.setEnabled(true);
                       app.cl.show(app.panels,"GamePanel");
                       
                       
               }
        });
        
        startGameButton.setEnabled(false);

     //Set the layout and add the panels and the start game button
        setLayout(new BorderLayout());
        add(startGameButton,BorderLayout.EAST);
        JPanel panel = new JPanel(new GridLayout(2,1));
        panel.add(createPlayer1Panel);
        panel.add(createPlayer2Panel);
        add(panel,BorderLayout.CENTER);

    }
    
 //For shortcut purposes
    public void enablePlayer1Button(){
        createPlayer1Panel.createStartButton.setEnabled(true);
    }
    
 //for shortcut purposes
    public void enablePlayer2Button(){
        createPlayer2Panel.createStartButton.setEnabled(true);
    }
    
   
  //Creates the panel for creating or loading player 1
    class CreatePlayer1 extends JPanel{  
       JTextField newPlayer1Text;
       JLabel createPlayerLabel;
       JButton createStartButton;
       
       public CreatePlayer1(){
        //Layout for placing the components
           setLayout(new GridBagLayout());
           GridBagConstraints c = new GridBagConstraints();
           
        //Creates the label
           createPlayerLabel = new JLabel("   Create/Load Player 1");
           c.fill = GridBagConstraints.HORIZONTAL;
           c.gridx = 1;
           c.gridy = 0;
           add(createPlayerLabel,c);
           
        //Creates Textfield
           newPlayer1Text = new JTextField(10);
           newPlayer1Text.setText("");
           
           c.fill = GridBagConstraints.HORIZONTAL;
           c.gridx = 0;
           c.gridy = 1;
           add(newPlayer1Text,c);
           
           
       //Creates the button for creating or loading player1
           createStartButton = new JButton("Create/Load Player");
           createStartButton.addActionListener(new ActionListener(){
            @Override
               public void actionPerformed(ActionEvent e){
              //if the textfield is not empty then go ahead..
                  if(!"".equals(newPlayer1Text.getText())){
                   //Check if the list of players is not empty
                      if(!app.game.playersList.isEmpty()){
                      //Search through the list of players
                        for(int i = 0; i < app.game.playersList.size();i++){
                           //Got a match then load player 
                            if(app.game.playersList.get(i).playerName.equals(newPlayer1Text.getText())){
                                p1 = app.game.playersList.get(i);
                                p1.newHand(1);
                                JOptionPane.showMessageDialog(
                                           null, "Welcome Back "+p1.playerName);
                                createStartButton.setEnabled(false);
                                break;
                                
                            }
                          //Didn't find a match? then create a player
                            else if(i == app.game.playersList.size()-1){
                                p1 = new Player(1,newPlayer1Text.getText());
                                app.game.addPlayer(p1);
                                JOptionPane.showMessageDialog(
                                           null, "Acount Created. Welcome "+p1.playerName);
                                createStartButton.setEnabled(false);
                                break;
                            }
                        }
                      }
                      
                    //There is no history, then no players go ahead and create
                       //the player
                      else{
                          p1 = new Player(1,newPlayer1Text.getText());
                          app.game.addPlayer(p1);
                          JOptionPane.showMessageDialog(  null, 
                                  "Account Created. Welcome "+p1.playerName);
                          createStartButton.setEnabled(false);
                      }
                      
                    //Player2 is already set then go ahead and press the start game button
                      if(p2 != null)
                       startGameButton.setEnabled(true);
                  }
                  //The textfield is empty, please enter a name
                  else{
                       JOptionPane.showMessageDialog(null, "Please Enter Player1 Name");
                  }
                  
                       
                       
               }
           });
           c.fill = GridBagConstraints.HORIZONTAL;
           c.gridx = 1;
           c.gridy = 2;
           add(createStartButton,c);
           
           
       }
    }
    
  //Panel that creates or loads player2
    class CreatePlayer2 extends JPanel{  
       JTextField newPlayer2Text;
       JLabel loadPlayerLabel;
       JButton createStartButton;
       
       public CreatePlayer2(){
           setLayout(new GridBagLayout());
           GridBagConstraints c = new GridBagConstraints();
           
           this.setBackground(Color.BLACK);
           
         //Create label
           loadPlayerLabel = new JLabel("   Create/Load Player 2");
           loadPlayerLabel.setForeground(Color.WHITE);
           c.fill = GridBagConstraints.HORIZONTAL;
           c.gridx = 1;
           c.gridy = 0;
           add(loadPlayerLabel,c);
           
        //Create textfield for input
           newPlayer2Text = new JTextField(10);
           newPlayer2Text.setText("");
           c.fill = GridBagConstraints.HORIZONTAL;
           c.gridx = 2;
           c.gridy = 1;
           add(newPlayer2Text,c);
           
           
        //Create the button for creating or loading a player
           createStartButton = new JButton("Create/Load Player");
           createStartButton.addActionListener(new ActionListener(){
            @Override
               public void actionPerformed(ActionEvent e){
                //Textfield is not empty, go ahead...
                  if(!"".equals(newPlayer2Text.getText())){
                    //The list of players is not empty, then...
                      if(!app.game.playersList.isEmpty()){
                        //Check through the list
                        for(int i = 0; i < app.game.playersList.size();i++){
                           //there is a match, then load the player
                            if(app.game.playersList.get(i).playerName.equals(newPlayer2Text.getText())){
                                   p2 = app.game.playersList.get(i);
                                   p2.newHand(2);
                                   JOptionPane.showMessageDialog(
                                           null, "Welcome Back "+p2.playerName);
                                   createStartButton.setEnabled(false);
                                   break;
                            }
                           //there is no match, create new player
                            else if(i == app.game.playersList.size()-1){
                                p2 = new Player(2,newPlayer2Text.getText());
                                app.game.addPlayer(p2);
                                JOptionPane.showMessageDialog(
                                           null, "Account Created. Welcome "+p2.playerName);
                                createStartButton.setEnabled(false);
                                break;
                            }
                        }
                      }
                      
                      //There is no list, create player then...
                      else{
                          p2 = new Player(2,newPlayer2Text.getText());
                          app.game.addPlayer(p2);
                          JOptionPane.showMessageDialog(  null, 
                                  "Acount Created. Welcome "+p2.playerName);
                          createStartButton.setEnabled(false);
                      }
                      
                    //Player 1 is set to go, enable the start game button
                      if(p1 != null)
                       startGameButton.setEnabled(true);
                  }
                //Left it blank...
                  else{
                       JOptionPane.showMessageDialog(null, "Please Enter Player2 Name");
                  }
                  
               }
           });
           c.fill = GridBagConstraints.HORIZONTAL;
           c.gridx = 1;
           c.gridy = 2;
           add(createStartButton,c);
           
           
       }
    }
}
