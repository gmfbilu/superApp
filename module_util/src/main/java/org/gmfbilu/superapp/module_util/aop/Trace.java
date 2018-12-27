package org.gmfbilu.superapp.module_util.aop;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 *  追踪某个方法花费的时间，用于性能调优
 */
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Trace {

    boolean enable() default true;
}
