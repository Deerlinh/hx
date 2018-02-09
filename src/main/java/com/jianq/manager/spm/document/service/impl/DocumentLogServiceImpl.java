package com.jianq.manager.spm.document.service.impl;

import com.jianq.manager.spm.document.dao.DocumentLogDao;
import com.jianq.manager.spm.document.entity.DocumentLog;
import com.jianq.manager.spm.document.service.IDocumentLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Leo on 2017/9/6
 */
@Service
public class DocumentLogServiceImpl implements IDocumentLogService {
    private static final Log LOG = LogFactory.getLog(DocumentLogServiceImpl.class);

    @Autowired
    private DocumentLogDao documentLogDao;

    @Override
    public Integer deleteByPrimaryKey(Integer id) {
        Integer count = null;
        if (id != null) {
            try {
                count = documentLogDao.deleteByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 deleteByPrimaryKey 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public Integer insertDocumentLog(DocumentLog documentLog) {
        Integer count = null;
        if (documentLog != null) {
            try {
                count = documentLogDao.insertSelective(documentLog);
            } catch (Exception e) {
                LOG.error("方法 insertDocumentLog 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public DocumentLog selectByPrimaryKey(Integer id) {
        DocumentLog documentLog = null;
        if (id != null) {
            try {
                documentLog = documentLogDao.selectByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 selectByPrimaryKey 异常,{}", e);
            }
        }
        return documentLog;
    }

    @Override
    public Integer updateDocumentLog(DocumentLog documentLog) {
        Integer count = null;
        if (documentLog != null) {
            try {
                count = documentLogDao.updateByPrimaryKeySelective(documentLog);
            } catch (Exception e) {
                LOG.error("方法 updateDocumentLog 异常,{}", e);
            }
        }
        return count;
    }
}
