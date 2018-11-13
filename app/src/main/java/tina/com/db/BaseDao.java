package tina.com.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;

import tina.com.database.annotation.DbField;
import tina.com.database.annotation.DbTable;

/**
 * @author yxc
 * @date 2018/11/13
 */
public class BaseDao<T> implements IBaseDao<T> {

    //持有数据库操作的引用
    private SQLiteDatabase sqLiteDatabase;
    //表名
    private String tableName;

    private Class<T> entityClass;

    private boolean isInit = false;

    protected boolean init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass) {
        this.entityClass = entityClass;
        this.sqLiteDatabase = sqLiteDatabase;

        if (!isInit) {
            if (entityClass.getAnnotation(DbTable.class) == null) {
                //反射取类名
                tableName = entityClass.getSimpleName();
            } else {
                tableName = entityClass.getAnnotation(DbTable.class).value();
            }

            if (!sqLiteDatabase.isOpen()) {
                return false;
            }
            //执行建表操作
            //create table if not exists tb_user(_id integer, name varchar(20), password varchar(20))
            //单独用个方法生成create命令
            String createTableSql = getCreateTableSQL();
            sqLiteDatabase.execSQL(createTableSql);
            isInit = true;

        }
        return isInit;

    }

    private String getCreateTableSQL() {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("create table if not exists ");
        stringBuffer.append(tableName + "(");

        //反射得到所有的成员变量
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            Class type = field.getType();

            if (field.getAnnotation(DbField.class) != null) {

                if (type == String.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " TEXT,");
                } else if (type == Integer.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " INTEGER,");
                } else if (type == Long.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " BIGINT,");
                } else if (type == Double.class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " DOUBLE,");
                } else if (type == byte[].class) {
                    stringBuffer.append(field.getAnnotation(DbField.class).value() + " BLOB,");
                } else {
                    //不支持的类型号
                    continue;
                }
            } else {
                if (type == String.class) {
                    stringBuffer.append(field.getName() + " TEXT,");
                } else if (type == Integer.class) {
                    stringBuffer.append(field.getName() + " INTEGER,");
                } else if (type == Long.class) {
                    stringBuffer.append(field.getName() + " BIGINT,");
                } else if (type == Double.class) {
                    stringBuffer.append(field.getName() + " DOUBLE,");
                } else if (type == byte[].class) {
                    stringBuffer.append(field.getName() + " BLOB,");
                } else {
                    //不支持的类型号
                    continue;
                }
            }
        }

        if (stringBuffer.charAt(stringBuffer.length() - 1) == ',') {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        stringBuffer.append(")");
        return stringBuffer.toString();
    }


    @Override
    public long insert(T entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", 1);
        contentValues.put("name", "jett");

        sqLiteDatabase.insert(tableName, null, contentValues);
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
