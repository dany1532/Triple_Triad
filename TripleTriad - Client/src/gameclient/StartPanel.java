
package gameclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartPanel extends JPanel {
    CreatePlayer createPlayer1Panel;
    LoadPlayer createPlayer2Panel;
    JButton startGameButton;
    GameClient app;

    
    public StartPanel(GameClient frame){
        setSize(900,50);
        app = frame;
        
     //Creates panels for creating or loading the players
        createPlayer1Panel = new CreatePlayer();
        createPlayer2Panel = new LoadPlayer();
        
     //Creates a button for starting the game once the players are set
        startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener(){
            @Override
               public void actionPerformed(ActionEvent e){
                       app.score1 = 5;
                       app.score2 = 5;
                       app.sendRequest("clearBoard");
                       app.cl.show(app.panels,"GamePanel");
                       app.sendRequest("setPlayer");
                       app.setWhatPlayer();
                       app.startGame = true;
                       app.canPlay = false;
                       app.currentBoard.clearBoard();
                       app.gamePanel.repaint();
                       app.startDelayTimer.start();
                       app.menuBar.viewScoresItem.setEnabled(false);                   
                       
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
    class CreatePlayer extends JPanel{  
       JTextField newPlayer1Text;
       JLabel createPlayerLabel;
       JButton createStartButton;
       
       public CreatePlayer(){
        //Layout for placing the components
           setLayout(new GridBagLayout());
           GridBagConstraints c = new GridBagConstraints();
           
        //Creates the label
           createPlayerLabel = new JLabel("   Create Player");
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
           createStartButton = new JButton("Create Player");
           createStartButton.addActionListener(new ActionListener(){
            @Override
               public void actionPerformed(ActionEvent e){
                if(!"".equals(newPlayer1Text.getText())){
                    app.sendRequest("beforegame");
                    
                    if(app.permissionGranted()){
                        app.createPlayer(newPlayer1Text.getText());
                        app.sendRequest("addPlayer");                      
                        app.waitServer();
                        app.sendObject(app.returnPlayer());
                        app.waitServer();
                        JOptionPane.showMessageDialog(null, "Account Created: "
                                + "Welcome " + app.returnPlayerName());
                        startGameButton.setEnabled(true);
                    }
                }
                
                else{
                       JOptionPane.showMessageDialog(null, "Please Enter Player Name");
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
    class LoadPlayer extends JPanel{  
       JTextField loadPlayerText;
       JLabel loadPlayerLabel;
       JButton createStartButton;
       
       public LoadPlayer(){
           setLayout(new GridBagLayout());
           GridBagConstraints c = new GridBagConstraints();
           
           this.setBackground(Color.BLACK);
           
         //Create label
           loadPlayerLabel = new JLabel("   Load Player ");
           loadPlayerLabel.setForeground(Color.WHITE);
           c.fill = GridBagConstraints.HORIZONTAL;
           c.gridx = 1;
           c.gridy = 0;
           add(loadPlayerLabel,c);
           
        //Create textfield for input
           loadPlayerText = new JTextField(10);
           loadPlayerText.setText("");
           c.fill = GridBagConstraints.HORIZONTAL;
           c.gridx = 2;
           c.gridy = 1;
           add(loadPlayerText,c);
           
           
        //Create the button for creating or loading a player
           createStartButton = new JButton("Load Player");
           createStartButton.addActionListener(new ActionListener(){
            @Override
               public void actionPerformed(ActionEvent e){
                
                if(!"".equals(loadPlayerText.getText())){
                    app.sendRequest("beforegame");
                    
                    if(app.permissionGranted()){
                        app.sendRequest("loadPlayer");
                        app.sendRequest(loadPlayerText.getText());
                        Player p = (Player) app.returnedObject();
                        if(p == null)
                            JOptionPane.showMessageDialog(null, "Player Not Found");
                        else{
                            app.loadPlayer(p);
                            JOptionPane.showMessageDialog(null, "Player Loaded: "
                                    + "Welcome Back " + app.returnPlayerName());
                            startGameButton.setEnabled(true);
                        }
                    }
                }
                
                else{
                       JOptionPane.showMessageDialog(null, "Please Enter Player Name");
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
