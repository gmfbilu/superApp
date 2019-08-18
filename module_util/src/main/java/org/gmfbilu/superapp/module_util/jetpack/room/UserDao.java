package org.gmfbilu.superapp.module_util.jetpack.room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

//加注解@Dao，表名它是Room的一个查询类
@Dao
public interface UserDao {

    @Insert //声明一个插入用户的方法insert，并给它添加注解@Insert，不用提供任何SQL语句
    void insert(User user);

    @Query("select * from user") //查询没有便捷方法，需要使用@Query注解，并且提供相应的SQL语句select * from user
    LiveData<List<User>> getUserList();

    @Query("delete from user") //删除方法没有便捷方法，需要使用@Query注解，并且提供相应的SQL语句delete from user
    void deleteAll();

    @Update
    void updateUsers(User... users);
}
