package org.gmfbilu.superapp.module_java.retrofit_rxjava;

import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by gmfbilu on 2016/12/20.
 */

public abstract class AppSubscribe<T> implements Observer<T> {


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        HttpMethods.clearSingleton();
        if (e!=null) Logger.d(e.toString());
    }


}
