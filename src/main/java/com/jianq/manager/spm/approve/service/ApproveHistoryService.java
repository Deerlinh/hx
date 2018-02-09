package com.jianq.manager.spm.approve.service;

import com.jianq.manager.spm.approve.entity.ApproveHistory;
import com.jianq.manager.spm.order.entity.Order;

import java.util.List;

/**
 * @author Leo on 2017/10/23
 */
public interface ApproveHistoryService {
    Integer delete(Integer id);

    Integer insert(ApproveHistory record);

    ApproveHistory selectById(Integer id);

    Integer update(ApproveHistory approveHistory);

    Integer saveApproveHistory(Order order, ApproveHistory approveHistory);

    List<ApproveHistory> listByOrderId(Integer orderId);
}
