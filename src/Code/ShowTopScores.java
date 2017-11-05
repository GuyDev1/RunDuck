/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/** Show's the top 10 scores.
 *
 * @author guy
 */
public class ShowTopScores extends Canvas {
    

    private GameMidlet midlet;
   
    
    
    public static boolean gameStop;
    private  Image title;
    private GameDesign gd;
/** Set's the table to full screen mode, and get's the midlet
     * 
     * @param m the game's midlet.
     */
public ShowTopScores(GameMidlet m)
{
    super();
        setFullScreenMode(true);
        gd=new GameDesign();
        try {
            title=gd.getTop10();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
       midlet=m;
       
}

    /** Draws the top 10 player's info: name, score and elapsed game time.
 * 
 * @param g the game's graphics.
 */
    protected void paint(Graphics g) {
          g.setColor(0xffffff);
         g.drawImage(title, 30, 0 ,Graphics.TOP | Graphics.LEFT);
        g.drawString(midlet.getStringTopTen(), getWidth()/20, getHeight()*3/10, Graphics.TOP | Graphics.LEFT);
     g.drawString("Click Enter To Go Back To Menu",getWidth()/2, getHeight()-5, Graphics.HCENTER | Graphics.BOTTOM);
    }
    /** Manages the actions made by the user, and performs actions according to the buttons pressed.
     * 
     * @param keyCode the code representing the key.
     */
       protected void keyPressed(int keyCode)
    {
        if (keyCode < 0)
        {
           
            if (getGameAction(keyCode) == Canvas.FIRE)
            {
               midlet.getDisplay().setCurrent(midlet.getcMenu());
               
                 
            }

        }

        repaint();
    }
    
    
}
