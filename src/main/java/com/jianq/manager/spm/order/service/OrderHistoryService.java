package com.jianq.manager.spm.order.service;

import com.jianq.manager.spm.order.entity.OrderHistory;

import java.util.List;

public interface OrderHistoryService {
    int deleteById(Integer id);

    int insertOrderHistory(OrderHistory orderHistory);

    OrderHistory selectById(Integer orderHistory);

    int updateOrderHistory(OrderHistory record);

    List<OrderHistory> selectByOrderId(Integer orderId);
}