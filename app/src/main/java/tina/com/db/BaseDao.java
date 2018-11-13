package tina.com.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * @author yxc
 * @date 2018/11/13
 */
public class BaseDao<T> implements IBaseDao<T> {

    //持有数据库操作的引用
    private SQLiteDatabase sqLiteDatabase;
    //表名
    private String tableName;



    @Override
    public long insert(T entity) {
        return 0;
    }

    @Override
    public long update(T entity, T where) {
        return 0;
    }

    @Override
    public long delete(T entity) {
        return 0;
    }

    @Override
    public long query() {
        return 0;
    }
    
}
