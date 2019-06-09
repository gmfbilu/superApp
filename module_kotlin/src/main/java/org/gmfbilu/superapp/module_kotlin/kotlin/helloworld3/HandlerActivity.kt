package org.gmfbilu.superapp.module_kotlin.kotlin.helloworld3

import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference

class HandlerActivity : AppCompatActivity() {

    private var mHandler: Handler? = WithoutLeakHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHandler?.sendEmptyMessage(CODE_LOAD_DATA)
    }

    //静态
    companion object {
        //常量
        const val CODE_LOAD_DATA: Int = 101

        //带一个参数的构造函数的静态内部类
        // 将 Handler 以静态内部类的形式声明，然后通过弱引用的方式让 Handler 持有外部类 Activity 的引用，这样就可以避免内存泄漏问题了
        private class WithoutLeakHandler(activity: HandlerActivity) : Handler() {
            private val mActivity: WeakReference<HandlerActivity> = WeakReference(activity)
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                val handlerActivity = mActivity.get()
                when (msg?.what) {
                    CODE_LOAD_DATA -> {
                        //data load started

                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler?.removeCallbacksAndMessages(null)
    }
}

/*
public class HandlerActivity extends AppCompatActivity {

    private MyHandler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler.sendEmptyMessage(mHandler.CODE_LOAD_DATA);
    }

    private static class MyHandler extends Handler {
        public static int CODE_LOAD_DATA = 101;
        private final WeakReference<HandlerActivity> mActivity;

        public MyHandler(HandlerActivity activity) {
            mActivity = new WeakReference<HandlerActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HandlerActivity activity = mActivity.get();
            if (activity != null) {
                //do Something
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
*/
