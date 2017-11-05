/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import javax.microedition.lcdui.Graphics;

/** Handles the button properties and index, in order to use them in the menu.
 *
 * @author guy
 */
public class Button{
        private int height;
        private int width;
        private int buttonIndex;
        private int x;
        private int y;
        private String title;
    public static int selectedButton=0;
        
    /** The buttons constructor that gets the button's location, size, title and index.
     * 
     * @param t title
     * @param x location
     * @param y location
     * @param h height
     * @param w width
     * @param index 
     */
        public Button(String t, int x,int y,int h,int w,int index)
        {
           this.height=h;
           this.width=w;
           this.buttonIndex=index;
           this.x=x;
           this.y=y;
           this.title=t;
        }
        /** Draws the button, and changes the button's color if his index is choosed.
         * 
         * @param g the game's graphics.
         */
        public void PaintButton(Graphics g)
        {
            if(buttonIndex==selectedButton)
            {
                g.setColor(0,0,255);
                g.fillRect(x, y, height, width);
                g.setColor(255, 255, 255);
                g.drawString(title, x+height/2, y, Graphics.TOP | Graphics.HCENTER);
            }
            else
            {
              g.setColor(255,255,0);
                g.fillRect(x, y, height, width);
                g.setColor(0);
                g.drawString(title, x+height/2, y, Graphics.TOP | Graphics.HCENTER);  
            }
        }
    }