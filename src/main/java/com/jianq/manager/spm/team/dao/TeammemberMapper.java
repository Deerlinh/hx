package com.jianq.manager.spm.team.dao;

import com.jianq.manager.spm.team.entity.Teammember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeammemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Teammember record);

    Teammember selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Teammember record);

    Teammember selectByRoleId(Integer roleId);

    Teammember getNextRoleTeammember(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

    Teammember selectByTeamIdAndUserId(@Param("teamId") Integer teamId, @Param("userId") Integer userId);

    List<Teammember> selectByTeamIdAndRoleId(@Param("teamId") Integer teamId, @Param("roleId") Integer roleId);
}