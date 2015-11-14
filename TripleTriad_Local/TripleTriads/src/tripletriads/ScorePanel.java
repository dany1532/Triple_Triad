
package tripletriads;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class ScorePanel extends JPanel {
    JScrollPane scrollPane;
    TripleTriad app;
    BorderLayout bl;
    Vector<String> columnNames;
    Vector<Object> vector;
    Vector<Vector> mainVec;
    JTable table;
    
  //Create panel for storing the player's stats
    public ScorePanel(TripleTriad tt){
        app = tt;
        bl = new BorderLayout();
        
        setLayout(bl);
        
     //Create the vectors for the column and row data
        mainVec = new Vector<Vector>();
        columnNames = new Vector<String>();
        
    //What does each column represent
        columnNames.addElement("Player's Name");
        columnNames.addElement("Games Won");
        columnNames.addElement("Games Lost");
        
    //Obtain player's info and store it in the row data
        for(int i = 0; i < app.game.playersList.size();i++){
            vector = new Vector<Object>();
            vector.addElement(app.game.playersList.get(i).playerName);
            vector.addElement(app.game.playersList.get(i).gamesWon);
            vector.addElement(app.game.playersList.get(i).gamesLost);
            mainVec.addElement(vector);
        }
        
    //Populate the table
        table = new JTable(mainVec,columnNames);
        
     //Add it in a scroll panel 
        scrollPane = new JScrollPane(table);
        
    //add it to the panel
        add(scrollPane,BorderLayout.CENTER);
        
        
        

    }
    
    public void refreshHistory(){
        setLayout(new BorderLayout());
        
     //Create vectors for the column and row data
        mainVec = new Vector<Vector>();
        columnNames = new Vector<String>();
        
        
    //What does each column represent
        columnNames.addElement("Player's Name");
        columnNames.addElement("Games Won");
        columnNames.addElement("Games Lost");
        
    //Obtain players info and store it in the row's data
        for(int i = 0; i < app.game.playersList.size();i++){
            vector = new Vector<Object>();
            vector.addElement(app.game.playersList.get(i).playerName);
            vector.addElement(app.game.playersList.get(i).gamesWon);
            vector.addElement(app.game.playersList.get(i).gamesLost);
            mainVec.addElement(vector);
        }
        
     //For refreshing purposes
        this.remove(scrollPane);
        this.doLayout();
        
     //Create Table with info
        table = new JTable(mainVec,columnNames);
        scrollPane = new JScrollPane(table);
        add(scrollPane,BorderLayout.CENTER);

        
        
    }
    

    

}
