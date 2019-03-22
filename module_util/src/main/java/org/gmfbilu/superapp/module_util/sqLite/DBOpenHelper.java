package org.gmfbilu.superapp.module_util.sqLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    //数据库名称
    private static final String DATABASE_NAME = "test.db";
    //数据库版本
    private static final int DATABASE_VERSION = 1;


    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //该方法当名字对应的数据库不存在时,会创建
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Person.CREATE_TABLE);
    }

    //当数据库需要升级的时候系统会主动的调用这个方法。一般我们在这个方法里边删除数据表，并建立新的数据表
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 删除指定表的语句
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Person.TABLE_NAME);
        // Create tables again
        onCreate(sqLiteDatabase);
    }
}
