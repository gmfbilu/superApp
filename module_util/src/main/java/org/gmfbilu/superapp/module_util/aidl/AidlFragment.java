package org.gmfbilu.superapp.module_util.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.BookManager;
import org.gmfbilu.superapp.module_util.R;

import java.util.List;

/**
 * 这门语言的目的是为了实现进程间通信
 * 我们可以在一个进程访问另一个进程的数据，甚至调用它的一些方法，当然，只能是特定的方法
 * <p>
 * 文件类型：用AIDL书写的文件的后缀是 .aidl，而不是 .java
 * 数据类型：AIDL默认支持一些数据类型，在使用这些数据类型的时候是不需要导包的，但是除了这些类型之外的数据类型，在使用之前必须导包，就算目标文件与当前正在编写的 .aidl 文件在同一个包下
 * 默认支持的数据类型包括：
 * Java中的八种基本数据类型，String 类型，CharSequence类型，List类型，Map类型，定向tag
 * AIDL中的定向 tag 表示了在跨进程通信中数据的流向，其中 in 表示数据只能由客户端流向服务端， out 表示数据只能由服务端流向客户端，而 inout 则表示数据可在服务端与客户端之间双向流通。其中，数据流向是针对在客户端中的那个传入方法的对象而言的
 * <p>
 * 两种AIDL文件：所有的AIDL文件大致可以分为两类。一类是用来定义parcelable对象，以供其他AIDL文件使用AIDL中非默认支持的数据类型的。一类是用来定义方法接口，以供系统使用来完成跨进程通信的。可以看到，两类文件都是在“定义”些什么，而不涉及具体的实现，这就是为什么它叫做“Android接口定义语言”
 * 所有的非默认支持数据类型必须通过第一类AIDL文件定义才能被使用
 * <p>
 * <p>
 * 由于不同的进程有着不同的内存区域，并且它们只能访问自己的那一块内存区域，所以我们不能像平时那样，传一个句柄过去就完事了——句柄指向的是一个内存区域，现在目标进程根本不能访问源进程的内存，那把它传过去又有什么用呢？所以我们必须将要传输的数据转化为能够在内存之间流通的形式。这个转化的过程就叫做序列化与反序列化
 * <p>
 * 客户端我们要完成的工作主要是调用服务端的方法，但是在那之前，我们首先要连接上服务端
 */

public class AidlFragment extends BaseFragment {


    private Toolbar mToolbar;
    //由AIDL文件生成的Java类
    private BookManager mBookManager = null;
    //标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private boolean mBound = false;
    //包含Book对象的list
    private List<Book> mBooks;


    public static AidlFragment newInstance() {
        Bundle args = new Bundle();
        AidlFragment fragment = new AidlFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        mToolbar = view.findViewById(R.id.module_util_toolbar);
        view.findViewById(R.id.module_util_bt_connect).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_aidl;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //点击之后调用服务端的addBookIn方法
        if (id == R.id.module_util_bt_connect) {
            //如果与服务端的连接处于未连接状态，则尝试连接
            if (!mBound) {
                attemptToBindService();
                Toast.makeText(_mActivity, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mBookManager == null) return;
            Book book = new Book();
            book.name = "APP研发录In";
            try {
                mBookManager.addBook(book);
                Logger.d(book.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
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
        mToolbar.setTitle("AIDL");
    }

    /**
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     *
     * @param savedInstanceState
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

    }

    /**
     * 尝试与服务端建立连接
     */
    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("org.gmfbilu.superapp.module_util.aidl");
        intent.setPackage("org.gmfbilu.superapp.module_util.aidl");
        _mActivity.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mBound) {
            attemptToBindService();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBound) {
            _mActivity.unbindService(mServiceConnection);
            mBound = false;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logger.d("service connected");
            mBookManager = BookManager.Stub.asInterface(service);
            mBound = true;

            if (mBookManager != null) {
                try {
                    mBooks = mBookManager.getBooks();
                    Logger.d(mBooks.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.d("service disconnected");
            mBound = false;
        }
    };

}
