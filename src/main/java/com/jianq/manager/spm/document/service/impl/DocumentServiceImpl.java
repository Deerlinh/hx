package com.jianq.manager.spm.document.service.impl;

import com.jianq.manager.spm.document.dao.DocumentDao;
import com.jianq.manager.spm.document.entity.Document;
import com.jianq.manager.spm.document.entity.DocumentLog;
import com.jianq.manager.spm.document.service.IDocumentLogService;
import com.jianq.manager.spm.document.service.IDocumentService;
import com.jianq.manager.util.ListUtil;
import com.jianq.manager.util.NumberUtil;
import com.jianq.manager.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @author Leo on 2017/9/6
 */
@Service
public class DocumentServiceImpl implements IDocumentService {
    private static final Log LOG = LogFactory.getLog(DocumentServiceImpl.class);

    @Autowired
    private DocumentDao documentDao;
    @Autowired
    private IDocumentLogService documentLogService;

    @Value("${file.hongxing.path}")
    private String fileSharePath;


    @Override
    public Integer deleteByPrimaryKey(Integer id) {
        Integer count = null;
        if (id != null) {
            try {
                count = documentDao.deleteByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 deleteByPrimaryKey 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public Integer insertDocument(Document document) {
        Integer documentId = null;
        if (document != null) {
            try {
                documentDao.insertSelective(document);
                documentId = document.getId();
                if (NumberUtil.isGreaterThan0(documentId)) {
                    String remark = null;
                    if (document.getType() == 0) {
                        remark = "新增文件:";
                    } else {
                        remark = "新增文件夹:";
                    }
                    documentLogService.insertDocumentLog(new DocumentLog(documentId, remark + document.getName(), document.getCreater()));
                }
            } catch (Exception e) {
                LOG.error("方法 insertDocument 异常,{}", e);
            }
        }
        return documentId;
    }

    @Override
    public Document selectByPrimaryKey(Integer id) {
        Document document = null;
        if (id != null) {
            try {
                document = documentDao.selectByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 selectByPrimaryKey 异常,{}", e);
            }
        }
        return document;
    }

    @Override
    public Integer updateDocument(Document document) {
        Integer count = null;
        if (document != null) {
            try {
                count = documentDao.updateByPrimaryKeySelective(document);
            } catch (Exception e) {
                LOG.error("方法 updateDocument 异常,{}", e);
            }
        }
        return count;
    }


    @Override
    public List<Document> listOwnDocument(Document document) {
        List<Document> documentList = null;
        if (document != null) {
            try {
                documentList = documentDao.listOwnDocument(document);
            } catch (Exception e) {
                LOG.error("方法 listOwnDocument 异常,{}", e);
            }
        }
        return documentList;
    }

    @Override
    public List<Document> listByFid(Integer fid) {
        List<Document> documentList = null;
        if (null != fid) {
            try {
                documentList = documentDao.listByFid(fid);
            } catch (Exception e) {
                LOG.error("方法 listByFid 异常,{}", e);
            }
        }
        return documentList;
    }

    @Override
    public Integer saveDir(String name, Integer fid, Integer creater) {
        Integer documentId = null;
        if (StringUtils.isNotEmpty(name)) {
            Document fDocument = this.selectByPrimaryKey(fid);
            if (0 == fid || null != fDocument) {
                //父类的层级
                Integer fLevel = null;
                if (0 == fid) {
                    fLevel = 0;
                } else {
                    fLevel = fDocument.getLevel();
                }
                Document document = new Document();
                document.setFid(fid);
                document.setLevel(1 + fLevel);
                document.setName(name);
                document.setCreater(creater);
                document.setType(Document.TYPE_DIR);
                documentId = this.insertDocument(document);
            }
        }
        return documentId;
    }

    @Override
    public Integer saveFile(MultipartFile file, Integer fid, Integer creater) {
        Integer documentId = null;
        if (!file.isEmpty() && fid != null) {
            //如果没用创建文件夹
            File dir = new File(fileSharePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Document fDocument = this.selectByPrimaryKey(fid);
            if (fDocument != null) {
                String extName = "";
                String originalFilename = file.getOriginalFilename();
                if (originalFilename.lastIndexOf(".") != -1) {
                    extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                }
                String newFileName = UUID.randomUUID().toString() + (StringUtil.isEmpty(extName) ? "" : "." + extName);
                try {
                    InputStream inputStream = file.getInputStream();
                    FileOutputStream outputStream = new FileOutputStream(new File(fileSharePath, newFileName));
                    IOUtils.copy(file.getInputStream(), outputStream);
                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();
                } catch (IOException ex) {
                    LOG.error("方法 saveFile 异常,{}", ex);
                    ;
                }
                //插入记录
                Integer fLevel = fDocument.getLevel();
                Document document = new Document();
                document.setName(originalFilename);
                document.setFid(fid);
                document.setLevel(1 + (fLevel == null ? 0 : fLevel));
                document.setType(Document.TYPE_DOC);
                document.setCreater(creater);
                document.setContexttype(file.getContentType());
                document.setSize(file.getSize());
                document.setUuidname(newFileName);
                document.setSuffix(extName);
                documentId = this.insertDocument(document);
            }
        }
        return documentId;
    }

    /**
     * 删除文件/文件夹
     *
     * @param documentId
     * @return
     */
    public Integer deleteDocOrFile(Integer documentId) {
        Integer count = 0;
        if (documentId != null) {
            Document document = this.selectByPrimaryKey(documentId);
            if (document != null && document.getType() == 1) {
                List<Document> sonDocumentList = documentDao.listByFid(documentId);
                if (ListUtil.isNotEmpty(sonDocumentList)) {
                    for (Document sonDocument : sonDocumentList) {
                        Integer sonDocumentId = sonDocument.getId();
                        Integer sonDocumentType = sonDocument.getType();
                        if (sonDocumentType == 0) {
                            //删除文件
                            Boolean flag = this.deleteDocumentFile(sonDocument.getId());
                            if (flag) {
                                count++;
                            }
                        } else if (sonDocumentType == 1) {
                            //删除文件夹
                            this.deleteDoc(sonDocumentId);
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * 删除文件夹
     *
     * @param documentId
     * @return
     */
    public Integer deleteDoc(Integer documentId) {
        Integer count = 0;
        if (documentId != null) {
            Document document = this.selectByPrimaryKey(documentId);
            if (document != null && document.getType() == 1) {
                Integer deleteCount = this.deleteByPrimaryKey(documentId);
                if (NumberUtil.isGreaterThan0(deleteCount)) {
                    //删除子文件/夹
                    List<Document> sonDocumentList = documentDao.listByFid(documentId);
                    for (Document sonDocument : sonDocumentList) {
                        this.deleteDocOrFile(sonDocument.getId());
                    }
                }
                this.deleteByPrimaryKey(documentId);
            }
        }
        return count;
    }

    /**
     * 删除文件
     *
     * @param documentId
     * @return
     */
    public Boolean deleteDocumentFile(Integer documentId) {
        Boolean flag = false;
        if (documentId != null) {
            Document document = this.selectByPrimaryKey(documentId);
            if (document != null) {
                String uuidname = document.getUuidname();
                File file = new File(fileSharePath, uuidname);
                if (file.exists()) {
                    try {
                        flag = file.delete();
                        this.deleteByPrimaryKey(documentId);
                    } catch (Exception e) {
                        LOG.error("方法 deleteDocumentFile 异常,{}", e);
                    }
                }
            }
        }
        return flag;
    }
}
