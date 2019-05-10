package org.gmfbilu.superapp.module_util.hook;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import androidx.appcompat.widget.AppCompatButton;

/**
 * Hook过程：
 * 寻找Hook点，原则是尽量静态变量或者单例对象，尽量Hook public的对象和方法。
 * 选择合适的代理方式，如果是接口可以用动态代理。
 * 偷梁换柱——用代理对象替换原始对象。
 * Android的API版本比较多，方法和类可能不一样，所以要做好API的兼容工作
 */
public class HookFragment extends BaseFragment {

    private AppCompatButton bt_OnClickListener;

    public static HookFragment newInstance() {
        Bundle args = new Bundle();
        HookFragment fragment = new HookFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        bt_OnClickListener = view.findViewById(R.id.bt_OnClickListener);
        bt_OnClickListener.setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_hook;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_OnClickListener) {
            hookClickListener();
        }
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }

    /**
     * 先分析View.setOnClickListener源码，找出合适的Hook点
     * 可以看到OnClickListener对象被保存在了一个叫做ListenerInfo的内部类里，
     * 其中mListenerInfo是View的成员变量。ListenerInfo里面保存了View的各种监听事件
     * 可以想办法hook ListenerInfo的mOnClickListener
     * <p>
     * 第一步：获取ListenerInfo对象
     * 从View的源代码，我们可以知道我们可以通过getListenerInfo方法获取，于是我们利用反射得到ListenerInfo对象
     * 第二步：获取原始的OnClickListener事件方法
     * 我们知道OnClickListener事件被保存在ListenerInfo里面，同理我们利用反射获取
     * 第三步：偷梁换柱，用Hook代理类替换原始的OnClickListener
     */
    private void hookClickListener() {
        try {
            // 第一步：反射得到ListenerInfo对象
            //反射执行View类的getListenerInfo()方法，拿到view的mListenerInfo对象，这个对象就是点击事件的持有者
            Method getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");
            //由于getListenerInfo()方法并不是public的，所以要加这个代码来保证访问权限
            getListenerInfo.setAccessible(true);
            //hook的范围仅限于这个view，这里拿到的就是mListenerInfo对象，也就是点击事件的持有者
            Object listenerInfo = getListenerInfo.invoke(bt_OnClickListener);

            // 第二步：得到原始的OnClickListener事件方法
            //要从这里面拿到当前的点击事件对象
            Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");// 这是内部类的表示方法
            Field mOnClickListener = listenerInfoClz.getDeclaredField("mOnClickListener");
            mOnClickListener.setAccessible(true);
            //取得真实的mOnClickListener对象
            View.OnClickListener originOnClickListener = (View.OnClickListener) mOnClickListener.get(listenerInfo);

            // 第三步：用Hook代理类 替换原始的OnClickListener
            //方式一由于View.OnClickListener是一个接口，所以可以直接用动态代理模式
            //本地的类加载器;代理类的对象所继承的接口（用Class数组表示，支持多个接口）;代理类的实际逻辑，封装在new出来的InvocationHandler内
         /*   Object proxyOnClickListener = Proxy.newProxyInstance(_mActivity.getClass().getClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Log.d("HookSetOnClickListener", "点击事件被hook到了");//加入自己的逻辑
                    return method.invoke(originOnClickListener, args);//执行被代理的对象的逻辑
                }
            });*/
            //方式二自己创建代理类
            View.OnClickListener hookedOnClickListener = new HookedClickListenerProxy(originOnClickListener);
            mOnClickListener.set(listenerInfo, hookedOnClickListener);
        } catch (Exception e) {

        }
    }
}
