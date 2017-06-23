
package trainingdaybook.model.dao;

import java.util.List;
import trainingdaybook.model.Exercise;

/**
 *
 * @author YARUS
 */
public interface ExerciseDao extends AbstractDAO<Exercise>{
    public Exercise findBySetId(long id);    
    public List<Exercise> findByTitle(String name);
    public List<Exercise> findAllByTrainingId(long id);
}
