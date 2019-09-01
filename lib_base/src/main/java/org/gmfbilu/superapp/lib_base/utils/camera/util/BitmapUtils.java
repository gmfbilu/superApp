package org.gmfbilu.superapp.lib_base.utils.camera.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okio.Okio;

public class BitmapUtils {

    private BitmapUtils() {
    }

    public static byte[] toByteArray(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        return os.toByteArray();
    }

    public static Bitmap mirror(Bitmap rawBitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(-1f, 1f);
        return Bitmap.createBitmap(rawBitmap, 0, 0, rawBitmap.getWidth(), rawBitmap.getHeight(), matrix, true);
    }

    public static Bitmap rotate(Bitmap rawBitmap, Float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(rawBitmap, 0, 0, rawBitmap.getWidth(), rawBitmap.getHeight(), matrix, true);
    }

    public static Bitmap decodeBitmap(Bitmap bitmap, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.size(), options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.size(), options);
    }

    public static Bitmap decodeBitmapFromFile(String path, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap decodeBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int rawWidth = options.outWidth;
        int rawHeight = options.outHeight;
        int inSampleSize = 1;
        if (rawWidth > reqWidth || rawHeight > reqHeight) {
            int halfWidth = rawWidth / 2;
            int halfHeight = rawHeight / 2;
            while ((halfWidth / inSampleSize) > reqWidth && (halfHeight / inSampleSize) > reqHeight) {
                inSampleSize *= 2; //设置inSampleSize为2的幂是因为解码器最终还是会对非2的幂的数进行向下处理，获取到最靠近2的幂的数
            }
        }
        return inSampleSize;
    }

    public static void savePic(byte[] data, boolean isMirror, SavePicInterface savePicInterface) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long temp = System.currentTimeMillis();
                    File picFile = FileUtil.createCameraFile("camera2");
                    if (picFile != null && data != null) {
                        Bitmap rawBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        Bitmap resultBitmap = isMirror ? mirror(rawBitmap) : rawBitmap;
                        Okio.buffer(Okio.sink(picFile)).write(toByteArray(resultBitmap)).close();
                        savePicInterface.onSuccess(picFile.getAbsolutePath(), System.currentTimeMillis() - temp+"");
                        LoggerUtil.d("图片已保存! 耗时：" + (System.currentTimeMillis() - temp) + "   路径：" + picFile.getAbsolutePath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    savePicInterface.onFailed(e.getMessage());
                }
            }
        }).start();
    }


    public interface SavePicInterface {
        void onSuccess(String savedPath, String time);

        void onFailed(String message);
    }

}
