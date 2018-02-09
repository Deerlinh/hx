package com.jianq.manager.spm.order.dao;

import com.jianq.manager.spm.order.entity.OrderHistory;

import java.util.List;

public interface OrderHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrderHistory record);

    OrderHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderHistory record);

    List<OrderHistory> selectByOrderId(Integer orderId);
}