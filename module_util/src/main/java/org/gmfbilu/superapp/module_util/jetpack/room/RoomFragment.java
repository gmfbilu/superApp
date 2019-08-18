package org.gmfbilu.superapp.module_util.jetpack.room;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.module_util.R;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * Room是Google提供的一个ORM库。Room提供了三个主要的组件：
 * 1.@Database：@Database用来注解类，并且注解的类必须是继承自RoomDatabase的抽象类。该类主要作用是创建数据库和创建Daos（data access objects，数据访问对象
 * 2.@Entity：@Entity用来注解实体类，@Database通过entities属性引用被@Entity注解的类，并利用该类的所有字段作为表的列名来创建表
 * 3.@Dao：@Dao用来注解一个接口或者抽象方法，该类的作用是提供访问数据库的方法。在使用@Database注解的类中必须定一个不带参数的方法，这个方法返回使用@Dao注解的类
 * <p>
 * Room是SQLite数据库之上的数据库层，可以让我们轻松的使用系统原始API：SQLiteOpenHelper
 */
public class RoomFragment extends BaseFragment {

    List<User> mUsers = new ArrayList<>();
    // UserRepository mRepository;
    private UserViewModel mViewModel;

    public static RoomFragment newInstance() {
        Bundle args = new Bundle();
        RoomFragment fragment = new RoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_room_add).setOnClickListener(this);
        view.findViewById(R.id.bt_room_delete).setOnClickListener(this);
        view.findViewById(R.id.bt_room_update).setOnClickListener(this);
        view.findViewById(R.id.bt_room_query).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_room;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_room_add) {
            insert();
        } else if (id == R.id.bt_room_delete) {
            deleteAll();
        } else if (id == R.id.bt_room_update) {
            update();
        } else if (id == R.id.bt_room_query) {
            query();
        }
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        createDatabase();
    }


    /**
     * 第一步创建实体类:
     * 假如我们有一个用户表(User)，每个用户的实体就是表中的一列
     * <p>
     * 第二步创建DAO：UserDao
     * DAO是数据访问对象，指定SQL查询，并让他与方法调用相关联
     * DAO必须是一个接口或者抽象类
     * 默认情况下，所有的查询都必须在单独的线程中执行
     * <p>
     * 第三步添加Database：UserRoomDatabase
     * <p>
     * 创建一个UserRepository类来管理这个数据库，初始化数据库和响应的DAO类，对外提供插入、查询等方法
     * 数据库的创建，表的插入和删除操作，Room会强制要求在非UI线程中使用，否则会崩溃
     * <p>
     * ViewModel与Room和LiveData一起工作。
     * ViewModel确保数据在设备配置更改后仍然存在。当数据库发生更改时，Room会通LiveData，而LiveData反过来又用修改后的数据更的UI
     */
    private void createDatabase() {
        mViewModel = ViewModelProviders.of(_mActivity).get(UserViewModel.class);
        mViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                mUsers.clear();
                mUsers.addAll(users);
                int size = mUsers.size();
                if (size == 0) {
                    LoggerUtil.d("没有数据啦，请添加新数据");
                    return;
                }
                for (int i = 0; i < size; i++) {
                    LoggerUtil.d(mUsers.get(i));
                }
            }
        });
    }

    private void insert() {
        User user = new User();
        user.name = "张三" + Math.random() * 100;
        user.school = "北大" + Math.random() * 100;
        user.password = "123" + Math.random() * 100;
        //mRepository.insert(user);
        mViewModel.insertUser(user);
    }

    private void query() {
        //LiveData<List<User>> allUser = mRepository.getAllUser();
        LiveData<List<User>> allUser = mViewModel.getUsers();
        List<User> value = allUser.getValue();
        if (value == null) {
            value = new ArrayList<>();
        }
        mUsers.addAll(value);
    }

    private void deleteAll() {
        mViewModel.deleteAll();
    }

    public void update() {
        User user = new User();
        user.id = 0;
        user.name = "张三" + Math.random() * 100;
        user.school = "北大" + Math.random() * 100;
        user.password = "123" + Math.random() * 100;
        mViewModel.update(user);
    }
}
