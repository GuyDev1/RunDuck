/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;


/** The database where all of the player's score data is being saved, 
 * or opened in order to retrieve the information stored in the recordStore.
 *
 * @author guy
 */
public class ScoreData {
private RecordStore store;
     private String nameOfStore;
     private boolean saving;
     
     /**
      * Creates a new DataBase.
      * @param nameOfStore the name of the recordStore
      * @param how defines if the record store needs to be created or not.
      */
     public ScoreData (String nameOfStore, boolean  how){
        try {
          this.store = RecordStore.openRecordStore(nameOfStore, how);
           this. nameOfStore = nameOfStore;
        } catch (RecordStoreException ex) {}
}
    
   
     
     /**Opens the record Store
      * @return the high score information that has been opened from the record store, in bytes.
      */
     public byte[][] openDB(){
           byte[][] bts = null;
           boolean bs = true;
           try {
            store = RecordStore.openRecordStore(nameOfStore, false);
           }
           catch (RecordStoreException ex){
               bs = false;
           }
           if (!bs){
               return null;
           }
           try{
            bts = new byte[store.getNumRecords()][];
            int i=0;
            RecordEnumeration en = store.enumerateRecords(null, null, true);
            while (en.hasNextElement()){

                    bts[i++] = en.nextRecord();


                }
          this.store.closeRecordStore();
           }

           catch (RecordStoreNotOpenException ex){}
           catch (RecordStoreException ex1){}
           return bts;
     }
     
     /**
      * saves all of the high score information in the recordStore and closes it.
      * @param allStrings the array of strings containing the high scores that should be saved.
      */
     public void saveAll (String[] allStrings){
        try {
           store.closeRecordStore();
            RecordStore.deleteRecordStore(nameOfStore);
            this.store = RecordStore.openRecordStore(nameOfStore, true);
            if (allStrings!=null)
            for (int i= 0; i < allStrings.length; i++){
                byte[] ch= allStrings[i].getBytes();
                this.store.addRecord(ch, 0, ch.length);
            }
            this.store.closeRecordStore();
        } catch (RecordStoreException ex) {}
     }

}
