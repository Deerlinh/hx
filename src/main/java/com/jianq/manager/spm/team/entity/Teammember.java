package com.jianq.manager.spm.team.entity;

import java.util.Date;

public class Teammember {
    private Integer id;

    private Integer userId;

    private Integer roleId;

    private Integer teamId;

    private Integer robotType;

    private Integer creator;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getRobotType() {
        return robotType;
    }

    public void setRobotType(Integer robotType) {
        this.robotType = robotType;
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

    @Override
    public String toString() {
        return "Teammember{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                ", teamId=" + teamId +
                ", robotType=" + robotType +
                ", creator=" + creator +
                ", createTime=" + createTime +
                '}';
    }
}