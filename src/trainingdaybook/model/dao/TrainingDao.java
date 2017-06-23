
package trainingdaybook.model.dao;

import java.util.GregorianCalendar;
import java.util.List;
import trainingdaybook.model.Training;

/**
 *
 * @author YARUS
 */
public interface TrainingDao extends AbstractDAO<Training>{
    public Training findByExerciseId(long id);    
    public List<Training> findByTitle(String name);
    public List<Training> findByDate(GregorianCalendar date);
}
