/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameclient;

import java.io.*;
import java.net.*;
import java.util.*;


//Extends the game, so the server is the game as well...
public class GameServer extends Game{
    ServerSocket ss = null;
    Socket s = null;
    int startedGameCount = 0;
    int playerCount; //I hard code this. You must prompt the user starting the server
    final int MAX_PLAYERS = 2;
    Object playerTurnObject;
    Scanner scan;
    

    public GameServer(Object playerTurnObject) {
        this.playerTurnObject = playerTurnObject;
    }

    public GameServer() {
        playerTurnObject = new Object();
        int count = 0;

    //Load Player's History
        loadHistory();
        
     //Create Server Socket
        try {
            ss = new ServerSocket( 1532 );
        } catch( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
        
     //Ask the user for how many players can play. Can only input 1 or 2
        scan = new Scanner(System.in);
        while(true){
            System.out.println("How many Players? (Can only play up to 2");
            playerCount = scan.nextInt();
            if(playerCount > 0 && playerCount <= 2)
                break;
        }
        
     //If there is only one player, then create an AI
        if(playerCount == 1){
            AI createAI = new AI();
        }
        
     //Create Threads
        while ( true) {
                try {
                    if(count < MAX_PLAYERS){
                        s = ss.accept();
                        HandleAPlayerClient hapc = new HandleAPlayerClient( s, this );
                        new Thread(hapc).start();
                        System.out.println( "Server: got a connection" );
                        count++;
                    }

                } catch (Exception e) {
                System.out.println("got an exception" + e.getMessage() );
                }

                
            
        }//end while
    }//end constructor

    public static void main( String[] args ) {
        GameServer server = new GameServer();
    }

    class HandleAPlayerClient implements Runnable {
     //Create Socket and Streams
        Socket mySocket;
        PrintWriter pw;
        BufferedReader br;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        
        GameServer myGameServer;
        boolean startedGame = false;

        public HandleAPlayerClient( Socket s, GameServer gs ) {
            mySocket = s;
            myGameServer = gs;
        }
        
        
     //send an object to the client
        public void sendObject(Object obj){
            try {
                oos.writeObject(obj);
                //oos.reset();
            } 
            catch (Exception e) {
                System.out.println("got an exception" + e.getMessage());
            }
        }


        public void run() {
            try {
        //Create the 4 streams for talking to the player client
                pw = new PrintWriter( mySocket.getOutputStream(), true );
                br = new BufferedReader( new InputStreamReader( mySocket.getInputStream() ) );
                oos = new ObjectOutputStream(mySocket.getOutputStream());
                ois = new ObjectInputStream(mySocket.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }

            while( true ) {
                try {
            //Wait for the client to send a request
                    String message = br.readLine();
                    System.out.println("message is:" + message );

                    if ( message.equals( "beforegame" ) ) {
            //User can do 'beforegame' a many times as they want
            //I just print it out. I send back 'ok'
            //As long as they have started the game
                        if ( startedGame ) {
                            System.out.println("Error. Game is started" );
                        } 
                        else {
                            pw.println( "ok" );
                        }
                    } // end "beforegame" statement
                    
    /////////////////////////////////////////////////////////////////////
                    
                    else if ( message.equals( "Beginning" ) ) {
                            startedGame = false;
                            //startedGameCount = 0;

                    } // end "Beginning" statement
                    
     ///////////////////////////////////////////////////////////////////  
                    
                    else if ( message.equals( "addPlayer" ) ) {
            //User can do 'addPlayer' a many times as they want
            //I add Player to the list. Save history..
                        if ( startedGame ) {
                            System.out.println("Error. Game is started" );
                        } 
                        else {
                            pw.println("ok");
                            Player p = (Player) ois.readObject();
                            myGameServer.addPlayer(p);
                            for(int i = 0; i < myGameServer.playersList.size(); i++) {
                                System.out.println(myGameServer.playersList.get(i).playerName);
                            }
                            saveHistory();
                            pw.println("Server: Player Added"); 
                        }

                    } // end "addPlayer" statement
      /////////////////////////////////////////////////////////////////        
                    
           ///////////////////////////////////////////////////////////////////               
                    else if ( message.equals( "loadPlayer" ) ) {
            //User can do 'loadPlayer' a many times as they want
            //I load player from list and send it to the client
                        if ( startedGame ) {
                            System.out.println("Error. Game is started" );
                        } 
                        else {
                            String playerName = br.readLine();
                            if(myGameServer.playerFound(playerName) != null)
                                sendObject(myGameServer.playerFound(playerName));
                            else
                                sendObject(null);
                         
                            System.out.println("Player sent");
                        }

                    } // end "loadPlayer" statement
      /////////////////////////////////////////////////////////////////   
                    
                    else if ( message.equals( "startgame" ) ) {
                        startedGame = true;

                        if ( startedGameCount == MAX_PLAYERS ) {
          //I'm the last player to start the game. Makes me player 1. I go first
                            pw.println("your turn");
                        } 
                        else {
            //I'm not the last player to start the game, so I must wait my turn
                            synchronized( playerTurnObject ) {
                                playerTurnObject.wait();
                                System.out.println("I was notified");

                    //Send a reply to my player that it is their turn
                                pw.println( "Server: your turn" );
                            }
                        }
                    } 
                    
         ///////////////////////////////////////////////////////////////////  
                    
                    else if ( message.equals( "history" ) ) {
            //User can do 'history' a many times as they want
            //As long as they have not started the game
                        if ( startedGame ) {
                            System.out.println("Error. Game is started" );
                        } 
                        else {
                        //No history whatsoever, send 0
                            if(myGameServer.playersList.isEmpty()){
                                Integer nothing = 0;
                                sendObject(nothing);
                            }
                        //Send how many players there are
                            else{
                                sendObject(myGameServer.returnPlayerListSize());
                              //Start sending the name, games won and lost
                                for(int i = 0;i < myGameServer.returnPlayerListSize();i++){
                                    sendObject(myGameServer.returnPlayerListName(i));
                                    sendObject(myGameServer.returnPlayerListWon(i));
                                    sendObject(myGameServer.returnPlayerListLost(i));
                                }
                            }
                            
                        }
                    } // end "history" statement
                    
       /////////////////////////////////////////////////////////////////////
                    
                    
                    else if ( message.equals( "returnBoard" ) ) {
            //User can do 'returnBoard' a many times as they want
                        if ( !startedGame ) {
                            System.out.println("Error. Game is started" );
                        } 
                        else {
                      //Start Sending board data to client
                            pw.println("Server: Initializing");
                                Integer player1 = (Integer) ois.readObject();
                                Integer amount = myGameServer.amountPlayerBoardCard(player1);
                                sendObject(amount);
                                
                           //Player1
                                //Send location in the board and the card in it
                                for(Integer i = 0; i < myGameServer.boardSize(); i++){
                                    if(myGameServer.returnCardBoard(i)!= null){
                                        if(myGameServer.isCardPlayer(player1, i)){
                                            sendObject(i);
                                            sendObject(myGameServer.returnCardBoard(i));
                                        }
                                    }
                                            
                                }
                                
                            //Continuing to player 2
                                pw.println("Server: Continuing");
                                Integer player2 = (Integer) ois.readObject();
                                amount = myGameServer.amountPlayerBoardCard(player2);
                                sendObject(amount);
                                
                              //Send location in board and the card in it
                                for(Integer i = 0; i < myGameServer.boardSize(); i++){
                                    if(myGameServer.returnCardBoard(i)!= null){
                                        if(myGameServer.isCardPlayer(player2, i)){
                                            sendObject(i);
                                            sendObject(myGameServer.returnCardBoard(i));
                                        }
                                    }
                                            
                                }
                            
                           //Send the current scores of player 1 and 2
                                sendObject(myGameServer.scorePlayer1());
                                sendObject(myGameServer.scorePlayer2());
                         
                            System.out.println("Server: Board sent");
                        }

                    } // end "returnBoard" statement
      /////////////////////////////////////////////////////////////////   
                    
                //To figure out who is going to be player 1 and 2
                    else if ( message.equals( "setPlayer" ) ) {
                        //startedGame = true;
                        startedGameCount++;
                        pw.println(startedGameCount);

                    } //end 'setPlayer'
                    
                         ///////////////////////////////////////////////////////////////////   
                    
              //Get data to update the board in the game
                    else if ( message.equals( "sendBoard" ) ) {
                        
            //User can do 'sendBoard' a many times as they want
                        if ( !startedGame ) {
                            System.out.println("Error. Game has not started" );
                        } 
                        else {
                    //Obtain the desired location in the board, the card and whatplayer sent it
                            pw.println("Server: Waiting to receive Board");
                            Integer loc = (Integer) ois.readObject();
                            Integer whatPlayer = (Integer) ois.readObject();
                            Card card = (Card) ois.readObject();
                            Integer cardsHand = (Integer) ois.readObject();
                            
                      //send the obtained information to the board
                            myGameServer.sendBoard(loc,whatPlayer,card, cardsHand);
                            
                       //Play the Same, Plus and Basic Rule
                            myGameServer.playGame(loc,whatPlayer);
                            
                            pw.println("Server: Board Received"); 
                        }

                    } // end "sendBoard" statement
      ///////////////////////////////////////////////////////////////// 
                    
                    
                    
             //Got a 'turnOver' wake up the other client     
                    else if ( message.equals("turn over" ) ) {
//this player is done with their turn. Notify the next player then wait for my turn again
                        System.out.println("got a turn over");
                        synchronized( playerTurnObject ) {
                            playerTurnObject.notify();
                            playerTurnObject.wait();
                            System.out.println("was notified");
                        }
                        
                  //Is the board full?
                        if(myGameServer.isGameOver()){
                            startedGameCount = 0;
                            
                       //wake everyone up
                            synchronized( playerTurnObject ) {
                            playerTurnObject.notifyAll();
                        }
                       //restart the number players to start game
                            startedGameCount = 0;
                            System.out.println("GameOver");
                            pw.println("Server: GameOver");
                        }
                        else
                    //It is my player's turn. Send their message.
                        pw.println("Server: Your turn" );
                    } 
                    
                //Clear the board and restart score...
                    else if ( message.equals("clearBoard" ) ) {
                        myGameServer.myBoard.clearBoard();
                        myGameServer.numberCards1 = 5;
                        myGameServer.numberCards2 = 5;
                            

                    } 
                    

                    else {
                    //a bad message
                        System.out.println("a bad message received");
                        pw.println("error");
                    }//end bad Message
                    
                   

            //Something went horribly wrong
                } catch (Exception e) {
                    System.out.println("this");
                        e.printStackTrace();
                  }
            }//end while loop
        }// end run method
    }//end of public class HandlePlayer
}//end of GameServer class
