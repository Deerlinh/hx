package com.jianq.manager.spm.order.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jianq.manager.spm.order.entity.Order;

/**
 * @author Leo on 2017/10/23
 */
public class OrderVO extends Order {
    /**
     * 起始数量
     */
    @JsonIgnore
    private Integer startNum;
    /**
     * 每页数量
     */
    @JsonIgnore
    private Integer orderPageSize;

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public Integer getOrderPageSize() {
        return orderPageSize;
    }

    public void setOrderPageSize(Integer orderPageSize) {
        this.orderPageSize = orderPageSize;
    }


}
