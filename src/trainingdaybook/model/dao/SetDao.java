
package trainingdaybook.model.dao;

import java.util.List;
import trainingdaybook.model.Set;

/**
 *
 * @author YARUS
 */
public interface SetDao extends AbstractDAO<Set>{
    public List<Set> findAllByExerciseId(Long id);   
}
