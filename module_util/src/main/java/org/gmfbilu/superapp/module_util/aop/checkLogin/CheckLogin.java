package org.gmfbilu.superapp.module_util.aop.checkLogin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckLogin {

    /**
     * isLogin是属性
     * @return
     */
    boolean isLogin() default false;

    String token() default "";
}