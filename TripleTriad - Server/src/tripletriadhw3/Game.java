/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tripletriadhw3;

import java.util.*;


public class Game {
    ArrayList<Player> playersList;
    
    public Game(){
        playersList = new ArrayList<Player>();
    }
    
    public void addPlayer(Player p1){
        playersList.add(p1);
    }
    
}
