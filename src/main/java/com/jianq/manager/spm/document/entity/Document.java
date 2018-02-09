package com.jianq.manager.spm.document.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Document {
    public static final Integer TYPE_DIR = 1;
    public static final Integer TYPE_DOC = 0;
    /**
     * id
     */
    private Integer id;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件uuid名称
     */
    private String uuidname;
    /**
     * 文件大小(/kb)
     */
    private Long size;
    /**
     * 级别
     */
    private Integer level;
    /**
     * 类型:0文件1文件夹
     */
    private Integer type;
    /**
     * 父id
     */
    private Integer fid;
    /**
     * 后缀
     */
    private String suffix;
    /**
     * 文件标识
     */
    private String contexttype;
    /**
     * 创建人
     */
    private Integer creater;
    /**
     * 创建人名称
     */
    private String creatorName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUuidname() {
        return uuidname;
    }

    public void setUuidname(String uuidname) {
        this.uuidname = uuidname == null ? null : uuidname.trim();
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    public String getContexttype() {
        return contexttype;
    }

    public void setContexttype(String contexttype) {
        this.contexttype = contexttype == null ? null : contexttype.trim();
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

    public String getCreatorName() {
        return creatorName;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uuidname='" + uuidname + '\'' +
                ", size=" + size +
                ", level=" + level +
                ", type=" + type +
                ", fid=" + fid +
                ", suffix='" + suffix + '\'' +
                ", contexttype='" + contexttype + '\'' +
                ", creater=" + creater +
                ", creatorName=" + creatorName +
                ", createtime=" + createtime +
                '}';
    }
}