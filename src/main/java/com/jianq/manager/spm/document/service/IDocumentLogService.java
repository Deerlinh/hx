package com.jianq.manager.spm.document.service;

import com.jianq.manager.spm.document.entity.Document;
import com.jianq.manager.spm.document.entity.DocumentLog;

/**
 * @author Leo on 2017/9/6
 */
public interface IDocumentLogService {
    /**
     * 删
     *
     * @param id
     * @return
     */
    Integer deleteByPrimaryKey(Integer id);

    /**
     * 增
     *
     * @param documentLog
     * @return
     */
    Integer insertDocumentLog(DocumentLog documentLog);

    /**
     * 查
     *
     * @param id
     * @return
     */
    DocumentLog selectByPrimaryKey(Integer id);

    /**
     * 改
     *
     * @param documentLog
     * @return
     */
    Integer updateDocumentLog(DocumentLog documentLog);

}
