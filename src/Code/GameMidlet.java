/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Code;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.media.Controllable;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;
import javax.microedition.midlet.*;
// Remember!, the player starts the general music when you do "new game".
//When you resume a game, it simply does "play music", and not "start player".
/** Handles the game screens, player info, savings and more.
 * @author Student
 */
public class GameMidlet extends MIDlet implements CommandListener   {
    private MyGameCanvas gameCanvas;
    private ScoreData data;
    
    public cMenu cmenu;
    public ColorMenu colormenu;
    public ShowTopScores scores;
    public boolean GameRun=true;
    public boolean gameOver;
    public boolean TerrainChanged=false;
    public boolean Top10=false;
    public boolean GameOver=false;
    public static boolean GameStopped=false;
    private Player player;
 
    public static String nickname;
    private VolumeControl vc;
    private Command go;
    private final Command cmdOK;
  private final String storeName = "HighScores";
    private final ScoreData db;
    private Top10 tTen;
    private final TextBox resultBox;
    private PlayerData newScore;
    private EndGameMenu endmenu;
    private Instructions instructions;
/**
     *  if the color menu is null it initializes a new menu.
     * @return the color menu
     */
   public ColorMenu getColorMenu()
   {
       if(colormenu==null)
       {
           colormenu=new ColorMenu(this);
       }
         Button.selectedButton=0;
       return colormenu;
   }
   public Instructions getInstructions() throws IOException
   {
       if(instructions==null)
       {
           instructions=new Instructions(this);
       }
         Button.selectedButton=0;
       return instructions;
   }
   /**  if null initializes  a new score table.
    * 
    * @return the top scores.
    */
   public ShowTopScores getTopScores()
   {
       if(scores==null)
       {
           scores=new ShowTopScores(this);
       }
       return scores;
   }
   /** If the menu is null it initializes a new menu.
    * 
    * @return the menu, 
    */
    public cMenu getcMenu()
    {
        if(cmenu==null)
        {
            cmenu = new cMenu(this);
            
            
            
        }
          Button.selectedButton=0;
        return cmenu;
    }
     public EndGameMenu getEndMenu()
    {
        if(endmenu==null)
        {
            endmenu = new EndGameMenu(this);
            
            
            
        }
          Button.selectedButton=0;
        return endmenu;
    }
    /** initializes the game canvas if null.
     * 
     * @return the game canvas.
     */
   public MyGameCanvas getCanvas(){
       if (gameCanvas ==null){
           
           gameCanvas = new MyGameCanvas(this);
           gameCanvas.addCommand(getCmdPause());
            gameCanvas.setCommandListener(this);
           gameCanvas.startGame();
       }
       return gameCanvas;
   }
   
     

    public Display display;
    /**  
     * 
     * @return the game display
     */
    public Display getDisplay()
    {
        return display;
    }
           private Command cmdPause;
           /** 
            * 
            * @return the pause command.
            */
    private Command getCmdPause()
    {
        if(cmdPause==null)
        {
           
            cmdPause = new Command("Pause",Command.STOP,0);
        }
        return cmdPause;
    }

/** Get's the game display, set's some of the commands, the name text field, and the top 10 scores, if available.
     * 
     */
    public GameMidlet()
    {
        display = Display.getDisplay(this);
       cmdOK=new Command("OK",Command.OK,0);
        db = new ScoreData(storeName, true);
        getTopTen();
        resultBox = new TextBox("Top 10!, Please Enter Your Name", "", 10, TextField.ANY);
        resultBox.addCommand(cmdOK);
        resultBox.setCommandListener(this);
      
      
   
    }
    /** initializes the top ten class (by opening the database) if null.
     * 
     * @return the best 10 scores in the game.
     */
     public final Top10 getTopTen(){
        if(tTen==null)
            tTen=new Top10(db);
        return tTen;
    }
     /** Set's a new player.
      * 
      * @param name the name of the player
      * @param score the player's score
      * @param time the elapsed game time.
      * @return 
      */
      public PlayerData NewScore(String name, int score , long time){
        if(newScore==null)
       newScore=new PlayerData(name, score, time);
       return newScore;
        
    }
      /**
       * 
       * @return the player currently playing.
       */
      public PlayerData getNewScore()
      {
          return newScore;
      }
      /** 
       * 
       * @return the high score string.
       */
       public String  getStringTopTen(){
        return tTen.toString();
    }
       /** Displays the top 10 players, and their info.
        * 
        */
       public void DisplayTopTen(){
        getDisplay().setCurrent(resultBox);
    }
       /** When the game is over, it gets the player's info,
        * and checks if he is one of the top 10 scores. If he is, he get's to enter his name
        * otherwise he is sent to the menu.
        * @param score the player's score.
        * @param time the elapsed game time.
        */
        public void gameOver(int score,long time) {
            
        if(getTopTen().isHighScore(NewScore("", score, time)))
            DisplayTopTen();
        else{
            newScore=null;
            GameOver=true;
            display.setCurrent(getEndMenu());
            
        }
    }

/** Start's the application by setting the display to show the menu.
         * 
         */
    public void startApp() {
        display.setCurrent(getcMenu());
   
       
 



    }

    public void pauseApp() {
       
    }
/** Destroys the application, and saves the top 10 high scores.
     * 
     * @param unconditional 
     */
    public void destroyApp(boolean unconditional) {
        db.saveAll(tTen.allTopTens());
        notifyDestroyed();
          
    }
    /** Shows the menu by stopping the game and setting the display to show the menu. */
    public void ShowMenu()
    {
        
        try {
            player.stop();
        } catch (MediaException ex) {
            ex.printStackTrace();
        }
        GameMidlet.GameStopped=true;
     
        display.setCurrent(getcMenu());
        
                
    }

/** Starts the game by creating a new game canvas and setting the display to show it. */
    public void NewGame()
    {
        
      
       StartPlayer();
    
     gameCanvas=null;
     
                        display.setCurrent(getCanvas());

      
     
    }
/** Handles the commands in the game canvas and the player's name box.
     * 
     * @param c the command pressed.
     * @param d the current window that is being displayed. 
     */
    public void commandAction(Command c, Displayable d) {
         if(d==getCanvas())
         {
             if(c==getCmdPause())
             {
                 
             cmenu.gameStop=true;
                ShowMenu();
             }
         }
         
             if ( d ==this.resultBox){
        if(c== cmdOK){
            if(!resultBox.getString().equals(""))
            getNewScore().setNick(resultBox.getString());
            else
            getNewScore().setNick("Player");
            getTopTen().addPlayer(newScore);
            newScore=null;
            resultBox.setString(null);
           
            display.setCurrent(getEndMenu());
            
        }
        
         
       
    }
    }
    


/** Start's the background music and initializes the required variables.
     * 
     */
     public  void StartPlayer()
     {
        try
  {
    InputStream is = getClass().getResourceAsStream("/Music/alive.mid");
    player = Manager.createPlayer(is,"audio/midi");

    player.realize();
    // get volume control for player and set volume to max
    vc = (VolumeControl) player.getControl("VolumeControl");
    if(vc != null)
    {
      vc.setLevel(100);
    }
    player.start();
    player.prefetch();
    
  }
  catch(Exception e)
  {}
     }
     /** Closes the music player.
      * 
      */
     public  void CloseMusic()
     {
         player.close();
     }
     /** Start's playing the music.
      * 
      */
     public void PlayMusic()
     {
        try {
            player.start();
        } catch (MediaException ex) {
            ex.printStackTrace();
        }
     }
    
 


}
