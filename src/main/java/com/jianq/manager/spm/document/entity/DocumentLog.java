package com.jianq.manager.spm.document.entity;

import java.util.Date;

public class DocumentLog {
    public DocumentLog(Integer documentId, String remark, Integer creater) {
        this.documentId = documentId;
        this.remark = remark;
        this.creater = creater;
    }

    /**
     * 主键
     */
    private Integer id;
    /**
     * 文件id
     */
    private Integer documentId;
    /**
     * 描述
     */
    private String remark;
    /**
     * 创建人
     */
    private Integer creater;
    /**
     * 创建时间
     */
    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}