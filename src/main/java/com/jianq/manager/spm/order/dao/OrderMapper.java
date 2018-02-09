package com.jianq.manager.spm.order.dao;

import com.jianq.manager.spm.order.entity.Order;
import com.jianq.manager.spm.order.vo.OrderVO;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Order record);

    List<Order> listOrder(OrderVO orderVO);

    Integer countOrder(OrderVO orderVO);
}