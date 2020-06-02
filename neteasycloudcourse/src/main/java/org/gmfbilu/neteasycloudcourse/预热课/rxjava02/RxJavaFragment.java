package org.gmfbilu.neteasycloudcourse.预热课.rxjava02;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.neteasycloudcourse.R;
import org.gmfbilu.superapp.lib_base.base.BaseFragment;

/**
 * 标准的观察者模式：多个观察者(警察)，一个被观察者(小偷)，需要主动发出改变通知观察者
 * Rxjava是变异的观察者 只有一个观察者(警察)，一个被观察者(小偷)，订阅就能发出改变通知观察者
 */
public class RxJavaFragment extends BaseFragment {

    public static RxJavaFragment newInstance() {
        Bundle args = new Bundle();
        RxJavaFragment fragment = new RxJavaFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_beforecoure_rxjava;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        //多个警察，多个观察者
        ObserverImpl observer1 = new ObserverImpl();
        ObserverImpl observer2 = new ObserverImpl();
        ObserverImpl observer3 = new ObserverImpl();
        ObserverImpl observer4 = new ObserverImpl();
        //小偷 一个被观察者
        ObservableImpl observable = new ObservableImpl();
        //订阅
        observable.registerObserver(observer1);
        observable.registerObserver(observer2);
        observable.registerObserver(observer3);
        observable.registerObserver(observer4);
        //小偷开始作案
        observable.changeObservers();
    }
}
