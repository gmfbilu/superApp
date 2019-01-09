package org.gmfbilu.superapp.lib_base.view.dialog;


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.gmfbilu.superapp.lib_base.R;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;

public class DialogHelper extends Dialog {

    private Context mContext;

    public DialogHelper(@NonNull Context context) {
        super(context, R.style.dialog_helper);
        mContext = context;
    }

    public DialogHelper(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    /**
     * @param l
     * @param t
     * @param r
     * @param b
     * @param layoutId
     * @return
     */
    private View defaultSetting(int l, int t, int r, int b, int layoutId) {
        View view = LayoutInflater.from(mContext).inflate(layoutId, null);
        Window win = this.getWindow();
        if (win != null) {
            win.getDecorView().setPadding(l, t, r, b);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);
            win.setGravity(Gravity.CENTER);
            this.setContentView(view);
            this.setCanceledOnTouchOutside(true);
        }
        return view;
    }

    public View setCalendarLayout(int layoutId) {
        int dp2px = AppUtils.dp2px(mContext, 45);
        return defaultSetting(dp2px, 0, dp2px, 0, layoutId);
    }

    public View setLocationLinkageLayout(int layoutId) {
        int dp2px = AppUtils.dp2px(mContext, 30);
        return defaultSetting(dp2px, 0, dp2px, 0, layoutId);
    }

    public View setSearchLayout(int layoutId) {
        int dp2px = AppUtils.dp2px(mContext, 30);
        return defaultSetting(dp2px, 0, dp2px, 0, layoutId);
    }


    /**
     * dialog指定在view下面
     *
     * @param layoutId
     * @param v
     * @return
     */
    public View setSearch_Local_All_Layout(int layoutId, View v) {
        int notificationBar = AppUtils.getNotificationBarHeight();
        //获取控件 cl_all 的绝对坐标,( y 轴坐标是控件上部到屏幕最顶部（不包括控件本身）)
        //location [0] 为x绝对坐标;location [1] 为y绝对坐标
        int[] location = new int[2];
        //获取在当前窗体内的绝对坐标
        v.getLocationInWindow(location);
        //获取在整个屏幕内的绝对坐标
        v.getLocationOnScreen(location);
        //对dialog设置y轴坐标
        int y = location[1] + v.getHeight() - notificationBar;
        int screenHeight = AppUtils.getScreenHeight(mContext);
        View view = LayoutInflater.from(mContext).inflate(layoutId, null);
        Window win = this.getWindow();
        if (win != null) {
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.x = 0;
            lp.y = y;
            lp.gravity = Gravity.TOP;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            //dialog的高度最高不能超过这个
            lp.height = screenHeight - y - notificationBar;
            //去掉dialog的背景阴影
            win.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            win.setAttributes(lp);
            this.setContentView(view);
            this.setCanceledOnTouchOutside(true);
        }
        return view;
    }


}
