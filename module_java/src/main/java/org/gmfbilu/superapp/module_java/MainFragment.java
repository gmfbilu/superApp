package org.gmfbilu.superapp.module_java;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.rxbus.RxBus;
import org.gmfbilu.superapp.module_java.retrofit_rxjava.RxJava_RetrofitFragment;
import org.gmfbilu.superapp.module_java.rxJava.RxJavaFragment;
import org.gmfbilu.superapp.module_java.webSocket.WebSocketFragment;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by gmfbilu on 18-3-11.
 * 主界面Fragment,根Fragment
 */


public class MainFragment extends BaseFragment {


    private CompositeDisposable compositeDisposable;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.module_java_bt_rxjava).setOnClickListener(this);
        view.findViewById(R.id.module_java_bt_rxjava_retrofit).setOnClickListener(this);
        view.findViewById(R.id.module_java_bt_webSocket).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_java_fragment_main;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.module_java_bt_rxjava) {
            start(RxJavaFragment.newInstance());
        } else if (id == R.id.module_java_bt_rxjava_retrofit) {
            start(RxJava_RetrofitFragment.newInstance());
        } else if (id == R.id.module_java_bt_webSocket) {
            start(WebSocketFragment.newInstance());
        }
    }


    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        compositeDisposable = new CompositeDisposable();

        /**
         * 1.Rxbus订阅事件
         */
        RxBus.getDefault().subscribe(this, new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {

            }

        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unregister(this);
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
