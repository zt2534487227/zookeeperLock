package com.zt.zookeeperlock.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/8/8
 */
@ConfigurationProperties(prefix = "zt.zookeeper")
public class ZookeeperProps {
    private String addresses="localhost:4181";
    private Integer timeOut=1000;
    private String aopExpression="@annotation(com.zt.zookeeperlock.annotation.Lock)";

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public String getAopExpression() {
        return aopExpression;
    }

    public void setAopExpression(String aopExpression) {
        this.aopExpression = aopExpression;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ZookeeperProps{");
        sb.append("addresses='").append(addresses).append('\'');
        sb.append(", timeOut=").append(timeOut);
        sb.append(", aopExpression='").append(aopExpression).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
