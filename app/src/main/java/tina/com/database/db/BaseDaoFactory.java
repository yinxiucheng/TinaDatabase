package tina.com.database.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    //设计一个数据库连接池
//    protected Map<String, BaseDao> map = new ConcurrentHashMap<>();
    protected Map<String,BaseDao> map= Collections.synchronizedMap(new HashMap<String, BaseDao>());
    //定义

    protected BaseDaoFactory() {
        if (!Environment.isExternalStorageEmulated()) {
            Log.e("BaseDaoFactory", "没有外置卡");
            return;
        }

        File file = new File(Environment.getExternalStorageDirectory(), "update");
        if (!file.exists()){
            file.mkdir();
        }

        sqliteDatabasePath = file.getAbsolutePath() + "/user.db";
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqliteDatabasePath, null);
    }

    public  <T extends BaseDao<M>,M> T  getBaseDao(Class<T> daoClass,Class<M> entityClass){
        BaseDao baseDao=null;
        if(map.get(daoClass.getSimpleName())!=null){
            return (T) map.get(daoClass.getSimpleName());
        }
        try {
            baseDao=daoClass.newInstance();
            baseDao.init(sqLiteDatabase,entityClass);
            map.put(daoClass.getSimpleName(), baseDao);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T)baseDao;
    }

}
