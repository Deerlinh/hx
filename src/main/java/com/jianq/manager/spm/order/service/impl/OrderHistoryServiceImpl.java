package com.jianq.manager.spm.order.service.impl;

import com.jianq.manager.spm.order.dao.OrderHistoryMapper;
import com.jianq.manager.spm.order.entity.Order;
import com.jianq.manager.spm.order.entity.OrderHistory;
import com.jianq.manager.spm.order.service.OrderHistoryService;
import com.jianq.manager.util.NumberUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Leo on 2017/10/23
 */
@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {
    private static final Log LOG = LogFactory.getLog(OrderServiceImpl.class);

    @Autowired
    private OrderHistoryMapper orderHistoryMapper;

    @Override
    public int deleteById(Integer id) {
        Integer count = null;
        if (id != null) {
            try {
                count = orderHistoryMapper.deleteByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 deleteById 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public int insertOrderHistory(OrderHistory orderHistory) {
        Integer count = null;
        if (orderHistory != null) {
            try {
                count = orderHistoryMapper.insertSelective(orderHistory);
            } catch (Exception e) {
                LOG.error("方法 insertOrderHistory 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public OrderHistory selectById(Integer id) {
        OrderHistory orderHistory = null;
        if (id != null) {
            try {
                orderHistory = orderHistoryMapper.selectByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 selectById 异常,{}", e);
            }
        }
        return orderHistory;
    }

    @Override
    public int updateOrderHistory(OrderHistory orderHistory) {
        Integer count = null;
        if (orderHistory != null) {
            try {
                count = orderHistoryMapper.updateByPrimaryKeySelective(orderHistory);
            } catch (Exception e) {
                LOG.error("方法 updateOrderHistory 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public List<OrderHistory> selectByOrderId(Integer orderId) {
        List<OrderHistory> orderHistoryList = null;
        try {
            orderHistoryList = orderHistoryMapper.selectByOrderId(orderId);
        } catch (Exception e) {
            LOG.error("方法 selectByOrderId 异常,{}", e);
        }
        return orderHistoryList;
    }
}
