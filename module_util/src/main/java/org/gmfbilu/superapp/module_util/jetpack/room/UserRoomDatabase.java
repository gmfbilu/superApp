package org.gmfbilu.superapp.module_util.jetpack.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//添加一个注解@Database表名它是一个数据库，注解有两个参数第一个是数据库的实体，它是一个数组，可以传多个，当数据库创建的时候，会默认给创建好对应的表，第二个参数是数据库的版本号
@Database(entities = {User.class}, version = 1)
public abstract class UserRoomDatabase extends RoomDatabase {

    //定义跟数据库一起使用的相关的DAO类
    public abstract UserDao userDao();

    //创建一个UserRoomDatabase的单例，防止同时打开多个数据库的实例
    public static UserRoomDatabase instance;

    public static UserRoomDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (UserRoomDatabase.class) {
                if (instance == null) {
                    //使用Room提供的数据库构建器来创建该实例，第一个参数application，第二个参数当前数据库的实体类，第三个参数数据库的名字
                    instance = Room.databaseBuilder(context.getApplicationContext(), UserRoomDatabase.class, "user_database").build();
                }
            }
        }
        return instance;
    }
}
