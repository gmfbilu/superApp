package org.gmfbilu.superapp.lib_base.base.mvp;

import android.os.Bundle;

import androidx.annotation.NonNull;

import org.gmfbilu.superapp.lib_base.base.BaseActivity;


public abstract class BaseMVPActivity<V extends BaseView, P extends BasePresenter<V>>
        extends BaseActivity implements BaseView, DelegateCallBack<V, P> {


    protected ActivityMvpDelegate mvpDelegate;
    protected boolean retainInstance;
    protected P mPresenter;


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
}
