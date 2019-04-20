package org.gmfbilu.superapp.module_view.customViews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.http.NetObserver;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.customViews.canvasView.LeafLoading;
import org.gmfbilu.superapp.module_view.customViews.stepView.StepViewOne;
import org.gmfbilu.superapp.module_view.customViews.stepView.StepViewOneBean;
import org.gmfbilu.superapp.module_view.customViews.stepView.StepViewTwo;
import org.gmfbilu.superapp.module_view.customViews.stepView.StepViewTwoBean;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gmfbilu on 2018/3/27.
 */

public class CustomViewsFragment extends BaseFragment {

    private Toolbar mToolbar;
    private StepViewOne mStepViewOne;
    private StepViewTwo mStepViewTwo;
    private LeafLoading mLeafLoading;

    public static CustomViewsFragment newInstance() {
        Bundle args = new Bundle();
        CustomViewsFragment fragment = new CustomViewsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        mToolbar = view.findViewById(R.id.module_view_toolbar);
        mStepViewOne = view.findViewById(R.id.StepViewOne);
        mStepViewTwo = view.findViewById(R.id.StepViewTwo);
        mLeafLoading = view.findViewById(R.id.LeafLoading);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_custom_views;
    }

    /**
     * 所以在onActivityCreated进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToolbar.setNavigationIcon(R.mipmap.lib_base_ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
        mToolbar.setTitle("generalView!");
    }

    /**
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     *
     * @param savedInstanceState
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        Observable
                //2秒后执行
                .timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        ArrayList<StepViewOneBean> stepViewOneBeans = new ArrayList<>();
                        StepViewOneBean stepViewOneBean1 = new StepViewOneBean();
                        stepViewOneBean1.city = "北京市";
                        stepViewOneBean1.statusName = "已发货";
                        stepViewOneBeans.add(stepViewOneBean1);
                        StepViewOneBean stepViewOneBean2 = new StepViewOneBean();
                        stepViewOneBean2.statusName = "运输中";
                        stepViewOneBeans.add(stepViewOneBean2);
                        StepViewOneBean stepViewOneBean6 = new StepViewOneBean();
                        stepViewOneBean6.statusName = "运输中";
                        stepViewOneBeans.add(stepViewOneBean6);
                        StepViewOneBean stepViewOneBean3 = new StepViewOneBean();
                        stepViewOneBean3.statusName = "派送中";
                        stepViewOneBean3.status = true;
                        stepViewOneBeans.add(stepViewOneBean3);
                        StepViewOneBean stepViewOneBean5 = new StepViewOneBean();
                        stepViewOneBean5.statusName = "出问题啦";
                        stepViewOneBean5.city = "兰州";
                        stepViewOneBean5.status = true;
                        stepViewOneBeans.add(stepViewOneBean5);
                        StepViewOneBean stepViewOneBean4 = new StepViewOneBean();
                        stepViewOneBean4.statusName = "已签收";
                        stepViewOneBean4.city = "深圳市";
                        stepViewOneBeans.add(stepViewOneBean4);
                        mStepViewOne.setData(stepViewOneBeans);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Observable
                //2秒后执行
                .timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        super.onNext(aLong);
                        ArrayList<StepViewTwoBean> stepViewTwoBeans = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            StepViewTwoBean stepViewTwoBean1 = new StepViewTwoBean();
                            if (i == 1) {
                                stepViewTwoBean1.highlight = true;
                            }
                            stepViewTwoBean1.location = "到达目的地网点广东深圳公司，快件将很快进aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
                            stepViewTwoBean1.time = "2018-11-25 12:00:00";
                            stepViewTwoBeans.add(stepViewTwoBean1);

                        }
                        mStepViewTwo.setData(stepViewTwoBeans);
                    }
                });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mLeafLoading.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLeafLoading.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLeafLoading.destroy();
    }
}
