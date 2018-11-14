package tina.com.database.sub_db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import tina.com.database.db.BaseDao;
import tina.com.database.db.BaseDaoFactory;

/**
 * @author yxc
 * @date 2018/11/14
 */
public class BaseDaoSubFactory extends BaseDaoFactory {

    private static final BaseDaoSubFactory instance = new BaseDaoSubFactory();

    public static BaseDaoSubFactory getInstance() {
        return instance;
    }

    private SQLiteDatabase subSqliteDatabase;

    protected BaseDaoSubFactory() {
    }

    public <T extends BaseDao<M>, M> T getSubDao(Class<T> daoClass, Class<M> entityClass) {

        BaseDao baseDao = null;
        if(map.get(PrivateDataBaseEnums.database.getValue())!=null){
            return (T) map.get(PrivateDataBaseEnums.database.getValue());
        }
        Log.i("jett", "生成数据库文件的位置:" + PrivateDataBaseEnums.database.getValue());

        subSqliteDatabase = SQLiteDatabase.openOrCreateDatabase(PrivateDataBaseEnums.database.getValue(), null);
        try {
            baseDao = daoClass.newInstance();
            baseDao.init(subSqliteDatabase, entityClass);
            map.put(PrivateDataBaseEnums.database.getValue(),baseDao);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T) baseDao;
    }
}
