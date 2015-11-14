
package gameclient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class MusicPlayer implements Serializable {
 // Initial set up
    InputStream in;
    AudioStream as;
    
//Creates the music player and plays the background music (no loop)
    MusicPlayer(String fileName){
     // Initial set up
        in = null;
        as = null;
        
     //obtains the file from directory,
        try{
            in = new FileInputStream(fileName);
        }
        
    //or displays error message that it was not found
        catch(FileNotFoundException ble){
            System.out.println("The audio file was not found");
        }
        
   //Creates an audio stream from file
        try{
            as = new AudioStream(in);
        }
        
   //or fail trying...
        catch(IOException ie){
            System.out.println("Audio stream could not be created");
        }
        
  //Let the music begin...
        AudioPlayer.player.start(as);
        
    }
    
 //stop the music from playing
    public void stopAudio(){
        AudioPlayer.player.stop(as);
    }
}
