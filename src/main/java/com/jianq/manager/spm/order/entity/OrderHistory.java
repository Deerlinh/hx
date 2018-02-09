package com.jianq.manager.spm.order.entity;

import java.util.Date;

public class OrderHistory {
    private Integer id;

    private Integer orderId;

    private String remark;

    private Integer creator;

    private Date createTime;

    public OrderHistory() {
    }

    public OrderHistory(Integer orderId, String remark, Integer creator) {
        this.orderId = orderId;
        this.remark = remark;
        this.creator = creator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "OrderHistory{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", remark='" + remark + '\'' +
                ", creator=" + creator +
                ", createTime=" + createTime +
                '}';
    }
}