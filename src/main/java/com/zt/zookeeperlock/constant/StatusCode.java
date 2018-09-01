package com.zt.zookeeperlock.constant;

/**
 * @Author: ZhouTian
 * @Description:
 * @Date: 2018/8/21
 */
public interface StatusCode {
    enum Status {
        /**
         * 成功
         */
        SUCCESS("00", "成功"),
        /**
         * 业务失败
         */
        BUSINESS_ERROR("01", "失败"),
         /**
          * 签名错误
          */
        SIGN_ERROR("02", "签名错误！"),
        /**
         * 解密失败
         */
        DECODE_ERROR("03","解密失败"),
        /**
         * 参数为空
         */
        PARAM_EMPTY("04","参数为空"),
        /**
         * 服务器内部错误
         */
        SERVER_ERROR("05","服务器内部错误"),
         /**
         * 请求超时
         */
        TIMEOUT_ERROR("06", "请求超时"),
        ;
        private String code;
        private String msg;
        Status(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Status{");
            sb.append("code='").append(code).append('\'');
            sb.append(", msg='").append(msg).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
