/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author guy
 */
public class Instructions extends Canvas implements CommandListener {
    private GameMidlet midlet;
    private final Image title;
    private GameDesign gd;
    public Instructions(GameMidlet midlet) throws IOException
    {
        super();
        setFullScreenMode(true);
      gd= new GameDesign();
            title=gd.getTitle3();
      
        this.midlet = midlet;
        
        Command c = new Command("Back", Command.BACK, 0);
        addCommand(c);
        setCommandListener(this);
    }

    protected void paint(Graphics g) {
       g.setColor(0);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(title, 20, 0, Graphics.TOP | Graphics.LEFT);
        g.setColor(0xFFFFFF);
        g.setFont(Font.getDefaultFont());
        g.drawString("You can move the duck using the arrow keys.", 5, 80, Graphics.LEFT|Graphics.TOP);
        g.drawString("When you see a red obstacle, press the", 5, 100, Graphics.LEFT|Graphics.TOP);
        g.drawString("fire key when you're nearby, to jump over it. ", 5, 120, Graphics.LEFT|Graphics.TOP);
        g.drawString("Be careful from the walls, hitting them ", 5, 140, Graphics.LEFT|Graphics.TOP);
        g.drawString("will decrease your life by 1. Also be", 5, 160, Graphics.LEFT|Graphics.TOP);
        g.drawString("careful from the big blue bird", 5, 180, Graphics.LEFT|Graphics.TOP);
        g.drawString("It can hurt you as well. Pick diamonds,", 5, 200, Graphics.LEFT|Graphics.TOP);
        g.drawString("they will increase your score.", 5, 220, Graphics.LEFT|Graphics.TOP);
        g.drawString("Picking a coffee sign will increase ", 5, 240, Graphics.LEFT|Graphics.TOP);
        g.drawString("your speed. Picking a sword sign will", 5, 260, Graphics.LEFT|Graphics.TOP);
        g.drawString("make you invincible.", 5, 280, Graphics.LEFT|Graphics.TOP);
        g.drawString("Back", 0, getHeight(), Graphics.BOTTOM | Graphics.LEFT);
    }

    public void commandAction(Command c, Displayable d) {
        midlet.getDisplay().setCurrent(midlet.getcMenu());
    }
    
}
