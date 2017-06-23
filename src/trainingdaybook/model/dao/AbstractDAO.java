
package trainingdaybook.model.dao;

import java.util.List;

/**
 *
 * @author YARUS
 */
public interface AbstractDAO<T> {
    public abstract List<T> findAll();
    public abstract T find(long id);
    public abstract boolean delete(long id);
    public abstract T create(T entity);
    public abstract T update(T entity);
}
