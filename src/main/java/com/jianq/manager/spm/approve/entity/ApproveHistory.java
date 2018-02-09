package com.jianq.manager.spm.approve.entity;

import java.util.Date;

public class ApproveHistory {
    private Integer id;

    private Integer userId;

    private Integer orderId;

    private String userName;

    private String shop;

    private Integer roleId;

    private Integer state;

    private String remark;

    private String opinion;

    private Date createTime;

    public ApproveHistory() {
    }

    public ApproveHistory(Integer userId, Integer state, String shop, Integer roleId, String opinion) {
        this.userId = userId;
        this.state = state;
        this.shop = shop;
        this.roleId = roleId;
        this.opinion = opinion;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop == null ? null : shop.trim();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion == null ? null : opinion.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ApproveHistory{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderId=" + orderId +
                ", userName='" + userName + '\'' +
                ", shop='" + shop + '\'' +
                ", roleId=" + roleId +
                ", state=" + state +
                ", remark='" + remark + '\'' +
                ", opinion='" + opinion + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}