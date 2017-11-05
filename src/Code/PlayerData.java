/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

/** Handles the player's information.
 *
 * @author guy
 */
public class PlayerData {
    private String nickName;
    private int score;
    private long elapsedTime;
    
    /** Get's the player's name, score and game elapsed time.
     * 
     * @param nick name.
     * @param s score.
     * @param t elapsed time.
     */
    public PlayerData(String nick,int s,long t)
    {
       nickName=nick;
       score=s;
      elapsedTime=t;
    }
    /** Turns the player's info from bytes to int, or string (depending on variable).
     * 
     * @param the player's info in bytes. 
     */
       public PlayerData(byte[] playerInfo){
            
        String s = new String (playerInfo);
        int place1 = s.indexOf('#'),  place2=s.indexOf("#", place1+1);
        this.nickName = s.substring(0,place1);
        this.score = Integer.parseInt(s.substring(place1+1 , place2));
        this.elapsedTime= Integer.parseInt(s.substring(place2+1));
    }


    /**
     * @return the name
     */
    public String getNickName() {
        return nickName;
    }
    /**
     * 
     * @return the player's score. 
     */
    public int getScore(){
        return this.score;
    }
    /**
     * 
     * @return the game's elapsed time.
     */
    public long getElapsedTime()
    {
        return this.elapsedTime;
    }
    /** 
     * 
     * @return the player info in string format.
     */
    public String toString(){
        String str=nickName;
        for(int i=12;i>=nickName.length();i--){
            str+=" ";
        }
        str+= score;
        str+="     ";
        str+=MyGameCanvas.getEstimatedTime(elapsedTime);
        return str;
    }
    /** 
     * 
     * @return the format in which the information is saved.
     */
    public String playerFormat(){
        return nickName + "#" + score+"#"+MyGameCanvas.getEstimatedTime(elapsedTime);
    }

   



 /** Set's the player's score.
     * 
     * @param score the player's score.
     */
    public void setScore(int score) {
        this.score = score;
    }
    /** Set's the game's elapsed time.
     * 
     * @param elapsedtime the game's elapsed time.
     */
     public void setTime(long elapsedtime) {
        this.elapsedTime = elapsedtime;
    }
     /** Set's the player's nick name
      * 
      * @param name the player's nick name.
      */
      public void setNick(String name) {
        this.nickName = name;
    }
    
}
