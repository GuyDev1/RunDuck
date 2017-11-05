/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Code;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;
import javax.microedition.midlet.MIDlet;

/** Handles the game's tiles, sprites, collisions, score, lives, ability timer tasks, and more.
 *
 * @author Student
 *///// Remember to make a collision which that will check all 4 tiles around
// the barvaz, change "definecollisionrectangle" accordingly.
public class Controller {
// in the playbgmusic, I'll add something that will get some int, and according to
    // the int, it will play a different sound, for that matter.
    

    private TiledLayer wall;
    private TiledLayer collect;
    private Runner bird;
    private AttackBird atbird;
    private AttackBird atbird2;
    private AttackBird atbird3;
    /** contains the amount of hearts the player currently possesses  */
    private int lives; 
    private   TiledLayer bg;
    private LayerManager lm;
    private GameDesign gd;
    private static int sum=0;
    private static boolean invincible=false;
    private static boolean fast=false;
     TimerTask t1;
     TimerTask t2;
     
    private static  int InvincibleSum=0;
    private static int SuperfastSum=0;
    public boolean newGame=true;
   public static boolean ChangeTerrain=false;
 public static int TerrainType;
 public static int livesOver3=0;
   public static boolean SuperJump=false;
   public static boolean GameFinished=false;
    
  
    private Player boomSound;
    private Player player;
    private VolumeControl vc;
    private Player player2;
    private VolumeControl vc2;
    
    /** Get's all the required parameters and sets the game's properties, in order to perform actions, check collisions and more.
     * 
     * @param gd game design
     * @param lm layer manager
     * @param w tiler layer
     * @param bg background
     * @param r the duck sprite
     * @param g
     * @param atbird the first attack bird
     * @param atbird2 the second attack bird
     * @param c the collectables tile.
     */
    public Controller(GameDesign gd,LayerManager lm,TiledLayer w,TiledLayer bg, Runner r,AttackBird atbird,AttackBird atbird2,AttackBird atbird3, TiledLayer c){
        this.wall = w;
        this.bird = r;
      
        this.lives = 3;
        this.bg=bg;
        this.lm=lm;
        this.gd=gd;
        this.atbird=atbird;
        this.atbird2=atbird2;
        this.atbird3=atbird3;
        this.collect=c;
       sum=0;
        fast=false;
        Runner.XY_SPEED=10;
        InvincibleSum=0;
        SuperfastSum=0;
        if(ChangeTerrain){
            switch(TerrainType)
            {
               case 0: ChangeTerrain(138); break;
                case 1:  ChangeTerrain(244); break;
                
            }
        }
        

    }
    /** Sets the character's direction according to the given value, and initiates the method moveForward**/
    
    public  void  setDirection (int direction){
      
        bird.setDirection(direction);
        
         moveForward();
       
    }
    /** Checks the tiles around the sprite for collisions, creating a collision rectangle around the sprite
     if the sprite collides with a tile that contains the given index, the method returns true. Otherwise it returns false.*/
    public boolean CheckCollison(int index)//Consider adding diagonal lines (col+1,row+1), though it might be too much.
    {
        int col=getColCell();
int row=getRowCell();
if(collect.getCell(col, row)==index)
{
    
    collect.setCell(col, row, 0);
    return true;
}
if(collect.getCell(col+1, row)==index)
{
    collect.setCell(col+1, row, 0);
    return true;
}
if(collect.getCell(col-1, row)==index)
{
    collect.setCell(col-1, row, 0);
    return true;
}
if(collect.getCell(col, row+1)==index)
{
    collect.setCell(col, row+1, 0);
    return true;
}
if(collect.getCell(col, row-1)==index)
{
    collect.setCell(col, row-1, 0);
    return true;
}
return false;
    }
    /** Makes sure the tiles around the player are of the kind he can jump over,
     and if so, returns true, otherwise it returns false.
     Later this method is used to determine if the player is allowed to jump, or not.
     
     */
    public boolean isNotWall()
    {
        int col=getColCellWall();
int row=getRowCellWall();
        if(wall.getCell(col+1, row)==133)
           return true;

if(wall.getCell(col+2, row)==133)
    return true;

if(wall.getCell(col, row+1)==133)
    return true;

if(wall.getCell(col, row+2)==133)
    return true;
if(wall.getCell(col, row-2)==133)
    return true;
if(wall.getCell(col, row-1)==133)
    return true;
if(wall.getCell(col-2, row)==133)
    return true;
if(wall.getCell(col-1, row)==133)
    return true;
if(SuperJump)
    return true;

return false;
    }
    

    /** Moves the character, and checks for collisions.
        If the bird is out of frame, it stops it, therefore prevents the bird from going
        out of the map's limits.
     * In case the bird collides with the collectable tile, it checks for specific collisions:
     * In case the bird collides with a diamond, the score grows. if it collides with a special power
     * then the bird gets the special power for a limited amount of time by using a timer task.
     * In case the bird collides with the flying big bird, or the wall tiles, it gets hurt,
     * and 1 heart is deducted. */
    public  void  moveForward(){
        this.bird.move();
        birdOutOfFrame();
int col=getColCell();
int row=getRowCell();

if(bird.collidesWith(atbird, true)||bird.collidesWith(atbird2, true)||bird.collidesWith(atbird3, true))
{
    if(!isInvincible())
    lives--;
}
    
        if (bird.collidesWith(wall, true)){
            bird.moveBack();
              if(!isInvincible())   
                lives--;
              
        }
       if(bird.collidesWith(collect, true))
       {

            
            if(CheckCollison(124))
            {
                lives++;
                playPickMusic();
          

                
            }
            if(collect.getCell(col, row)==180)
            {
                sum+=5000;
                sum+=lives*1000;
                GameFinished=true;
                
            }
            if(CheckCollison(140))
            {
                sum+=200;
                playPickMusic();
                
            }
             
            if(CheckCollison(4))
            {
                randomItem();
                playPickMusic();
                
            }
            
            if(CheckCollison(411))
            {
                MakeInvincible();
                playPickMusic();

            }
           
              if(CheckCollison(410))

            {
               MakeSuperFast();
               playPickMusic();
  
 
 
            }
             if(CheckCollison(94))
             {
            sum+=20;
            playPickMusic();
            
             }
              if (CheckCollison(34))
             {
            sum+=100;
            playPickMusic();
            
             }
             if(CheckCollison(64))
              {
                  sum+=50;
                  playPickMusic();
                  
              }
   
       }

     
        
    }

    /** Returns the column of the cell that the bird is standing on. */
    public int getColCell(){
      double col=(bird.getX()+bird.getWidth()/2-collect.getX())/collect.getCellWidth();
      int temp=(int)col;
      if(col>temp)
          return temp+1;
      else
          return temp;
    }
/** Returns the row of the cell that the bird is standing on. */
    public int getRowCell(){
      double row=(bird.getY()+bird.getHeight()/2-collect.getY())/collect.getCellHeight();
            int temp=(int)row;
      if(row>temp)
          return temp+1;
      else
          return temp;
    }
       /** Returns the column of the cell that the bird is standing on. */
    public int getColCellWall(){
      double col=(bird.getX()+bird.getWidth()/2-wall.getX())/wall.getCellWidth();
      int temp=(int)col;
      if(col>temp)
          return temp+1;
      else
          return temp;
    }
/** Returns the row of the cell that the bird is standing on. */
    public int getRowCellWall(){
      double row=(bird.getY()+bird.getHeight()/2-wall.getY())/wall.getCellHeight();
            int temp=(int)row;
      if(row>temp)
          return temp+1;
      else
          return temp;
    }
/** Returns the amount of lives the player currently has, if the player has more than
     3 lives, it returns 3, and the drawAll method in the game canvas returns a string
     containing the amount of hearts. For example, for 5 hearts, 3 hearts will show, 
     and next to them "+2" */
    public int getLives() {
        if(lives<=3)
        {
            livesOver3=0;
        return lives;
        }
        else{
            
        livesOver3=lives-3;
            
               
            return 3;
        }
    }

/** Returns the current score */
        public  int getSum() {
        return sum;
    }
        /** Checks if the bird is out of the frame's boundaries and if so, moves the bird back
         * and prevents it from going out of the frame's boundaries. 
         */
        public void birdOutOfFrame(){
            if (bird.getX() <=bg.getX())
                bird.moveBack();
            if (bird.getX() >= bg.getX() + bg.getWidth()-bird.getWidth() )
                bird.moveBack();
            if (bird.getY() <= bg.getY())
                bird.move(0,Runner.XY_SPEED);
            if (bird.getY() >= bg.getY() + bg.getHeight() - bird.getHeight())
                bird.move(0, -Runner.XY_SPEED);
        }
       






/** Returns true if the player is invincible, or false if he's not */
public static boolean isInvincible()
{
    return invincible;
}
/** Returns true if the player is Super fast, or false if he's not */
public static boolean isFast()
{
    return fast;
}

public static class Clock extends TimerTask  {

  int timeLeft;
  
/**Gets the time left for the method using the clock (invincibility timer).*/
  public Clock(int maxTime) {
    timeLeft = maxTime;
  }

        
/** Deducts time if the player is invincible*/
  public void run() {
      if(Controller.isInvincible())
	timeLeft--;
    
        
        
  }
 
/** Returns the time left until the tasks stops and adds 15 seconds if it's the second use of the timer task*/
  public int getTimeLeft() {
        if(InvincibleSum>1)
      return this.timeLeft+15;
      else
          return this.timeLeft;

      
      }

    }
public static class Clock2 extends TimerTask  {

  int timeLeft2;
  
/**Gets the time left for the method using the clock (Super Fast timer).*/
  public Clock2(int maxTime) {
    timeLeft2 = maxTime;
  }

        
/** Deducts time if the player is Super fast*/
  public void run() {
      if(Controller.isFast())
	timeLeft2--;
      
        
        
  }
 
/** Returns the time left until the tasks stops and adds 15 seconds if it's the second use of the timer task*/
  public int getTimeLeft() {
      if(SuperfastSum>1)
      return this.timeLeft2+15;
      else
          return this.timeLeft2;

    }
}
/** Gives a random special prize or penalty when the player
 picks up the question mark item.
 */
public void randomItem()
{
  
   Random rnd=new Random();
   int col=getColCell();
int row=getRowCell();
int i;
   switch(rnd.nextInt(10))
   {
       case 0:i=rnd.nextInt(10);
       if(i==3)
           lives-=3;
           break;
       case 1: lives+=3;
           break;
       case 2: lives++;
           break;

  
       case 3: sum+=1000;
           break;
       case 4: sum+=30;
           break;
       case 5: sum+=50;
           break;
       case 6: Runner.ChangeColor(0);
           break;
       case 7:sum+=1500;
           break;
           
       default: sum+=100;
           break;
   }
   
    
}
/** Returns the number of times the player was invincible*/
public int getInvSum()
{
    return InvincibleSum;
}
/** Returns the number of times the player was Super Fast*/
public int getFastSum()
{
    return SuperfastSum;
}
/** Returns the Timer Task t1 which Stops the player from being SuperFast*/
public TimerTask getT1()
{
    return t1;
}
/** Returns the Timer Task t2 which Stops the player from being Invincible*/
public TimerTask getT2()
{
    return t2;
}
/** Changes the project's background tiles according to the given variable "c"  **/
public final void ChangeTerrain(int c) 
{
   for(int i=0;i<bg.getColumns();i++)
     for(int j=0;j<bg.getRows();j++)
         bg.setCell(i, j, c);
}
 /** Plays music when an item is picked.
 * 
 */
  public void playPickMusic()
{
  try
  {
    InputStream is = getClass().getResourceAsStream("/Music/bloop_x.wav");
    player = Manager.createPlayer(is,"audio/x-wav");

    player.realize();
    // get volume control for player and set volume to max
    vc = (VolumeControl) player.getControl("VolumeControl");
    if(vc != null)
    {
      vc.setLevel(100);
    }
    player.prefetch();
    player.start();
  }
  catch(Exception e)
  {}
}
   public void playAbilityMusic()
{
  try
  {
    InputStream is = getClass().getResourceAsStream("/Music/laser_x.wav");
    player2 = Manager.createPlayer(is,"audio/x-wav");

    player2.realize();
    // get volume control for player and set volume to max
    vc2 = (VolumeControl) player.getControl("VolumeControl");
    if(vc2 != null)
    {
      vc2.setLevel(100);
    }
    player2.prefetch();
    player2.start();
  }
  catch(Exception e)
  {}
}
  /** Makes the player invincible, and starts the timer that shows how much time is left until
  the invincibility will wear off.*/
  public void MakeInvincible()
  {
      invincible=true;
                
               
              
                 this.t2= new TimerTask(){
    public void run()
    {
       invincible=false;
       playAbilityMusic();
      
    InvincibleSum++;   
     
    }
}; 
                 
                new Timer().schedule(t2, 10000);
               
  }
    /** Makes the player super fast, and starts the timer that shows how much time is left until
  the super fast ability will wear off.*/
  public void MakeSuperFast()
  {
              fast=true;
          Runner.XY_SPEED+=7;


 
  this.t1= new TimerTask(){
     
    public void run()
    {
         fast=false;
         if(Runner.XY_SPEED>10)
         {
       Runner.XY_SPEED-=7;
       playAbilityMusic();
         }
        SuperfastSum++;  
      
    }
    
};
  
 

 new Timer().schedule(t1, 15000);
  }
  
  public void stopMusic()
  {
      player.close();
      player2.close();
  }
     
       


     
       }
      
      
      
     

        



