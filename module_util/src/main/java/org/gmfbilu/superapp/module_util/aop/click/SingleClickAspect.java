package org.gmfbilu.superapp.module_util.aop.click;

import android.view.View;

import com.orhanobut.logger.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SingleClickAspect {

    private static final Long FILTER_TIME = 1000L;
    /**
     * 最近一次点击的时间
     */
    private static long mLastClickTime;
    /**
     * 最近一次点击的控件ID
     */
    private static int mLastClickViewId;

    @Pointcut("execution(@org.gmfbilu.superapp.module_util.aop.click.SingleClick * *(..))")//方法切入点
    public void executionAspectJ() {
    }

    @Pointcut("execution(@org.gmfbilu.superapp.module_util.aop.click.SingleClick *.new(..))")
//构造器切入点
    public void constructorAspectJ() {
    }

    @Around("executionAspectJ()||constructorAspectJ")//在连接点进行方法替换
    public void aroundAspectJ(ProceedingJoinPoint joinPoint) throws Throwable {
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
                break;
            }
        }
        if (view == null) {
            return;
        }
        int viewId = view.getId();
        long time = System.currentTimeMillis();
        long timeInterval = Math.abs(time - mLastClickTime);
        if (timeInterval < FILTER_TIME && viewId == mLastClickViewId) {
            Logger.d("重复点击,已过滤");
        } else {
            mLastClickTime = time;
            mLastClickViewId = viewId;
           // collectClickInfo(view, time);
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
