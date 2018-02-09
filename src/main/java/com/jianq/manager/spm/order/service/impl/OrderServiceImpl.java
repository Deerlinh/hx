package com.jianq.manager.spm.order.service.impl;

import com.jianq.manager.spm.approve.entity.ApproveHistory;
import com.jianq.manager.spm.approve.service.ApproveHistoryService;
import com.jianq.manager.spm.cargo.entity.TeamCargo;
import com.jianq.manager.spm.cargo.service.TeamCargoService;
import com.jianq.manager.spm.constant.OrderConstant;
import com.jianq.manager.spm.order.dao.OrderMapper;
import com.jianq.manager.spm.order.entity.Order;
import com.jianq.manager.spm.order.entity.OrderHistory;
import com.jianq.manager.spm.order.service.IOrderService;
import com.jianq.manager.spm.order.service.OrderHistoryService;
import com.jianq.manager.spm.order.vo.OrderVO;
import com.jianq.manager.spm.team.entity.Team;
import com.jianq.manager.spm.team.entity.Teammember;
import com.jianq.manager.spm.team.service.TeammemberService;
import com.jianq.manager.spm.todo.entity.Todo;
import com.jianq.manager.spm.todo.service.TodoService;
import com.jianq.manager.spm.user.entity.User;
import com.jianq.manager.spm.user.service.UserService;
import com.jianq.manager.util.ApprovePushNotificationUtil;
import com.jianq.manager.util.NumberUtil;
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
public class OrderServiceImpl implements IOrderService {
    private static final Log LOG = LogFactory.getLog(OrderServiceImpl.class);

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderHistoryService orderHistoryService;
    @Autowired
    private TodoService todoService;
    @Autowired
    private TeammemberService teammemberService;
    @Autowired
    private ApproveHistoryService approveHistoryService;
    @Autowired
    private TeamCargoService teamCargoService;
    @Autowired
    private UserService userService;


    @Override
    public Integer deleteById(Integer id) {
        Integer count = null;
        if (id != null) {
            try {
                count = orderMapper.deleteByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 deleteById 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    @Transactional
    public Integer insertOrder(Order order) {
        Integer id = null;
        if (order != null) {
            try {
                Integer count = orderMapper.insertSelective(order);
                if (NumberUtil.isGreaterThan0(count)) {
                    id = order.getId();
                    orderHistoryService.insertOrderHistory(new OrderHistory(id, OrderConstant.SUBMIT_ORDER_INFO, order.getCreator()));
                }
            } catch (Exception e) {
                LOG.error("方法 insertOrder 异常,{}", e);
            }
        }
        return id;
    }

    @Override
    public Order selectById(Integer id) {
        Order order = null;
        if (id != null) {
            try {
                order = orderMapper.selectByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 selectById 异常,{}", e);
            }
        }
        return order;
    }

    @Override
    public Integer updateById(Order order) {
        Integer count = null;
        if (order != null) {
            try {
                count = orderMapper.updateByPrimaryKey(order);
            } catch (Exception e) {
                LOG.error("方法 updateById 异常,{}", e);
            }
        }
        return count;
    }


    @Override
    public List<Order> listOrder(OrderVO orderVO) {
        List<Order> orderList = null;
        try {
            orderList = orderMapper.listOrder(orderVO);
        } catch (Exception e) {
            LOG.error("方法 listOrder 异常,{}", e);
        }
        return orderList;
    }

    @Override
    public Integer countOrder(OrderVO orderVO) {
        Integer count = null;
        try {
            count = orderMapper.countOrder(orderVO);
        } catch (Exception e) {
            LOG.error("方法 countOrder 异常,{}", e);
        }
        return count;
    }

    @Override
    @Transactional
    public Integer paymentCompleted(Integer orderId) {
        Integer flag = null;
        Order order = this.selectById(orderId);
        if (order != null && order.getStatus() == OrderConstant.NON_PAYMENT_STATUS) {
            //订单创建者
            Integer userId = order.getCreator();
            Integer cargoId = order.getCargoId();
            TeamCargo teamCargo = teamCargoService.selectByCargoId(cargoId);
            if (teamCargo != null) {
                try {
                    //1.查找审批团队申请人
                    Teammember teammember = teammemberService.selectByTeamIdAndRoleId(teamCargo.getTeamId(), OrderConstant.APPLICANT_ROLE_ID).get(0);
                    Integer applicationUserId = teammember.getUserId();//申请人用户id
                    User applicationUser = userService.selectById(applicationUserId);
                    if (applicationUser == null) {
                        return flag;
                    }
                    //2.付款完成,修改订单状态
                    flag = this.updateById(new Order(orderId, OrderConstant.PAYMENT_COMPLETED_STATUS));
                    //3.插入订单完成记录
                    orderHistoryService.insertOrderHistory(new OrderHistory(orderId, OrderConstant.PAYMENT_COMPLETED_INFO, userId));
                    //4.插入待办记录
                    Integer todoId = todoService.insertTodo(new Todo(orderId, applicationUserId, order.getAmount()));
                    //5.插入审批历史记录
                    approveHistoryService.saveApproveHistory(order, new ApproveHistory(userId, OrderConstant.APPROVE_STATE_AGREE, OrderConstant.DEFAULT_SHOP_NAME, OrderConstant.CLIENT_SUBMISSION_ROLE_ID, null));
                    // 消息推送：未处理状态
                    ApprovePushNotificationUtil.pushOrFlushUserMsgForApproveSystem(ApprovePushNotificationUtil.getBizCodeByCargoId(cargoId), order.getName(), applicationUser.getUserCode(), orderId.toString(), OrderConstant.DEFAULT_SHOP_NAME, false, null);
                    //检测是否是机器人并自动执行
                    this.systemApprove(teammember, order, OrderConstant.APPROVE_STATE_AGREE, todoId);
                } catch (Exception e) {
                    LOG.error("方法 paymentCompleted 异常,{}", e);
                }
            } else {
                LOG.info("该订单没用对应的审批团队");
            }
        }
        return flag;
    }

    @Override
    @Transactional
    public Integer submitOrderWithOutPay(Order order) {
        if (order == null) {
            return null;
        }
        Integer id = null;
        Integer cargoId = order.getCargoId();
        TeamCargo teamCargo = teamCargoService.selectByCargoId(cargoId);
        if (teamCargo != null) {
            Integer teamId = teamCargo.getTeamId();
            //1.查找审批团队申请人
            Teammember teammember = teammemberService.selectByTeamIdAndRoleId(teamId, OrderConstant.APPLICANT_ROLE_ID).get(0);
            Integer applicationUserId = teammember.getUserId();//申请人用户id
            User applicationUser = userService.selectById(applicationUserId);
            if (applicationUser == null) {
                return id;
            }
            //2.插入订单信息
            order.setStatus(OrderConstant.PAYMENT_IN_REVIEW);
            id = this.insertOrder(order);
            order.setId(id);
            //3.插入待办记录
            Integer todoId = todoService.insertTodo(new Todo(id, applicationUserId, order.getAmount()));
            //4.插入审批历史记录
            approveHistoryService.saveApproveHistory(order, new ApproveHistory(order.getCreator(), OrderConstant.APPROVE_STATE_AGREE, OrderConstant.DEFAULT_SHOP_NAME, OrderConstant.CLIENT_SUBMISSION_ROLE_ID, null));
            //5.消息推送：未处理状态
            ApprovePushNotificationUtil.pushOrFlushUserMsgForApproveSystem(ApprovePushNotificationUtil.getBizCodeByCargoId(cargoId), order.getName(), applicationUser.getUserCode(), id + "", OrderConstant.DEFAULT_SHOP_NAME, false, null);
            //6.检测是否是机器人并自动执行
            this.systemApprove(teammember, order, OrderConstant.APPROVE_STATE_AGREE, todoId);
        }
        return id;
    }

    private void systemApprove(Teammember teammember, Order order, Integer state, Integer todoId) {
        if (teammember.getRobotType() == 1) {
            Integer roleId = teammember.getRoleId();
            //自动提交到下一审批人
            teammember = teammemberService.getNextRoleTeammember(teammember.getTeamId(), teammember.getUserId());
            if (teammember != null) {
                teammemberService.approveOrder(order, todoService.selectById(todoId), state, OrderConstant.initMap.get(roleId) + "自动执行", teammember.getUserId());
            }
        }
    }
}
