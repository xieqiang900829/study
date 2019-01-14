package com.annotation;

/**
 * Created by WD42700 on 2019/1/12.
 */

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 8  * 水果名称注解
 9  */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
public @interface FruitName {

    String value() default "";


}
