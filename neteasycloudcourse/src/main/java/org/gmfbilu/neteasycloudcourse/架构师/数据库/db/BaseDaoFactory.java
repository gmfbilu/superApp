package org.gmfbilu.neteasycloudcourse.架构师.数据库.db;

import android.database.sqlite.SQLiteDatabase;

import org.gmfbilu.superapp.lib_base.base.BaseApplication;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;

import java.io.File;

/**
 * 安卓存储分为内部存储和外部存储
 * 内部存储：内部存储位于系统中很特殊的一个位置，如果你想将文件存储于内部存储中，那么文件默认只能被你的应用访问到。
 * 也就是说应用创建于内部存储的文件，与这个应用是关联起来的。当一个应用卸载之后，内部存储中的这些文件也被删除。
 * 从技术上来讲如果你在创建内部存储文件的时候将文件属性设置成可读，其他app能够访问自己应用的数据，前提是他知道你这个应用的包名，
 * 如果一个文件的属性是私有（private），那么即使知道包名其他应用也无法访问。
 * 内部存储空间十分有限，因而显得可贵，另外，它也是系统本身和系统应用程序主要的数据存储所在地，一旦内部存储空间耗尽，
 * 手机也就无法使用了。所以对于内部存储空间，我们要尽量避免使用。
 * Shared Preferences和SQLite数据库都是存储在内部存储空间上的。内部存储一般用Context来获取和操作。
 * 访问内部存储的API方法：
 * 1、Environment.getDataDirectory()
 * 2、getFilesDir().getAbsolutePath()
 * 3、getCacheDir().getAbsolutePath()
 * 4、getDir(“myFile”, MODE_PRIVATE).getAbsolutePath()
 * 外部存储：从4.4的系统开始，很多的中高端机器都将自己的机身存储扩展到了32G以上的，但是这个32G不是内部存储！，
 * 它依然是外部存储，也就是说4.4系统及以上的手机将机身存储存储（手机自身带的存储叫做机身存储）在概念上分成了”内部存储internal” 和”外部存储external” 两部分。
 * 那4.4系统及以上的手机要是插了SD卡呢，SD卡也是外部存储。
 * 在4.4以后的系统中，API提供了这样一个方法来遍历手机的外部存储路径：
 * File[] files;
 * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
 * files = getExternalFilesDirs(Environment.MEDIA_MOUNTED);
 * for(File file:files){
 * Log.e("main",file);
 * }
 * }
 * 如果你的手机插了SD卡的话，那么它打印的路径就有两条了：
 * /storage/emulated/0/Android/data/packname/files/mounted
 * /storage/B3E4-1711/Android/data/packname/files/mounted
 * 其中/storage/emulated/0目录就是机身存储的外部存储路径
 * 而/storage/B3E4-1711/就是SD卡的路径
 * 他们统称为外部存储
 * 访问外部存储的API方法：
 * 1、Environment.getExternalStorageDirectory().getAbsolutePath()
 * 2、Environment.getExternalStoragePublicDirectory(“”).getAbsolutePath()
 * 3、getExternalFilesDir(“”).getAbsolutePath()
 * 4、getExternalCacheDir().getAbsolutePath()
 * 所以手机的外部存储可能包含两部分，一是机身存储的外部存储部分，还有一个是SD卡部分
 * <p>
 * 系统版本6.0：
 * 1、Environment.getDataDirectory() = /data
 * 这个方法是获取内部存储的根路径
 * 2、getFilesDir().getAbsolutePath() = /data/user/0/packname/files
 * 这个方法是获取某个应用在内部存储中的files路径
 * 3、getCacheDir().getAbsolutePath() = /data/user/0/packname/cache
 * 这个方法是获取某个应用在内部存储中的cache路径
 * 4、getDir(“myFile”, MODE_PRIVATE).getAbsolutePath() = /data/user/0/packname/app_myFile
 * 这个方法是获取某个应用在内部存储中的自定义路径
 * 方法2,3,4的路径中都带有包名，说明他们是属于某个应用
 * 5、Environment.getExternalStorageDirectory().getAbsolutePath() = /storage/emulated/0
 * 这个方法是获取外部存储的根路径
 * 6、Environment.getExternalStoragePublicDirectory(“”).getAbsolutePath() = /storage/emulated/0
 * 这个方法是获取外部存储的根路径
 * 7、getExternalFilesDir(“”).getAbsolutePath() = /storage/emulated/0/Android/data/packname/files
 * 这个方法是获取某个应用在外部存储中的files路径
 * 8、getExternalCacheDir().getAbsolutePath() = /storage/emulated/0/Android/data/packname/cache
 * 这个方法是获取某个应用在外部存储中的cache路径
 * <p>
 * Environment.getDownloadCacheDirectory() = /cache
 * Environment.getRootDirectory() = /system
 * <p>
 * 第一类是位于根目录/data下；
 * 还有一类是位于根目录/storage下，可以看到调用它们的API方法都带了一个External；
 * 另外一类不在/data下也不再/storage下，比如系统文件/system，或者缓存文件/cache。
 * /data目录下的文件物理上存放在我们通常所说的内部存储里面
 * /storage目录下的文件物理上存放在我们通常所说的外部存储里面
 * /system用于存放系统文件，/cache用于存放一些缓存文件，物理上它们也是存放在内部存储里面的
 * <p>
 * getFilesDir().getAbsolutePath()和getCacheDir().getAbsolutePath()有什么区别:
 * getFilesDir获取的是files目录，getCacheDir获取的是cache目录，它们位于同一级目录，只是为了用来存放不同类型的数据的，
 * 由文件名不难看出：cache下存放缓存数据，databases下存放使用SQLite存储的数据，files下存放普通数据（log数据，json型数据等），
 * shared_prefs下存放使用SharedPreference存放的数据。这些文件夹都是由系统创建的。
 * <p>
 * getFilesDir().getAbsolutePath()和getExternalFilesDir(“”).getAbsolutePath()有什么区别:
 * /data/user/0/packname/files
 * /storage/emulated/0/Android/data/packname/files
 * 一个在内部存储里面，一个在外部存储里面，这是它们的区别。
 * 它们的路径都带有包名，表明是这个APP的专属文件，这类文件应该是随着app卸载而一起被删除的，并且我们在设置里面清除该应用的数据时，这两个文件夹下的数据都会被清除。
 * <p>
 * 什么是APP专属文件:
 * 所谓专属文件就是它是属于某个具体的应用的，他的文件路径都带有相应的包名，当APP卸载时，它们会随应用一起删除，当我们在设置里面手动清除某个应用数据时（不是清除缓存），它们也会一起被清掉。
 * <p>
 * 既然内部存储与外部存储都有APP专属文件，那么我们该使用哪个:
 * 很显然应该用外部存储的，因为内部存储本身就比较小，而且已经存储了一些系统的文件，因此内部存储我们尽量不要去使用。但是当手机没有外部存储时，我们还是得使用内部存储，一般程序员会做判断是否有外部存储，没有再使用内部存储.
 */
public class BaseDaoFactory {

    private static final BaseDaoFactory instance = new BaseDaoFactory();

    public static BaseDaoFactory getInstance() {
        return instance;
    }

    private SQLiteDatabase mSQLiteDatabase;
    private String sqliteDatabasePath;

    private BaseDaoFactory() {
    }

    public <T> BaseDao<T> getBaseDao(Class<T> entityClass) {
        String name = entityClass.getSimpleName();//getName是完整路径名
        init(name);
        BaseDao baseDao = null;
        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.init(mSQLiteDatabase, entityClass);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return baseDao;
    }

    private void init(String dbName){
        /**
         * 当用户卸载app的时候此目录也会被删除
         * data/data/applicationId/中的applicationId需要和应用的applicationId一致才会随着应用的删除而删除
         * BaseApplication.mApplicationContext.getFilesDir()，Context.getFilesDir()是/data/user/0/org.gmfbilu.superapp/files ，使用的是内部存储，这个目录在模拟机根目录下/data/data/org.gmfbilu.superapp/files，在真机中需要root才能查看
         */
        sqliteDatabasePath = BaseApplication.mApplicationContext.getFilesDir() + "/databases/"+dbName+".db";
        //先判断目录是否存在
        File file = new File(sqliteDatabasePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();//创建多层目录，每一个斜杆都代表一个目录
            LoggerUtil.e("创建数据库目录");
        }
       // LoggerUtil.d(sqliteDatabasePath);
        mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqliteDatabasePath, null);
    }
}
