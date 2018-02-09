/**
 * <br>
 * Package: com.jianq.emm.api.util <br>
 * FileName: PushNotificationUtil.java <br>
 * <br>
 *
 * @version
 * @author hp
 * @created 2017-4-13
 */
package com.jianq.manager.util;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 审批消息推送的工具类
 */
public class ApprovePushNotificationUtil {

    /**
     * 消息推送服务的接口
     */
    private static final String PUSH_SERVER_URL = StaticPropertiesUtil
            .getValue("pushServerUrl");

    /**
     * 星金融工作群的bizCode
     */
    private static final String BIZ_CODE_XDSQ = "biz-db-msg";
    /**
     * 财务公司用印审批团队的bizCode
     */
    private static final String BIZ_CODE_CWYYSP = "biz-cwyysp-db-msg";
    /**
     * 集团用印审批团队的bizCode
     */
    private static final String BIZ_CODE_JTYYSP = "biz-jtyysp-db-msg";

    public static String getBizCodeByCargoId(Integer cargoId) {
        String bizCode = "";
        switch (cargoId) {
            case 1:
                bizCode = BIZ_CODE_XDSQ;
                break;

            case 2:
                bizCode = BIZ_CODE_CWYYSP;
                break;

            case 3:
                bizCode = BIZ_CODE_JTYYSP;
                break;

        }
        return bizCode;
    }


    private static Log log = LogFactory.getLog(PushNotificationUtil.class);

    /**
     * 消息推送
     *
     * @param userCode    被推送者手机号
     * @param bizCode
     * @param id          作为H5的入参
     * @param title       标题
     * @param summary     控制格式的数组。 如：des:value
     * @param processed   其余H5的入参
     * @param otherParams 其余H5的入参
     * @return
     */
    private static boolean pushNotification(String userCode, String bizCode,
                                            String id, String title, JSONArray summary, boolean processed,
                                            Map<String, Object> otherParams) {
        StringBuilder sb = new StringBuilder();
        sb.append("usercode=").append(userCode);
        sb.append("&bizCode=").append(bizCode);

        JSONObject contentJson = new JSONObject();
        contentJson.put("id", id);
        contentJson.put("title", title);
        contentJson.put("summary", summary.toJSONString());
        if (processed) {
            contentJson.put("status", "已处理");
        } else {
            contentJson.put("status", "未处理");
        }
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
        System.out.println("param:" + param + ",url:" + PUSH_SERVER_URL
                + " result：" + result);
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
     * 推送或者更新
     *
     * @param userCode    手机号
     * @param orderId     订单id
     * @param cargoName   商品名称
     * @param processed   处理状态 false:未处理 true:已处理
     * @param otherParams 其余需要传递到H5页面的参数
     * @return
     */
    public static boolean pushOrFlushUserMsgForApproveSystem(String bizCode, String title, String userCode,
                                                             String orderId, String cargoName, boolean processed,
                                                             Map<String, Object> otherParams) {

        // 订单需要达到的长度
        Integer needLength = Integer.parseInt(StaticPropertiesUtil
                .getValue("orderIdLength") == null ? "14"
                : StaticPropertiesUtil.getValue("orderIdLength"));

        // 组装summary 格式
        Map<String, Object> summaryMap = new LinkedHashMap<String, Object>();
        summaryMap.put("订单号：",
                StringUtil.appendStrToLength(orderId, needLength, "0", true));
        summaryMap.put("商品名称：", cargoName);
        if (processed) {
            summaryMap.put("处理状态：", "已处理");
        } else {
            summaryMap.put("处理状态：", "未处理");
        }
        JSONArray summary = ApprovePushNotificationUtil.buildSummary(summaryMap);

        // 推送
        boolean result = pushNotification(userCode, bizCode,
                orderId, title, summary, processed, otherParams);
        return result;
    }

    /**
     * 给下一处理人推送消息，并给当前处理人更新状态为 已处理
     *
     * @param nextUserCode    下一人的手机号
     * @param currentUserCode 当前处理人的手机号
     * @param orderId         订单id
     * @param cargoName       商品名称
     * @param otherParams     其余H5的入参
     */
    public static void pushNextUserAndFlushCurrentUser(String bizCode, String title, String nextUserCode,
                                                       String currentUserCode, String orderId, String cargoName,
                                                       Map<String, Object> otherParams) {
        // 推送下一个人的消息
        pushOrFlushUserMsgForApproveSystem(bizCode, title, nextUserCode, orderId, cargoName,
                false, otherParams);
        // 刷新当前人的消息
        pushOrFlushUserMsgForApproveSystem(bizCode, title, currentUserCode, orderId, cargoName,
                true, otherParams);
    }
}
