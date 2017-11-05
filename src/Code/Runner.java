
package Code;


import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/** Handles the Runner's movement and properties.
 *
 * @author Student
 */
public class Runner extends Sprite  {

    /** The player's default moving speed. */
    public static int  XY_SPEED = 10; 

    private int currentDirection;
    /** The sprite's up sequence array. */
       public static int[] upSequence={0,1,2};
       /** The sprite's left sequence array. */
       public static int[] leftSequence= {36,37,38};
        /** The sprite's down sequence array. */
       public static int [] downSequence ={24,25,26};
       /** Holds a certain value that determines if the sprite's look needs to be transformed (to a mirror image, for example) */
    private int leftTrans;
      /**Holds a certain value that determines if the sprite's look needs to be transformed (to a mirror image, for example) */
    private int rightTrans;
    /** Holds a boolean value that determines if the player is moving or not. */
    public static  boolean isMoving=false;
    /** The Sprite animation timer. */
    private Timer t;
     /** Holds a boolean value that determines if the player's direction has been changed. */
    private boolean changed;
     class SpriteAnimationTask extends TimerTask{
  /** Switches the sprite's frames
          * 
          */
        public synchronized void run() {
            
           if (isMoving)
               nextFrame();
        }

    }
     /** Set's the runner properties, animation timer.
      * 
      * @param s the duck sprite.
      */
    public Runner (Sprite s){
        super (s);
          setPosition (37,407);

          defineReferencePixel(getWidth()/2, getHeight()/2);

          isMoving = false;
        leftTrans = TRANS_NONE;
        rightTrans = TRANS_MIRROR;
        currentDirection = GameCanvas.DOWN;
        this.changed = false;
        setFrameSequence(downSequence);
        t = new Timer();
        t.scheduleAtFixedRate(new SpriteAnimationTask(), 0, 150);

            

    }
    /** Sets a new direction. It also gives the object 'changed' true if the current direction is different
      from the given direction. */
    
    public void setDirection (int dir){
        changed= currentDirection !=dir;
        this.currentDirection = dir;
    }
    /** Returns the value of the current direction.*/
    public int GetCurrDirection()
    {
        return this.currentDirection;
    }
    /** Returns true if the player is currently moving, otherwise returns false.*/
    public boolean isMovingNow(){
        return isMoving;
    }
    /** Informs that the player is currently moving, and then moves the player
     according to the current direction received by th
     */
    public void move (){
     int x=0;
     int y = 0;
     isMoving = true;
     
     switch (currentDirection){


         case GameCanvas.LEFT: x = -XY_SPEED;
           if(changed){
   setFrameSequence(leftSequence);
   setTransform(TRANS_NONE);
           }

         break;
         case GameCanvas.RIGHT: x=+XY_SPEED;
           if(changed){
         setFrameSequence(leftSequence);
         setTransform(TRANS_MIRROR); 
           }
         break;
             
         case GameCanvas.UP: y=-XY_SPEED;
           if(changed){
         setFrameSequence(upSequence);
           }
         break;
         default: y=+XY_SPEED;
           if(changed){
         setFrameSequence(downSequence);
           }
         break;

     }
      
     move (x,y);
     
   
   
      
    }
    /** Turns the player to the opposite direction, and moves him in that direction.
     This method is used when colliding with a wall. When the player collides,
     with a wall, this method is used to make the player turn to the opposite direction
     (away from the wall).*/
     
    public void moveBack(){
        int x=0;
     int y = 0;
     switch (currentDirection){


         case GameCanvas.LEFT: x = +XY_SPEED;
         this.setFrameSequence(leftSequence);
         this.setTransform(rightTrans);
         currentDirection = GameCanvas.RIGHT;


         break;
         case GameCanvas.RIGHT: x=-XY_SPEED;
         this.setFrameSequence(leftSequence);
         this.setTransform(leftTrans);
         currentDirection = GameCanvas.LEFT;
         break;
         case GameCanvas.UP: y=+XY_SPEED;
          this.setFrameSequence(downSequence);
         currentDirection = GameCanvas.DOWN;

         break;
         default: y=-XY_SPEED;
          this.setFrameSequence(upSequence);
         currentDirection= GameCanvas.UP;
       
         break;
     }
     move (x,y);
    }
  /** Stops the player's movement by changing 'isMoving' to false.*/
public void stopMoving(){
    isMoving = false;
}
/** Changes the character's color according to the given index.
 The color is changed by changing the sprite's sequence arrays, with the help
 * of the method 'SetColor'. */
public static void ChangeColor(int i)
{
    switch(i)
    {
        case 0:  int[] upSequence0= {54,55,56};//grey
     int[] leftSequence0= {90,91,92};
       int [] downSequence0 ={78,79,80};
       SetColor(upSequence0, leftSequence0, downSequence0);
            break;
        case 1:  int[] upSequence1= {48,49,50};//blue
     int[] leftSequence1= {84,85,86};
       int [] downSequence1 ={72,73,74};
       SetColor(upSequence1, leftSequence1, downSequence1); 
            break;
        case 2:
             int[] upSequence2= {57,58,59};//green
     int[] leftSequence2= {93,94,95};
       int [] downSequence2 ={81,82,83};
       SetColor(upSequence2, leftSequence2, downSequence2);
            break;
        case 3:
             int[] upSequence3= {51,52,53};//yellow
     int[] leftSequence3= {87,88,89};
       int [] downSequence3 ={75,76,77};
       SetColor(upSequence3, leftSequence3, downSequence3);
            break;
        case 4:
            int[] upSequence4= {9,10,11};//purple
     int[] leftSequence4= {45,46,47};
       int [] downSequence4 ={33,34,35};
       SetColor(upSequence4, leftSequence4, downSequence4);
            break;
        case 5:
            int[] upSequence5= {0,1,2};//red-default;
     int[] leftSequence5= {36,37,38};
       int [] downSequence5 ={24,25,26};
       SetColor(upSequence5, leftSequence5, downSequence5);

    }
    
   
}
/** Uses the given parameters to change the default sequence arrays.
 This method works with the method 'ChangeColor'.*/
public static void SetColor(int[] upSeq, int[]leftSeq,int[] downSeq)
{
  upSequence=upSeq;
   leftSequence=leftSeq;
  downSequence=downSeq;
}

}
// in the change color method, when in computer's room, if it will allow it, you might try
//using a switch string, and then in the methods heading, write the color that you want.




