package org.gmfbilu.superapp.lib_base.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;


public abstract class BottomNavigationBaseFragment extends BaseFragment {

    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        init();
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 说明：创建视图时的初始化操作均写在该方法
     */
    protected abstract void init();

    /**
     * @return 返回该Fragment的layout id
     */
    protected abstract int getLayoutId();




    /**
     * 说明：返回当前View
     *
     * @return view
     */
    protected View getContentView() {
        return mRootView;
    }


}
