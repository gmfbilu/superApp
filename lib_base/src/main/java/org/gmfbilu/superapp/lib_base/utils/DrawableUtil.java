package org.gmfbilu.superapp.lib_base.utils;


/**
 * Glide.with(Application/Activity/Fragment)，参数的生命周期决定图片加载的生命周期。生命周期范围越小越好
 * <p>
 * Glide.with.load(SD卡资源/assets资源/raw资源/drawable资源/ContentProvider资源/http资源/Uri/File/resourceId/URL/byte[]/T/)
 * SD卡资源:"file://Environment.getExternalStorageDirectory().getPath()+/test.jpg"
 * assets资源:"file:///android_asset/test.gif"
 * raw资源:""android.resource://org.example/raw/test
 * drawable资源:""
 * ContentProvider资源:"content://media/external/images/media/test"
 * <p>
 * 禁止内存缓存：.skipMemoryCache(true)
 * 清除内存缓存：Glide.get().clearMemory(),必须在UI线程中调用
 * 禁止磁盘缓存：.diskCacheStrategy(DiskCacheStrategy.NONE)
 * 清除磁盘缓存：Glide.get().clearDiskCache(),必须在子线程中调用，建议同时清除内存缓存
 * 获取缓存的大小：
 * 对指定资源的优先加载顺序：priority()
 * 先显示缩略图，再显示原图:thumbnail(0.1f),使用原图的十分之一作为缩略图。DrawableRequestBuild<Integer> thumb = Glide.with().load(),.thumbnail(thumb),使用其它图作为缩略图
 * 对图片进行裁剪，模糊，滤镜处理：bitmapTransform()
 * 对请求状态进行监听
 * 对下载的资源进度进行监听
 */
public class DrawableUtil {

    private DrawableUtil() {
    }


}
