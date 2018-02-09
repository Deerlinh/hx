package com.jianq.manager.spm.order.service;

import com.jianq.manager.spm.order.entity.Order;
import com.jianq.manager.spm.order.vo.OrderVO;

import java.util.List;

public interface IOrderService {
    Integer deleteById(Integer id);

    Integer insertOrder(Order record);

    Order selectById(Integer id);

    Integer updateById(Order order);

    List<Order> listOrder(OrderVO orderVO);

    Integer countOrder(OrderVO orderVO);

    Integer paymentCompleted(Integer orderId);

    Integer submitOrderWithOutPay(Order order);
}
