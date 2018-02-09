package com.jianq.manager.spm.approve.service.impl;

import com.jianq.manager.spm.approve.dao.ApproveHistoryMapper;
import com.jianq.manager.spm.approve.entity.ApproveHistory;
import com.jianq.manager.spm.approve.service.ApproveHistoryService;
import com.jianq.manager.spm.constant.OrderConstant;
import com.jianq.manager.spm.constant.TeamCargoConstant;
import com.jianq.manager.spm.order.entity.Order;
import com.jianq.manager.spm.user.entity.User;
import com.jianq.manager.spm.user.service.UserService;
import com.jianq.manager.util.NumberUtil;
import com.jianq.manager.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Leo on 2017/10/23
 */
@Service
public class ApproveHistoryServiceImpl implements ApproveHistoryService {
    private static final Log LOG = LogFactory.getLog(ApproveHistoryServiceImpl.class);
    @Autowired
    private ApproveHistoryMapper approveHistoryMapper;
    @Autowired
    private UserService userService;

    @Override
    public Integer delete(Integer id) {
        Integer count = null;
        if (id != null) {
            try {
                count = approveHistoryMapper.deleteByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 deleteApproveHistory 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public Integer insert(ApproveHistory approveHistory) {
        Integer count = null;
        if (approveHistory != null) {
            try {
                count = approveHistoryMapper.insertSelective(approveHistory);
            } catch (Exception e) {
                LOG.error("方法 insertApproveHistory 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public ApproveHistory selectById(Integer id) {
        ApproveHistory approveHistory = null;
        if (id != null) {
            try {
                approveHistory = approveHistoryMapper.selectByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 selectById 异常,{}", e);
            }
        }
        return approveHistory;
    }

    @Override
    public Integer update(ApproveHistory approveHistory) {
        Integer count = null;
        if (approveHistory != null) {
            try {
                count = approveHistoryMapper.updateByPrimaryKeySelective(approveHistory);
            } catch (Exception e) {
                LOG.error("方法 updateApproveHistory 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public Integer saveApproveHistory(Order order, ApproveHistory approveHistory) {
        Integer count = null;
        if (approveHistory != null) {
            //获取完整的审批记录对象
            approveHistory = this.getCompleteByApproveHistory(order, approveHistory);
            count = this.insert(approveHistory);
        }
        return count;
    }

    @Override
    public List<ApproveHistory> listByOrderId(Integer orderId) {
        List<ApproveHistory> approveHistoryList = null;
        if (orderId != null) {
            try {
                approveHistoryList = approveHistoryMapper.listByOrderId(orderId);
            } catch (Exception e) {
                LOG.error("方法 listByOrderId 异常,{}", e);
            }
        }
        return approveHistoryList;
    }

    private ApproveHistory getCompleteByApproveHistory(Order order, ApproveHistory approveHistory) {
        String remark = "";
        Integer state = approveHistory.getState();
        Integer roleId = approveHistory.getRoleId();
        User user = userService.selectById(approveHistory.getUserId());
        String userName = (user == null ? "" : user.getUserName());
        approveHistory.setUserName(userName);
        approveHistory.setOrderId(order.getId());
        if (NumberUtil.isGreaterThan0(roleId)) {
            String opinion = approveHistory.getOpinion();
            if (NumberUtil.isGreaterThan0(state)) {
                opinion = (StringUtil.isEmpty(opinion) ? "同意" : opinion);
                remark = TeamCargoConstant.initMap.get(order.getCargoId()) + " " + OrderConstant.initMap.get(roleId) + ":" + userName + "审批结果:" + "同意" + " 意见:" + opinion;
            } else {
                opinion = (StringUtil.isEmpty(opinion) ? "不同意" : opinion);
                remark = TeamCargoConstant.initMap.get(order.getCargoId()) + " " + OrderConstant.initMap.get(roleId) + ":" + userName + "审批结果:" + "不同意" + " 意见:" + opinion;
            }
        } else {
            remark = order.getPayee() + "" + userName + "提交订单,来自店铺: " + approveHistory.getShop();
        }
        approveHistory.setRemark(remark);
        return approveHistory;
    }
}
