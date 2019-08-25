package org.gmfbilu.superapp.lib_base.utils.camera.util;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtil {

    //"CameraDemo"就是xml/path文件下中的 <external-path name="external-path" path="CameraDemo" />
    private final static String rootFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CameraDemo";

    private FileUtil() {
    }

    public static File createImageFile(boolean isCrop) {
        File rootFile;
        try {
            rootFile = new File(rootFolderPath + File.separator + "capture");
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = isCrop ? "IMG_" + timeStamp + "_CROP.jpg" : "IMG_" + timeStamp + ".jpg";
            rootFile = new File(rootFile.getAbsolutePath() + File.separator + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            rootFile = null;
        }
        return rootFile;
    }

    public static File createCameraFile(String folderName) {
        File rootFile;
        try {
            rootFile = new File(rootFolderPath + File.separator + folderName);
            if (!rootFile.exists())
                rootFile.mkdirs();

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "IMG_" + timeStamp + ".jpg";
            rootFile = new File(rootFile.getAbsoluteFile() + File.separator + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            rootFile = null;
        }
        return rootFile;
    }

    public static File createVideoFile() {
        File rootFile;
        try {
            rootFile = new File(rootFolderPath + File.separator + "video");
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }

            String timeStamp = (new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.CHINA)).format(new Date());
            String fileName = "VIDEO_" + timeStamp + ".mp4";
            rootFile = new File(rootFile.getAbsolutePath() + File.separator + fileName);
        }  catch (Exception e) {
            e.printStackTrace();
            rootFile = null;
        }
        return rootFile;
    }
}
