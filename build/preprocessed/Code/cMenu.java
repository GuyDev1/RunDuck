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

/** Handles the menu and the buttons, and performs actions according to the buttons the user pressed.
 *
 * @author guy
 */
public class cMenu extends Canvas {
    
    private GameMidlet midlet;
    private Image bgImage,title,pauseGame;
    private Button newGame,changeTerrain,Exit,Menu,changeColor,Top10,Resume,Instructions;
    private GameDesign gd;
    public static boolean gameStop,gameOver,chosemenu;

    /** Set's all the buttons and their names, and also sets the duck's picture.
     * 
     * @param m the game's midlet.
     */
    public cMenu(GameMidlet m)
    {
        super();
        setFullScreenMode(true);
        gd=new GameDesign();
        chosemenu=false;
       midlet=m;
        try {
            bgImage=gd.getBarvazImage();
            pauseGame=gd.getGamePausedPic();
            title=Image.createImage("Pictures/Title2.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        newGame=new Button("New Game",60,140,100,20,0);
        changeTerrain= new Button("Change Terrain",60,170,100,20,1);
        Exit= new Button("Exit",60,290,100,20,5);
        Menu= new Button("Return to menu",65,130,100,20,0);
         changeColor= new Button("Change Color",60,200,100,20,2);
         Top10= new Button("Top 10",60,230,100,20,3);
         Resume=  new Button("Resume",65,160,100,20,1);
         Instructions= new Button("Instructions",60,260,100,20,4);
         Button.selectedButton=0;
        
    }
    /** Paints all the buttons and the pictures.
     * 
     * @param g the game's graphics.
     */
    protected void paint(Graphics g) {
       
        if(gameStop)
        {
             g.setColor(0);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(pauseGame, 20, 0, Graphics.TOP | Graphics.LEFT);
        Menu.PaintButton(g);
        Resume.PaintButton(g);
        }

       
        else
        {
        g.setColor(0);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(bgImage, 30, -20,Graphics.TOP | Graphics.LEFT);
        Instructions.PaintButton(g);
        newGame.PaintButton(g);
        changeTerrain.PaintButton(g);
        changeColor.PaintButton(g);
        Top10.PaintButton(g);
        Exit.PaintButton(g);
        
        }
    }
    /** Manages the actions made by the user, and performs actions according to the buttons pressed.
     * 
     * @param keyCode the code representing the key.
     */
       protected void keyPressed(int keyCode)
    {
        if (keyCode < 0)
        {
            if (getGameAction(keyCode) == Canvas.DOWN)
            {
                if (Button.selectedButton != 5)
                {
                   Button.selectedButton++;
                }
                else
                {
                    Button.selectedButton = 0;
                }
            }
            else if (getGameAction(keyCode) == Canvas.UP)
            {
                if (Button.selectedButton!= 0)
                {
                    Button.selectedButton--;
                }
                else
                {
                    Button.selectedButton = 5;
                }
            }
            else if (getGameAction(keyCode) == Canvas.FIRE)
            {
                if (Button.selectedButton == 0)
                {
                    
                
                 if(gameStop)
                 {
                     gameStop=false;  
                     midlet.CloseMusic();
                     midlet.getDisplay().setCurrent(midlet.getEndMenu());
                 }
                      
                else
                {
                    
                   
                        midlet.NewGame();
                        midlet.GameStopped=false;
                
                    
                }
                }
                else if (Button.selectedButton == 2)
                {
                    
                    midlet.getDisplay().setCurrent(midlet.getColorMenu());
                }
                else if(Button.selectedButton==4)
                {
                    try {
                        midlet.getDisplay().setCurrent(midlet.getInstructions());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else if(Button.selectedButton==5)
                {
                   midlet.destroyApp(true);
                    midlet.notifyDestroyed(); 
                }
                else if(Button.selectedButton==1)
                {
                    if(gameStop)
                    {
                       midlet.GameStopped=false;
                       midlet.PlayMusic();
                       midlet.getDisplay().setCurrent(midlet.getCanvas());
                    }
                    else
                    {
                        midlet.TerrainChanged=true;
                     midlet.getDisplay().setCurrent(midlet.getColorMenu());   
                    }
                        
                }
                else if(Button.selectedButton==3)
                {
                    midlet.getDisplay().setCurrent(midlet.getTopScores());
                }
           
                 
            }

        }

        repaint();
    }
   
    

    
}
