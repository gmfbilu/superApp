package org.gmfbilu.superapp.module_util.sqLite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.R;

/**
 * 1, SQLiteOpenHelper是一个抽象类，我们通常需要继承它，并且实现里面的3个函数
 * <p>
 * 查询语句：select * from 表名 where 条件子句 group by 分组字句 having ... order by 排序子句 (顺序一定不能错)
 * select * from person
 * select * from person order by id desc
 * select name from person group by name having count(*)>1
 * <p>
 * 获取5条记录，跳过前面3条记录：select * from Account limit 5 offset 3   /select * from Account limit 3,5
 * <p>
 * 插入语句：insert into 表名(字段列表) values(值列表)
 * insert into person(name, age) values(‘小明’,3)
 * <p>
 * 更新语句：update 表名 set 字段名=值 where 条件子句
 * update person set name=‘小明‘ where id=10
 * <p>
 * 删除语句：delete from 表名 where 条件子句
 * delete from person  where id=10
 * <p>
 * execSQL(String sql)方法可以执行insert、delete、update和CREATE TABLE之类有更改行为的SQL语句； rawQuery()方法用于执行select语句
 * execSQL(String sql, Object[] bindArgs)方法解决特殊字符的转义，这种更好
 * db.execSQL("insert into person(name, age) values('炸死特', 4)"); -> db.execSQL("insert into person(name, age) values(?,?)", new Object[]{"炸死特", 4});
 * rawQuery()方法的第一个参数为select语句；第二个参数为select语句中占位符参数的值，如果select语句没有使用占位符，该参数可以设置为null
 * db.rawQuery("select * from person where name like ? and age=?", new String[]{"%炸死特%", "4"});
 * <p>
 * LIMIT关键字用来分页查找
 *
 *
 * <p>
 * <p>
 * 在data/data/<包名>/database/目录 下除了有我们创建的db文件外，还有一个xxx.db-journal这个文件就是用来让数据库支持事务而 产生的 临时的日志文件
 * <p>
 * SQLite存储大二进制文件。一般我们很少往数据库中存储大二进制文件，比如图片，音频，视频等
 * 保存图片到SQLite中
 * 在创建数据库表的时候需要创建一个BLOB的字段，用于存储二进制的值
 * db.execSQL("Create table test(_id INTEGER PRIMARY KEY AUTOINCREMENT,head_image BLOB)");
 * 将图片转为BLOB格式
 * SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
 * try{
 * ByteArrayOutputStream outs = new ByteArrayOutputStream();
 * //压缩为png格式，100表示和原图一样大
 * ((BitmapDrawable)imageView.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG,100,outs);
 * Object[] args = new Object[]{outs.toByteArray()};
 * db.execSQL("INSERT INTO test(head_img) values(?)",args);
 * outs.close();
 * db.close();
 * }catch(Exception e){
 * e.printStarkTrace();
 * }
 * 读取SQLite中的图片
 * Cursor cursor = db.rawQuery("SELECT head_img FROM test", null);
 * if (cursor!=null){
 * if (cursor.moveToFirst()){
 * //取出图片保存到字节数组中
 * byte[] img = cursor.getBlob(cursor.getColumnIndex("head_img"));
 * if (img!=null){
 * ByteArrayInputStream stream = new ByteArrayInputStream(img);
 * imageView.setImageDrawable(Drawable.createFromStream(stream,"img"));
 * }
 * }
 * cursor.close();
 * }
 */
public class SQLiteFragment extends BaseFragment {

    //SQLiteDatabase代表一个数据库对象，提供了操作数据库的一些方法
    private SQLiteDatabase mSQLiteDatabase;

    public static SQLiteFragment newInstance() {
        Bundle args = new Bundle();
        SQLiteFragment fragment = new SQLiteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.AppCompatButton_add).setOnClickListener(this);
        view.findViewById(R.id.AppCompatButton_delete).setOnClickListener(this);
        view.findViewById(R.id.AppCompatButton_update).setOnClickListener(this);
        view.findViewById(R.id.AppCompatButton_find).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_sqlite;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mSQLiteDatabase = new DBOpenHelper(_mActivity).getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.AppCompatButton_add) {
            hello1(new Person("A",1));
        } else if (id == R.id.AppCompatButton_delete) {
            hello2("B");
        } else if (id == R.id.AppCompatButton_update) {
            hello3("B",0);
        } else if (id == R.id.AppCompatButton_find) {
        }
    }

    /**
     * 插入数据
     */
    private void hello1(Person person) {
        /**
         * 插入数据有两种方法：
         * 参数1表名， 参数2空列的默认值，参数3ContentValues类型的一个封装了列名称和列值的Map
         * 1.SQLiteDatabase的insert(String table,String nullColumnHack,ContentValues  values)
         * 2.编写插入数据的SQL语句，直接调用SQLiteDatabase的execSQL()方法来执行
         */
        try {
            //开始事物
            mSQLiteDatabase.beginTransaction();
            //执行SQL语句
            mSQLiteDatabase.execSQL("insert into " + Person.TABLE_NAME + "(" + Person.COLUMN_NAME + "," + Person.COLUMN_AGE + ") values(?,?)", new Object[]{person.name, person.age});
            //提交事物
            mSQLiteDatabase.setTransactionSuccessful();
        } finally {
            //结束事物，结束事物有两种：事物回滚和事物提交
            mSQLiteDatabase.endTransaction();
        }
    }

    /**
     * 删除数据
     */
    private void hello2(String name) {
        /**
         * 删除数据有两种方法:
         * 参数1表称，参数2删除条件，参数3删除条件值数组
         * 1.SQLiteDatabase的delete(String table,String whereClause,String[]  whereArgs)
         * 2.编写删除SQL语句，调用SQLiteDatabase的execSQL()方法来执行删除
         */
        String sql = "delete from " + Person.TABLE_NAME + " where " + Person.TABLE_NAME + " =" + name;
        mSQLiteDatabase.execSQL(sql);
    }


    /**
     * 修改数据,更改年龄
     */
    private void hello3(String name, int age) {
        /**
         * 修改数据有两种方法：
         * 参数1表名称，参数2跟行列ContentValues类型的键值对Key-Value，参数3更新条件（where字句），参数4  更新条件数组
         * 1.SQLiteDatabase的update(String table,ContentValues values,String  whereClause, String[]  whereArgs)方法
         * 2.编写更新的SQL语句，调用SQLiteDatabase的execSQL执行更新
         */
        String sql = "update " + Person.TABLE_NAME + " set " + Person.COLUMN_AGE + " =" + age + " where " + Person.COLUMN_NAME + " = " + age;
        mSQLiteDatabase.execSQL(sql);
    }

    /**
     * 查询数据
     */
    private void hello4() {
        /**
         *  询数据是通过Cursor类来实现的，当我们使用SQLiteDatabase.query()方法时，会得到一个Cursor对象，Cursor指向的就是每一条数据。它提供了很多有关查询的方法
         *  Cursor是一个游标接口，提供了遍历查询结果的方法，如移动指针方法move()，获得列值方法getString()等
         *
         *  表名称。列名称数组。条件字句，相当于where。条件字句，参数数组。分组列。分组条件。排序列。分页查询限制。返回值，相当于结果集ResultSet
         *  Cursor query(String table,String[] columns,String selection,String[]  selectionArgs,String groupBy,String having,String orderBy,String limit);
         *
         *  getCount()获得总的数据项数
         *  isFirst()判断是否第一条记录
         *  isLast()判断是否最后一条记录
         *  moveToFirst()移动到第一条记录
         *  moveToLast()移动到最后一条记录
         *  move(int offset)移动到指定记录
         *  moveToNext()移动到下一条记录
         *  moveToPrevious()移动到上一条记录
         *  getColumnIndexOrThrow(String  columnName)根据列名称获得列索引
         *  getInt(int columnIndex)获得指定列索引的int类型值
         *  getString(int columnIndex)获得指定列缩影的String类型值
         *
         */

        //查询获得游标
        Cursor cursor = mSQLiteDatabase.query(Person.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null) {
            //判断游标是否为空
            if (cursor.moveToFirst()) {
                //遍历游标
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.move(i);
                    //获得ID
                    int id = cursor.getInt(0);
                    //获得用户名
                    String username = cursor.getString(1);
                    //获得密码
                    String password = cursor.getString(2);
                }
            }
            cursor.close();
        }
    }

}
