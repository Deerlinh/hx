package com.jianq.manager.spm.order.controller;

import com.jianq.manager.spm.order.entity.OrderHistory;
import com.jianq.manager.spm.order.service.OrderHistoryService;
import com.jianq.manager.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Leo on 2017/10/23
 */
@RestController
@RequestMapping("/**/orderHistory")
public class OrderHistoryController {
    @Autowired
    private OrderHistoryService orderHistoryService;

    @RequestMapping("/list")
    public Object listOrderHistory(Integer orderId) {
        List<OrderHistory> orderHistoryList = orderHistoryService.selectByOrderId(orderId);
        return ReturnUtil.success(orderHistoryList);
    }
}
