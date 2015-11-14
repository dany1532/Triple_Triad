
package gameclient;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class ScorePanel extends JPanel {
    JScrollPane scrollPane;
    GameClient app;
    BorderLayout bl;
    Vector<String> columnNames;
    Vector<Object> vector;
    Vector<Vector> mainVec;
    JTable table;
    
  //Create panel for storing the player's stats
    public ScorePanel(GameClient tt){
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
        
        app.sendRequest("history");
        Integer listSize = (Integer) app.returnedObject();
        if(!listSize.equals(0)){
        
    //Obtain player's info and store it in the row data
            for(int i = 0; i < listSize;i++){
                vector = new Vector<Object>();
          //player's name
                vector.addElement((String) app.returnedObject());
          //player's won
                vector.addElement((Integer) app.returnedObject());
          //player's lost
                vector.addElement((Integer) app.returnedObject());
                mainVec.addElement(vector);
            }
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
        
        app.sendRequest("history");
        Integer listSize = (Integer) app.returnedObject();
        if(!listSize.equals(0)){
        
    //Obtain player's info and store it in the row data
            for(int i = 0; i < listSize;i++){
                vector = new Vector<Object>();
            //Player Name
                vector.addElement((String) app.returnedObject());
            //player's won
                vector.addElement((Integer) app.returnedObject());
            //players'lost
                vector.addElement((Integer) app.returnedObject());
            
                mainVec.addElement(vector);
            }
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
