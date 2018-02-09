package com.jianq.manager.spm.approve.dao;

import com.jianq.manager.spm.approve.entity.ApproveHistory;

import java.util.List;

public interface ApproveHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ApproveHistory record);

    ApproveHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApproveHistory record);

    List<ApproveHistory> listByOrderId(Integer orderId);
}