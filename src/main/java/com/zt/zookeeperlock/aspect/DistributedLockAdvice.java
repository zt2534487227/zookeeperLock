package com.zt.zookeeperlock.aspect;

import com.zt.zookeeperlock.annotation.Lock;
import com.zt.zookeeperlock.constant.StatusCode;
import com.zt.zookeeperlock.distributed.DistributedLock;
import com.zt.zookeeperlock.entity.Result;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/8/8
 */
public class DistributedLockAdvice implements MethodInterceptor {

    private DistributedLock distributedLock;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Object result=new Result<String>(false,StatusCode.Status.TIMEOUT_ERROR);
        if (method.isAnnotationPresent(Lock.class)) {
            Lock annotation = method.getAnnotation(Lock.class);
            String name = annotation.name();
            int timeOut = annotation.timeOut();
            if (!StringUtils.isEmpty(name)){
                Result<String> lock = distributedLock.acquireDistributedLock(name, timeOut);
                //获取分布式锁成功,才会执行锁定的方法,否则返回超时
                if (lock.isSuccess()){
                   result=methodInvocation.proceed();
                }
                distributedLock.releaseDistributedLock(lock.getData());
            }
        }
        return result;
    }


    public DistributedLockAdvice(DistributedLock distributedLock) {
        this.distributedLock = distributedLock;
    }
}
