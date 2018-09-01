package com.zt.zookeeperlock.config;

import com.zt.zookeeperlock.aspect.DistributedLockAdvice;
import com.zt.zookeeperlock.distributed.DistributedLock;
import com.zt.zookeeperlock.distributed.ZookeeperLock;
import com.zt.zookeeperlock.property.ZookeeperProps;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/8/8
 */
@Configuration
@EnableConfigurationProperties(ZookeeperProps.class)
public class DistributedLockConfig  {

    @Autowired
    private ZookeeperProps zookeeperProps;


    @Bean
    public DistributedLock distributedLock(){
        return new ZookeeperLock(zookeeperProps);
    }


    @Bean
    public Advice lockAdvice(){
        return new DistributedLockAdvice(distributedLock());
    }

    @Bean
    public Advisor lockAdvisor(){
        String expression = zookeeperProps.getAopExpression();
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return new DefaultPointcutAdvisor(pointcut, lockAdvice());
    }

}
