package com.jianq.manager.spm.todo.entity;

import java.util.Date;

public class Todo {
    private Integer id;

    private Integer orderId;

    private Integer userId;

    private Integer status;

    private Double amount;

    private Date createTime;

    private Date lastTime;

    private String payee;

    private String name;

    private Integer cargoId;

    public Todo() {
    }

    public Todo(Integer orderId, Integer userId, Double amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
    }

    public Todo(Integer orderId, Integer userId, Integer status) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
    }

    public Todo(Integer id, Integer status) {
        this.id = id;
        this.status = status;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCargoId() {
        return cargoId;
    }

    public void setCargoId(Integer cargoId) {
        this.cargoId = cargoId;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", status=" + status +
                ", amount=" + amount +
                ", createTime=" + createTime +
                ", lastTime=" + lastTime +
                ", payee='" + payee + '\'' +
                ", name='" + name + '\'' +
                ", cargoId=" + cargoId +
                '}';
    }
}