package com.jianq.manager.spm.payee.controller;

import com.jianq.manager.spm.payee.entity.Payee;
import com.jianq.manager.spm.payee.service.PayeeService;
import com.jianq.manager.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Leo on 2017/10/30
 */
@RestController
@RequestMapping("/**/payee")
public class PayeeController {
    @Autowired
    private PayeeService payeeService;

    @RequestMapping("/list")
    public Object listPayee(Payee payee) {
        List<Payee> payeeList = payeeService.listPayee(payee);
        return ReturnUtil.success(payeeList);
    }
}
