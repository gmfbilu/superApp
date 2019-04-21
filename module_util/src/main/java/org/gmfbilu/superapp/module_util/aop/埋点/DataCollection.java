package org.gmfbilu.superapp.module_util.aop.埋点;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解不会继承，所以在基类上加注解，然后每个类自动执行的想法太傻了
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataCollection {

    String tag() default "";

    String type() default "";

    String methodName() default "";

    String className() default "unknow";
}
