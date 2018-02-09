package com.jianq.manager.spm.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Leo on 2017/11/24
 */
public class TeamCargoConstant {
    /**
     * 审批团队的的初始化map:商品id和审批团队的对照
     */
    public static Map<Integer, String> initMap;

    /**
     * 初始化返回值及默认描述信息
     */
    static {
        initMap = new HashMap<Integer, String>();
        initMap.put(1, "星金融工作群");
        initMap.put(2, "财务公司用印审批团队");
        initMap.put(3, "集团用印审批团队");
    }
}
