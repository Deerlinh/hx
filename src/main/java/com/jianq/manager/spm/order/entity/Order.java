package com.jianq.manager.spm.order.entity;

import java.util.Date;

public class Order {
    private Integer id;

    private String name;

    private String payee;

    private Double amount;

    private String picUrl;

    private Integer status;

    private Integer creator;

    private Date createTime;

    private String creatorName;

    private Integer roleId;

    private String currentProcessingPerson;

    private Integer cargoId;

    private String remark;

    private Integer todoStatus;

    public Order() {
    }

    public Order(Integer id, Integer status) {
        this.id = id;
        this.status = status;
    }

    public Order(Integer cargoId, String name, String payee, Double amount, String picUrl, Integer creator, String remark) {
        this.name = name;
        this.payee = payee;
        this.amount = amount;
        this.picUrl = picUrl;
        this.creator = creator;
        this.cargoId = cargoId;
        this.remark = remark;
    }

    public Order(Integer cargoId, String name, String picUrl, Integer creator, String remark) {
        this.cargoId = cargoId;
        this.name = name;
        this.picUrl = picUrl;
        this.creator = creator;
        this.remark = remark;
    }

    public Integer getTodoStatus() {
        return todoStatus;
    }

    public void setTodoStatus(Integer todoStatus) {
        this.todoStatus = todoStatus;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPayee() {
        return payee == null ? "" : payee;
    }

    public void setPayee(String payee) {
        this.payee = payee == null ? null : payee.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCurrentProcessingPerson() {
        return currentProcessingPerson;
    }

    public void setCurrentProcessingPerson(String currentProcessingPerson) {
        this.currentProcessingPerson = currentProcessingPerson;
    }

    public Integer getCargoId() {
        return cargoId;
    }

    public void setCargoId(Integer cargoId) {
        this.cargoId = cargoId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", payee='" + payee + '\'' +
                ", amount=" + amount +
                ", picUrl='" + picUrl + '\'' +
                ", status=" + status +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", creatorName='" + creatorName + '\'' +
                ", roleId=" + roleId +
                ", currentProcessingPerson='" + currentProcessingPerson + '\'' +
                ", cargoId=" + cargoId +
                ", remark='" + remark + '\'' +
                '}';
    }
}