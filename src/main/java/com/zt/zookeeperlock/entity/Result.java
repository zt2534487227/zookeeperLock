package com.zt.zookeeperlock.entity;

import com.zt.zookeeperlock.constant.StatusCode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class Result<T> implements Serializable{

    private static final long serialVersionUID = 1L;
    /**
     * 成功状态
     */
    private boolean success;
    /**
     * 状态响应码
     */
	private String code;
    /**
     * 提示信息
     */
	private String msg;
    /**
     * 附加数据
     */
	private T data = null;
    /**
     * 列表数据
     */
	private List<T> itemList;
    /**
     * map 数据
     */
	private Map<String, Object> attributes;

	public Result() {

	}
	public Result(boolean success) {
		this.success = success;
	}

    public Result(StatusCode.Status status) {
        this.msg = status.getMsg();
        this.code=status.getCode();
    }

    public Result(boolean success, StatusCode.Status status) {
        this.success = success;
        this.msg = status.getMsg();
        this.code=status.getCode();
    }

    public Result(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}

	public Result(boolean success, String code, String msg) {
		this.success = success;
		this.code = code;
		this.msg = msg;
	}

	public Result(boolean success, String code, String msg, T data) {
		this.success = success;
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<T> getItemList() {
		return itemList;
	}

	public void setItemList(List<T> itemList) {
		this.itemList = itemList;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Result{");
        sb.append("success=").append(success);
        sb.append(", code='").append(code).append('\'');
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", data=").append(data);
        sb.append(", itemList=").append(itemList);
        sb.append(", attributes=").append(attributes);
        sb.append('}');
        return sb.toString();
    }
}
