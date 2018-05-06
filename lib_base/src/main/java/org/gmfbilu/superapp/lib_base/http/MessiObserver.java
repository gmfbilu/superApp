package org.gmfbilu.superapp.lib_base.http;

import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.gmfbilu.superapp.lib_base.base.BaseApplication;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class MessiObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
        Logger.d(d.isDisposed());
    }

    @Override
    public void onNext(T t) {
    }


    @Override
    public void onComplete() {
        Logger.d("onComplete");
    }

    // TODO: 2017/2/27  doing more
    @Override
    public void onError(Throwable e) {
        HttpMethods.clearSingleton();
        if (e != null) {
            Logger.d(e.toString());
            if (e instanceof ApiException) {
                Toast.makeText(BaseApplication.getInstance(), ((ApiException) e).ResponseMessageError, Toast.LENGTH_SHORT).show();
            }
        }
        //RxBus.getDefault().post(e);
    }

}
