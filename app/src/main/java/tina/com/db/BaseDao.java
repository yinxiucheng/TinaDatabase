package tina.com.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
    //定义一个缓存空间（key-字段名， value-成员变量）
    private HashMap<String, Field> cacheMap;

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
            cacheMap = new HashMap<>();
            initCacheMap();
        }
        return isInit;

    }

    private void initCacheMap() {
        //1. 取到所有的列名
        String sql = "select * from " + tableName + " limit 1,0";//空表
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        String[] columnNames = cursor.getColumnNames();
        //2.取所有的成员变量
        Field[] columFields = entityClass.getDeclaredFields();
        //把所有的字段访问权限打开
        for (Field field : columFields) {
            field.setAccessible(true);
        }

        for (String columnName : columnNames) {

            Field columField = null;

            for (Field field : columFields) {
                String fieldName = null;
                if (field.getAnnotation(DbField.class) != null) {
                    fieldName = field.getAnnotation(DbField.class).value();
                } else {
                    fieldName = field.getName();
                }

                if (columnName.equals(fieldName)) {
                    columField = field;
                }
            }
            if (columField != null) {
                cacheMap.put(columnName, columField);
            }
        }
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
        //准备好需要缓存的数据
        Map<String, String> map = getValues(entity);

        ContentValues contentValues = getContentValues(map);
        //开始插入
        long result = sqLiteDatabase.insert(tableName, null, contentValues);

        return result;
    }

    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        Set keys = map.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            String value = map.get(key);
            if (null != value){
                contentValues.put(key, value);
            }
        }
        return contentValues;
    }

    private Map<String, String> getValues(T entity) {
        HashMap<String, String> map = new HashMap<>();
        //返回的是所有的字段
        Iterator<Field> fieldIterator = cacheMap.values().iterator();
        while (fieldIterator.hasNext()) {
            Field field = fieldIterator.next();
            field.setAccessible(true);
            //获取变量的值
            Object object = null;
            try {
                object = field.get(entity);

                if (null == object) {
                    continue;
                }
                //这个地方的toString 拿到的是对应的值嚒
                String value = object.toString();
                //获取列名
                String key = null;

                if (field.getAnnotation(DbField.class) != null) {
                    key = field.getAnnotation(DbField.class).value();
                } else {
                    key = field.getName();
                }

                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)){
                    map.put(key, value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
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
