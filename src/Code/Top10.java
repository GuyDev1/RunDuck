/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

/** Handles the high scores.
 *
 * @author guy
 */
public class Top10 {
    
    private PlayerData  [] players;
    private int counter;
    
/**
     * Creates a new player array, that will be used to manage the high scores.
     */
public Top10(){
         this.players = new PlayerData[10];
         this.counter = 0;
         addPlayer (new PlayerData("Guy",1000,10000));
         addPlayer(new PlayerData("tomer",180,9000));
         addPlayer(new PlayerData("aviv",160,8000));
         addPlayer(new PlayerData("alon",140,7000));
         addPlayer(new PlayerData("yotam",120,6000));
         addPlayer (new PlayerData("Amigo",100,5000));
         addPlayer (new PlayerData("David",80,4000));
         addPlayer (new PlayerData("Rom",60,3000));
         addPlayer (new PlayerData("Avi",40,2000));
         addPlayer (new PlayerData("Senior",20,1000));

     

     }

/** Adds a player to the high score table, and positions him in the appropriate place
 * according to his score and elapsed game time.
 * @param p the player that is added to the high score array.
 */
public final void addPlayer(PlayerData p){
    int i=0;
    boolean b= true;
    while(i< counter && b){
        if (players[i].getScore()< p.getScore())
            b=false;
        if (players[i].getScore()== p.getScore())
            if (players[i].getElapsedTime() > p.getElapsedTime())
                b=false;
        if (b)
                i++;
    }
  
    for (int j = counter>=9? 9:counter; j > i; j--){
        players[j] = players[j-1];
    }
    if(i==10)
        players[i-1]=p;
    else
    players[i] = p;
    if (counter <10)
        counter++;

}

/**
 * @param p The player.
 * @return  true if the player has one of the top 10 scores, false if he doesn't.
 */
public boolean isHighScore(PlayerData p){
    int i=0;
    boolean b= false;
    
    if(counter<10)
        return true;
    
    while(i< counter && !b){
        if (players[i].getScore()< p.getScore())
            b=true;
        if (players[i].getScore()== p.getScore())
            if (players[i].getElapsedTime() < p.getElapsedTime())
                b=true;
        if (!b)
                i++;
    }
    
    return b;
}

/** Creates the high scores array by opening the Record Store at the given database
 *and sets the players with the saved scores in bytes
 * @param db the database.
 */
public Top10 (ScoreData db){
    this();
    byte[][] b= db.openDB();
    if (b!=null){
    for (int i=0; i < b.length; i++){
        players[i] = new PlayerData(b[i]);
        counter++;
    }

}
}

/**
 * @return the String that contains the high score information.
 */
public String toString(){
    String s ="Nickname       Score      Time"+"\n\n";
    for (int i=0; i < counter; i++){
        s+= players[i].toString() + "\n";
    }
    return s;
}

/**
 * @return the array of strings containing the high score information.
 */
public String[] allTopTens(){
    if (counter == 0)
        return null;
    String[] st = new String[counter];
    for (int i=0; i < counter; i++){
        st[i] =players[i].playerFormat();
    }
    return st;
}


}
    

