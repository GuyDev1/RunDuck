/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author guy
 */
public class EndGameMenu extends Canvas {
     private GameMidlet midlet;
   
    
    private Controller controller;

    private GameDesign gd;
    private Image GameOver;
    
    /** Set's all the buttons and their names
     * 
     * @param m the game's midlet.
     */
    public EndGameMenu(GameMidlet m)
    {
        super();
        setFullScreenMode(true);
        gd=new GameDesign();
       midlet=m;
       
        
        try {
            GameOver=gd.getGameOverPic();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /** Draw's the buttons, titles, etc.
     * 
     * @param g the game's graphics.
     */

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
               midlet.CloseMusic();
               midlet.getDisplay().setCurrent(midlet.getcMenu());
               Controller.GameFinished=false;
                 
            }

        }
        repaint();
    }
     
    protected void paint(Graphics g) {
         g.fillRect(0, 0, getWidth(), getHeight()); 
         g.drawImage(GameOver, 40, 0, Graphics.TOP | Graphics.LEFT);
         
            g.setColor(255, 255, 255);
            g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
    
            g.drawString("Elapsed Time:   "+  MyGameCanvas.getEstimatedTime(MyGameCanvas.estimatedTime), 60, 110, Graphics.TOP | Graphics.LEFT);
            g.drawString("Score:   "+MyGameCanvas.getScore(), 60, 130, Graphics.TOP | Graphics.LEFT);
            if(Controller.GameFinished)
            {
                g.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_ITALIC, Font.SIZE_LARGE));
            g.drawString("The duck has returned ", 25, 160, Graphics.TOP | Graphics.LEFT);
            g.drawString("to the sea!", 65, 180, Graphics.TOP | Graphics.LEFT);
            g.drawString("Well Done!", 70, 230, Graphics.TOP | Graphics.LEFT);
            }
            g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
             g.drawString("Click Enter To Go Back To Menu",getWidth()/2, getHeight()-5, Graphics.HCENTER | Graphics.BOTTOM);

        
    }
    
}
