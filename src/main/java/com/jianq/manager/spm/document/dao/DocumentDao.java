package com.jianq.manager.spm.document.dao;

import com.jianq.manager.spm.document.entity.Document;

import java.util.List;

public interface DocumentDao {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Document record);

    Document selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Document record);

    List<Document> listShareDocument(Document document);

    List<Document> listOwnDocument(Document document);

    List<Document> listByFid(Integer fid);
}