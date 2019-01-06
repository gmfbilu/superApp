package org.gmfbilu.superapp.module_view.switch_checkbox_listpreference;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.module_view.R;

public class CustomDialog extends Dialog {

    private Context mContext;

    public CustomDialog(@NonNull Context context) {
        super(context, R.style.custom_dialog);
        mContext = context;
    }

    public CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    public View setCalanderLayout() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.module_view_dialog_double_date, null);
        Window win = this.getWindow();
        int dp2px = AppUtils.dp2px(30);
        if (win != null) {
            win.getDecorView().setPadding(dp2px, 0, dp2px, 0);
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


    public View setLocationLinkageLayout() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.module_view_dialog_location_linkage, null);
        Window win = this.getWindow();
        int dp2px = AppUtils.dp2px(30);
        if (win != null) {
            win.getDecorView().setPadding(dp2px, 0, dp2px, 0);
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


    public View setSearchLayout() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.module_view_dialog_search, null);
        Window win = this.getWindow();
        int dp2px = AppUtils.dp2px(30);
        if (win != null) {
            win.getDecorView().setPadding(dp2px, 0, dp2px, 0);
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

}
