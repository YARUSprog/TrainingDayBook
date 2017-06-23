
package trainingdaybook.model.dao.BinaryFileDao;

import java.util.ArrayList;
import java.util.List;
import trainingdaybook.model.Exercise;
import trainingdaybook.model.Set;
import trainingdaybook.model.dao.SetDao;

/**
 *
 * @author YARUS
 */
public class BinaryFileSetDAO implements SetDao{

    private static  final String FILE_FOR_SAVE = "sets";    
    private static final Loader<Set> loader = new Loader<>();
    private static List<Set> sets = new ArrayList<>();
    
    public BinaryFileSetDAO(){
        load();            
    }
    
    private void load(){     
        List<Set> setsTemp = loader.loadFromBinaryFile(FILE_FOR_SAVE);        
        if(setsTemp != null) sets = setsTemp;
        
    }
    
    private void save(){        
        loader.saveToBinaryFile(sets, FILE_FOR_SAVE);                
    } 
    
    @Override
    public List<Set> findAll() {
        load();
        return (List<Set>) ((ArrayList<Set>)sets).clone();
    }

    @Override
    public Set find(long id) {
        load();
        for(Set set : sets) {
            if (id == (long)set.getId()) {                
                return set;
            }
        }
        return null;
    }

    @Override
    public boolean delete(long id) {        
        for(Set set : sets) {
            if (id == (long)set.getId()) {                
                sets.remove(set);
                save();
                return true;
            }
        }
        return false;
    }

    @Override
    public Set create(Set set) {
        if(set == null){
            return null;   
        }
        if(sets.isEmpty())
            set.setId(1L);
        else 
            set.setId(sets.get(sets.size()-1).getId()+1);
        sets.add(set);
        save();
        return set;
    }

    @Override
    public Set update(Set set) {
        if(set == null || set.getId() == null){
            return null;   
        }               
        for(Set oldSet : sets) {
            if ((long)set.getId() == (long)oldSet.getId()) {
                //newSet = oldSet;
                oldSet.setCount(set.getCount());
                oldSet.setExercise(set.getExercise());
                oldSet.setWheight(set.getWheight());
                oldSet.setMinutes(set.getMinutes());
                oldSet.setSeconds(set.getSeconds());  
                save();
                return oldSet;
            }
        }
        return null;
    }

    @Override
    public List<Set> findAllByExerciseId(Long id) {
        if(id == null || id <= 0 || sets == null){
            return null;
        }
        load();
        List<Set> setsRes = new ArrayList<>();
        for(Set set: sets){
            if(set != null && set.getExercise() != null && set.getExercise().getId() != null 
                    && (long)set.getExercise().getId() == (long)id){
                setsRes.add(set);
            }
        }        
        return setsRes;
    }
    
}
