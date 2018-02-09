package com.jianq.manager.spm.order.controller;

import com.jianq.manager.spm.constant.OrderConstant;
import com.jianq.manager.spm.order.entity.Order;
import com.jianq.manager.spm.order.service.IOrderService;
import com.jianq.manager.spm.order.vo.OrderVO;
import com.jianq.manager.spm.picture.service.PicService;
import com.jianq.manager.spm.todo.service.TodoService;
import com.jianq.manager.spm.user.entity.User;
import com.jianq.manager.spm.user.service.UserService;
import com.jianq.manager.util.NumberUtil;
import com.jianq.manager.util.ReturnUtil;
import com.jianq.manager.util.StringUtil;
import com.jianq.manager.util.page.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Leo on 2017/10/23
 */
@RestController
@RequestMapping("/**/order")
public class OrderController {
    private static final Log LOG = LogFactory.getLog(OrderController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private PicService picService;
    @Autowired
    private TodoService todoService;

    /**
     * 提交订单(需要付款)
     *
     * @param userCode
     * @param name
     * @param payee
     * @param amount
     * @param base64pic base64编码的图片
     * @return
     */
    @RequestMapping("/submit")
    public Object submitOrder(Integer cargoId, String userCode, String name, String payee, Double amount, String base64pic, String remark) {
        User user = userService.getUser(userCode);
        if (user == null) {
            return ReturnUtil.fail("请重新登录");
        }
        String picUrl = picService.saveBase64Pic(base64pic);
        if (StringUtil.isEmpty(picUrl)) {
            LOG.info("图片编码: " + base64pic);
            return ReturnUtil.fail("照片上传失败,请稍后重试");
        }
        Order order = new Order(cargoId, name, payee, amount, picUrl, user.getUserId(), remark);
        Integer orderId = orderService.insertOrder(order);
        if (!NumberUtil.isGreaterThan0(orderId)) {
            return ReturnUtil.fail("提交失败,请稍后重试");
        }
        return ReturnUtil.success(orderService.selectById(orderId));
    }

    /**
     * 提交订单(不用付款)
     *
     * @param userCode
     * @param name
     * @param remark
     * @param base64pic base64编码的图片
     * @return
     */
    @RequestMapping("/submitOrder")
    public Object submitOrder2(Integer cargoId, String userCode, String name, String base64pic, String remark) {
        User user = userService.getUser(userCode);
        if (user == null) {
            return ReturnUtil.fail("请重新登录");
        }
        String picUrl = picService.saveBase64Pic(base64pic);
        if (StringUtil.isEmpty(picUrl)) {
            LOG.info("图片编码: " + base64pic);
            return ReturnUtil.fail("照片上传失败,请稍后重试");
        }
        Order order = new Order(cargoId, name, picUrl, user.getUserId(), remark);
        //提交订单(不用付款)
        Integer orderId = orderService.submitOrderWithOutPay(order);
        if (!NumberUtil.isGreaterThan0(orderId)) {
            return ReturnUtil.fail("提交失败,请稍后重试");
        }
        return ReturnUtil.success(orderService.selectById(orderId));
    }

    /**
     * 订单集合(含分页)
     *
     * @param userCode
     * @param orderVO
     * @param page
     * @return
     */
    @RequestMapping("/list")
    public Object listOrder(String userCode, OrderVO orderVO, Page page) {
        User user = userService.getUser(userCode);
        if (user == null) {
            return ReturnUtil.fail("请重新登录");
        }
        orderVO.setOrderPageSize(page.getPageSize());
        orderVO.setStartNum(page.getStartLineIndex());
        orderVO.setCreator(user.getUserId());
        List<Order> orderList = orderService.listOrder(orderVO);
        Integer orderCount = orderService.countOrder(orderVO);
        return ReturnUtil.successPage(orderList, orderCount);
    }

    /**
     * 查看订单详情
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/info")
    public Object getOrderInfo(String userCode, Integer orderId) {
        User user = userService.getUser(userCode);
        if (user == null) {
            return ReturnUtil.fail("请重新登录");
        }
        Order order = orderService.selectById(orderId);
        if (order == null) {
            return ReturnUtil.fail(OrderConstant.ORDER_NON_EXISTENT);
        }
        order.setRoleId(todoService.selectOrderRoleId(orderId));
        order.setCurrentProcessingPerson(todoService.selectUserNameByOrderId(orderId));
        order.setTodoStatus(todoService.selectTodoStatusByOrderIdAndUserCode(orderId, userCode));
        return ReturnUtil.success(order);
    }

    /**
     * 完成订单操作
     *
     * @param userCode
     * @param orderId
     * @return
     */
    @RequestMapping("/pay/completed")
    public Object paymentCompleted(String userCode, Integer orderId) {
        User user = userService.getUser(userCode);
        if (user == null) {
            return ReturnUtil.fail("请重新登录");
        }
        Order order = orderService.selectById(orderId);
        if (order == null) {
            return ReturnUtil.fail("订单不存在");
        }
        if (order.getStatus() != 0) {
            return ReturnUtil.fail("订单已经支付,请勿重复操作");
        }
        Integer flag = orderService.paymentCompleted(orderId);
        if (!NumberUtil.isGreaterThan0(flag)) {
            return ReturnUtil.fail("订单异常,请联系管理员");
        }
        return ReturnUtil.success("订单支付成功");
    }

}
