package com.jianq.manager.spm.payee.service.impl;

import com.jianq.manager.spm.payee.dao.PayeeMapper;
import com.jianq.manager.spm.payee.entity.Payee;
import com.jianq.manager.spm.payee.service.PayeeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Leo on 2017/10/30
 */
@Service
public class PayeeServiceImpl implements PayeeService {
    private static final Log LOG = LogFactory.getLog(PayeeServiceImpl.class);
    @Autowired
    private PayeeMapper payeeMapper;


    @Override
    public List<Payee> listPayee(Payee payee) {
        List<Payee> payeeList = null;
        try {
            payeeList = payeeMapper.listPayee(payee);
        } catch (Exception e) {
            LOG.error("方法 listPayee 异常,{}", e);
        }
        return payeeList;
    }
}
