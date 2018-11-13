package tina.com.db;

/**
 * @author yxc
 * @date 2018/11/13
 */
public interface IBaseDao<T> {

    long insert(T entity);

    long update(T entity, T where);

    long delete(T entity);

    long query();
}
