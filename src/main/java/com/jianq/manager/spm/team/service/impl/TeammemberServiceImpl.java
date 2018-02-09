package com.jianq.manager.spm.team.service.impl;

import com.jianq.manager.spm.approve.entity.ApproveHistory;
import com.jianq.manager.spm.approve.service.ApproveHistoryService;
import com.jianq.manager.spm.cargo.entity.TeamCargo;
import com.jianq.manager.spm.cargo.service.TeamCargoService;
import com.jianq.manager.spm.constant.FileConstant;
import com.jianq.manager.spm.constant.OrderConstant;
import com.jianq.manager.spm.order.entity.Order;
import com.jianq.manager.spm.order.entity.OrderHistory;
import com.jianq.manager.spm.order.service.IOrderService;
import com.jianq.manager.spm.order.service.OrderHistoryService;
import com.jianq.manager.spm.team.dao.TeammemberMapper;
import com.jianq.manager.spm.team.entity.Teammember;
import com.jianq.manager.spm.team.service.TeammemberService;
import com.jianq.manager.spm.todo.entity.Todo;
import com.jianq.manager.spm.todo.service.TodoService;
import com.jianq.manager.spm.user.entity.User;
import com.jianq.manager.spm.user.service.UserService;
import com.jianq.manager.util.ApprovePushNotificationUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Leo on 2017/10/23
 */
@Service
public class TeammemberServiceImpl implements TeammemberService {
    private static final Log LOG = LogFactory.getLog(TeammemberServiceImpl.class);
    @Autowired
    private TeammemberMapper teammemberMapper;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private TodoService todoService;
    @Autowired
    private ApproveHistoryService approveHistoryService;
    @Autowired
    private OrderHistoryService orderHistoryService;
    @Autowired
    private TeamCargoService teamCargoService;
    @Autowired
    private UserService userService;

    @Override
    public int delete(Integer id) {
        Integer count = null;
        if (id != null) {
            try {
                count = teammemberMapper.deleteByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 delete 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public int insert(Teammember teammember) {
        Integer count = null;
        if (teammember != null) {
            try {
                count = teammemberMapper.insertSelective(teammember);
            } catch (Exception e) {
                LOG.error("方法 insertTodo 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public Teammember selectById(Integer id) {
        Teammember teammember = null;
        if (id != null) {
            try {
                teammember = teammemberMapper.selectByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 selectById 异常,{}", e);
            }
        }
        return teammember;
    }

    @Override
    public int update(Teammember teammember) {
        Integer count = null;
        if (teammember != null) {
            try {
                count = teammemberMapper.updateByPrimaryKeySelective(teammember);
            } catch (Exception e) {
                LOG.error("方法 updateTeammember 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public Teammember getNextRoleTeammember(Integer teamId, Integer userId) {
        Teammember teammember = null;
        if (userId != null) {
            try {
                teammember = teammemberMapper.getNextRoleTeammember(teamId, userId);
            } catch (Exception e) {
                LOG.error("方法 getNextRoleTeammember 异常,{}", e);
            }
        }
        return teammember;
    }

    @Override
    @Transactional
    public Integer approveOrder(Order order, Todo todo, Integer state, String opinion, Integer nextUserId) {
        Integer flag = null;
        Integer teamId = teamCargoService.getTeamIdByOrderId(order.getId());
        Teammember teammember = this.selectByTeamIdAndUserId(teamId, todo.getUserId());
        if (teammember == null) {
            LOG.error("订单处理人不在审批团队");
            return flag;
        }
        Integer roleId = teammember.getRoleId();
        try {
            if (roleId == 5) {
                flag = this.executeOrder(order, todo, state, opinion);
            } else {
                flag = this.approve(order, todo, state, opinion, nextUserId);
            }
        } catch (Exception e) {
            LOG.error("方法 approveOrder 异常, {}", e);
        }
        return flag;
    }

    @Override
    public Teammember selectByTeamIdAndUserId(Integer teamId, Integer userId) {
        Teammember teammember = null;
        if (userId != null && teamId != null) {
            try {
                teammember = teammemberMapper.selectByTeamIdAndUserId(teamId, userId);
            } catch (Exception e) {
                LOG.error("方法 selectByUserId 异常,{}", e);
            }
        }
        return teammember;
    }

    @Override
    public List<Teammember> selectByTeamIdAndRoleId(Integer teamId, Integer roleId) {
        List<Teammember> teammemberList = null;
        if (teamId != null && roleId != null) {
            try {
                teammemberList = teammemberMapper.selectByTeamIdAndRoleId(teamId, roleId);
            } catch (Exception e) {
                LOG.error("方法 selectByUserId 异常,{}", e);
            }
        }
        return teammemberList;
    }

    /**
     * 执行人执行订单
     *
     * @return
     */
    private Integer executeOrder(Order order, Todo todo, Integer state, String opinion) {
        Integer flag = null;
        Integer orderId = order.getId();
        Integer cargoId = order.getCargoId();
        Integer orderStatus = null;
        String reamrk = null;
        Integer currentUserId = todo.getUserId();
        User currentUser = userService.selectById(currentUserId);
        if (state == 1) {//审批同意
            orderStatus = OrderConstant.PAYMENT_AUDIT_COMPLETED;
            reamrk = "您的订单已经审批完成";
        } else {
            orderStatus = OrderConstant.PAYMENT__DIS_AGREE;
            reamrk = "您的订单审批未通过";
        }
        //1.新增商品记录
        orderHistoryService.insertOrderHistory(new OrderHistory(orderId, reamrk, currentUserId));
        //2.修改订单状态
        order.setStatus(orderStatus);
        flag = orderService.updateById(order);
        //3.待办变已办
        todoService.updateTodo(new Todo(todo.getId(), OrderConstant.DONE_STATE));
        //4.新增审批记录
        approveHistoryService.saveApproveHistory(order, new ApproveHistory(currentUserId, state, OrderConstant.DEFAULT_SHOP_NAME, 5, opinion));
        // 消息推送：改成已处理状态
        ApprovePushNotificationUtil.pushOrFlushUserMsgForApproveSystem(ApprovePushNotificationUtil.getBizCodeByCargoId(cargoId), order.getName(), currentUser.getUserCode(), orderId.toString(), OrderConstant.DEFAULT_SHOP_NAME, true, null);

        return flag;
    }

    /**
     * 非执行人执行订单(申请/审核/风控/终审)
     *
     * @return
     */
    private Integer approve(Order order, Todo todo, Integer state, String opinion, Integer nextUserId) {
        Integer flag = null;
        Integer orderId = order.getId();
        Integer cargoId = order.getCargoId();
        String reamrk = null;
        Integer orderStatus = null;//订单审批状态:0不同意1同意
        Integer currentUserId = todo.getUserId();
        Teammember teammember = this.selectByTeamIdAndUserId(teamCargoService.getTeamIdByOrderId(orderId), currentUserId);
        Integer roleId = teammember.getRoleId();
        User nextUser = userService.selectById(nextUserId);
        User currentUser = userService.selectById(currentUserId);
        if (state == 1) {//审批同意
            if (nextUser == null || currentUser == null) {
                LOG.info("审批同意没有下一处理人");
                return flag;
            }
            //0.新增下一处理人的待办
            orderStatus = OrderConstant.PAYMENT_IN_REVIEW;
            todoService.insertTodo(new Todo(orderId, nextUserId, order.getAmount()));
            //消息推送到下一处理人
            ApprovePushNotificationUtil.pushNextUserAndFlushCurrentUser(ApprovePushNotificationUtil.getBizCodeByCargoId(cargoId), order.getName(), nextUser.getUserCode(), currentUser.getUserCode(), orderId + "", OrderConstant.DEFAULT_SHOP_NAME, null);
        } else {
            //1.新增商品记录
            reamrk = "您的订单审批未通过";
            orderStatus = OrderConstant.PAYMENT__DIS_AGREE;
            orderHistoryService.insertOrderHistory(new OrderHistory(orderId, reamrk, currentUserId));
            // 消息推送：改成已处理状态
            ApprovePushNotificationUtil.pushOrFlushUserMsgForApproveSystem(ApprovePushNotificationUtil.getBizCodeByCargoId(cargoId), order.getName(), currentUser.getUserCode(), orderId.toString(), OrderConstant.DEFAULT_SHOP_NAME, true, null);
        }
        //2.修改订单状态
        order.setStatus(orderStatus);
        flag = orderService.updateById(order);
        //3.待办变已办
        todoService.updateTodo(new Todo(todo.getId(), OrderConstant.DONE_STATE));
        //4.新增审批记录
        approveHistoryService.saveApproveHistory(order, new ApproveHistory(currentUserId, state, OrderConstant.DEFAULT_SHOP_NAME, roleId, opinion));
        return flag;
    }
}
