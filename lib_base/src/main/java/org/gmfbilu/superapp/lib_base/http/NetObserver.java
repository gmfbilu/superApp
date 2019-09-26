package org.gmfbilu.superapp.lib_base.http;

import org.gmfbilu.superapp.lib_base.utils.ToastUtil;

import java.net.ConnectException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * onComplete和onError是互斥的
 */
public abstract class NetObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
        //Logger.d(d.isDisposed());
    }

    @Override
    public void onNext(T t) {
    }


    @Override
    public void onComplete() {
        // Logger.d("onComplete");
    }

    // TODO: 2017/2/27  doing more
    @Override
    public void onError(Throwable e) {
        HttpMethods.clearSingleton();
        if (e != null) {
            // Logger.d(e.toString());
            if (e instanceof ApiException) {
                ToastUtil.show(((ApiException) e).ResponseMessageError);
            } else if (e instanceof ConnectException) {
                ToastUtil.show("无法连接上服务器");
            }
        }
        //RxBus.getDefault().post(e);
    }

}
