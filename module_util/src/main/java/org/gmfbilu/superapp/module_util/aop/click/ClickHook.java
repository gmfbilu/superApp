package org.gmfbilu.superapp.module_util.aop.click;

import android.view.View;

import com.orhanobut.logger.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.gmfbilu.superapp.lib_base.utils.ToastUtil;

@Aspect
public class ClickHook {


    private static final Long FILTER_TIME = 1000L;
    /**
     * 最近一次点击的时间
     */
    private static long mLastClickTime;
    /**
     * 最近一次点击的控件ID
     */
    private static int mLastClickViewId;


    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
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
            Object tag = view.getTag();
            if (tag instanceof String && !((String) tag).isEmpty()) {
                collectClickInfo((String) tag);
            }
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private void collectClickInfo(String tag) {
        ToastUtil.show(tag);
    }

}
