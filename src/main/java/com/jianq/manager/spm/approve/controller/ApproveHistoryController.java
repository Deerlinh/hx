package com.jianq.manager.spm.approve.controller;

import com.jianq.manager.spm.approve.entity.ApproveHistory;
import com.jianq.manager.spm.approve.service.ApproveHistoryService;
import com.jianq.manager.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Leo on 2017/10/25
 */
@RestController
@RequestMapping("/**/approveHistory")
public class ApproveHistoryController {
    @Autowired
    private ApproveHistoryService approveHistoryService;

    @RequestMapping("/list")
    public Object listByOrderId(Integer orderId) {
        List<ApproveHistory> approveHistoryList = approveHistoryService.listByOrderId(orderId);
        return ReturnUtil.success(approveHistoryList);
    }
}
