package org.gmfbilu.superapp.lib_base.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.leon.channel.helper.ChannelReaderUtil;

import org.gmfbilu.superapp.lib_base.app.Constant;
import org.gmfbilu.superapp.lib_base.base.BaseApplication;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * Activity跳转
     *
     * @param context
     * @param clazz
     */
    public static void startActivity(Context context, Class<? extends Activity> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }


    /**
     * 弹Toast
     *
     * @param content
     */
    public static void toast(String content) {
        Toast.makeText(BaseApplication.mApplicationContext, content, Toast.LENGTH_SHORT).show();
    }


    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 判断WIFI是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 判断MOBILE网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 获取当前网络连接的类型信息
     *
     * @param context
     * @return
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 验证手机号码格式是否正确
     *
     * @param mobiles
     * @return
     */
    public static boolean isPhoneNumber(String mobiles) {
        String telRegex = "[1][34578]\\d{9}";
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            mobiles = mobiles.replaceAll("\\s*", "");
            return mobiles.matches(telRegex);
        }
    }


    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getDeviceName() {
        return Build.MODEL;
    }


    /**
     * 获取设备SDK版本
     *
     * @return
     */
    public static int getDeviceVersionSDK() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备的系统版本
     *
     * @return
     */
    public static String getDeviceVersionRelease() {
        return Build.VERSION.RELEASE;
    }


    /**
     * 从资源图片中获取Bitmap
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap getBitmapFromResource(Context context, int resId) {
        Resources resources = context.getResources();
        return BitmapFactory.decodeResource(resources, resId);
    }


    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取包名
     *
     * @return
     */
    public static String getPackageName() {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) BaseApplication.mApplicationContext.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = Objects.requireNonNull(manager).getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return Constant.BASE_PACKAGENAME;
    }

    /**
     * 获取VersionCode
     *
     * @return
     */
    public static String getAppVersion() {
        PackageManager packageManager = BaseApplication.mApplicationContext.getPackageManager();
        String packageName = getPackageName();
        String name;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            name = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            name = "1.0.0";
        }
        return name;
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取本app进程名，效率上比上一个高效
     *
     * @return
     */
    public static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取渠道名
     *
     * @return
     */
    public static String getChannelName() {
        String name = ChannelReaderUtil.getChannel(BaseApplication.mApplicationContext);
        if (TextUtils.isEmpty(name)) {
            name = Constant.BASE_CHANNEL;
        }
        return name;
    }


    /**
     * 判断是否有网络连接
     *
     * @return
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.mApplicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    public static int dp2px(float dpValue) {
        final float scale = BaseApplication.mApplicationContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(float pxValue) {
        final float scale = BaseApplication.mApplicationContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, BaseApplication.mApplicationContext.getResources().getDisplayMetrics());
    }

    /**
     * 获取手机屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        DisplayMetrics displayMetrics = BaseApplication.mApplicationContext.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取手机屏幕的高度
     *
     * @return
     */
    public static int getScreenHeight() {
        DisplayMetrics displayMetrics = BaseApplication.mApplicationContext.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 获取字符串的高度
     *
     * @param string
     * @param paint
     * @return
     */
    public static int getStringHeight(String string, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(string, 0, string.length(), rect);
        return rect.height();
    }

    /**
     * 获取字符串的宽度
     *
     * @param string
     * @param paint
     * @return
     */
    public static int getStringWidth(String string, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(string, 0, string.length(), rect);
        return rect.width();
    }

    /**
     * 获取通知栏高度
     *
     * @return
     */
    public static int getNotificationBarHeight() {
        Resources system = Resources.getSystem();
        return system.getDimensionPixelSize(system.getIdentifier("status_bar_height", "dimen", "android"));
    }

    /**
     * 判断Activity是否存在
     *
     * @param activity
     * @return
     */
    public static boolean isActivityRunning(Activity activity) {
        return activity != null && !activity.isDestroyed() && !activity.isFinishing();
    }

    /**
     * 将Bitmap转为byte[]
     *
     * @param bm
     * @return
     */
    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    /**
     * 将byte[]转为Bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }


    /**
     * 缩放Bitmap
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }


    /**
     * 将drawable转为Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }


    /**
     * 将Bitmap转为Drawable
     *
     * @param context
     * @param bitmap
     * @return
     */
    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }


    /**
     * 将Bitmap转变为圆角图片
     *
     * @param bitmap
     * @param roundPx
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }


    /**
     * 从资源图片中获取Drawable
     *
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getDrawableFromResource(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }


    /**
     * 缩放Drawable
     *
     * @param drawable
     * @param w
     * @param h
     * @return
     */
    public static Drawable zoomDrawable(Context context, Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        // drawable转换成bitmap
        Bitmap oldbmp = drawableToBitmap(drawable);
        // 创建操作图片用的Matrix对象
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float sx = ((float) w / width);
        float sy = ((float) h / height);
        // 设置缩放比例
        matrix.postScale(sx, sy);
        // 建立新的bitmap，其内容是对原bitmap的缩放后的图
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(context.getResources(), newbmp);
    }


    /**
     * 根据文件路径将文件中的内容读取成StringBuilder
     *
     * @param filePath
     * @param charsetName
     * @return
     */
    public static StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (!file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 根据文件路径以及String内容创建文件
     *
     * @param filePath
     * @param content
     * @param append   是否追加
     * @return
     */
    public static boolean writeFile(String filePath, String content, boolean append) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 根据文件路径以及List<String>内容创建文件
     *
     * @param filePath
     * @param contentList
     * @param append      是否追加
     * @return
     */
    public static boolean writeFile(String filePath, List<String> contentList, boolean append) {
        if (contentList.size() == 0) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            int i = 0;
            for (String line : contentList) {
                if (i++ > 0) {
                    fileWriter.write("\r\n");
                }
                fileWriter.write(line);
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件夹
     *
     * @param root
     */
    public static void deleteFile(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteFile(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteFile(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }


    private static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) || folder.mkdirs();
    }


    /**
     * 根据文件路径返回文件夹名
     *
     * @param filePath
     * @return
     */
    public static String getFolderName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }


    /**
     * 判断文件夹是否存在
     *
     * @param directoryPath
     * @return
     */
    public static boolean isFolderExist(String directoryPath) {
        if (isBlank(directoryPath)) {
            return false;
        }
        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }


    /**
     * 判断文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        if (isBlank(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    private static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    private static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }


    /**
     * 获取每个应用程序的最高可用内存
     *
     * @return
     */
    public static int maxMemory() {
        return (int) (Runtime.getRuntime().maxMemory() / 1024);
    }


    /**
     * 获取name对应的AndroidManifest中的meta-data值
     *
     * @param name
     * @return
     */
    public static String getManifestMetaData(String name) {
        try {
            PackageManager packageManager = BaseApplication.mApplicationContext.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(BaseApplication.mApplicationContext.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo == null) {
                return "";
            } else {
                Bundle bundle = applicationInfo.metaData;
                if (bundle == null) {
                    return "";
                }
                String result = bundle.getString(name);
                if (result == null) {
                    return "";
                } else {
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 获取当前时间 格式 21:22:40
     *
     * @return
     */
    public static String getCurrentTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);//01:00:00~10:00:00
        return format.format(date);
    }


    /**
     * 2017-04-25 15:27
     *
     * @return
     */
    public static String getFullCurrentTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);//01:00:00~10:00:00
        return format.format(date);
    }

    /**
     * 获取当前时间毫秒值since jan 1, 1970
     *
     * @return
     */
    public static long getCurrentTimeMiles() {
        return System.currentTimeMillis();
    }


    /**
     * 得到二个日期间的间隔天数
     *
     * @param date1 yyyy-MM-dd HH:mm:ss
     * @param date2 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getTwoDayDiatance(String date1, String date2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            java.util.Date date = myFormatter.parse(date1);
            java.util.Date mydate = myFormatter.parse(date2);
            long l = Math.abs(date.getTime() - mydate.getTime());
            day = (l) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return "";
        }
        return day + "";
    }

    /**
     * 根据毫秒值转换为日期
     *
     * @param miles
     * @return
     */
    public static String transMilesToDate(long miles) {
        Date date = new Date(miles);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return format.format(date);
    }


    public static String getYear() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy", Locale.CHINA);//01:00:00~10:00:00
        return format.format(date);
    }

    public static String getMonth() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("MM", Locale.CHINA);//01:00:00~10:00:00
        return format.format(date);
    }

    public static String getDay() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("dd", Locale.CHINA);//01:00:00~10:00:00
        return format.format(date);
    }


    /**
     * 判断当天是否是双休日
     *
     * @return
     */
    public static boolean isTodayWeekend() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int i = cal.get(Calendar.DAY_OF_WEEK);//1 ,0
        return i == 0 || i == 1;
    }


    /**
     * 当前时间是否是上班时间段内：09~16
     *
     * @return
     */
    public static boolean isWorkTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);//01:00:00~10:00:00
        String time = format.format(date);
        String substring = time.substring(0, 2);
        if (substring.substring(0, 1).equals("0")) {
            String substring1 = substring.substring(1, 2);
            return substring1.equals("9");
        } else if (substring.substring(0, 1).equals("1")) {
            String substring2 = substring.substring(1, 2);
            return !(substring2.equals("9") || substring2.equals("8"));
        }
        return false;
    }

    /**
     * 禁用复制粘贴功能
     *
     * @param editText
     */
    public static void disableEditTextCopyAndPaste(EditText editText) {
        editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        editText.setLongClickable(false);
    }


    /**
     * 设置TabLayout下划线长度
     *
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public static void setIndicatorLine(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    /**
     * 获取清单文件中Activity标签下的label
     *
     * @param activity
     * @return
     */
    public static String getManifestActivityLabel(Activity activity) {
        String label;
        try {
            PackageManager packageManager = activity.getPackageManager();
            ActivityInfo activityInfo = packageManager.getActivityInfo(activity.getComponentName(), PackageManager.GET_META_DATA);
            label = activityInfo.loadLabel(packageManager).toString();
        } catch (Exception e) {
            label = null;
        }
        return label;
    }

    /**
     * 获取sha1值
     *
     * @return
     */
    public static String getSHA1() {
        try {
            PackageInfo info = BaseApplication.mApplicationContext.getPackageManager().getPackageInfo(BaseApplication.mApplicationContext.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 判断当前线程是否是主线程
     *
     * @return
     */
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }


}
