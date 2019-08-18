package org.gmfbilu.superapp.module_util.jetpack.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//@Entity： 代表一个表中的实体，默认类名就是表名，如果不想使用类名作为表名，可以给注解添加表名字段@Entity(tableName = "user_table")
@Entity
public class User {

    @PrimaryKey //@PrimaryKey： 每个实体都需要自己的主键
    @NonNull //@NonNull 表示字段，方法，参数返回值不能为空
    @ColumnInfo(name = "id_") //@ColumnInfo(name = “lastname”) 如果希望表中字段名跟类中的成员变量名不同，添加此字段指明
    public int id;

    public String name;
    public String password;
    public String school;

    @Override
    public String toString(){
        return "名叫 "+name+",密码是 "+password+",学校 "+school;
    }

}
