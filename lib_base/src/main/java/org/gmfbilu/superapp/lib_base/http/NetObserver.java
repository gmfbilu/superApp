package org.gmfbilu.superapp.lib_base.http;

import org.gmfbilu.superapp.lib_base.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
            }
        }
        //RxBus.getDefault().post(e);
    }

}
