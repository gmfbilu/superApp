package org.gmfbilu.superapp.lib_base.utils.camera.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;

import java.lang.ref.WeakReference;

public class DecodeImgTask extends AsyncTask<String, Integer, Bitmap> {

    private WeakReference<ImageView> imageViewReference;
    private long temp = 0L;

    public DecodeImgTask(ImageView imageView) {
        imageViewReference = new WeakReference(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        temp = System.currentTimeMillis();
        return BitmapFactory.decodeFile(strings[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            ImageView o = imageViewReference.get();
            if (o != null) {
                Bitmap compressBitmap = BitmapUtils.decodeBitmap(bitmap, 720, 1080);
                o.setImageBitmap(compressBitmap);
                LoggerUtil.d("原始图片大小:" + AppUtils.getSize(AppUtils.getBitmapSize(bitmap)) + "\n 压缩后图片大小:" + AppUtils.getSize(AppUtils.getBitmapSize(compressBitmap)) + "\n 加载图片耗时:" + (System.currentTimeMillis() - temp) + "毫秒");
            }
        }
        super.onPostExecute(bitmap);
    }
}
