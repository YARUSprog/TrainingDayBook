
package trainingdaybook.model.dao.H2Dao;

import trainingdaybook.model.dao.DaoFactory;
import trainingdaybook.model.dao.ExerciseDao;
import trainingdaybook.model.dao.SetDao;
import trainingdaybook.model.dao.TrainingDao;

/**
 *
 * @author YARUS
 */
public class H2DaoFactory extends DaoFactory{

    @Override
    public TrainingDao getTrainingDao() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ExerciseDao getExerciseDao() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SetDao getSetDao() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
