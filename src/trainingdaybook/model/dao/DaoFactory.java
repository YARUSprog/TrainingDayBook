
package trainingdaybook.model.dao;

import trainingdaybook.model.dao.BinaryFileDao.BinaryFileDaoFactory;
import trainingdaybook.model.dao.H2Dao.H2DaoFactory;

/**
 *
 * @author YARUS
 */
public abstract class DaoFactory {    
    public static final int H2DaoFactory = 1;
    public static final int BinaryFileDaoFactory = 2;

    public abstract TrainingDao getTrainingDao();
    public abstract ExerciseDao getExerciseDao();
    public abstract SetDao getSetDao();

    public static DaoFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case H2DaoFactory:
                return new H2DaoFactory();
            case BinaryFileDaoFactory:
                return new BinaryFileDaoFactory();           
            default:
                return null;
        }
    }
}
