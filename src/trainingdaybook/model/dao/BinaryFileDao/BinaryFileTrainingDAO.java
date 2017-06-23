
package trainingdaybook.model.dao.BinaryFileDao;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import trainingdaybook.model.Exercise;
import trainingdaybook.model.Training;
import trainingdaybook.model.dao.TrainingDao;

/**
 *
 * @author YARUS
 */
public class BinaryFileTrainingDAO implements TrainingDao{

    private static  final String FILE_FOR_SAVE = "trainings";    
    private static final Loader<Training> loader = new Loader<>();
    private static List<Training> trainings = new ArrayList<>();
    
    public BinaryFileTrainingDAO(){
        load();            
    }
            
    private void load(){     
        List<Training> trainingsTemp = loader.loadFromBinaryFile(FILE_FOR_SAVE);        
        if(trainingsTemp != null) trainings = trainingsTemp;
        
    }
    
    private void save(){        
        loader.saveToBinaryFile(trainings, FILE_FOR_SAVE);                
    }       
    
    @Override
    public Training findByExerciseId(long id) {
        if(id <= 0){
            return null;
        }
        load();
        BinaryFileExerciseDAO exerciseDAO = new BinaryFileExerciseDAO();
        List<Exercise> exercises = exerciseDAO.findAll();
        for(Exercise exercise : exercises){
            if(id == (long)exercise.getId()){
                return exercise.getTraining();
            }
        }
        return null;
    }

    @Override
    public List<Training> findByTitle(String title) {
        if(title == null){
            return null;
        }
        load();
        List<Training> trainingsRes = new ArrayList<>();
        for(Training training : trainings){
            if(title.equals(training.getTitle())){
                trainingsRes.add(training);                
            }
        }
        return trainingsRes;        
    }

    @Override
    public List<Training> findByDate(GregorianCalendar date) {
        if(date == null){
            return null;
        }
        load();
        List<Training> trainingsRes = new ArrayList<>();
        for(Training training : trainings){
            if(date.equals(training.getDate())){
                trainingsRes.add(training);                
            }
        }
        return trainingsRes;
    }

    @Override
    public List<Training> findAll() {
        load();
        return (List<Training>) ((ArrayList<Training>)trainings).clone();
    }

    @Override
    public Training find(long id) {
        load();
        for(Training training : trainings) {
            if (id == (long)training.getId()) {                
                return training;
            }
        }
        return null;    
    }

    @Override
    public boolean delete(long id) {
        
        for(Training training : trainings) {
            if (id == (long)training.getId()) {                
                trainings.remove(training);
                save();
                return true;
            }
        }
        return false;
    }

    @Override
    public Training create(Training training) {
        if(training == null){
            return null;   
        }   
        if(trainings.isEmpty())
            training.setId(1L);
        else 
            training.setId(trainings.get(trainings.size()-1).getId()+1);             
        trainings.add(training);
        save();
        return training;
    }

    @Override
    public Training update(Training training) {
        if(training == null || training.getId() == null){
            return null;   
        }
        Training newTraining;                
        for(Training oldTraining : trainings) {
            if ((long)training.getId() == (long)oldTraining.getId()) {
                newTraining = oldTraining;
                newTraining.setTitle(training.getTitle());        
                newTraining.setDate(training.getDate());  
                save();
                return newTraining;
            }
        }
        return null;
    }    
}
