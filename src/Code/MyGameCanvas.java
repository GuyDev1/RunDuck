/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Code;

import Code.Controller.Clock;
import Code.Controller.Clock2;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Timer;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

/** Handles the game screen, adjusts the window view according to the ducks movement,
 * Manages the sprites, part of the timers, and the layers in the game.
 *
 * @author Student
 */


//occasionally get's stuck for a second, check why, maybe it has something to do witht he synchronized.

// ask toby where do I need to use "synchronized".


//7. Remember to delete all irrelvant things and methods from the project.




public class MyGameCanvas extends GameCanvas implements Runnable{
private LayerManager lm;
private GameMidlet midlet;

public static boolean gameOn=true;

private Runner barvaz;

private AttackBird abird;

 private static final int MINIMUM_DISTANCE = 100;//the distance of the sprite  before moving the VW
    private int vwX =0;//where the window view begins
    private  int vwY =0;
private Controller controller;
private TiledLayer wall;
private TiledLayer bg;
private TiledLayer collect;
private Thread thread;
private GameDesign gd;
private TiledLayer diamond;
    private  Clock clock;
    private  Clock2 clock2;
    private Player player;
    private VolumeControl vc;
   
   

   
    private long startTime;
    public static long estimatedTime;
    public static int score;
    private  AttackBird abird2;
    private int i;
    private AttackBird abird3;
    
    
   

/** Sets everything in the game, from the tiled layers to the sprites, clocks and so on
     * (It set's everything when the constructor is being called, when the game starts).
     * 
     * @param m the midlet of the game.
     */
public MyGameCanvas(GameMidlet m) {
        super(true);
        setFullScreenMode(true);
        midlet=m;
        gameOn=true;
       
midlet.gameOver=false;
        gd=new GameDesign();
        try {
            wall = gd.getWallTiles();
            bg=gd.getBackgroundTiles();
            collect=gd.getCollectables();
           
        } catch (IOException ex) {

        }
        lm = new LayerManager();



        try{

         gd.updateLayerManagerForScene1(lm);
         Sprite bird =  new Sprite(gd.getRunningDuck());
        barvaz = new Runner(bird);
        lm.insert(barvaz, 0);
        
        Sprite atbird=new Sprite(gd.getAttackBird());
        abird=new AttackBird(atbird,0);
        abird.start();
        lm.insert(abird, 0);
        abird2=new AttackBird(atbird,700);
        abird2.start();
        lm.insert(abird2, 0);
        abird3=new AttackBird(atbird,1400);
        abird3.start();
        lm.insert(abird3, 0);
        
         controller = new Controller(gd,lm,wall,bg, barvaz,abird,abird2,abird3,collect);
   
         gd.updateLayerManagerForScene1(lm);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        

clock=new Clock(10);
        new Timer().schedule(clock,0, 1000);
        clock2= new Clock2(15);
        new Timer().schedule(clock2,0, 1000);
  
        

       
        vwX = bg.getX();
        vwY = bg.getY() + bg.getHeight() - getHeight();
        lm.setViewWindow(vwX, vwY, getWidth(), getHeight()-30);
      
        drawAll();
}
/** Start's the thread and starts counting the elapsed time
 * 
 */
public void startGame(){
   
        thread = new Thread(this);
    thread.start();
     startTime = System.currentTimeMillis();
   
    

  
}

/** Takes care of the drawing of the hearts, the diamond icon, and the rest of the things.
 * Also checks when the player is dead and acts accordingly.
 * 
 */
public  void drawAll(){
        Graphics g = getGraphics();
         g.setColor(0);
         g.fillRect(0,0,getWidth(), getHeight());
         lm.paint(g, 0, 0);
         showTimeLeft(getGraphics());
               
       
         for( i =10;i<(10 + 16*controller.getLives());i+=16)
             try {
            getGraphics().drawImage(gd.getHeart(), i, 308, Graphics.BOTTOM | Graphics.LEFT);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         g.setColor(255,255,255);
         if(Controller.livesOver3>0)
{
    g.drawString("+" + String.valueOf(Controller.livesOver3), i+16, 310, Graphics.BOTTOM | Graphics.RIGHT);
}
        try {
            getGraphics().drawImage(gd.getIconDiamond(), 231, 310, Graphics.BOTTOM| Graphics.RIGHT);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
g.drawString(String.valueOf(controller.getSum()), 212, 310, Graphics.BOTTOM | Graphics.RIGHT);
g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL));
g.drawString("Pause", 7, 322, Graphics.BOTTOM | Graphics.LEFT);

         flushGraphics();
    }
/** Manages the actions of the game canvas, including player movement, jump and so on.
 * 
 */
public void input(){
    int ks = getKeyStates();
    
    if ((ks&RIGHT_PRESSED)> 0)
    {
   
        controller.setDirection(RIGHT);
            adjustView(this.vwX+ Runner.XY_SPEED, this.vwY);
    }
    if ((ks&LEFT_PRESSED)>0)
    {
       
        controller.setDirection(LEFT);
                    adjustView(this.vwX- Runner.XY_SPEED, this.vwY);
    }
    if ((ks&DOWN_PRESSED)>0)
    {

          controller.setDirection(DOWN);
                                adjustView(this.vwX, this.vwY+  Runner.XY_SPEED);
    }
        if ((ks&UP_PRESSED)>0)
        {
            
              controller.setDirection(UP);
                            adjustView(this.vwX, this.vwY-  Runner.XY_SPEED);
        }
    if(ks==0)
    {
        Runner.isMoving=false;
    }
        if((ks&FIRE_PRESSED)>0)
        {
           
                if(controller.isNotWall())
                {
                
           switch(barvaz.GetCurrDirection())
           {
               case LEFT: barvaz.setPosition(barvaz.getX()-50, barvaz.getY());
               adjustView(this.vwX-50, this.vwY);
                   break;
               case RIGHT: barvaz.setPosition(barvaz.getX()+50, barvaz.getY());
               adjustView(this.vwX+50, this.vwY);
                   break;
               case UP: barvaz.setPosition(barvaz.getX(), barvaz.getY()-50);
               adjustView(this.vwX, this.vwY-50);
                   break;
               default: barvaz.setPosition(barvaz.getX(), barvaz.getY()+50);
                   adjustView(this.vwX, this.vwY+50);
                   break;
           }
            }
            
          
          
        }


}
/** Runs the game, and the methods input, and process.
 * 
 */
    public void run() {
        
       while (true){
          if(!GameMidlet.GameStopped)
          {
               
           input();
           process();
            
           

           drawAll();

         
           try{
               Thread.sleep(200);
           }
           catch(Exception ex){

           }
          }

       }
       
     
       

   
    }
    /** In case the game ends, the method is used and gameOn becomes false. When that happens the method run stops.*/
    public void stopGame(){
       gameOn = false;
    }
    /** Adjusts the phone's window view according to the movement of the player
     * 
     * @param x the x view coordinates.
     * @param y the y view coordinates.
     */
    public void adjustView(int x, int y){
        
        int sX = barvaz.getX();
        int sY = barvaz.getY();
        int maxBgX = barvaz.getX() + bg.getWidth();
        int maxBgY = barvaz.getY() + bg.getHeight();
        // Do we have to move the window???
        int minX = this.vwX + MINIMUM_DISTANCE;

        int minY = this.vwY + MINIMUM_DISTANCE;
        int maxX = vwX+ getWidth() - barvaz.getWidth()-MINIMUM_DISTANCE;
        int maxY = vwY + getHeight() - minY- barvaz.getHeight()-MINIMUM_DISTANCE;
        
        if (sX >= minX && sX <= maxX && sY >= minY && sY <= maxY)
            return;
        if (barvaz.GetCurrDirection() ==LEFT && sX >=minX)
            return;
        if (barvaz.GetCurrDirection()== RIGHT && sX  <= maxX){
            
            return;
        }
        else{
                
                if (x >= maxBgX)
                this.vwX = maxBgX;
            else
                this.vwX= x;

        }

        if (barvaz.GetCurrDirection() ==UP && sY >=minY)
            return;
        if (barvaz.GetCurrDirection()== DOWN && sY <= maxY)
            return;
        switch (barvaz.GetCurrDirection()){
            case LEFT:
                if (x < bg.getX())
                 this.vwX = bg.getX();
            else
                this.vwX = x;
            break;
            case UP:
                if (x < bg.getY())
                    vwY = bg.getY();
            else
                vwY = y;
                break;
            default:
                
                if (y > maxBgY)
                vwY = bg.getY();
            else
                vwY = y;

        }

        this.lm.setViewWindow(vwX, vwY, getWidth(), getHeight()-30);
        }


 /** In case a special ability starts, this method shows the time left for the ability by using the class clock.
    This method also checks if it's the second time the ability starts and if so it restarts the clock by adding extra time.
    When the time left is less than 6 seconds, the clock begins to flicker the time shown with red and black colors.
     */ 
private void showTimeLeft(Graphics g) {

  // what does the clock say
    
  int timeLeft = clock.getTimeLeft();
  int timeLeft2 =clock2.getTimeLeft();

  // if less than 6 seconds left
  // flicker time with red and black
  if(timeLeft < 6) {
	if((timeLeft % 2) == 0)
	  g.setColor(0xff0000);
	else
	  g.setColor(0x000000);
  }
g.setColor(0x000000);
if(Controller.isInvincible())
{
    if(controller.getInvSum()>0)
        timeLeft+=10;
    
g.setColor(255,255,255);
if(timeLeft<6)
 {
     if(timeLeft%2==0)
     g.setColor(0xff0000);
     else 
         g.setColor(255,255,255);
         
 }

  g.drawString("Invincibility: " + timeLeft , 165, 305,Graphics.BOTTOM| Graphics.RIGHT);
  
}
if(Controller.isFast())
{
    
       
    if(controller.getFastSum()>0)
        timeLeft2+=15;
    
              
        g.setColor(255,255,255);
             if(timeLeft2<6)
 {
     if(timeLeft2%2==0)
     g.setColor(0xff0000);
     else 
         g.setColor(255,255,255);
         
 }

  g.drawString("Super fast: " + timeLeft2 , 165, 320,Graphics.BOTTOM| Graphics.RIGHT);
}

  // reset the color
   g.setColor(0x000000);
}

  
/** Deals with processes like checking if the player died, measuring the elapsed time, and so on*/
 private void process() {


    
        if(controller.getLives()==0||Controller.GameFinished)
{


    
   
            

 estimatedTime = System.currentTimeMillis() - startTime;
 score=controller.getSum();
 
   if(Controller.isFast())  
controller.getT1().cancel();
   
   if(Controller.isInvincible())
            controller.getT2().cancel();
   
   midlet.gameOver(controller.getSum(), estimatedTime);
    
  controller = new Controller(gd,lm,wall,bg, barvaz,abird,abird2,abird3,collect);
       
  midlet.GameStopped=true;
 abird.stop();
       this.thread = null;
  cMenu.gameStop=false;
   midlet.GameRun=false;
  
   
     
}
    }
/** Gets the estimated time, and returns a string showing minutes and seconds.
  * 
  * @param estimatedTime the elapsed time of the game.
  * @return 
  */
 public static String getEstimatedTime(long estimatedTime)
 {
     
     int seconds = (int) (estimatedTime / 1000) % 60 ;
int minutes = (int) ((estimatedTime / (1000*60)) % 60);
String sec= String.valueOf(seconds);
String min= String.valueOf(minutes);

String min_sec= min + ":" +sec;

return min_sec;

 }
 public static int getScore()
 {
     return score;
 }

 
 
   
    

}

