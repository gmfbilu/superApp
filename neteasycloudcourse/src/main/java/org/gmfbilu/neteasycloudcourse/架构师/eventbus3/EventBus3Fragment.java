package org.gmfbilu.neteasycloudcourse.架构师.eventbus3;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.gmfbilu.neteasycloudcourse.R;
import org.gmfbilu.neteasycloudcourse.架构师.数据库.User;
import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;

/**
 * 常规的事件传递
 * 1.intent:跳转+传参。局限性很大，对数据量大小有限制，A到B到C，C要通知A，这种就达不到。
 * 2.interface:仅限于同一线程中数据交互。
 * 3.广播：维护成本大
 * 4.AIDL:代码阅读性不好，维护成本高。
 * 5.handler：使用不当容易出现内存泄漏。
 * 6.本地存储：数据库，SQ。
 * <p>
 * EventBus是一个Android优化的publish/subscribe消息事件总线，简化了应用程序内各个组件间、组件和后台线程间的通信。
 * 添加依赖，添加annotationProcessor，
 * 在app的build.gradle中的defaultConfig{}节点下添加javaCompileOptions{annotationProcessorOptions {{ arguments = [eventBusIndex:'包名.MyEventBusIndex']}}}。
 * 然后重新编译，在Application中注册EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus()。
 * <p>
 * 正确的使用eventbus，在3.0之后不进行此操作的话就会使用反射，进行此操作就会使用apt。3.0之前都是使用反射。
 * EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
 */

/**
 * EventBus是一个Android优化的publish/subscribe消息事件总线，简化了应用程序内各个组件间、组件和后台线程间的通信。
 * 添加依赖，添加annotationProcessor，
 * 在app的build.gradle中的defaultConfig{}节点下添加javaCompileOptions{annotationProcessorOptions {{ arguments = [eventBusIndex:'包名.MyEventBusIndex']}}}。
 * 然后重新编译，在Application中注册EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus()。
 *
 * 正确的使用eventbus，在3.0之后不进行此操作的话就会使用反射，进行此操作就会使用apt。3.0之前都是使用反射。
 * EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
 */

/**
 * 1.在onCreate中EventBus.getDefault().register(this)，在onDestroy中EventBus.getDefault().unregister(this)。
 * 2.添加一个订阅方法@Subscribe，
 * 3.添加发送事件， EventBus.getDefault().postSticky()或 EventBus.getDefault().post()。
 */
public class EventBus3Fragment extends BaseFragment {


    private TextView tv_info;

    public static EventBus3Fragment newInstance() {
        Bundle args = new Bundle();
        EventBus3Fragment fragment = new EventBus3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        tv_info = view.findViewById(R.id.tv_info);
        view.findViewById(R.id.bt_post).setOnClickListener(this);
        view.findViewById(R.id.bt_postSticky).setOnClickListener(this);
        view.findViewById(R.id.bt_postObject).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_architect_eventbus3;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_post) {
            /**
             * 调用post方法会触发event1和event2方法，也就是说post方法会触发sticky事件和非sticky事件。
             * 触发的时机是所有的非sticky事件执行完成再执行sticky事件，同一类型之间的执行顺序按照优先级。
             */
            EventBus.getDefault().post("post事件");
        } else if (id == R.id.bt_postSticky) {
            /**
             * 调用postSticky方法会触发event1和event2方法，也就是说postSticky方法会触发sticky事件和非sticky事件
             * 触发的时机是所有的非sticky事件执行完成再执行sticky事件，同一类型之间的执行顺序按照优先级。
             */
            EventBus.getDefault().postSticky("postSticky事件");
        } else if (id == R.id.bt_postObject) {
            /**
             * 只会调用event4方法，发送的数据是UserBean，那么只用接受参数是UserBean的方法才会被调用。
             * eventbus是按照订阅参类型数进行匹配的
             * 所以最好的实践是把发送的数据封装成对象
             */
            EventBus.getDefault().post(new User(10,""));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 订阅事件，默认不是sticky事件，默认优先级是0
     * threadMode有POSTING(默认，事件的处理和事件的发送在相同的线程)，MAIN(事件的处理会在主线程中执行)，
     * BACKGROUND(后台线程，处理如保存到数据库等操作)，ASYNC(异步执行，事件的处理会在单独的线程中执行，只要用于在后台线程中执行耗时操作)
     * 发送sticky事件也会触发非sticky事件
     * 所以在订阅事件中最好判断控件是否为空
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = false, priority = 1)
    public void event1(String msg) {
        LoggerUtil.d("event1:" + msg);
        if (tv_info != null) {
            tv_info.setText(msg);
        }
    }

    @Subscribe(sticky = true, priority = 0)
    public void event2(String msg) {
        LoggerUtil.d("event2:" + msg);
        if (tv_info != null) {
            tv_info.setText(msg);
        }
    }

    @Subscribe(sticky = true, priority = 1)
    public void event3(String msg) {
        LoggerUtil.d("event3:" + msg);
        if (tv_info != null) {
            tv_info.setText(msg);
        }
    }

    @Subscribe
    public void event4(User user) {
        LoggerUtil.d("event4:" + user);
    }
}
