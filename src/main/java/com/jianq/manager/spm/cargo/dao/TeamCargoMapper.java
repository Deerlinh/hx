package com.jianq.manager.spm.cargo.dao;

import com.jianq.manager.spm.cargo.entity.TeamCargo;

public interface TeamCargoMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TeamCargo record);

    TeamCargo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeamCargo record);

    TeamCargo selectByCargoId(Integer id);
}