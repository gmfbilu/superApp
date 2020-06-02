package org.gmfbilu.neteasycloudcourse.ui.屏幕适配;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.gmfbilu.neteasycloudcourse.R;
import org.gmfbilu.neteasycloudcourse.ui.屏幕适配.网易云适配.ViewCalculateUtil;
import org.gmfbilu.neteasycloudcourse.ui.屏幕适配.自定义像素.PixelShiPeiUtil;
import org.gmfbilu.superapp.lib_base.base.BaseApplication;
import org.gmfbilu.superapp.lib_base.base.BaseFragment;

public class ScreenShiPeiFragment extends BaseFragment {

    public static ScreenShiPeiFragment newInstance() {
        Bundle args = new Bundle();
        ScreenShiPeiFragment fragment = new ScreenShiPeiFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        TextView tv = view.findViewById(R.id.tv);
        PixelShiPeiUtil.getInstance(BaseApplication.mApplicationContext);
        ViewCalculateUtil.setViewConstraintLayoutParam(tv, 540, 100, 0, 0, 0, 0, true);
        ViewCalculateUtil.setTextSize(tv, 50);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_ui_screenshipei;
    }

    @Override
    public void onClick(View v) {

    }
}
