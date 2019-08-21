package org.gmfbilu.superapp.module_util.aop.checkLogin;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.orhanobut.logger.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.gmfbilu.superapp.lib_base.app.ActivitiesManager;


@Aspect
public class CheckLoginAspect {

    @Pointcut("execution(@org.gmfbilu.superapp.module_util.aop.checkLogin.CheckLogin * *(..))")
    public void executionAspectJ() {

    }

    @Around("executionAspectJ()")//在连接点进行方法替换
    public void aroundAspectJ(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Logger.d("aroundAspectJ(ProceedingJoinPoint joinPoint)");
        CheckLogin aspectJAnnotation = methodSignature.getMethod().getAnnotation(CheckLogin.class);
        final boolean isLogin = aspectJAnnotation.isLogin();
        String token = aspectJAnnotation.token();
        //未登录
        if (!isLogin) {
            Context context = null;
            Object object = joinPoint.getThis();
            if (object instanceof Activity) {
                context = (Context) object;
            } else if (object instanceof android.app.Fragment) {
                context = ((android.app.Fragment) object).getActivity();
            } else if (object instanceof Fragment) {
                context = ((Fragment) object).getActivity();
            } else {
                context = ActivitiesManager.getInstance().getCurrentActivity();
            }
            Toast.makeText(context, " go  Login", Toast.LENGTH_SHORT).show();
            //  context.startActivity(new Intent(context, LoginActivity.class));
    /*        NotifyMessageManager.getInstance().setOnHandleMessageListener(new NotifyMessageManager.NotifyMessageListener() {
                @Override
                public void onHandleMessage(String msg) {
                    if (isJump && "proceed".equals(msg)) {
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }
            });*/
            return;
        }
        joinPoint.proceed();
    }

}
