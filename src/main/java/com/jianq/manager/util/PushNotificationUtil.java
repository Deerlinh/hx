package com.jianq.manager.util;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 商城消息推送的工具类
 */
public class PushNotificationUtil {

    /**
     * 消息推送服务的接口
     */
    private static final String PUSH_SERVER_URL = StaticPropertiesUtil.getValue("pushServerUrl");
    /**
     * 金融超市的订单处理完毕的bizCode
     */
    private static final String BIZ_CODE_JINRONGCHAOSHI_ORDER = "biz-jrcs-order";

    /**
     * 金融超市的定向专享的bizCode
     */
    private static final String BIZ_CODE_JINRONGCHAOSHI_DINGXIANGZHUANGXIANG = "biz-jrcs-dxzx";

    /**
     * 商城的订单处理的标题
     */
    private static final String TITLE_SPM_ORDER = "您的订单已处理完成";

    /**
     * 商城的定向专享推送的标题
     */
    private static final String TITLE_SPM_DIRECT = "您有一笔定向专享待处理";

    private static Log log = LogFactory.getLog(PushNotificationUtil.class);

    /**
     * 消息推送
     *
     * @param userCode    被推送者手机号
     * @param bizCode
     * @param id          作为H5的入参
     * @param title       标题
     * @param summary     控制格式的数组。 如：des:value
     * @param otherParams 其余H5的入参
     * @return
     */
    private static boolean pushNotification(String userCode, String bizCode,
                                            String id, String title, JSONArray summary,
                                            Map<String, Object> otherParams) {
        StringBuilder sb = new StringBuilder();
        sb.append("usercode=").append(userCode);
        sb.append("&bizCode=").append(bizCode);

        JSONObject contentJson = new JSONObject();
        contentJson.put("id", id);
        contentJson.put("title", title);
        contentJson.put("summary", summary.toJSONString());
        if (otherParams == null) {

            otherParams = new LinkedHashMap<String, Object>();
        }
        // 默认添加orderId
        otherParams.put("orderId", id);

        Set<String> keySet = otherParams.keySet();
        for (String key : keySet) {
            contentJson.put(key, otherParams.get(key));
        }

        sb.append("&content=").append(contentJson);
        String param = sb.toString();

        String result = HttpUtil.sendPost(PUSH_SERVER_URL, param, false);
        log.info("消息推送接口，参数param是：" + param + ",url地址:" + PUSH_SERVER_URL
                + " ic的接口返回值：" + result);
        if (!StringUtils.isBlank(result) && result.contains("1000")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 组装summary的json数组
     *
     * @param paramMap
     * @return
     */
    private static JSONArray buildSummary(Map<String, Object> paramMap) {
        JSONArray summary = new JSONArray();
        Set<String> keySet = paramMap.keySet();
        for (String key : keySet) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("des", key);
            jsonObj.put("value", paramMap.get(key));
            summary.add(jsonObj);
        }
        return summary;
    }

    /**
     * 推送订单审批完成的
     *
     * @param userCode          手机号
     * @param orderId           订单id
     * @param cargoName         商品名称
     * @param approveStatusName 审批状态名称
     * @return
     */
    public static boolean pushOrderInfo(String userCode, String orderId,
                                        String cargoName, String approveStatusName) {
        // 订单需要达到的长度
        Integer needLength = Integer.parseInt(StaticPropertiesUtil
                .getValue("orderIdLength") == null ? "14"
                : StaticPropertiesUtil.getValue("orderIdLength"));

        // 组装summary 格式
        Map<String, Object> summaryMap = new LinkedHashMap<String, Object>();
        summaryMap.put("订单号：", StringUtil.appendStrToLength(orderId.toString(),
                needLength, "0", true));
        summaryMap.put("商品名称：", cargoName);
        summaryMap.put("审核状态：", approveStatusName);
        JSONArray summary = PushNotificationUtil.buildSummary(summaryMap);

        Map<String, Object> otherParams = new HashMap<String, Object>();
        otherParams.put("orderId", orderId);

        // 推送
        boolean result = pushNotification(userCode,
                BIZ_CODE_JINRONGCHAOSHI_ORDER, orderId, TITLE_SPM_ORDER,
                summary, otherParams);
        return result;
    }

    /**
     * 推送定向专享商品信息
     *
     * @param userCode            手机号
     * @param orderCargoId        订单商品id
     * @param lastOrderId         上一关联订单id
     * @param cargoName           商品名称
     * @param customerCompanyName 客户名称
     * @param overTime            过期时间
     * @return
     */
    public static boolean pushDirectEnjoyCargoInfo(String userCode,
                                                   String orderCargoId, String lastOrderId, String cargoName,
                                                   String customerCompanyName, Date overTime) {

        // 组装summary 格式
        Map<String, Object> summaryMap = new LinkedHashMap<String, Object>();
        summaryMap.put("商品名称：", cargoName);
        summaryMap.put("所属客户：", customerCompanyName);
        summaryMap.put("有效时间：",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(overTime));
        JSONArray summary = PushNotificationUtil.buildSummary(summaryMap);

        Map<String, Object> otherParams = new HashMap<String, Object>();
        otherParams.put("cargoId", orderCargoId);
        otherParams.put("lastOrderId", lastOrderId);
        otherParams.put("orderId", lastOrderId);

        // 推送
        boolean result = pushNotification(userCode,
                BIZ_CODE_JINRONGCHAOSHI_DINGXIANGZHUANGXIANG, lastOrderId,
                TITLE_SPM_DIRECT, summary, otherParams);
        return result;
    }

    public static void main(String[] args) {

        pushOrderInfo("15710183606", "550", "贴现商品", "审核通过");

        pushDirectEnjoyCargoInfo("15710183606", "538", "550", "贴现商品", "我是客户",
                new Date());
    }
}
