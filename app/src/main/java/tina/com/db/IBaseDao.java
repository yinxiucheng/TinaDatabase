package tina.com.db;

import java.util.List;

/**
 * @author yxc
 * @date 2018/11/13
 */
public interface IBaseDao<T> {

    long insert(T entity);

    long update(T entity, T where);

    int delete(T where);

    List<T> query(T where);
}
