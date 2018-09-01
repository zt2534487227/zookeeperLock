package com.zt.zookeeperlock.distributed;

import com.zt.zookeeperlock.entity.Result;
import com.zt.zookeeperlock.property.ZookeeperProps;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ZhouTian
 * @Description: zookeeper 工具类
 * @Date: 2018/8/7
 */
@EnableConfigurationProperties(ZookeeperProps.class)
public class ZookeeperLock implements DistributedLock {

    private ZooKeeper zooKeeper;
    private ZookeeperProps zookeeperProps;

    //根节点
    private static final String ROOT_LOCK="/locks";

    public ZookeeperLock(ZookeeperProps zookeeperProps){
        this.zookeeperProps=zookeeperProps;
        buildClient();
    }

    /**
     * 创建 zookeeper 客户端
     */
    private void buildClient(){
        try {
            if (zooKeeper == null){
                final CountDownLatch connectedSignal = new CountDownLatch(1);
                this.zooKeeper = new ZooKeeper(zookeeperProps.getAddresses(), zookeeperProps.getTimeOut(), event->{
                   if  (event.getState()  ==  Watcher.Event.KeeperState.SyncConnected) {
                        connectedSignal.countDown();
                    }
                });
                connectedSignal.await(zookeeperProps.getTimeOut(),TimeUnit.MILLISECONDS);
                if (connectedSignal.getCount()>0){//超时 建立 zookeeper 连接失败
                    zooKeeper.close();
                    zooKeeper=null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取分布式锁
     * @param lockName
     * @param timeout
     * @return
     */
    public Result<String> acquireDistributedLock(String lockName, int timeout) {
        Result<String> result=new Result<String>(false);
        try {
            buildClient();
            String path = ROOT_LOCK+"/"+lockName;
            //判断根节点是否存在
            Stat rootStat = zooKeeper.exists(ROOT_LOCK, false);
            if(rootStat == null){
                // 创建根节点  持久化化节点
                zooKeeper.create(ROOT_LOCK, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            }
            //创建子节点  临时顺序节点
            String currentLock= zooKeeper.create(path, "".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            result.setData(currentLock);
            //取出所有子节点
            List<String> subNodes = zooKeeper.getChildren(ROOT_LOCK, false);
            // 取出所有lockName的锁
            List<String> lockObjects = new ArrayList<String>();
            for (String node : subNodes) {
                if (node.startsWith(lockName)) {
                    lockObjects.add(node);
                }
            }
            //节点排序
            Collections.sort(lockObjects);
            // 若当前节点为最小节点，则获取锁成功
            if (currentLock.equals(ROOT_LOCK + "/" + lockObjects.get(0))) {
                result.setSuccess(true);
                System.out.println(Thread.currentThread().getName()+" 获取到锁："+currentLock);
                return result;
            }
            // 若不是最小节点,则找到自己的前一个节点
            String currentNode = currentLock.substring(currentLock.lastIndexOf("/") + 1);
            String prevNode = lockObjects.get(Collections.binarySearch(lockObjects, currentNode) - 1);

            String prevLock=ROOT_LOCK + "/" + prevNode;
            final CountDownLatch latch = new CountDownLatch(1);

            Stat stat = zooKeeper.exists(prevLock, event->{
                if (event.getType() == Watcher.Event.EventType.NodeDeleted)
                    latch.countDown();
                }
            );
            //设置超时等待
            latch.await(timeout,TimeUnit.MILLISECONDS);
            if (latch.getCount()>0) {
                result.setSuccess(false);
                return result;
            }
            result.setSuccess(true);
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return result;
        } catch (KeeperException e) {
            e.printStackTrace();
            return result;
        }
    }


    /**
     * 释放分布式锁
     */
    public void releaseDistributedLock(String currentLock) {
        try {
            if (!StringUtils.isEmpty(currentLock)){
                System.out.println(Thread.currentThread().getName()+" 释放锁："+currentLock);
                Stat stat = zooKeeper.exists(currentLock, false);
                if(stat != null){
                    zooKeeper.delete(currentLock, -1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
