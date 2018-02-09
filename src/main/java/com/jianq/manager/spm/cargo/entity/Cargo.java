package com.jianq.manager.spm.cargo.entity;

import java.util.Date;

public class Cargo {
    private Integer id;

    private String name;

    private String slogan;

    private String reamrk;

    private Integer type;

    private String typeName;

    private String pic;

    private String pkg;

    private Integer creator;

    private Date createTime;

    private String cargoUrl;

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

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan == null ? null : slogan.trim();
    }

    public String getReamrk() {
        return reamrk;
    }

    public void setReamrk(String reamrk) {
        this.reamrk = reamrk == null ? null : reamrk.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg == null ? null : pkg.trim();
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCargoUrl() {
        return cargoUrl;
    }

    public void setCargoUrl(String cargoUrl) {
        this.cargoUrl = cargoUrl;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slogan='" + slogan + '\'' +
                ", reamrk='" + reamrk + '\'' +
                ", type=" + type +
                ", typeName='" + typeName + '\'' +
                ", pic='" + pic + '\'' +
                ", pkg='" + pkg + '\'' +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", cargoUrl='" + cargoUrl + '\'' +
                '}';
    }
}