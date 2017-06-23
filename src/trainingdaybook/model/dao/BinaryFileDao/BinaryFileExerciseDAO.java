
package trainingdaybook.model.dao.BinaryFileDao;

import java.util.ArrayList;
import java.util.List;
import trainingdaybook.model.Exercise;
import trainingdaybook.model.Set;
import trainingdaybook.model.dao.ExerciseDao;

/**
 *
 * @author YARUS
 */
public class BinaryFileExerciseDAO implements ExerciseDao{

    private static  final String FILE_FOR_SAVE = "exercises";    
    private static final Loader<Exercise> loader = new Loader<>();
    private static List<Exercise> exercises = new ArrayList<>();
    
    public BinaryFileExerciseDAO(){
        load();            
    }
    
    private void load(){     
        List<Exercise> trainingsTemp = loader.loadFromBinaryFile(FILE_FOR_SAVE);        
        if(trainingsTemp != null) exercises = trainingsTemp;        
    }
    
    private void save(){        
        loader.saveToBinaryFile(exercises, FILE_FOR_SAVE);                
    }     
    
    @Override
    public Exercise findBySetId(long id) {
        if(id <= 0){
            return null;
        }
        load();
        BinaryFileSetDAO setDAO = new BinaryFileSetDAO();
        List<Set> sets = setDAO.findAll();
        for(Set set : sets){
            if(id == (long)set.getId()){
                return set.getExercise();
            }
        }
        return null;
    }

    @Override
    public List<Exercise> findByTitle(String title) {
        if(title == null){
            return null;
        }
        load();
        List<Exercise> exercisesRes = new ArrayList<>();
        for(Exercise exercise : exercises){
            if(title.equals(exercise.getTitle())){
                exercisesRes.add(exercise);                
            }
        }
        return exercisesRes; 
    }

    @Override
    public List<Exercise> findAll() {
        load();
        return (List<Exercise>) ((ArrayList<Exercise>)exercises).clone();
    }

    @Override
    public Exercise find(long id) {
        load();
        for(Exercise exercise : exercises) {
            if (id == (long)exercise.getId()) {                
                return exercise;
            }
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        for(Exercise exercise : exercises) {
            if (id == (long)exercise.getId()) {                
                exercises.remove(exercise);
                save();
                return true;
            }
        }
        return false;
    }

    @Override
    public Exercise create(Exercise exercise) {
        if(exercise == null){
            return null;   
        }
        if(exercises.isEmpty())
            exercise.setId(1);
        else 
            exercise.setId(exercises.get(exercises.size()-1).getId()+1);
        exercises.add(exercise);
        save();
        return exercise;
    }

    @Override
    public Exercise update(Exercise exercise) {
        if(exercise == null || exercise.getId() == null){
            return null;   
        }
        Exercise newExercise;                
        for(Exercise oldExercise : exercises) {
            if ((long)exercise.getId() == (long)oldExercise.getId()) {
                newExercise = oldExercise;
                newExercise.setTitle(exercise.getTitle());                        
                newExercise.setTraining(exercise.getTraining());
                save();
                return newExercise;
            }
        }
        return null;
    }

    @Override
    public List<Exercise> findAllByTrainingId(long id) {
        if(id <= 0 || exercises == null){
            return null;
        }
        load();
        List<Exercise> exercisesRes = new ArrayList<>();        
        for(Exercise exercise: exercises){
            if((exercise != null && exercise.getTraining() != null) && 
                    exercise.getTraining().getId() != null && exercise.getTraining().getId() == id){
                exercisesRes.add(exercise);
            }
        }        
        return exercisesRes;
    }    
}
