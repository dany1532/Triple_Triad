/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tripletriadhw3;

import java.io.*;
import java.net.*;

public class GameServer extends Game{
    ServerSocket ss = null;
    Socket s = null;
    int startedGameCount = 0;
    int playerCount = 2; //I hard code this. You must prompt the user starting the server
    Object playerTurnObject;
    

    public GameServer(Object playerTurnObject) {
        this.playerTurnObject = playerTurnObject;
    }

    public GameServer() {
        playerTurnObject = new Object();

        try {
            ss = new ServerSocket( 23119 );
        } catch( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }


        while ( true ) {
            try {
                s = ss.accept();

                HandleAPlayerClient hapc = new HandleAPlayerClient( s, this );
                new Thread(hapc).start();

            } catch (Exception e) {
                System.out.println("got an exception" + e.getMessage() );
            }

            System.out.println( "got a connection" );
        }//end while
    }//end constructor

    public static void main( String[] args ) {
        GameServer server = new GameServer();
    }

    class HandleAPlayerClient implements Runnable {
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

        public void run() {
            try {
        //Create the 2 streams for talking to the player client
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
                    
                    else if ( message.equals( "play" ) ) {
            //This is allowed when it is the player's turn. They
            //can do this as many times as they want until 'turn over'
            //Once the game is started
                        if ( startedGame ) {
                //This is correct. Print the message and reply 'ok'.
                            System.out.println( message );
                            pw.println( "ok" );
                        } 
                        else {
                //This is not ok. Trying to play before starting game
                            pw.println( "Error" );
                        }

                    } // end "Play" statement
                    
     ///////////////////////////////////////////////////////////////////               
                    else if ( message.equals( "addPlayer" ) ) {
            //User can do 'addPlayer' a many times as they want
            //I add Player to the list. I send back 'ok'
                        if ( startedGame ) {
                            System.out.println("Error. Game is started" );
                        } 
                        else {
                            //pw.println("ok");
                            Person p = (Person) ois.readObject();
                            System.out.println(p.getPerson());
                            //System.out.println(p.toString());
//                            myGameServer.addPlayer(p);
                            //pw.println(myGameServer.playersList.size());
//                            System.out.println(myGameServer.playersList.get(0).playerName);
                            
                            
                            
                        }

                    } // end "addPlayer" statement
      /////////////////////////////////////////////////////////////////            
                    
                    else if ( message.equals( "startgame" ) ) {
                        startedGame = true;
                        startedGameCount++;

                        if ( startedGameCount == playerCount ) {
          //I'm the last player to start the game. Makes me player 1. I go first
                            pw.println("your turn");
                        } 
                        else {
            //I'm not the last player to start the game, so I must wait my turn
                            synchronized( playerTurnObject ) {
                                playerTurnObject.wait();
                                System.out.println("I was notified");

                    //Send a reply to my player that it is their turn
                                pw.println( "your turn" );
                            }
                        }
                    }    
                    else if ( message.equals("turn over" ) ) {
//this player is done with their turn. Notify the next player then wait for my turn again
                        System.out.println("got a turn over");
                        synchronized( playerTurnObject ) {
                            playerTurnObject.notify();
                            playerTurnObject.wait();
                            System.out.println("was notified");
                        }

                    //It is my player's turn. Send their message.
                        pw.println("Your turn" );
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
