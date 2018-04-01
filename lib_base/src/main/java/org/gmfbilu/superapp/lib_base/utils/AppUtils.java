package org.gmfbilu.superapp.lib_base.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import com.leon.channel.helper.ChannelReaderUtil;
import com.orhanobut.logger.Logger;

import org.gmfbilu.superapp.lib_base.app.Constant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
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
        ActivityManager manager = (ActivityManager) Utils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = Objects.requireNonNull(manager).getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                Logger.d(info.processName);
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
        PackageManager packageManager = Utils.getContext().getPackageManager();
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
     * 获取渠道名
     *
     * @return
     */
    public static String getChannelName() {
        String name = ChannelReaderUtil.getChannel(Utils.getContext());
        if (TextUtils.isEmpty(name)) {
            name = Constant.BASE_CHANNEL;
        }
        return name;
    }

}
