package org.gmfbilu.neteasycloudcourse.架构师.数据库.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.gmfbilu.neteasycloudcourse.架构师.数据库.annotation.DbField;
import org.gmfbilu.neteasycloudcourse.架构师.数据库.annotation.DbTable;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.lib_base.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * SQLite最大的特点是你可以把各种类型的数据保存到任何字段中，而不用关心字段声明的数据类型是什么。
 * 例如：可以在Integer类型的字段中存放字符串，或者在布尔型字段中存放浮点数，或者在字符型字段中存放日期型值。
 * 但有一种情况例外：定义为INTEGER PRIMARY KEY的字段只能存储64位整数， 当向这种字段保存除整数以外的数据时，将会产生错误。
 * <p>
 * 另外， SQLite 在解析CREATE TABLE 语句时，会忽略 CREATE TABLE 语句中跟在字段名后面的数据类型信息，如下面语句会忽略 name字段的类型信息：
 * CREATE TABLE person (personid integer primary key autoincrement, name varchar(20))
 * <p>
 * 查询语句：select * from 表名 where 条件子句 group by 分组字句 having ... order by 排序子句 (顺序一定不能错)
 * select * from person
 * select * from person order by id desc
 * select name from person group by name having count(*)>1
 * <p>
 * 分页SQL与MySQL类似，下面SQL语句获取5条记录，跳过前面3条记录
 * select * from Account limit 5 offset 3
 * select * from Account limit 3,5
 * <p>
 * 插入语句：
 * insert into 表名(字段列表) values(值列表)。如：
 * insert into person(name, age) values(‘小明’,3)
 * <p>
 * 更新语句：
 * update 表名 set 字段名=值 where 条件子句。如：
 * update person set name=‘小明‘ where id=10
 * <p>
 * 删除语句：
 * delete from 表名 where 条件子句。如：
 * delete from person  where id=10
 * <p>
 * SQLite 存储类:
 * 每个存储在 SQLite 数据库中的值都具有以下存储类之一：
 * NULL	空值。
 * INTEGER	值是一个带符号的整数，根据值的大小存储在 1、2、3、4、6 或 8 字节中。
 * REAL	值是一个浮点值，存储为 8 字节的 IEEE 浮点数字。
 * TEXT	值是一个文本字符串，使用数据库编码（UTF-8、UTF-16BE 或 UTF-16LE）存储。
 * BLOB	值是一个 blob 数据，完全根据它的输入存储。
 * <p>
 * Boolean 数据类型
 * SQLite 没有单独的 Boolean 存储类。相反，布尔值被存储为整数 0（false）和 1（true）。
 * <p>
 * Date 与 Time 数据类型
 * SQLite 没有一个单独的用于存储日期和/或时间的存储类，但 SQLite 能够把日期和时间存储为 TEXT、REAL 或 INTEGER 值。
 * TEXT	格式为 "YYYY-MM-DD HH:MM:SS.SSS" 的日期。
 * REAL	从公元前 4714 年 11 月 24 日格林尼治时间的正午开始算起的天数。
 * INTEGER	从 1970-01-01 00:00:00 UTC 算起的秒数。
 * <p>
 * 数据库的表结构里字段数据定义为INTEGER,但是使用Cursor的getInt方法，获取到的数据跟数据库中的数值不一致。
 * SQLite中，INTEGER被认为是一个存储类，INTEGER值是一个带符号的整数，根据值的大小存储在 1、2、3、4、6 或 8 字节中。
 * 所以INTEGER是可以存储long long类型的。因此SQLite里面部分int类型的字段，存储的数据可能是long类型的
 */

/**
 * SQLite的关键字不能作为bean的属性名，如is、group。
 * 建表语句可以是没有主键的
 */
public class BaseDao<T> implements IBaseDao<T> {

    //持有数据库操作的引用
    private SQLiteDatabase mSQLiteDatabase;
    //表名
    private String tableName;
    //操作数据库所对应的Java类型
    private Class<T> entityClass;

    private boolean isInit = false;
    //缓存反射获取的字段名，避免下次使用的时候再次使用反射去获取。key:字段名，value:成员变量
    private HashMap<String, Field> cacheMap;//String=u_id,Field=public java.lang.Integer org.gmfbilu.neteasycloudcourse.架构师.数据库.User.id

    protected boolean init(SQLiteDatabase sqLiteDatabase, Class<T> entityClass) {
        this.mSQLiteDatabase = sqLiteDatabase;
        this.entityClass = entityClass;
        if (!isInit) {
            //根据传入的class进行数据表创建
            //查找entityClass有没有DbTable这个注解
            DbTable dt = entityClass.getAnnotation(DbTable.class);
            if (dt != null) {
                if (!StringUtils.isEmpty(dt.value())) {
                    tableName = dt.value();
                } else {
                    //entityClass有DbTable这个注解，但是注解@DbTable("")里面没有值，就使用entityClass类名
                    tableName = entityClass.getSimpleName();
                }
            } else {
                //entityClass没有DbTable这个注解，就使用entityClass类名
                tableName = entityClass.getSimpleName();
            }
            if (!sqLiteDatabase.isOpen()) {
                return false;
            }
            String createTableSql = getCreateTableSql();
            //  LoggerUtil.d(tableName + " ," + createTableSql);
            mSQLiteDatabase.execSQL(createTableSql);
            cacheMap = new HashMap<>();
            initCacheMap();
            isInit = true;
        }
        return isInit;
    }

    private void initCacheMap() {
        //从第一条数据开始，取0条数据。这样把表的结构给取出来了
        String sql = "select * from " + tableName + " limit 1,0";
        Cursor cursor = mSQLiteDatabase.rawQuery(sql, null);
        //获取所有的列名
        String[] columnNames = cursor.getColumnNames();
        //获取所有的成员变量
        Field[] declaredFields = entityClass.getDeclaredFields();
        //将字段访问权限打开
        for (Field field : declaredFields) {
            field.setAccessible(true);
        }
        //将列明和成员变量名一一对应
        for (String columnName : columnNames) {
            Field columnField = null;
            for (Field field : declaredFields) {
                String fieldName = null;
                DbField dbfield = field.getAnnotation(DbField.class);
                if (dbfield != null) {
                    if (!StringUtils.isEmpty(dbfield.value())) {
                        fieldName = dbfield.value();
                    } else {
                        fieldName = field.getName();
                    }
                } else {
                    fieldName = field.getName();
                }
                if (columnName.equals(fieldName)) {
                    columnField = field;
                    break;
                }
            }
            if (columnField != null) {
                //LoggerUtil.d(columnName + " ," + columnField);
                cacheMap.put(columnName, columnField);
            }
        }
        //  LoggerUtil.d(cacheMap);//u_id=public java.lang.Integer org.gmfbilu.neteasycloudcourse.架构师.数据库.User.id
    }


    //获取创建表语句
    private String getCreateTableSql() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("create table if not exists ");
        stringBuilder.append(tableName + "(");
        //反射获得所有的属性
        Field[] fields = entityClass.getFields();
        for (Field field : fields) {
            //得到属性的类型
            Class type = field.getType();
            //查找属性有没有DbField这个注解
            DbField dbField = field.getAnnotation(DbField.class);
            String name = "";
            if (dbField != null) {
                if (!StringUtils.isEmpty(dbField.value())) {
                    name = dbField.value();
                } else {
                    //属性有DbField这个注解，但是注解@DbField("")里面没有值，就使用属性名
                    name = field.getName();
                }
            } else {
                //属性没有DbField这个注解，使用属性名作为列名
                name = field.getName();
            }
            //LoggerUtil.d(type + " ," + name); //class java.lang.Double ,Bigprice // int ,age
            if (type == String.class ||
                    type == char.class) {
                stringBuilder.append(name + " TEXT,");//class java.lang.Integer和int
            } else if (type == Integer.class ||
                    type == int.class ||
                    type == Long.class ||
                    type == long.class ||
                    type == Short.class ||
                    type == short.class ||
                    type == Boolean.class ||
                    type == boolean.class) { //Integer不是int类型
                stringBuilder.append(name + " INTEGER,");
            } else if (type == Double.class ||
                    type == double.class ||
                    type == Float.class ||
                    type == float.class) {
                stringBuilder.append(name + " REAL,");
            } else if (type == Byte[].class ||
                    type == byte[].class) {
                stringBuilder.append(name + " BLOB,");
            } else {
                //暂时不支持的类型
                LoggerUtil.e("暂时不支持的类型" + type);
            }
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.append(")").toString();
    }

    @Override
    public long insert(T entity) {
        //entity对象转换为ContentValues
        Map<String, String> map = getValues(entity);
        //LoggerUtil.d(map);
        ContentValues values = getContentValues(map);
        LoggerUtil.d(values);//age=10
        return mSQLiteDatabase.insert(tableName, null, values);
    }

    @Override
    public long update(T entity, T where) {
        return 0;
    }

    @Override
    public int delete(T where) {
        return 0;
    }


    /**
     * 实际上的查询只能精确查询，因为有默认值存在：Test(int age, String name,double price)
     * Test where=new Test();where.name="batman";List<Test> query = baseDao.query(where);
     * 上面的语句会查询name="batman" and age=0 and price=0.0的对象，会精确匹配
     * 所以查询的时候要特别小心
     *
     * @param where
     * @return
     */
    @Override
    public List<T> query(T where) {
        return query(where, null, null, null);
    }


    /**
     * query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit)方法各参数的含义：
     * table：表名。相当于select语句from关键字后面的部分。如果是多表联合查询，可以用逗号将两个表名分开。
     * columns：要查询出来的列名。相当于select语句select关键字后面的部分。
     * selection：查询条件子句，相当于select语句where关键字后面的部分，在条件子句允许使用占位符“?”
     * selectionArgs：对应于selection语句中占位符的值，值在数组中的位置与占位符在语句中的位置必须一致，否则就会有异常。
     * groupBy：相当于select语句group by关键字后面的部分
     * having：相当于select语句having关键字后面的部分
     * orderBy：相当于select语句order by关键字后面的部分，如：personid desc, age asc;
     * limit：指定偏移量和获取的记录数，相当于select语句limit关键字后面的部分。
     *
     * @param where
     * @param orderBy
     * @param startIndex
     * @param limit
     * @return
     */
    @Override
    public List<T> query(T where, String orderBy, Integer startIndex, Integer limit) {
        return query(where, null, null, orderBy, startIndex, limit);
    }

    /**
     * @param where
     * @param queryKey   "age=? and name=?"
     * @param queryValue String[] a = {"11","joker"};
     * @param orderBy
     * @param startIndex 0
     * @param limit      10
     * @return
     */
    @Override
    public List<T> query(T where, String queryKey, String[] queryValue, String orderBy, Integer startIndex, Integer limit) {
        Map<String, String> map = getValues(where);
        LoggerUtil.d(map);
        //select * from tableName limit 0,10
        //select * 表示把表里面所有的列都查询出来，如果只查询某一个或者几个列 select id,name from tableName limit 0,10
        String limitString = null;
        if (startIndex != null && limit != null) {
            limitString = startIndex + " , " + limit;
        }
        //columns列名(查询所有的列就用null表示)，如果查询某一个或者几个列 String[] columns = {"id","name"}，这样没用，查询的结果其它列都会以默认数值表现出来
        String[] columns = null;
        if (StringUtils.isEmpty(queryKey) || queryValue == null || queryValue.length==0) {
            //select * from tableName where id=? and name=?
            Condition condition = new Condition(map);
            queryKey = condition.whereCause;
            queryValue = condition.whereArgs;
        }
        LoggerUtil.d(queryKey);
        for (int i = 0; i < queryValue.length; i++) {
            LoggerUtil.d(queryValue[i]);
        }
        //Cursor cursor = mSQLiteDatabase.query(tableName, columns, condition.whereCause, condition.whereArgs, null, null, orderBy, limitString);
        Cursor cursor = mSQLiteDatabase.query(tableName, columns, queryKey, queryValue, null, null, orderBy, limitString);
        return getResult(cursor, where);
    }


    //解析游标
    private List<T> getResult(Cursor cursor, T obj) {
        ArrayList list = new ArrayList<>();
        Object item = null; //User user=null;
        while (cursor.moveToNext()) {
            try {
                item = obj.getClass().newInstance();//user=new User();
                Iterator iterator = cacheMap.keySet().iterator();//获取成员变量
                //hasNext():判断当前元素是否存在，并没有指向下一个元素，直到执行next()后hasNext()才去判断是否存在下一个元素
                //next():返回当前元素，并指向下一个元素，如果时不存在下一个元素，就会报空指针异常
                while (iterator.hasNext()) {
                    //获取列名
                    String columnName = (String) iterator.next();
                    Field field = cacheMap.get(columnName);
                    //LoggerUtil.d(columnName + " ," + field);
                    //以列名获取列名在游标中的位置
                    int columnIndex = cursor.getColumnIndex(columnName);
                    //获取成员变量的类型
                    Class type = field.getType();
                    if (columnIndex != -1) {
                        // byte[] getBlob， String getString，short getShort，int getInt，long getLong，float getFloat，double getDouble
                        if (type == String.class) {
                            //user.setName("batman")，item相对于user，cursor.getString(columnIndex)获取到的是batman。field相对于name属性。
                            //name.set(user,batman)
                            field.set(item, cursor.getString(columnIndex));
                            LoggerUtil.d(type+" ,"+cursor.getString(columnIndex));
                        } else if (type == Integer.class || type == int.class) { //Integer不是int类型
                            field.set(item, cursor.getInt(columnIndex));
                            LoggerUtil.d(type+" ,"+cursor.getInt(columnIndex));
                        } else if (type == Long.class || type == long.class) {
                            field.set(item, cursor.getLong(columnIndex));
                            LoggerUtil.d(type+" ,"+cursor.getLong(columnIndex));
                        } else if (type == Double.class || type == double.class) {
                            field.set(item, cursor.getDouble(columnIndex));
                            LoggerUtil.d(type+" ,"+cursor.getDouble(columnIndex));
                        } else if (type == Byte[].class || type == byte[].class) {
                            field.set(item, cursor.getBlob(columnIndex));
                            LoggerUtil.d(type+" ,"+cursor.getBlob(columnIndex));
                        } else if (type == Float.class || type == float.class) {
                            field.set(item, cursor.getFloat(columnIndex));
                            LoggerUtil.d(type+" ,"+cursor.getFloat(columnIndex));
                        } else if (type == Short.class || type == short.class) {
                            field.set(item, cursor.getShort(columnIndex));
                            LoggerUtil.d(type+" ,"+cursor.getShort(columnIndex));
                        } else if (type == Boolean.class || type == boolean.class) {
                            //布尔值被存储为整数 0（false）和 1（true）
                            field.set(item, cursor.getInt(columnIndex) == 1);
                            LoggerUtil.d(type+" ,"+cursor.getInt(columnIndex));
                        } else if (type == char.class) {
                            //field.set(item, cursor.getBlob(columnIndex));
                            LoggerUtil.e("暂时不支持的类型" + type);
                        } else {
                            //暂时不支持的类型
                            LoggerUtil.e("暂时不支持的类型" + type);
                        }
                    }
                }
                LoggerUtil.d(item);
                list.add(item);
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return list;
    }

    private class Condition {
        //select * from tableName where id=? and name=?
        private String whereCause;//id=? and name=?
        private String[] whereArgs;//相对于id=? and name=?中的id=1, name=batman {1,"batman"}

        private Condition(Map<String, String> whereMap) {
            ArrayList list = new ArrayList(); //存储name=batman中的batman，最后转换成whereArgs
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("1=1");//查询语句中加上1=1不会影响任何查询结果，只是为了后面追加and字符方便
            Set<String> keySet = whereMap.keySet();
            for (String key : keySet) {
                String value = whereMap.get(key);
                if (!StringUtils.isEmpty(value)) {
                    stringBuilder.append(" and ").append(key).append(" =?");
                    list.add(value);
                }
            }
            this.whereCause = stringBuilder.toString();
            this.whereArgs = (String[]) list.toArray(new String[list.size()]);
            LoggerUtil.d(whereCause);
            LoggerUtil.d(whereArgs);
        }

    }

    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        Set<String> keys = map.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(key);
            if (!StringUtils.isEmpty(value)) {
                contentValues.put(key, value);
            } else {
                LoggerUtil.e("属性对应的值为空，不能存放空值");
            }
        }
        return contentValues;
    }


    /**
     * {price=0.0, name=batman, age=11}
     *
     * @param entity
     * @return
     */
    private Map<String, String> getValues(T entity) {
        HashMap<String, String> hashMap = new HashMap<>();
        //得到所有的成员变量
        Iterator<Field> iterator = cacheMap.values().iterator();
        while (iterator.hasNext()) {
            Field field = iterator.next();
            field.setAccessible(true);
            //获取成员变量的值
            try {
                Object object = field.get(entity);
                /*if (object == null) {
                    //非基本数据类型的字段
                    LoggerUtil.e("object == null ,"+field.getType()+" ,"+field.getName()+" ,"+entity);
                    continue;
                }*/
                String value = "null"; //非基本数据类型解析为null
                if (object != null) {
                    value = object.toString(); //基本数据类型
                }
                //获取列名
                String key = null;
                DbField dbField = field.getAnnotation(DbField.class);
                if (dbField != null) {
                    if (!StringUtils.isEmpty(dbField.value())) {
                        key = dbField.value();
                    } else {
                        key = field.getName();
                    }
                } else {
                    key = field.getName();
                }
                //LoggerUtil.d(key + " ," + value);
                //这里得到的value全部都是字符串形式
                hashMap.put(key, value); //key是name，value是batman
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return hashMap;
    }
}
