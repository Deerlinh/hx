package com.jianq.manager.spm.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leo on 2017/10/23
 */
public class OrderConstant {
    public static final String SUBMIT_ORDER_INFO = "您已提交订单,来自商铺:红星智慧金融";

    public static final String PAYMENT_COMPLETED_INFO = "您的付款已完成";

    public static final String ORDER_NON_EXISTENT = "订单不存在";
    /**
     * 订单未付款状态为0
     */
    public static final Integer NON_PAYMENT_STATUS = 0;
    /**
     * 订单付款完成状态为1
     */
    public static final Integer PAYMENT_COMPLETED_STATUS = 1;
    /**
     * 订单审核中状态为2
     */
    public static final Integer PAYMENT_IN_REVIEW = 2;
    /**
     * 订单审核完成状态为3
     */
    public static final Integer PAYMENT_AUDIT_COMPLETED = 3;
    /**
     * 订单审核不同意状态为4
     */
    public static final Integer PAYMENT__DIS_AGREE = 4;
    /**
     * 客户提交roleId为0
     */
    public static final Integer  CLIENT_SUBMISSION_ROLE_ID = 0;
    /**
     * 申请人roleId为1
     */
    public static final Integer APPLICANT_ROLE_ID = 1;
    /**
     * 默认商铺名称
     */
    public static final String DEFAULT_SHOP_NAME = "红星智慧金融";
    /**
     * 审批同意状态:1
     */
    public static final Integer APPROVE_STATE_AGREE = 1;
    /**
     * 待办状态:0
     */
    public static final Integer TODO_STATE = 0;

    /**
     * 已办状态:1
     */
    public static final Integer DONE_STATE = 1;
    /**
     * 审批团队的的初始化map:角色id和名称的对照
     */
    public static Map<Integer, String> initMap;

    /**
     * 初始化返回值及默认描述信息
     */
    static {
        initMap = new HashMap<Integer, String>();
        initMap.put(1, "申请人");
        initMap.put(2, "审核人");
        initMap.put(3, "风控人");
        initMap.put(4, "终审人");
        initMap.put(5, "执行人");
    }

}
