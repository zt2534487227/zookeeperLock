package com.zt.zookeeperlock.demo;

import com.zt.zookeeperlock.annotation.Lock;
import com.zt.zookeeperlock.entity.Result;
import org.springframework.stereotype.Service;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/9/1
 */
@Service
public class DemoService {


    @Lock(name = "test")
    public Result<String> test(){

        return null;
    }

}
