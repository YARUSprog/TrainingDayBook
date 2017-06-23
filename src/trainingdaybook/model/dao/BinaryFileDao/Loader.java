
package trainingdaybook.model.dao.BinaryFileDao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author YARUS
 */
public class Loader<T> {
    
    public void saveToBinaryFile(List<T> object, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.flush();            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(oos != null)
                    oos.close();
                if(fos != null)
                    fos.close();
            } catch (IOException ex) {
                Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public List<T> loadFromBinaryFile(String file) {
        FileInputStream fis = null;
        List<T> object = null;
        ObjectInputStream oin = null;
        try {
            fis = new FileInputStream(file);
            oin = new ObjectInputStream(fis);
            object = (List<T>) oin.readObject();
            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(oin != null)
                    oin.close();
                if(fis != null)
                    fis.close();
            } catch (IOException ex) {
                Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return object;
    }
}
