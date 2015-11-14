
package tripletriads;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class MenuBar extends JMenuBar{
    JMenu menu;
    JMenuItem startGameItem;
    JMenuItem saveGameItem;
    JMenuItem loadGameItem;
    JMenuItem viewScoresItem;
    JMenuItem exitItem;
    JMenuItem continueGameItem;
    JMenuItem stopGameItem;
    Container j;
    TripleTriad theApp;
    Game thisGame;
    
    public MenuBar(Container tt,TripleTriad app){
        j = tt;
        theApp = app;
        
        
        
   //Build the first menu.
        menu = new JMenu("Actions");
        add(menu);
        
    //Place Items in the Menu, first is the Start New Game Submenu
        startGameItem = new JMenuItem("Start New Game");
        startGameItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                theApp.startPanel.enablePlayer1Button();
                theApp.startPanel.enablePlayer2Button();
                theApp.cl.show(theApp.panels,"StartPanel");
                startGameItem.setEnabled(false);


            }
        });
        menu.add(startGameItem);
     
    //Return to the game, if you are in another window such as viewing scores
        continueGameItem = new JMenuItem("Return to Game");
        continueGameItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                startGameItem.setEnabled(true);
                theApp.cl.show(theApp.panels,"GamePanel");


            }
        });
        continueGameItem.setEnabled(false);
        menu.add(continueGameItem);
        
    
     //Stops the game menu
        stopGameItem = new JMenuItem("Stop Game");
        stopGameItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                theApp.game.stopGame();
                stopGameItem.setEnabled(false);
                continueGameItem.setEnabled(false);
                startGameItem.setEnabled(true);
                theApp.cl.show(theApp.panels,"GamePanel");


            }
        });
        stopGameItem.setEnabled(false);
        menu.add(stopGameItem);
        
        
    //View players and their stats 
        viewScoresItem = new JMenuItem("View Scores");
        viewScoresItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                startGameItem.setEnabled(true);
                theApp.scorePanel.refreshHistory();
                theApp.cl.show(theApp.panels,"ScorePanel");

            }
        });
        if(theApp.game.playersList.isEmpty())
            viewScoresItem.setEnabled(false);
        menu.add(viewScoresItem);

        
    //Asks user if they want to exit the application
        exitItem = new JMenuItem("Exit Game");
        exitItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int result = JOptionPane.showConfirmDialog(
                    theApp.getContentPane(),
                    "Are you sure you want to exit the application?",
                    "Exit Application",
                    JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION){
                    theApp.game.saveHistory();
                    System.out.println("Exiting Program");
                    System.exit(0);
             
                }
                
            }
        });
        menu.add(exitItem);
    }
}
