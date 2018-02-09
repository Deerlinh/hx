package com.jianq.manager.spm.team.controller;

import com.jianq.manager.spm.cargo.entity.TeamCargo;
import com.jianq.manager.spm.cargo.service.TeamCargoService;
import com.jianq.manager.spm.constant.OrderConstant;
import com.jianq.manager.spm.order.entity.Order;
import com.jianq.manager.spm.order.service.IOrderService;
import com.jianq.manager.spm.team.entity.Teammember;
import com.jianq.manager.spm.team.service.TeammemberService;
import com.jianq.manager.spm.todo.entity.Todo;
import com.jianq.manager.spm.todo.service.TodoService;
import com.jianq.manager.spm.user.entity.User;
import com.jianq.manager.spm.user.service.UserService;
import com.jianq.manager.util.ListUtil;
import com.jianq.manager.util.NumberUtil;
import com.jianq.manager.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Leo on 2017/10/24
 */
@RestController
@RequestMapping("/**/teammember")
public class TeammemberController {
    @Autowired
    private UserService userService;
    @Autowired
    private TeammemberService teammemberService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private TodoService todoService;
    @Autowired
    private TeamCargoService teamCargoService;

    /**
     * 获取下一审批人
     *
     * @param userCode
     * @return
     */
    @RequestMapping("/next")
    public Object getNextApproveUser(String userCode, Integer orderId) {
        User user = userService.getUser(userCode);
        if (user == null) {
            return ReturnUtil.fail("请重新登录");
        }
        Order order = orderService.selectById(orderId);
        if (order == null) {
            return ReturnUtil.fail("订单不存在");
        }
        TeamCargo teamCargo = teamCargoService.selectByCargoId(order.getCargoId());
        if (teamCargo == null) {
            return ReturnUtil.fail("商品没用对应的团队");
        }
        Teammember teammember = teammemberService.getNextRoleTeammember(teamCargo.getTeamId(), user.getUserId());
        if (teammember == null) {
            return ReturnUtil.fail("没有下一处理人");
        }
        user = userService.selectById(teammember.getUserId());
        if (user == null) {
            return ReturnUtil.fail("下一处理人不存在");
        }
        return ReturnUtil.success(user);
    }

    /**
     * @param userCode
     * @param orderId
     * @param state
     * @return
     */
    @RequestMapping("/approve")
    public Object approveOrder(String userCode, Integer orderId, Integer state, String opinion, Integer nextUserId) {
        User user = userService.getUser(userCode);
        if (user == null) {
            return ReturnUtil.fail("请重新登录");
        }
        Order order = orderService.selectById(orderId);
        if (order == null) {
            return ReturnUtil.fail("订单不存在");
        }
        List<Todo> todoList = todoService.selectByTodo(new Todo(orderId, user.getUserId(), OrderConstant.TODO_STATE));
        if (ListUtil.isEmpty(todoList)) {
            return ReturnUtil.fail("该订单已处理");
        }
        Integer flag = teammemberService.approveOrder(order, todoList.get(0), state, opinion, nextUserId);
        if (!NumberUtil.isGreaterThan0(flag)) {
            return ReturnUtil.fail("订单处理异常,请稍后重试");
        }
        return ReturnUtil.success("订单处理成功");
    }
}
