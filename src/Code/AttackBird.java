/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.util.Random;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.midlet.MIDlet;

/** Handles the attack bird thread.
 *
 * @author guy
 */
public class AttackBird extends Sprite implements Runnable {

     private boolean isMoving=false;
     private Thread t;
     private Random rnd;
     private int speed=7;
     private int x;
    // we give them the game interface so they wouldn't
     // have access to the whole GameCanvas, only to a specific part, the window view.
     
   /** Get's the attack bird's sprite, and it's 'x' coordinates
      * in order to position 
      * @param s the attack bird's sprite.
      * @param x the x coordinates.
      */
   public AttackBird(Sprite s,int x){
           super (s);
        this.x=x;
          setPosition (x,0);
         isMoving = true;
         t=new Thread(this);
        rnd=new Random();
   }
   
   /** Starts the thread and makes the bird move*/
   public void start(){
        isMoving=true;
       t.start();
   }
   /** Runs the thread actions by making the attack bird move and change frames.
    * 
    */
   public void run()
   {
       
  
     
      while(isMoving=true){
          if(!GameMidlet.GameStopped)
          {
          

          this.setPosition(x, 0);
    
             
       while(getY()<450)
       {
            try {
                
           
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            nextFrame();
            move(speed,speed);
         
       }
          }
       
      
      }
       
   }
/** Stops the attack bird's run action
    * 
    */
    void stop() {
      this.isMoving = false;
      t=null;
    
    }
    
}
