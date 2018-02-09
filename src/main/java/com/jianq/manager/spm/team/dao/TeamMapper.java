package com.jianq.manager.spm.team.dao;


import com.jianq.manager.spm.team.entity.Team;

public interface TeamMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Team record);

    Team selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Team team);
}