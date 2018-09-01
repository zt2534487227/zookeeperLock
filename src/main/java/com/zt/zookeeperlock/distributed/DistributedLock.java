package com.zt.zookeeperlock.distributed;


import com.zt.zookeeperlock.entity.Result;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/8/8
 */
public interface DistributedLock {

    //获取分布式锁
    Result<String> acquireDistributedLock(String name, int timeOut);

    //释放分布式锁
    void releaseDistributedLock(String lock);
}
