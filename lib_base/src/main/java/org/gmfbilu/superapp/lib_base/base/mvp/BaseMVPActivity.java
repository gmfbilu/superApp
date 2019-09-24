package org.gmfbilu.superapp.lib_base.base.mvp;

import android.os.Bundle;

import androidx.annotation.NonNull;


import org.gmfbilu.superapp.lib_base.base.BaseActivity;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.lib_base.view.LoadingLayout;


public abstract class BaseMVPActivity<V extends BaseView, P extends BasePresenter<V>>
        extends BaseActivity implements BaseView, DelegateCallBack<V, P> {


    protected ActivityMvpDelegate mvpDelegate;
    protected boolean retainInstance;
    protected P mPresenter;
    private LoadingLayout mLoadingLayout;


    /**
     * 请求成功后，刷新或者第二次请求的时候出现失败的情况
     */
    private boolean isRequestSucceed;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override protected void onDestroy() {
        getMvpDelegate().onDestroy();
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpDelegate().onSaveInstanceState(outState);
    }

    @Override public void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override public void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override protected void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override protected void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override protected void onRestart() {
        super.onRestart();
        getMvpDelegate().onRestart();
    }

    @Override public void onContentChanged() {
        super.onContentChanged();
        getMvpDelegate().onContentChanged();
    }

    @Override protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getMvpDelegate().onPostCreate(savedInstanceState);
    }


    @NonNull
    public abstract P createPresenter();


    @NonNull
    protected ActivityMvpDelegate<V, P> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new ActivityMvpDelegateImpl(this, this, true);
        }

        return mvpDelegate;
    }

    @NonNull
    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(@NonNull P presenter) {
        this.mPresenter = presenter;
    }

    @NonNull
    @Override
    public V getMvpView() {
        return (V) this;
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

}
