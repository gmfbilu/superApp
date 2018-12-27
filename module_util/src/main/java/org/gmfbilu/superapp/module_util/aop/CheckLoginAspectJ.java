package org.gmfbilu.superapp.module_util.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;


/**
 * 1.创建切面AspectJ
 * Before	前置通知, 在目标执行之前执行通知
 * After	后置通知, 目标执行后执行通知
 * Around	环绕通知, 在目标执行中执行通知, 控制目标执行时机
 * AfterReturning	后置返回通知, 目标返回时执行通知
 * AfterThrowing	异常通知, 目标抛出异常时执行通知
 */
@Aspect
public class CheckLoginAspectJ {


    /**
     * 找到处理的切点
     * * *(..)  可以处理CheckLogin这个类所有的方法
     * execution：处理Join Point的类型，例如call、execution
     * (* android.app.Activity.on**(..))：这个是最重要的表达式，第一个『*』表示返回值，『*』表示返回值为任意类型，后面这个就是典型的包名路径，其中可以包含『*』来进行通配，几个『*』没区别。同时，这里可以通过『&&、||、!』来进行条件组合。()代表这个方法的参数，你可以指定类型，例如android.os.Bundle，或者(..)这样来代表任意类型、任意个数的参数
     */
    @Pointcut("execution(@org.gmfbilu.superapp.module_util.aop.CheckLogin  * *(..))")
    public void executionCheckLogin() {
    }

    /**
     * @param joinPoint
     * @return
     * @Around是Advice，也就是具体的插入点
     */
    @Around("executionCheckLogin()")
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckLogin checkLogin = signature.getMethod().getAnnotation(CheckLogin.class);
        if (checkLogin != null) {
            boolean login = false;
            if (login) {
                return joinPoint.proceed();
            } else {
                //Toast.makeText(UtilApplication.sApplicationContext, "去登录", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        return joinPoint.proceed();
    }


    //带有DebugLog注解的所有类
    @Pointcut("within(@com.example.aoplib.DebugLog *)")
    public void withinAnnotatedClass() {
    }

    //在带有DebugLog注解的所有类，除去synthetic修饰的方法
    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    //在带有DebugLog注解的所有类，除去synthetic修饰的构造方法
    @Pointcut("execution(!synthetic *.new(..)) && withinAnnotatedClass()")
    public void constructorInsideAnnotatedType() {
    }

    //在带有DebugLog注解的方法
    @Pointcut("execution(@com.example.aoplib.DebugLog * *(..)) || methodInsideAnnotatedType()")
    public void method() {
    }

    //在带有DebugLog注解的构造方法
    @Pointcut("execution(@com.example.aoplib.DebugLog *.new(..)) || constructorInsideAnnotatedType()")
    public void constructor() {
    }

    /*@Around("method() || constructor()")
    public Object logAndExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        //执行方法前，做些什么
        enterMethod(joinPoint);
        long startNanos = System.nanoTime();
        //执行原方法
        Object result = joinPoint.proceed();
        long stopNanos = System.nanoTime();
        long lengthMillis = TimeUnit.NANOSECONDS.toMillis(stopNanos - startNanos);
        //执行方法后，做些什么
        exitMethod(joinPoint, result, lengthMillis);
        return result;
    }*/

}
