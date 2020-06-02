package org.gmfbilu.neteasycloudcourse.ui.屏幕适配.自定义像素;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 自定义像素适配
 * 子控件再xml中直接使用px就可以
 */
public class PixelShiPeiUtil {

    private static PixelShiPeiUtil util;

    //设计稿参考宽高，单位px
    private static final float STANDARD_WIDTH = 1080;
    private static final float STANDARD_HEIGHT = 1920;

    //屏幕显示宽高
    private int mDisplayWidth;
    private int mDisplayHeight;

    private PixelShiPeiUtil(Context context) {
        if (mDisplayWidth == 0 || mDisplayHeight == 0) {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (manager != null) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                manager.getDefaultDisplay().getMetrics(displayMetrics);//忽略掉底部导航栏高度的，getRealMetrics
                //得到1920*1080
                if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
                    //横屏
                    mDisplayWidth = displayMetrics.heightPixels;
                    mDisplayHeight = displayMetrics.widthPixels;
                } else {
                    //竖屏
                    mDisplayWidth = displayMetrics.widthPixels;
                    mDisplayHeight = displayMetrics.heightPixels - getStatusBarHeight(context);
                }
            }
        }
    }

    public int getStatusBarHeight(Context context) {
        int resID = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resID > 0) {
            return context.getResources().getDimensionPixelSize(resID);
        }
        return 0;
    }

    public static PixelShiPeiUtil getInstance(Context context) {
        if (util == null) {
            util = new PixelShiPeiUtil(context);
        }
        return util;
    }


    //获取水平方向的缩放比例
    public float getHorizontalScale() {
        return mDisplayWidth / STANDARD_WIDTH;
    }


    //获取垂直方向的缩放比例
    public float getVerticalScale() {
        return mDisplayHeight / STANDARD_HEIGHT;
    }

    public int getWidth(int width) {
        return Math.round((float) width * this.mDisplayWidth / STANDARD_WIDTH);
    }

    public int getHeight(int height) {
        return Math.round((float) height * this.mDisplayWidth / STANDARD_HEIGHT);
    }
}
