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

/** The color menu, allows the player to choose the duck's color,
 * by handling the buttons and the user's actions and acting accordingly.
 *
 * @author guy
 */
public class ColorMenu extends Canvas {
    
    private GameMidlet midlet;
   
    private Button grey,blue,green,yellow,purple,red,Sand,Sea;
    private Controller controller;
    private Image changeTerrain,changeColor;
    private GameDesign gd;
    /** Set's all the buttons and their names
     * 
     * @param m the game's midlet.
     */
    public ColorMenu(GameMidlet m)
    {
        super();
        setFullScreenMode(true);
       midlet=m;
       gd= new GameDesign();
        try {
            changeTerrain=gd.getChangeTerrainPic();
            changeColor=gd.getChooseColorPic();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        grey=new Button("Grey",60,130,100,20,0);
        blue=new Button("Blue",60,160,100,20,1);
        green=new Button("Green",60,190,100,20,2);
        yellow=new Button("Yellow",60,220,100,20,3);
        purple=new Button("Purple",60,250,100,20,4);
        red=new Button("Red-Default",60,280,100,20,5);
        Sand=new Button("Sand",70,150,100,20,0);
        Sea=new Button("Sea",70,180,100,20,1);
          Button.selectedButton=0;
        
    }
    /** Draw's the buttons, titles, etc.
     * 
     * @param g the game's graphics.
     */
    protected void paint(Graphics g) {
       
        if(midlet.TerrainChanged)
        {
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(changeTerrain, 10, 0, Graphics.TOP | Graphics.LEFT);
            Sand.PaintButton(g);
            Sea.PaintButton(g);
            g.setColor(255, 255, 255);
            g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
          
        }
        else
        {
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(changeColor, 35, 0, Graphics.TOP | Graphics.LEFT);
            grey.PaintButton(g);
            blue.PaintButton(g);
            green.PaintButton(g);
            yellow.PaintButton(g);
            purple.PaintButton(g);
            red.PaintButton(g);
            g.setColor(255, 255, 255);
            g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
            
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
                if (Button.selectedButton != 0)
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
                    if(midlet.TerrainChanged)
                    {
                      Controller.ChangeTerrain=true;
                        Controller.TerrainType=0;
                        midlet.TerrainChanged=false;
                   midlet.getDisplay().setCurrent(midlet.getcMenu());  
                    }
                    else
                    {
                   Runner.ChangeColor(0);
                   midlet.getDisplay().setCurrent(midlet.getcMenu());
                    }
                }
                else if (Button.selectedButton == 1)
                {
                    if(midlet.TerrainChanged)
                    {
                      Controller.ChangeTerrain=true;
                        Controller.TerrainType=1;
                        midlet.TerrainChanged=false;
                   midlet.getDisplay().setCurrent(midlet.getcMenu());  
                    }
                    else
                    {
                     Runner.ChangeColor(1);
                   midlet.getDisplay().setCurrent(midlet.getcMenu());
                    }
                }
                else if(Button.selectedButton==2)
                {
                        Runner.ChangeColor(2);
                   midlet.getDisplay().setCurrent(midlet.getcMenu());
                }
                else if(Button.selectedButton==3)
                {
                        Runner.ChangeColor(3);
                        Controller.ChangeTerrain=true;
                        Controller.TerrainType=3;
                   midlet.getDisplay().setCurrent(midlet.getcMenu());
                }
                else if(Button.selectedButton==4)
                {
                        Runner.ChangeColor(4);
                   midlet.getDisplay().setCurrent(midlet.getcMenu());
                }
                else if(Button.selectedButton==5)
                {
                  Runner.ChangeColor(5);
                   midlet.getDisplay().setCurrent(midlet.getcMenu());
                   Controller.ChangeTerrain=true;
                        Controller.TerrainType=5;
                }
           
                 
            }

        }

        repaint();
    }
   
    
}
    

