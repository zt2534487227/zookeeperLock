package com.zt.zookeeperlock.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/9/1
 */
@RestController("/")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/test")
    public void test() throws InterruptedException {
        //模拟30次并发
        CountDownLatch countDownLatch = new CountDownLatch(30);
        for (int i = 0; i < 30; i++) {
            demoService.test();
            countDownLatch.countDown();
        }
        countDownLatch.await();
    }

}
