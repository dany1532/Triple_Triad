
package gameclient;

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
    GameClient theApp;
    //Game thisGame;
    
    public MenuBar(Container tt,GameClient app){
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
                theApp.sendRequest("beforegame");
                if(theApp.permissionGranted()){
                //question...    
                    theApp.startPanel.enablePlayer1Button();
                    theApp.startPanel.enablePlayer2Button();
                    theApp.cl.show(theApp.panels,"StartPanel");
                    startGameItem.setEnabled(false);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Game has Started");
                }
            }
        });
        menu.add(startGameItem);
    
        
        
    //View players and their stats 
        viewScoresItem = new JMenuItem("View Scores");
        viewScoresItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                theApp.scorePanel.refreshHistory();
                startGameItem.setEnabled(true);
                theApp.cl.show(theApp.panels,"ScorePanel");

            }
        });
            viewScoresItem.setEnabled(true);
        menu.add(viewScoresItem);

        
    } 
}
