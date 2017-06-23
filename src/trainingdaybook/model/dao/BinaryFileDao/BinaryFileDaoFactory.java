
package trainingdaybook.model.dao.BinaryFileDao;

import trainingdaybook.model.dao.DaoFactory;
import trainingdaybook.model.dao.ExerciseDao;
import trainingdaybook.model.dao.SetDao;
import trainingdaybook.model.dao.TrainingDao;

/**
 *
 * @author YARUS
 */
public class BinaryFileDaoFactory extends DaoFactory{

    @Override
    public TrainingDao getTrainingDao() {
        return new BinaryFileTrainingDAO();
    }

    @Override
    public ExerciseDao getExerciseDao() {
        return new BinaryFileExerciseDAO();
    }

    @Override
    public SetDao getSetDao() {
        return new BinaryFileSetDAO();
    }
    
}
