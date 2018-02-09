package com.jianq.manager.spm.timer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jianq.manager.spm.timer.entity.TimerManager;

public interface TimerManagerDao {
	int deleteByPrimaryKey(Integer id);

	int insert(TimerManager record);

	int insertSelective(TimerManager record);

	TimerManager selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(TimerManager record);

	int updateByPrimaryKey(TimerManager record);

	List<TimerManager> list(@Param("name") String name);
}