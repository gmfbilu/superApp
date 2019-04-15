package org.gmfbilu.superapp.module_util.sqLite;

public class Person {

    //表名
    public static final String TABLE_NAME = "person";

    //列名
    public static final String COLUMN_id = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";

    //字段
    public String name;
    public int age;

    //创建表的语句,属性列为：_id（主键并且自动增加）、name、age
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +"("+
                    COLUMN_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_AGE + " INTEGER)";

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
