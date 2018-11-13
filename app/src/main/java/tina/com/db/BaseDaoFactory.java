package tina.com.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * @author yxc
 * @date 2018/11/13
 */
public class BaseDaoFactory {

    private static final BaseDaoFactory instance = new BaseDaoFactory();

    public static BaseDaoFactory getInstance() {
        return instance;
    }

    private SQLiteDatabase sqLiteDatabase;

    //定义建数据库的路径
    //写到SD卡中， 删除了数据还在
    private String sqliteDatabasePath;

    private BaseDaoFactory() {
        if (Environment.isExternalStorageEmulated()) {
            //可以先判断有没有SD卡
            sqliteDatabasePath = Environment.getExternalStorageDirectory() + "/tinadatabase/tina.db";
        }else{
            //可以先判断有没有SD卡

        }
        sqliteDatabasePath="data/data/tina.com.database/tina.db";
//        sqliteDatabasePath = Environment.getDataDirectory() + "tina.db";
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqliteDatabasePath, null);
    }

    public <T> BaseDao<T> getBaseDao(Class<T> entityClass) {
        BaseDao baseDao = null;
        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.init(sqLiteDatabase, entityClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return baseDao;
    }

}
