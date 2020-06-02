package org.gmfbilu.neteasycloudcourse.架构师.数据库;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.neteasycloudcourse.R;
import org.gmfbilu.neteasycloudcourse.架构师.数据库.db.BaseDao;
import org.gmfbilu.neteasycloudcourse.架构师.数据库.db.BaseDaoFactory;
import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;

import java.util.List;

/**
 * 数据库设计三大原则：
 * 1.原子性
 * 2.唯一性
 * 3.避免沉余性
 */
public class SQLiteFragment extends BaseFragment {


    public static SQLiteFragment newInstance() {
        Bundle args = new Bundle();
        SQLiteFragment fragment = new SQLiteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_insert).setOnClickListener(this);
        view.findViewById(R.id.bt_query).setOnClickListener(this);
        view.findViewById(R.id.bt_update).setOnClickListener(this);
        view.findViewById(R.id.bt_delete).setOnClickListener(this);

        //通过Environment获取的
        //LoggerUtil.d(Environment.getDataDirectory().getPath());           //获得根目录/data (内部存储路径)                        /data
        //LoggerUtil.d(Environment.getDownloadCacheDirectory().getPath());  //获得缓存目录/cache                                   /data/cache
        //LoggerUtil.d(Environment.getExternalStorageDirectory().getPath());//获得SD卡目录/mnt/sdcard（获取的是手机外置sd卡的路径） /storage/emulated/0
        //LoggerUtil.d(Environment.getRootDirectory().getPath());           //获得系统目录/system                                  /system

        //通过Context获取的
        //LoggerUtil.d(BaseApplication.mApplicationContext.getDatabasePath("/databases/ne.db"));// 返回通过Context.openOrCreateDatabase 创建的数据库文件                                  /databases/ne.db
        //LoggerUtil.d(BaseApplication.mApplicationContext.getCacheDir().getPath());            // 用于获取APP的cache目录 /data/data/<application package>/cache目录                      /data/user/0/org.gmfbilu.superapp/cache
        //LoggerUtil.d(BaseApplication.mApplicationContext.getExternalCacheDir().getPath());    //用于获取APP的在SD卡中的cache目录/mnt/sdcard/Android/data/<application package>/cache    /storage/emulated/0/Android/data/org.gmfbilu.superapp/cache
        //LoggerUtil.d(BaseApplication.mApplicationContext.getFilesDir().getPath());            //用于获取APP的files目录 /data/data/<application package>/files                          /data/user/0/org.gmfbilu.superapp/files
        //LoggerUtil.d(BaseApplication.mApplicationContext.getObbDir().getPath());              // 用于获取APP SDK中的obb目录 /mnt/sdcard/Android/obb/<application package>              /storage/emulated/0/Android/obb/org.gmfbilu.superapp
        //LoggerUtil.d(BaseApplication.mApplicationContext.getPackageName());                   // 用于获取APP的所在包目录                                                               org.gmfbilu.superapp
        //LoggerUtil.d(BaseApplication.mApplicationContext.getPackageCodePath());               // 来获得当前应用程序对应的 apk 文件的路径                                               /data/app/org.gmfbilu.superapp-nzvbYXRhiYrzFm168sLYUg==/base.apk
        //LoggerUtil.d(BaseApplication.mApplicationContext.getPackageResourcePath());           // 获取该程序的安装包路径                                                               /data/app/org.gmfbilu.superapp-nzvbYXRhiYrzFm168sLYUg==/base.apk
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_architect_sqlite;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_insert) {
           BaseDao<User> baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
            Long a = Long.valueOf(201020);
            Float b = Float.valueOf(34);
            Short s = 1000;
            short ss=999;
            Boolean is=false;

            long insert = baseDao.insert(new User(0, 10, "batman", a, 1020, 0.323, 12.56, b, 3, s, ss, 'A', is, true));
            LoggerUtil.d(insert);
            //baseDao.insert(new User(1,20,"joker",a,1020,0.323,12.56,b,3,s,ss,'A',is,true));
            //baseDao.insert(new User(2,11,"trump",a,1020,0.323,12.56,b,3,s,ss,'A',is,true));
           // baseDao.insert(new User(3,14,"haha",a,1020,0.323,12.56,b,3,s,ss,'A',is,true));

        } else if (id == R.id.bt_query) {
            BaseDao<User> baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
            User where=new User();
            String[] a= {"10"};
            List<User> query = baseDao.query(where,"age=?",a,null,null,null);
            LoggerUtil.d(query);

        } else if (id == R.id.bt_update) {

        } else if (id == R.id.bt_delete) {

        }
    }
}
