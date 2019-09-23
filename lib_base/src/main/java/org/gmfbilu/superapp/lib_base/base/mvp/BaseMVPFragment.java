package org.gmfbilu.superapp.lib_base.base.mvp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import org.gmfbilu.superapp.lib_base.base.LazyLoadFragment;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.lib_base.view.LoadingLayout;

public abstract class BaseMVPFragment<V extends BaseView, P extends BasePresenter<V>> extends LazyLoadFragment implements BaseDelegate<V, P> {

    private boolean isOnCreate;
    protected P mPresenter;
    private LoadingLayout mLoadingLayout;
    /**
     * 请求成功后，刷新或者第二次请求的时候出现失败的情况
     */
    private boolean isRequestSucceed;


    @Override
    public void lazyLoad() {
        if (!isOnCreate) {
            mPresenter = createPresenter();
            onCreate();
        }
    }

    protected abstract void onCreate();

    @NonNull
    @Override
    public P getPresenter() {
        return mPresenter;
    }

    public abstract void getData();

    /**
     * 必须在initViews() setContentView(R.layout.activity_);ButterKnife.bind(this);后调用
     *
     * @param loadingLayout
     */
    protected void initLoadingLayout(LoadingLayout loadingLayout) {
        mLoadingLayout = loadingLayout;
        if (AppUtils.isNetworkConnected()) {
            getData();
        } else {
            if (mLoadingLayout != null) {
                mLoadingLayout.setStatus(LoadingLayout.No_Network);
                mLoadingLayout.setOnReloadListener(v -> initLoadingLayout(mLoadingLayout));
            }
        }
    }

    /**
     * 网络请求开始
     */
    protected void requestBeginning() {
        if (!isRequestSucceed) {
            if (mLoadingLayout != null) {
                mLoadingLayout.setStatus(LoadingLayout.Loading);
            }
        }
    }


    /**
     * 网络请求成功
     */
    protected void requestSucceed() {
        if (mLoadingLayout != null) {
            mLoadingLayout.setStatus(LoadingLayout.Success);
            isRequestSucceed = true;
        }
    }

    /**
     * 请求失败，最好是单个页面只有一个网络请求的情况下使用
     */
    protected void requestFailed() {
        if (!isRequestSucceed) {
            if (mLoadingLayout != null) {
                mLoadingLayout.setStatus(LoadingLayout.Error);
                mLoadingLayout.setOnErrorListener(v -> initLoadingLayout(mLoadingLayout));
            }
        }
    }

    @Override
    public void stopLoad() {
        super.stopLoad();
        isOnCreate = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter=null;
        }
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

}
