package org.gmfbilu.superapp.module_util.aop;

import android.os.Looper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 异步事件
 */
@Aspect
public class AsyncAspect {

    @Around("execution(!synthetic * *(..)) && onAsyncMethod()")
    public void doAsyncMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        asyncMethod(joinPoint);
    }

    @Pointcut("@within(com.safframework.app.annotation.Async)||@annotation(com.safframework.app.annotation.Async)")
    public void onAsyncMethod() {
    }

    private void asyncMethod(final ProceedingJoinPoint joinPoint) {
        Single.create(emitter -> {
            Looper.prepare();
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            Looper.loop();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
