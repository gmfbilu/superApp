package org.gmfbilu.superapp.module_util.jetpack.livedata_viewmodel;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.ToastUtil;
import org.gmfbilu.superapp.module_util.R;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * LiveData:
 * 是一个可以感知 Activity 、Fragment生命周期的数据容器，当 LiveData 所持有的数据改变时，它会通知相应的界面代码进行更新
 * 不用手动控制生命周期，不用担心内存泄露，数据变化时会收到通知
 * LiveData 是一个抽象类，它的实现子类有 MutableLiveData ，MediatorLiveData。在实际使用中，用得比较多的是 MutableLiveData。他常常结合 ViewModel 一起使用
 * <p>
 * ViewModel:
 * 旨在以生命周期意识的方式存储和管理用户界面相关的数据,它可以用来管理Activity和Fragment中的数据.还可以拿来处理Fragment与Fragment之间的通信等等.
 * 当Activity或者Fragment创建了关联的ViewModel,那么该Activity或Fragment只要处于活动状态,那么该ViewModel就不会被销毁,即使是该Activity屏幕旋转时重建了.所以也可以拿来做数据的暂存.
 * ViewModel主要是拿来获取或者保留Activity/Fragment所需要的数据的,开发者可以在Activity/Fragment中观察ViewModel中的数据更改
 * ViewModel只是用来管理UI的数据的,千万不要让它持有View、Activity或者Fragment的引用(小心内存泄露)
 * <p>
 */
public class LiveData_ViewModelFragment extends BaseFragment {

    private SimpleViewModel simpleViewModel;

    public static LiveData_ViewModelFragment newInstance() {
        Bundle args = new Bundle();
        LiveData_ViewModelFragment fragment = new LiveData_ViewModelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_updateToast).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_livedata;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_updateToast) {
            //2.发送数据源
            simpleViewModel.changeData("更新数据啦");
        }
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        //1.第一步创建ViewModel，自定义ViewModel继承ViewModel，在里面做获取数据的工作
        simpleViewModel = ViewModelProviders.of(_mActivity).get(SimpleViewModel.class);
        //3.自动更新数据
        simpleViewModel.getData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ToastUtil.show(s);
            }
        });
    }
}
