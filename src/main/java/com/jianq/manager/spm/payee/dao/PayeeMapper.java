package com.jianq.manager.spm.payee.dao;

import com.jianq.manager.spm.payee.entity.Payee;

import java.util.List;

public interface PayeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Payee record);

    Payee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Payee record);

    List<Payee> listPayee(Payee payee);

}