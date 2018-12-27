package org.gmfbilu.superapp.module_util.aop;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * 异步线程
 */
@Target({METHOD})
@Retention(CLASS)
public @interface Async {
}
