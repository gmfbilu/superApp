package org.gmfbilu.superapp.lib_base.base.mvp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegate;
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegateImpl;

import org.gmfbilu.superapp.lib_base.R;
import org.gmfbilu.superapp.lib_base.base.LazyLoadFragment;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.lib_base.view.LoadingLayout;


public abstract class MVPFragment  <V extends MvpView, P extends MvpPresenter<V>>
        extends LazyLoadFragment implements MvpView,
        com.hannesdorfmann.mosby3.mvp.delegate.MvpDelegateCallback<V,P>{

    protected FragmentMvpDelegate<V, P> mvpDelegate;
    private boolean isOnCreate;

    private LoadingLayout mLoadingLayout;
    /**
     * 请求成功后，刷新或者第二次请求的时候出现失败的情况
     */
    private boolean isRequestSucceed;

    @Override
    public void lazyLoad() {
        if (!isOnCreate) {
            onCreate();
        }
    }

    protected abstract void onCreate();

    @Override
    public void stopLoad() {
        super.stopLoad();
        isOnCreate = true;
    }

    protected P presenter;


    @NonNull
    public abstract P createPresenter();


    @NonNull
    protected FragmentMvpDelegate<V, P> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new FragmentMvpDelegateImpl<>(this, this, true, true);
        }

        return mvpDelegate;
    }

    @NonNull @Override public P getPresenter() {
        return presenter;
    }

    @Override public void setPresenter(@NonNull P presenter) {
        this.presenter = presenter;
    }

    @NonNull @Override public V getMvpView() {
        return (V) this;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMvpDelegate().onViewCreated(view, savedInstanceState);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        getMvpDelegate().onDestroyView();
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }

    @Override public void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override public void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override public void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override public void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMvpDelegate().onActivityCreated(savedInstanceState);
    }

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        getMvpDelegate().onAttach(activity);
    }

    @Override public void onDetach() {
        super.onDetach();
        getMvpDelegate().onDetach();
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpDelegate().onSaveInstanceState(outState);
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



    /**
     * 加载数据对话框
     */
    private Dialog mLoadingDialog;

    public Dialog showDialogForLoading() {

        return showDialogForLoading(getActivity(), null);
    }

    public Dialog showDialogForLoading(Context context, String msg) {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.cancel();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.content_dialog_loading, null);
        TextView loadingText = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);
        if (TextUtils.isEmpty(msg)) {
            loadingText.setText("加载中...");
        } else {
            loadingText.setText(msg);
        }


        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        return mLoadingDialog;
    }

    /**
     * 关闭加载对话框
     */
    public void cancelDialogForLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.cancel();
        }
    }

}
