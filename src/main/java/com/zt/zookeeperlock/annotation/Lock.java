package com.zt.zookeeperlock.annotation;

import java.lang.annotation.*;

/**
 * @Author: ZhouTian
 * @Description: 分布所锁注解,用来标注哪些方法需要加锁
 * @Date: 2018/8/8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {
    String name() default "";

    int timeOut() default 3000;

}
