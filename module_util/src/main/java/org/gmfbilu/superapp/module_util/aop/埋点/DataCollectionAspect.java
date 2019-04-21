package org.gmfbilu.superapp.module_util.aop.埋点;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;

import java.lang.reflect.Method;

@Aspect
public class DataCollectionAspect {

    @Pointcut("execution(@org.gmfbilu.superapp.module_util.aop.埋点.DataCollection * *(..))")
    public void executionAspectJ() {

    }

    @Around("executionAspectJ()")//在连接点进行方法替换
    public void aroundAspectJ(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        /**
         * 方法名：test
         */
        String name = methodSignature.getName();
        /**
         * 方法：public void com.lqr.android.aopdemo.MainActivity.test(android.view.View)
         */
        Method method = methodSignature.getMethod();
        /**
         * 返回值类型：void
         */
        Class returnType = methodSignature.getReturnType();
        /**
         * 方法所在类名：MainActivity
         */
        Class declaringType = methodSignature.getDeclaringType();
        /**
         * 参数名：view
         */
        String[] parameterNames = methodSignature.getParameterNames();
        /**
         * 参数类型：View
         */
        Class[] parameterTypes = methodSignature.getParameterTypes();
        DataCollection aspectJAnnotation = method.getAnnotation(DataCollection.class);
        String tag = aspectJAnnotation.tag();
        String type = aspectJAnnotation.type();
        String methodName = aspectJAnnotation.methodName();
        AppUtils.toast(declaringType.getSimpleName());
        joinPoint.proceed();
    }


}

