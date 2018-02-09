package com.jianq.manager.spm.document.dao;

import com.jianq.manager.spm.document.entity.DocumentLog;


public interface DocumentLogDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(DocumentLog record);

    DocumentLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DocumentLog record);
}