package com.jianq.manager.util;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口返回工具类
 */
public class ReturnUtil {

    /**
     * 存放code和默认的message的初始化map
     */
    private static Map<String, String> initMap;

    /**
     * 成功
     */
    public static final String SUCCESS = "1000";
    /**
     * 参数值缺失（为空）错误
     */
    public static final String ERROR_PARAM_VALUE_MISSING = "1001";
    /**
     * 参数格式不正确错误
     */
    public static final String ERROR_PARAM_FORMAT_NOT_CORRECT = "1002";
    /**
     * 异常错误
     */
    public static final String ERROR_EXCEPTION = "500";

    /**
     * 服务器内部错误：即数据异常
     */
    public static final String ERROR_DATA_EXCEPTION = "501";

    /**
     * session失效，请重新登录
     */
    public static final String ERROR_UNLOGIN = "001";

    /**
     * 网络异常 请重试
     */
    public static final String ERROR_NETWORK_EXCEPTION = "000";

    /**
     * 初始化返回值及默认描述信息
     */
    static {
        initMap = new HashMap<String, String>();
        initMap.put(SUCCESS, "提交成功");
        initMap.put(ERROR_PARAM_VALUE_MISSING, "参数值不能为空");
        initMap.put(ERROR_PARAM_FORMAT_NOT_CORRECT, "参数格式不正确");
        initMap.put(ERROR_EXCEPTION, "接口异常");
        initMap.put(ERROR_DATA_EXCEPTION, "请求数据不正确");
        initMap.put(ERROR_UNLOGIN, "请重新登录");
        initMap.put(ERROR_NETWORK_EXCEPTION, "网络异常,请稍后重试");
    }

    /**
     * 接口返回成功
     */
    public static Object success() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", SUCCESS);
        resultMap.put("message", initMap.get(SUCCESS));
        return resultMap;
    }

    /**
     * 接口返回封装的方法
     *
     * @param message ：描述信息
     * @param object  ：前端需要的结果对象（没有此项传null）
     * @return
     */
    public static Object success(String message, Object object) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", SUCCESS);
        if (object != null) {
            resultMap.put("result", object);
        }
        resultMap.put("message", message);
        return resultMap;
    }

    /**
     * 接口返回封装的方法
     *
     * @param message ：描述信息
     * @return
     */
    public static Object success(String message) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", SUCCESS);
        resultMap.put("message", message);
        return resultMap;
    }

    /**
     * 接口返回异常
     */
    public static Object fail() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", ERROR_EXCEPTION);
        resultMap.put("message", initMap.put(ERROR_DATA_EXCEPTION, "请求数据不正确"));
        return resultMap;
    }

    /**
     * 接口返回异常
     */
    public static Object fail(String message) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", ERROR_EXCEPTION);
        resultMap.put("message", message);
        return resultMap;
    }

    /**
     * 接口返回异常
     */
    public static Object fail(String code, String message) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", code);
        resultMap.put("message", message);
        return resultMap;
    }

    /**
     * 接口返回封装的方法
     *
     * @param object ：前端需要的结果对象（没有此项传null）
     * @return
     */
    public static Object success(Object object) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", SUCCESS);
        if (object != null) {
            resultMap.put("result", object);
        }
        resultMap.put("message", initMap.get(SUCCESS));
        return resultMap;
    }

    /**
     * 接口返回封装的方法
     *
     * @param code ：状态码
     * @return
     */
    public static Object returnValue(String code) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", code);
        resultMap.put("message", initMap.get(code));
        return resultMap;
    }

    /**
     * 接口返回封装的方法
     *
     * @param code    ：状态码
     * @param message ：描述信息
     * @return
     */
    public static Object returnValue(String code, String message) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", code);
        resultMap.put("message", message);
        return resultMap;
    }

    /**
     * 接口返回封装的方法
     *
     * @param code   ：状态码
     * @param object ：前端需要的结果对象（没有此项传null）
     * @return
     */
    public static Object returnValue(String code, Object object) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", code);
        if (object != null) {
            resultMap.put("result", object);
        }
        resultMap.put("message", initMap.get(code));
        return resultMap;
    }

    /**
     * 接口返回封装的方法
     *
     * @param code    ：状态码
     * @param message ：描述信息（传null即为默认描述信息）
     * @param object  ：前端需要的结果对象（没有此项传null）
     * @return
     */
    public static Object returnValue(String code, String message, Object object) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", code);
        if (object != null) {
            resultMap.put("result", object);
        }
        if (StringUtil.isBlank(message)) {
            resultMap.put("message", initMap.get(code));
        } else {
            resultMap.put("message", message);
        }
        return resultMap;
    }

    /**
     * 接口返回封装的方法包含分页
     *
     * @param tList  ：前端需要的结果集合
     * @param tCount ：前端需要的结果数量
     * @return
     */
    public static <T> Object successPage(List<T> tList, Integer tCount) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", SUCCESS);
        resultMap.put("message", initMap.get(SUCCESS));
        resultMap.put("list", tList);
        resultMap.put("count", tCount);
        return resultMap;
    }

}
