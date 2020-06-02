package org.gmfbilu.neteasycloudcourse.架构师.数据库;

import org.gmfbilu.neteasycloudcourse.架构师.数据库.annotation.DbField;
import org.gmfbilu.neteasycloudcourse.架构师.数据库.annotation.DbTable;

//得到User对应表名，注解。不写@DbTable("tb_user")的话就会默认使用User类名作为表名
@DbTable("tb_user")
public class User {

    //得到User对象对应列名，注解。不写 @DbField("u_id")的话就会默认使用属性名作为列名
    @DbField("u_id")
    public Integer id;
    public int age;
    public String name;
    public Long mileSecond;
    public long smallSecond;
    public Double Bigprice;
    public double price;
    public Float haha;
    public float smallHaha;
    public Short sh;
    public short smallSh;
    public char ch;
    public Boolean iss;
    public boolean has;

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public User(Integer id,
                int age,
                String name,
                Long mileSecond,
                long smallSecond,
                Double bigprice,
                double price,
                Float haha,
                float smallHaha,
                Short sh,
                short smallSh,
                char ch,
                Boolean iss,
                boolean has) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.mileSecond = mileSecond;
        this.smallSecond = smallSecond;
        Bigprice = bigprice;
        this.price = price;
        this.haha = haha;
        this.smallHaha = smallHaha;
        this.sh = sh;
        this.smallSh = smallSh;
        this.ch = ch;
        this.iss=iss;
        this.has=has;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
