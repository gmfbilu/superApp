package org.gmfbilu.neteasycloudcourse.ui.屏幕适配.网易云适配;

import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.gmfbilu.neteasycloudcourse.ui.屏幕适配.自定义像素.PixelShiPeiUtil;
import org.gmfbilu.superapp.lib_base.base.BaseApplication;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ViewCalculateUtil {

    //字体适配,修改的是高度，单位像素
    public static void setTextSize(TextView view, int size) {
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getHeight(size));
    }

    //单独针对某个控件进行适配，asWidth：判断是否依据宽度进行适配，保证宽高不变形。
    public static void setViewRelativeLayoutParam(View view, int width, int height, int topMargin, int rightMargin, int bottomMargin, int leftMargin, boolean asWidth) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams != null) {
            if (width != RelativeLayout.LayoutParams.MATCH_PARENT && width != RelativeLayout.LayoutParams.WRAP_CONTENT && width != RelativeLayout.LayoutParams.FILL_PARENT) {
                layoutParams.width = PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getWidth(width);
            } else {
                layoutParams.width = width;
            }

            if (height != RelativeLayout.LayoutParams.MATCH_PARENT && height != RelativeLayout.LayoutParams.WRAP_CONTENT && height != RelativeLayout.LayoutParams.FILL_PARENT) {
                layoutParams.height = asWidth ? PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getWidth(height) : PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getHeight(height);
            } else {
                layoutParams.height = height;
            }

            layoutParams.topMargin = asWidth ? PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getWidth(topMargin) : PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getHeight(topMargin);
            layoutParams.rightMargin = PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getHeight(rightMargin);
            layoutParams.bottomMargin = asWidth ? PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getWidth(bottomMargin) : PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getHeight(bottomMargin);
            layoutParams.leftMargin = PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getWidth(leftMargin);

            view.setLayoutParams(layoutParams);
        }
    }


    //单独针对某个控件进行适配，asWidth：判断是否依据宽度进行适配，保证宽高不变形
    public static void setViewLinearLayoutParam(View view, int width, int height, int topMargin, int rightMargin, int bottomMargin, int leftMargin, boolean asWidth) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams != null) {
            if (width != LinearLayout.LayoutParams.MATCH_PARENT && width != LinearLayout.LayoutParams.WRAP_CONTENT && width != LinearLayout.LayoutParams.FILL_PARENT) {
                layoutParams.width = PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getWidth(width);
            } else {
                layoutParams.width = width;
            }

            if (height != LinearLayout.LayoutParams.MATCH_PARENT && height != LinearLayout.LayoutParams.WRAP_CONTENT && height != LinearLayout.LayoutParams.FILL_PARENT) {
                layoutParams.height = asWidth ? PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getWidth(height) : PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getHeight(height);
            } else {
                layoutParams.height = height;
            }

            layoutParams.topMargin = asWidth ? PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getWidth(topMargin) : PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getHeight(topMargin);
            layoutParams.rightMargin = PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getHeight(rightMargin);
            layoutParams.bottomMargin = asWidth ? PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getWidth(bottomMargin) : PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getHeight(bottomMargin);
            layoutParams.leftMargin = PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getWidth(leftMargin);

            view.setLayoutParams(layoutParams);
        }
    }

    //单独针对某个控件进行适配，asWidth：判断是否依据宽度进行适配，保证宽高比不变
    public static void setViewConstraintLayoutParam(View view, int width, int height, int topMargin, int rightMargin, int bottomMargin, int leftMargin, boolean asWidth) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams != null) {
            if (width != ConstraintLayout.LayoutParams.MATCH_PARENT && width != ConstraintLayout.LayoutParams.WRAP_CONTENT && width != ConstraintLayout.LayoutParams.FILL_PARENT) {
                layoutParams.width = PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getWidth(width);
            } else {
                layoutParams.width = width;
            }

            if (height != ConstraintLayout.LayoutParams.MATCH_PARENT && height != ConstraintLayout.LayoutParams.WRAP_CONTENT && height != ConstraintLayout.LayoutParams.FILL_PARENT) {
                layoutParams.height = asWidth ? PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getWidth(height) : PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getHeight(height);
            } else {
                layoutParams.height = height;
            }

            layoutParams.topMargin = asWidth ? PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getWidth(topMargin) : PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getHeight(topMargin);
            layoutParams.rightMargin = PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getHeight(rightMargin);
            layoutParams.bottomMargin = asWidth ? PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getWidth(bottomMargin) : PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getHeight(bottomMargin);
            layoutParams.leftMargin = PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext).getWidth(leftMargin);

            view.setLayoutParams(layoutParams);
        }
    }


}
