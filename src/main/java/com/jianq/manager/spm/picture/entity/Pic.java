package com.jianq.manager.spm.picture.entity;

import java.util.Date;

public class Pic {
    private Integer id;

    private String path;

    private String fileName;

    private Date createTime;

    public Pic() {
    }

    public Pic(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Pic{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", fileName='" + fileName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}